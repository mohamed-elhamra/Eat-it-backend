package com.melhamra.eatItBackend.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "products")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String publicId;
    @Column(length = 20, nullable = false)
    private String name;
    @Lob
    @Column(nullable = false, length = 512)
    private String description;
    @Column(nullable = false)
    private double price;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private ImageEntity image;

    @OneToMany(mappedBy = "product")
    private List<OrderProductEntity> orderProducts;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

}
