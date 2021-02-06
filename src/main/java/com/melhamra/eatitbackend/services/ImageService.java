package com.melhamra.eatitbackend.services;

import com.melhamra.eatitbackend.dtos.ImageDto;
import com.melhamra.eatitbackend.responses.ImageResponse;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {

    void init();

    ImageDto save(MultipartFile file);

    Resource load(String imageId);

    List<ImageResponse> loadAll();

    void delete(String publicId);

}
