package com.oceandiary.api.file.repository;

import com.oceandiary.api.file.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {

}
