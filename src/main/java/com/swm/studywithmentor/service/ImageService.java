package com.swm.studywithmentor.service;

import com.swm.studywithmentor.model.dto.create.ImageDto;

import java.util.List;
import java.util.UUID;

public interface ImageService {
    ImageDto getImageById(UUID id);

    List<ImageDto> createImages(List<ImageDto> images);

    void deleteImage(UUID id);
}
