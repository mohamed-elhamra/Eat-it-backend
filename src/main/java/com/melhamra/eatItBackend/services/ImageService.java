package com.melhamra.eatItBackend.services;

import com.melhamra.eatItBackend.dtos.ImageDto;
import com.melhamra.eatItBackend.responses.ImageResponse;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {

    void init();

    ImageDto save(MultipartFile file);

    Resource load(String imageId);

    List<ImageResponse> loadAll();

}
