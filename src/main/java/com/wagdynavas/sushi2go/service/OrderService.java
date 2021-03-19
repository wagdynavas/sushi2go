package com.wagdynavas.sushi2go.service;

import com.stripe.model.checkout.Session;
import com.wagdynavas.sushi2go.model.Order;
import com.wagdynavas.sushi2go.repository.OrderRepository;
import com.wagdynavas.sushi2go.util.type.OrderTypes;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
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
     * Update Order status
     *
     * @param orderType new status
     * @param orderId Order to be modified.
     */
    public void changeOrderStatus(Long orderId, OrderTypes orderType) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setStatus(orderType.getValue());

            orderRepository.save(order);
        }
    }

    /**
     * Change Order status for each customer payment confirmed
     * @param session used to get customer info
     */
    public  void fulfillOrder(Session session) {

        String customerEmail = session.getCustomerDetails().getEmail();
        List<Order> orders = orderRepository.findAllByStatusAndCustomerCustomerEmail(OrderTypes.NEW.getValue(), customerEmail);

        for (Order order : orders) {
            log.debug("Fulfilling order [ ", order.getOrderId() + " ]");
            order.setStatus(OrderTypes.PAID.getValue());

            orderRepository.save(order);
        }

    }
}
