package com.swm.studywithmentor.service.impl;

import com.swm.studywithmentor.model.dto.create.ImageDto;
import com.swm.studywithmentor.model.entity.Image;
import com.swm.studywithmentor.model.entity.course.Course;
import com.swm.studywithmentor.model.entity.user.Mentor;
import com.swm.studywithmentor.model.entity.user.User;
import com.swm.studywithmentor.model.exception.*;
import com.swm.studywithmentor.repository.CourseRepository;
import com.swm.studywithmentor.repository.ImageRepository;
import com.swm.studywithmentor.repository.UserRepository;
import com.swm.studywithmentor.service.ImageService;
import com.swm.studywithmentor.service.UserService;
import com.swm.studywithmentor.util.ApplicationMapper;
import com.swm.studywithmentor.util.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final CourseRepository courseRepository;
    private final UserService userService;
    private final UserRepository userRepository;
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
    public ImageDto getCourseImage(UUID courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException(Course.class, courseId));

        Image image = course.getImage();
        return mapper.toDto(image);
    }

    @Override
    public ImageDto getProfileImage() {
        User user = userService.getCurrentUser();
        Image image = user.getProfileImage();
        return mapper.toDto(image);
    }

    @Override
    public ImageDto getMentorImage(UUID mentoId) {
        User user = userRepository.findById(mentoId)
                .orElseThrow(() -> new NotFoundException(Mentor.class, mentoId));
        return mapper.toDto(user.getProfileImage());
    }

    @Override
    public ImageDto updateCourseImage(UUID courseId, ImageDto imageDto) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException(Course.class, courseId));
        if (!Utils.isCourseOpenForEdit(course)) {
            throw new ConflictException(Course.class, ActionConflict.UPDATE, "Cannot update course when course status is " + course.getStatus(), course.getId());
        }
        User user = userService.getCurrentUser();
        if (ObjectUtils.notEqual(user.getId(), course.getMentor().getId())) {
            throw new ForbiddenException(Course.class, ActionConflict.UPDATE, "User does not own this course", user.getId());
        }
        imageDto.setId(null);
        Image image = course.getImage();
        mapper.toEntity(imageDto, image);
        image = imageRepository.save(image);
        return mapper.toDto(image);
    }

    @Override
    public ImageDto updateProfileImage(ImageDto imageDto) {
        User user = userService.getCurrentUser();
        Image image = user.getProfileImage();
        mapper.toEntity(imageDto, image);
        image = imageRepository.save(image);
        return mapper.toDto(image);
    }
}
