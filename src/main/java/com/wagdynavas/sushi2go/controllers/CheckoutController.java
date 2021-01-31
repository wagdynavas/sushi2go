package com.wagdynavas.sushi2go.controllers;

import com.wagdynavas.sushi2go.model.Order;
import com.wagdynavas.sushi2go.util.type.OrderTypes;
import com.wagdynavas.sushi2go.util.type.RestaurantBranch;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

@Controller
public class CheckoutController {

    @GetMapping("/checkout")
    public ModelAndView checkout(HttpServletRequest servlet) {
        Order checkoutOrder  = (Order) servlet.getSession().getAttribute("order");
        ModelAndView view = new ModelAndView();

        if (checkoutOrder == null) {
            checkoutOrder = new Order();
        }

        if (checkoutOrder != null) {


        }

        checkoutOrder.setStatus(OrderTypes.NEW.toString());
        checkoutOrder.setOrderDate(LocalDate.now());
        view.addObject("checkoutOrder", checkoutOrder);
        view.addObject("restaurantBranch", RestaurantBranch.values());

        view.setViewName("checkout/checkout");

        return view;
    }
}
