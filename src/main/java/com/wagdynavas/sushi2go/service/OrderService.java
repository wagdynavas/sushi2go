package com.wagdynavas.sushi2go.service;

import com.wagdynavas.sushi2go.model.Order;
import com.wagdynavas.sushi2go.repository.OrderRepository;
import com.wagdynavas.sushi2go.util.type.OrderTypes;
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

    /**
     * Change Order status to ACCEPTED
     *
     * @param orderId Order to be modified.
     */
    public void acceptOrder(Long orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setStatus(OrderTypes.ACCEPTED.getValue());

            orderRepository.save(order);
        }
    }
}
