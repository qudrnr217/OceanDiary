package com.oceandiary.api.file.entity;

import com.oceandiary.api.common.entity.BaseEntity;
import com.oceandiary.api.file.dto.SavedFile;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "image")
public class Image extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    private String name;

    @Column(name = "origin_name")
    private String originName;

    private String extension;

    private String url;

    private Long size;

    private Integer width;

    private Integer height;

//    @OneToOne(mappedBy = "image")
//    private Room room;

    public static Image create(SavedFile request){
        return Image.builder()
                .name(request.getName())
                .originName(request.getOriginName())
                .extension(request.getExtension())
                .url(request.getPublicUrl())
                .size(request.getSize())
                .width(request.getWidth())
                .height(request.getHeight())
                .build();
    }
}