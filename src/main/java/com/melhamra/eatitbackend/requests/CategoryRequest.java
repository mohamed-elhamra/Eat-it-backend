package com.melhamra.eatitbackend.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {

    @NotBlank(message = "Category name should not be empty")
    @Size(min = 2, max = 20, message = "Category name should be between 2 and 20 character")
    private String name;

}
