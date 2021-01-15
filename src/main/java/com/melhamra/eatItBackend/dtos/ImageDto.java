package com.melhamra.eatItBackend.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageDto {

    private Long id;
    private String publicId;
    private String name;
    private String url;

}
