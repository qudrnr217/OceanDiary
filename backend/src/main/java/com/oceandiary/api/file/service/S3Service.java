package com.oceandiary.api.file.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.oceandiary.api.file.dto.S3Component;
import com.oceandiary.api.file.dto.SavedFile;
import com.oceandiary.api.file.exception.FileExtensionException;
import com.oceandiary.api.file.exception.S3Exception;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3Service {
    private final AmazonS3Client s3Client;
    private final S3Component component;
    @Value("${tmp.file.prefix}")
    private String fileNamePrefix;

    public SavedFile upload(MultipartFile file) {
        String originalName = file.getOriginalFilename();
        String extension = getFileExtension(originalName);
        String s3FileName = createFileName(extension);
        String publicUrl = component.getPublicUrl() + "/" + s3FileName;

        boolean isImage = isImage(extension);
        Integer width = null;
        Integer height = null;
        try {
            File uploadFile = convert(file)
                    .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File로 전환이 실패했습니다."));
            putS3(uploadFile, s3FileName);
            if (isImage) {
                BufferedImage image = ImageIO.read(file.getInputStream());
                width = image.getWidth();
                height = image.getHeight();
            }
            removeNewFile(uploadFile);
        } catch (IOException e) {
            log.info(e.getMessage());
            throw new S3Exception();
        }
        return SavedFile.create(s3FileName, extension, originalName, file.getSize(), isImage, publicUrl, width, height);
    }

    private void putS3(File uploadFile, String fileName) {
        s3Client.putObject(new PutObjectRequest(component.getBucket(), fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
    }

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("파일이 삭제되었습니다.");
        } else {
            log.info("파일이 삭제되지 못했습니다.");
        }
    }

    private boolean isImage(String extension) {
        return Optional.ofNullable(extension)
                .map(s -> s.toLowerCase().matches("png|jpeg|jpg|bmp|gif|svg"))
                .orElse(false);
    }

    private Optional<File> convert(MultipartFile file) throws IOException {
        File convertFile = new File(fileNamePrefix + file.getOriginalFilename());
        if (convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }


    private String createFileName(String extension) {
        return UUID.randomUUID().toString().concat("." + extension);
    }

    private String getFileExtension(String fileName) {
        try {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        } catch (StringIndexOutOfBoundsException e) {
            throw new FileExtensionException();
        }
    }
}
