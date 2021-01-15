package com.melhamra.eatItBackend.repositories;

import com.melhamra.eatItBackend.entities.OrderEntity;
import com.melhamra.eatItBackend.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    List<OrderEntity> findByUser(UserEntity userEntity);

}
