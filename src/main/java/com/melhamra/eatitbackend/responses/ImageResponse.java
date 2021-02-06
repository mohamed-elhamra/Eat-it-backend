package com.melhamra.eatitbackend.responses;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageResponse {

    private String publicId;
    private String name;
    private String url;

}
