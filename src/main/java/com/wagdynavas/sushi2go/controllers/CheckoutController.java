package com.wagdynavas.sushi2go.controllers;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.wagdynavas.sushi2go.model.Order;
import com.wagdynavas.sushi2go.model.Product;
import com.wagdynavas.sushi2go.service.CheckoutService;
import com.wagdynavas.sushi2go.util.NumberUtil;
import com.wagdynavas.sushi2go.util.SessionUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@Log4j2
public class CheckoutController {

    @Value("${stripe.test.key}")
    private  String stripePublicKey;

    private final BigDecimal taxPercentage = new BigDecimal(13);

    private CheckoutService checkoutService;


    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @GetMapping("/checkout")
    public ModelAndView checkout(HttpServletRequest request) {
        Order checkoutOrder  = (Order) request.getSession().getAttribute("order");
        if (checkoutOrder == null) {
            checkoutOrder = new Order();
        }
        String tipParameter =  request.getParameter("tipPercentage");
        if(tipParameter == null) tipParameter = "15";
        BigDecimal tipPercentage = new BigDecimal(tipParameter);

        BigDecimal subTotal = checkoutService.calculateTotalAmountFromOrder(checkoutOrder);
        checkoutOrder.setSubTotalAmount(subTotal);

        BigDecimal taxes = NumberUtil.calculatePercentage(taxPercentage, subTotal);
        checkoutOrder.setTax(taxes);

        BigDecimal tip = NumberUtil.calculatePercentage(tipPercentage, subTotal);
        checkoutOrder.setTip(tip);
        checkoutOrder.setTipPercentage(tipPercentage);

        BigDecimal totalAmount = subTotal.add(taxes).add(tip);
        checkoutOrder.setTotalAmount(totalAmount);

        ModelAndView view = new ModelAndView();
        view.setViewName("checkout/checkout");
        view.addObject("tip", tip);
        view.addObject("tax", taxes);
        view.addObject("subTotal", subTotal);
        view.addObject("totalAmount", subTotal);
        view.addObject("tipPercentage", tipPercentage);//used to keep an Active class on btn
        view.addObject("checkoutOrder", checkoutOrder);
        view.addObject("restaurantBranch", checkoutOrder.getRestaurantBranch());
        view.addObject("cartQuantity", SessionUtil.getCartQuantity(request));//USed to keep track of how many item are in the cart
        return view;
    }




    @PostMapping("/create-checkout-session")
    @ResponseBody
    public Map<String, String> create( HttpServletRequest request) throws StripeException {
        log.debug("Creating Stripe session.");
        Order checkoutOrder  = (Order) request.getSession().getAttribute("order");

        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:8080/checkout/succeeded")
                .setCancelUrl("https://example.com/cancel")
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(
                                SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("cad")
                                .setUnitAmount(checkoutService.convertOrderTotalAmountToStripeUnitAmount(checkoutOrder))
                                        .setProductData(
                                                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                .setName("Sushi2go")
                                                .build()
                                        ).build()
                        ).build()
                ).build();

        Session session = Session.create(params);
        Map<String, String> responseData = new HashMap<>();
        responseData.put("id", session.getId());
        log.debug("Stripe session created: " + session.getId());
        

        return responseData;
    }

    /**
     *
     * @param productId ID of the <code>Product</code> that will be removed from the Checkout list
     * @param request used to get currently <code>Order</code> Object to the Session
     *
     *
     * @return view to checkout.html
     */
    @GetMapping("/checkout/delete/{id}")
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


    @GetMapping("/checkout/succeeded")
    public ModelAndView checkoutSucceeded() {
       return new ModelAndView("/checkout/succeeded");
    }

    @GetMapping("/checkout/canceled")
    public ModelAndView checkoutCanceled() {
        return  new ModelAndView("/checkout/canceled");
    }


    @PostMapping("/checkout/save-and-continue")
    public ModelAndView saveOrder(Order checkoutOrder, HttpServletRequest request)  {
        log.debug("Saving order information");
        Order sessionOrder  = (Order) request.getSession().getAttribute("order");

        sessionOrder.setCustomer(checkoutOrder.getCustomer());
        ModelAndView view = new ModelAndView();
        view.setViewName("redirect:/checkout");
        return view;
    }

    @GetMapping("/checkout/location/{restaurantLocation}")
    public ModelAndView changeLocation(@PathVariable String restaurantLocation, HttpServletRequest request) {
        ModelAndView view = new ModelAndView();
        Order sessionOrder  = (Order) request.getSession().getAttribute("order");
        if (sessionOrder == null) {
            sessionOrder = new Order();
        }
        sessionOrder.setRestaurantBranch(restaurantLocation);



        request.setAttribute("restaurantBranch", restaurantLocation);
        request.setAttribute("order", sessionOrder);
        view.addObject("restaurantBranch", restaurantLocation);
        view.addObject("order", sessionOrder);
        view.setViewName("redirect:/checkout");
        return view;
    }



}
