package com.melhamra.eatItBackend.controllers;

import com.melhamra.eatItBackend.dtos.ImageDto;
import com.melhamra.eatItBackend.entities.ImageEntity;
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
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/images")
public class ImageController {

    @Autowired
    ImageService imageService;
    @Autowired
    ModelMapper modelMapper;

    @PostMapping("/upload")
    public ResponseEntity<ImageResponse> uploadFile(@RequestParam("image") MultipartFile image) {
        ImageResponse imageResponse = modelMapper.map(imageService.save(image), ImageResponse.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(imageResponse);
    }

    @GetMapping("/{imageId}")
    public ResponseEntity<Resource> getImage(@PathVariable String imageId) {
        Resource image = imageService.load(imageId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFilename() + "\"").body(image);
    }

    @GetMapping
    public ResponseEntity<List<ImageEntity>> getListImaged() {
        List<ImageEntity> fileInfos = imageService.loadAll().map(path -> {
            String filename = path.getFileName().toString();
            String url = MvcUriComponentsBuilder
                    .fromMethodName(ImageController.class, "getImage", path.getFileName().toString()).build().toString();

            //return new ImageEntity(filename, url);
            return new ImageEntity();
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }

}
