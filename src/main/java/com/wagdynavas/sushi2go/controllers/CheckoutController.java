package com.wagdynavas.sushi2go.controllers;

import com.wagdynavas.sushi2go.model.Order;
import com.wagdynavas.sushi2go.model.Product;
import com.wagdynavas.sushi2go.util.SessionUtil;
import com.wagdynavas.sushi2go.util.type.OrderTypes;
import com.wagdynavas.sushi2go.util.type.RestaurantBranch;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class CheckoutController {

    @GetMapping("/checkout")
    public ModelAndView checkout(HttpServletRequest request) {
        Order checkoutOrder  = (Order) request.getSession().getAttribute("order");
        List<Integer> productQuantitySelector = IntStream.rangeClosed(1, 50).boxed().collect(Collectors.toList());
        ModelAndView view = new ModelAndView();

        if (checkoutOrder == null) {
            checkoutOrder = new Order();
        }


        checkoutOrder.setStatus(OrderTypes.NEW.toString());
        checkoutOrder.setOrderDate(LocalDate.now());
        view.addObject("cartQuantity", SessionUtil.getCartQuantity(request));
        view.addObject("checkoutOrder", checkoutOrder);
        view.addObject("restaurantBranch", RestaurantBranch.values());
        view.addObject("productQuantitySelector", productQuantitySelector);

        view.setViewName("checkout/checkout");

        return view;
    }

    /**
     *
     * @param productId ID of the <code>Product</code> that will be removed from the Checkout list
     * @param request used to get currently <code>Order</code> Object to the Session
     *
     *
     * @return view to checkout.html
     */
    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long productId, HttpServletRequest request) {
        Order checkoutOrder  = (Order) request.getSession().getAttribute("order");
        ModelAndView view = new ModelAndView();

         List<Product> products = checkoutOrder.getProducts().stream().filter(p -> p.getId() != productId).collect(Collectors.toList());



         checkoutOrder.setProducts(products);

        request.setAttribute("order", checkoutOrder);
        view.addObject("order", checkoutOrder);
        view.setViewName("redirect:/checkout");
        return view;
    }
}
