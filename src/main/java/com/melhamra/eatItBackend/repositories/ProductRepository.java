package com.melhamra.eatItBackend.repositories;

import com.melhamra.eatItBackend.entities.CategoryEntity;
import com.melhamra.eatItBackend.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    Optional<ProductEntity> findByPublicId(String publicId);

    List<ProductEntity> findByCategory(CategoryEntity categoryEntity);

}
