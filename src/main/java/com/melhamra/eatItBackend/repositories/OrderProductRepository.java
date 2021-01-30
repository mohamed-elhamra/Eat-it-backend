package com.melhamra.eatItBackend.repositories;

import com.melhamra.eatItBackend.entities.OrderEntity;
import com.melhamra.eatItBackend.entities.OrderProductEntity;
import com.melhamra.eatItBackend.responses.ProductStatisticsResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.TemporalType;
import javax.xml.crypto.Data;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProductEntity, Long> {

    List<OrderProductEntity> findByOrder(OrderEntity orderEntity);

    @Query(value = "select p.public_id as productPublicId, count(op.id) as numberOfCommand, COALESCE(sum(op.quantity), 0) as quantity " +
            "from categories as c " +
            "left join products p on c.id = p.category_id " +
            "left join orders_products op on p.id = op.product_id " +
            "left join orders o on o.id = op.order_id " +
            "where c.public_id = :categoryPublicId " +
            "and o.date >= :startingDate and o.date <= :endingDate  " +
            "group by productPublicId", nativeQuery = true)
    List<ProductStatisticsResponse> getProductStatistics(@Param("categoryPublicId") String categoryPublicId,
                                                         @Param("startingDate") Instant startingDate,
                                                         @Param("endingDate") Instant endingDate);

}
