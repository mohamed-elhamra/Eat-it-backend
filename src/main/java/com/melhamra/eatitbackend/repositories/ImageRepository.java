package com.melhamra.eatitbackend.repositories;

import com.melhamra.eatitbackend.entities.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Long> {

    Optional<ImageEntity> findByPublicId(String imageId);

    void deleteByPublicId(String imageId);

}
