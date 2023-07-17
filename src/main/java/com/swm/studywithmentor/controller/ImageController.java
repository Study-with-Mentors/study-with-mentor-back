package com.swm.studywithmentor.controller;

import com.swm.studywithmentor.model.dto.create.ImageDto;
import com.swm.studywithmentor.model.exception.ApplicationException;
import com.swm.studywithmentor.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @GetMapping("{id}")
    public ResponseEntity<ImageDto> GetImageById(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(imageService.getImageById(id));
        } catch (ApplicationException applicationException) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<List<ImageDto>> AddImages(@RequestBody ArrayList<ImageDto> images) {
        try {
            return ResponseEntity.ok(imageService.createImages(images));
        } catch (ApplicationException ex) {
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<ImageDto> getImageById(@PathVariable UUID id) {
        return ResponseEntity.ok(imageService.getImageById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> DeleteImage(@PathVariable UUID id) {
        imageService.deleteImage(id);
        return ResponseEntity.accepted().build();
    }
}
