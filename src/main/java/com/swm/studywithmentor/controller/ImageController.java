package com.swm.studywithmentor.controller;

import com.swm.studywithmentor.model.dto.create.ImageDto;
import com.swm.studywithmentor.model.entity.Image;
import com.swm.studywithmentor.model.exception.ApplicationException;
import com.swm.studywithmentor.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @GetMapping("{id}")
    public ResponseEntity<Image> GetImageById(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(imageService.GetImageById(id));
        } catch (ApplicationException applicationException) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<List<ImageDto>> AddImages(@RequestBody ArrayList<ImageDto> images) {
        try {
            return ResponseEntity.ok(imageService.CreateImages(images));
        } catch (ApplicationException ex) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
