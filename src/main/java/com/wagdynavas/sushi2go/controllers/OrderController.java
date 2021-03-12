package com.wagdynavas.sushi2go.controllers;

import com.wagdynavas.sushi2go.exceptions.OrderNotFondException;
import com.wagdynavas.sushi2go.model.Order;
import com.wagdynavas.sushi2go.model.OrderItem;
import com.wagdynavas.sushi2go.model.User;
import com.wagdynavas.sushi2go.service.OrderItemService;
import com.wagdynavas.sushi2go.service.OrderService;
import com.wagdynavas.sushi2go.service.UserService;
import lombok.RequiredArgsConstructor;
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
        orderService.acceptOrder(orderId);
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
