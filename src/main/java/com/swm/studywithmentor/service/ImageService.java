package com.swm.studywithmentor.service;

import com.swm.studywithmentor.model.dto.create.ImageDto;
import com.swm.studywithmentor.model.entity.Image;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface ImageService {
    Image GetImageById(UUID id);

    List<ImageDto> CreateImages(List<ImageDto> images);
}
