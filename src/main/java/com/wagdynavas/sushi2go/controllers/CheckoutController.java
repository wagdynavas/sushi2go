package com.wagdynavas.sushi2go.controllers;

import com.stripe.exception.StripeException;
import com.stripe.model.TaxRate;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.wagdynavas.sushi2go.model.Order;
import com.wagdynavas.sushi2go.model.Product;
import com.wagdynavas.sushi2go.service.CheckoutService;
import com.wagdynavas.sushi2go.util.NumberUtil;
import com.wagdynavas.sushi2go.util.SessionUtil;
import com.wagdynavas.sushi2go.util.type.OrderTypes;
import com.wagdynavas.sushi2go.util.type.RestaurantBranch;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class CheckoutController {

    @Value("${stripe.test.key}")
    private  String stripePublicKey;

    private CheckoutService checkoutService;


    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @GetMapping("/checkout")
    public ModelAndView checkout(HttpServletRequest request) {
        Order checkoutOrder  = (Order) request.getSession().getAttribute("order");
        final BigDecimal taxPercentage = new BigDecimal(13);
        String tipParameter =  request.getParameter("tipPercentage");
        BigDecimal tipPercentage;
        if (tipParameter == null) {
             tipPercentage = new BigDecimal(15);
        } else {
            tipPercentage = new BigDecimal(tipParameter);
        }
        List<Integer> productQuantitySelector = IntStream.rangeClosed(1, 50).boxed().collect(Collectors.toList());
        ModelAndView view = new ModelAndView();

        if (checkoutOrder == null) {
            checkoutOrder = new Order();
        }


        BigDecimal subTotal = checkoutService.calculateTotalAmountFromOrder(checkoutOrder);
        BigDecimal tax = NumberUtil.calculatePercentage(taxPercentage, subTotal);
        BigDecimal tip = NumberUtil.calculatePercentage(tipPercentage, subTotal);
        BigDecimal totalAmount = subTotal.add(tax).add(tip);
        checkoutOrder.setTotalAmount(totalAmount);

        checkoutOrder.setStatus(OrderTypes.NEW.toString());
        checkoutOrder.setOrderDate(LocalDate.now());
        view.addObject("stripeKey", stripePublicKey);
        view.addObject("subTotal", subTotal);
        view.addObject("tax", tax);
        view.addObject("tip", tip);
        view.addObject("tipPercentage", tipPercentage);
        view.addObject("totalAmount", totalAmount);
        view.addObject("currency", "CAD");
        view.addObject("cartQuantity", SessionUtil.getCartQuantity(request));
        view.addObject("checkoutOrder", checkoutOrder);
        view.addObject("restaurantBranch", RestaurantBranch.values());
        view.addObject("productQuantitySelector", productQuantitySelector);

        view.setViewName("checkout/checkout");

        return view;
    }


    @PostMapping("/create-checkout-session")
    @ResponseBody
    public Map<String, String> create( HttpServletRequest request) throws StripeException {
        Order checkoutOrder  = (Order) request.getSession().getAttribute("order");

        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("https://example.com/success")
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
                                                .setName("Sushi@go")
                                                .build()
                                        ).build()
                        ).build()
                ).build();

        Session session = Session.create(params);
        Map<String, String> responseData = new HashMap<>();
        responseData.put("id", session.getId());
        

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

    @GetMapping("/change-tip/{value}")
    public ModelAndView changeTipValue(@PathVariable("value") Long value, HttpServletRequest request) {
        Order checkoutOrder  = (Order) request.getSession().getAttribute("order");
        ModelAndView view = new ModelAndView();



        request.setAttribute("tipPercentage", value);
        request.setAttribute("order", checkoutOrder);
        view.addObject("order", checkoutOrder);
        view.addObject("tipPercentage", value);
        view.setViewName("redirect:/checkout");
        return view;
    }
}
