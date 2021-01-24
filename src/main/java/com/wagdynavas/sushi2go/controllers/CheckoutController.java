package com.wagdynavas.sushi2go.controllers;

import com.wagdynavas.sushi2go.model.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CheckoutController {

    @GetMapping("/checkout")
    public ModelAndView checkout(HttpServletRequest servlet) {
        Order checkoutOrder  = (Order) servlet.getSession().getAttribute("order");
        ModelAndView view = new ModelAndView();

        if (checkoutOrder == null) {
            checkoutOrder = new Order();
            checkoutOrder.setStatus();
            view.addObject("checkoutOrder", checkoutOrder);
        } else {
            view.addObject(checkoutOrder);
        }

        view.setViewName("checkout/checkout");

        return view;
    }
}
