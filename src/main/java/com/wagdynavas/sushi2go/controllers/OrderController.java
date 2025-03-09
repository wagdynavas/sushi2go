package com.wagdynavas.sushi2go.controllers;

import com.stripe.Stripe;
import com.wagdynavas.sushi2go.exceptions.OrderNotFondException;
import com.wagdynavas.sushi2go.model.Order;
import com.wagdynavas.sushi2go.model.OrderItem;
import com.wagdynavas.sushi2go.model.User;
import com.wagdynavas.sushi2go.service.OrderItemService;
import com.wagdynavas.sushi2go.service.OrderService;
import com.wagdynavas.sushi2go.service.UserService;
import com.wagdynavas.sushi2go.util.type.OrderTypes;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;
    private final OrderItemService orderItemService;

    @Value("${stripe.test.key}")
    private  String stripePublicKey;

    @Value("${stripe.endpoint.secret}")
    private String endpointSecret;

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripePublicKey;
    }

    @GetMapping("/my-orders")
    public ModelAndView getMyOrders(Principal principal) {
        String username = principal.getName();
        User user = userService.getByUsername(username);
        String restaurantBranch = user.getUserRestaurantBranch();
        List<Order> payedOrders = orderService.getPayedOrdersByRestaurantBranch(restaurantBranch);
        if(payedOrders == null) {
            payedOrders = new ArrayList<>();
        }
        ModelAndView view = new ModelAndView();
        view.addObject("orders", payedOrders);
        view.setViewName("/order/my-orders");
        return view;
    }


    @GetMapping("/accept/{orderId}")
    public ModelAndView acceptOrder(@PathVariable Long orderId) {
        ModelAndView view = new ModelAndView();
        orderService.changeOrderStatus(orderId, OrderTypes.ACCEPTED);
        view.setViewName("redirect:/orders/my-orders");
        return view;
    }

    @GetMapping("/delivered/{orderId}")
    public ModelAndView orderDelivered(@PathVariable(name = "orderId") Long orderId) {
        ModelAndView view = new ModelAndView();
        orderService.changeOrderStatus(orderId, OrderTypes.DELIVERED);
        view.setViewName("redirect:/orders/my-orders");
        return view;
    }

    @GetMapping("/ajax-orders")
    @ResponseBody
    public List<Order> getMyOrdersAjax(Principal principal) {
        String username = principal.getName();
        User user = userService.getByUsername(username);
        String restaurantBranch = user.getUserRestaurantBranch();
        List<Order> payedOrders = orderService.getPayedOrdersByRestaurantBranch(restaurantBranch);
        if(payedOrders == null) {
            payedOrders = new ArrayList<>();
        }

        return payedOrders;
    }

    /**
     * Get All OrderItems by Order orderId
     *
     * @param orderId id of the Order
     * @return  view with All OrderItems for orderId and a DateTimeFormatter for orderDate
     */
    @GetMapping("/order-item/{orderId}")
    public ModelAndView getOrderItems(@PathVariable("orderId") Long orderId) {
        Optional <Order> optionalOrder = orderService.getOrderById(orderId);
        ModelAndView view = new ModelAndView();

        Order order = optionalOrder.orElseThrow(OrderNotFondException::new);
        List<OrderItem> orderItems = orderItemService.getOrderItemsByOrderId(order);


        view.addObject("order", order);
        view.addObject("orderItems", orderItems);
        view.addObject("dateTimeFormatter", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        view.setViewName("/order/order-details");
        return view;
    }
}
