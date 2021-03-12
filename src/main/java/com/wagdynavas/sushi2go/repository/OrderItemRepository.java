package com.wagdynavas.sushi2go.repository;

import com.wagdynavas.sushi2go.model.Order;
import com.wagdynavas.sushi2go.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findAllOrderItemByOrderId(Order orderId);
}
