package com.melhamra.eatItBackend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "categories")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String publicId;
    @Column(length = 20, nullable = false)
    private String name;

    @OneToMany(mappedBy = "category")
    private List<ProductEntity> products;

}
