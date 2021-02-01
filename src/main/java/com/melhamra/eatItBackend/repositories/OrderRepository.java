package com.melhamra.eatItBackend.repositories;

import com.melhamra.eatItBackend.entities.OrderEntity;
import com.melhamra.eatItBackend.entities.UserEntity;
import com.melhamra.eatItBackend.responses.UserByOrderStatisticsResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    List<OrderEntity> findByUser(UserEntity userEntity);

    Optional<OrderEntity> findByPublicId(String publicId);

    @Query("select u.publicId as clientPublicId, u.orders.size as numberOfOrders " +
            "from users as u " +
            "order by numberOfOrders desc")
    List<UserByOrderStatisticsResponse> getNumberOfOrdersByUserStatistics(Pageable pageable);

}
