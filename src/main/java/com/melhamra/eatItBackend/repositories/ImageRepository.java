package com.melhamra.eatItBackend.repositories;

import com.melhamra.eatItBackend.entities.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Long> {

    Optional<ImageEntity> findByPublicId(String imageId);

}
