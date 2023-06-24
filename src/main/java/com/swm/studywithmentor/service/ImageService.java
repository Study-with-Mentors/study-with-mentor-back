package com.swm.studywithmentor.service;

import com.swm.studywithmentor.model.dto.create.ImageDto;

import java.util.UUID;

public interface ImageService {
    ImageDto getImageById(UUID id);
    ImageDto getCourseImage(UUID courseId);
    ImageDto getProfileImage();
    ImageDto getMentorImage(UUID mentoId);
    ImageDto updateCourseImage(UUID courseId, ImageDto imageDto);
    ImageDto updateProfileImage(ImageDto imageDto);
}
