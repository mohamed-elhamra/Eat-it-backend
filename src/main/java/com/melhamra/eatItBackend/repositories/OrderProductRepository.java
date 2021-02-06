package com.melhamra.eatItBackend.repositories;

import com.melhamra.eatItBackend.entities.OrderEntity;
import com.melhamra.eatItBackend.entities.OrderProductEntity;
import com.melhamra.eatItBackend.responses.ProductStatisticsResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.time.Instant;
import java.util.List;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProductEntity, Long> {

    List<OrderProductEntity> findByOrder(OrderEntity orderEntity);

    @Query(value = "select p.name as productName, p.public_id as productPublicId, " +
            "COALESCE(count(op.id), 0) as numberOfCommand, COALESCE(sum(op.quantity), 0) as quantity " +
            "from categories as c " +
            "inner join products p on c.id = p.category_id " +
            "left join orders_products op on p.id = op.product_id " +
            "inner join orders o on o.id = op.order_id " +
            "where c.public_id = :categoryPublicId " +
            "and o.date >= :startingDate and o.date <= :endingDate  " +
            "group by productPublicId", nativeQuery = true)
    List<ProductStatisticsResponse> getProductStatistics(@Param("categoryPublicId") String categoryPublicId,
                                                         @Param("startingDate") Instant startingDate,
                                                         @Param("endingDate") Instant endingDate);

}
