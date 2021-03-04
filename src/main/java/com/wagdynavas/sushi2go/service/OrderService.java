package com.wagdynavas.sushi2go.service;

import com.wagdynavas.sushi2go.model.Order;
import com.wagdynavas.sushi2go.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    public Optional<Order> getOrderById(Long orderId) {
        return orderRepository.findById(orderId);
    }


    public List<Order> getOrdersByRestaurantBranch(String restaurantBranch) {

        return null;
    }

    public List<Order> getPayedOrdersByRestaurantBranch(String restaurantBranch) {
        return orderRepository.findAllPayedOrdersByRestaurantBranch(restaurantBranch);
    }
}
