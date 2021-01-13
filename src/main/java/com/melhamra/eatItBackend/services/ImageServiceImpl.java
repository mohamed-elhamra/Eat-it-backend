package com.melhamra.eatItBackend.services;

import com.melhamra.eatItBackend.controllers.ImageController;
import com.melhamra.eatItBackend.dtos.ImageDto;
import com.melhamra.eatItBackend.entities.ImageEntity;
import com.melhamra.eatItBackend.repositories.ImageRepository;
import com.melhamra.eatItBackend.responses.ImageResponse;
import com.melhamra.eatItBackend.utils.IDGenerator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ImageServiceImpl implements ImageService{

    private final Path root = Paths.get("uploads");
    private final String url = "http://192.168.1.153:8081/api/images/";

    @Autowired
    private IDGenerator idGenerator;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void init() {
        try {
            if(!Files.exists(root)){
                Files.createDirectory(root);
            }
        }catch (IOException e){
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    @Override
    public ImageDto save(MultipartFile file) {
        try {
            String imageExtension = Objects.requireNonNull(file.getOriginalFilename()).split("\\.")[1];
            List<String> extensions = Arrays.asList("JPEG", "PNG", "JPG", "jpeg", "png", "jpg");

            if(extensions.contains(imageExtension)){
                String imageID = idGenerator.generateStringId(15);
                String imageUrl = url + imageID;
                String imageName = imageID + "." + imageExtension;
                ImageEntity imageEntity = new ImageEntity(null, imageID, imageName, imageUrl);
                Files.copy(file.getInputStream(), this.root.resolve(imageName));

                return modelMapper.map(imageRepository.save(imageEntity), ImageDto.class);
            }else {
                throw new RuntimeException("File extension allowed (png, jpeg, jpg) !");
            }

        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    @Override
    public Resource load(String imageId) {
        try {
            ImageEntity imageEntity = imageRepository.findByImageId(imageId)
                    .orElseThrow(() -> new RuntimeException("Image not found with this id: " + imageId));
            Path image = root.resolve(imageEntity.getName());
            Resource resource = new UrlResource(image.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public List<ImageResponse> loadAll() {
        try {
            Stream<Path> imagesPath = Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);

            return imagesPath.map(path -> {
                String imageName = path.getFileName().toString();
                String imageId = imageName.split("\\.")[0];
                String url1 = MvcUriComponentsBuilder
                        .fromMethodName(ImageController.class, "getImage", imageId)
                        .build()
                        .toString();

                return new ImageResponse(imageId, imageName, url1);
            }).collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }
}
