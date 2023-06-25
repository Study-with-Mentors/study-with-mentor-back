package com.swm.studywithmentor.controller;

import com.swm.studywithmentor.model.dto.create.ImageDto;
import com.swm.studywithmentor.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @GetMapping("{id}")
    public ResponseEntity<ImageDto> getImageById(@PathVariable UUID id) {
        return ResponseEntity.ok(imageService.getImageById(id));
    }
}
