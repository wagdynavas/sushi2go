package com.wagdynavas.sushi2go.repository;

import com.wagdynavas.sushi2go.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o  from  Order o where o.restaurantBranch = :restaurantBranch and o.status in('PAID', 'ACCEPTED') ")
    List<Order> findAllPayedOrdersByRestaurantBranch(String restaurantBranch);

}
