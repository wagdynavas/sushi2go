package com.wagdynavas.sushi2go.controllers;

import com.wagdynavas.sushi2go.model.Order;
import com.wagdynavas.sushi2go.model.User;
import com.wagdynavas.sushi2go.service.OrderItemService;
import com.wagdynavas.sushi2go.service.OrderService;
import com.wagdynavas.sushi2go.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

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
}
