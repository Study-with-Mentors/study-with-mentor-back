package com.swm.studywithmentor.service.impl;

import com.swm.studywithmentor.model.dto.create.ImageDto;
import com.swm.studywithmentor.model.entity.Image;
import com.swm.studywithmentor.model.exception.ApplicationException;
import com.swm.studywithmentor.model.exception.ExceptionErrorCodeConstants;
import com.swm.studywithmentor.model.exception.NotFoundException;
import com.swm.studywithmentor.repository.CourseRepository;
import com.swm.studywithmentor.repository.ImageRepository;
import com.swm.studywithmentor.service.CourseService;
import com.swm.studywithmentor.service.ImageService;
import com.swm.studywithmentor.util.ApplicationMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final CourseRepository courseRepository;
    private final ApplicationMapper mapper;
    @Override
    public ImageDto getImageById(UUID id) {
        var image = imageRepository.findById(id).orElseThrow(() -> new NotFoundException(ImageServiceImpl.class, id));
        return  mapper.toDto(image);
    }

    @Override
    public List<ImageDto> createImages(List<ImageDto> images) {
        if(images == null || images.size() == 0)
            throw new ApplicationException("200", HttpStatus.ACCEPTED, "Request accepted but not do anything");
        try {
            var imagesToSave = new ArrayList<Image>();
            for(var image : images) {
                var imageToSave = mapper.toEntity(image);
                var course = courseRepository.findById(image.getCourse())
                        .orElseThrow(() -> new NotFoundException(ImageServiceImpl.class, imageToSave.getCourse().getId()));
                course.getImages().add(imageToSave);
                imageToSave.setCourse(course);
                imagesToSave.add(imageToSave);
            }
            return mapper.toDto(imageRepository.saveAll(imagesToSave));
        } catch (ApplicationException exception) {
            log.error(exception.getMessage());
            throw new ApplicationException("500", HttpStatus.INTERNAL_SERVER_ERROR, "Server error");
        }
    }

    @Override
    public void deleteImage(UUID id) {
        var image = imageRepository.findById(id).orElseThrow(() -> new NotFoundException(ImageServiceImpl.class, id));
        try {
            imageRepository.delete(image);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApplicationException("500", HttpStatus.INTERNAL_SERVER_ERROR, "Server error");
        }
    }
}
