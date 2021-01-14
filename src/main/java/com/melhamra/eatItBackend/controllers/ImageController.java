package com.melhamra.eatItBackend.controllers;

import com.melhamra.eatItBackend.responses.ImageResponse;
import com.melhamra.eatItBackend.services.ImageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/images")
public class ImageController {

    @Autowired
    ImageService imageService;
    @Autowired
    ModelMapper modelMapper;

    @PostMapping("/upload")
    public ResponseEntity<ImageResponse> uploadImage(@RequestParam("image") MultipartFile image) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(modelMapper.map(imageService.save(image), ImageResponse.class));
    }

    @GetMapping("/{imageId}")
    public ResponseEntity<Resource> getImage(@PathVariable String imageId) {
        Resource image = imageService.load(imageId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFilename() + "\"")
                .body(image);
    }

    @GetMapping
    public ResponseEntity<List<ImageResponse>> getListImages() {
        return ResponseEntity.status(HttpStatus.OK).body(imageService.loadAll());
    }

}
