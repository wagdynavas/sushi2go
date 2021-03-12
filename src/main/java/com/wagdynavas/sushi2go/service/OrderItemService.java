package com.wagdynavas.sushi2go.service;

import com.wagdynavas.sushi2go.model.Order;
import com.wagdynavas.sushi2go.model.OrderItem;
import com.wagdynavas.sushi2go.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;


    public OrderItem saveOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    public List<OrderItem> getOrderItemsByOrderId(Order orderId) {
        return orderItemRepository. findAllOrderItemByOrderId(orderId);
    }
}
