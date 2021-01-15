package com.melhamra.eatItBackend.repositories;

import com.melhamra.eatItBackend.entities.OrderEntity;
import com.melhamra.eatItBackend.entities.OrderProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProductEntity, Long> {

    List<OrderProductEntity> findByOrder(OrderEntity orderEntity);

}
