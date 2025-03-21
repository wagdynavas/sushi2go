package com.wagdynavas.sushi2go.controllers;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.wagdynavas.sushi2go.exceptions.OrderNotFondException;
import com.wagdynavas.sushi2go.model.Customer;
import com.wagdynavas.sushi2go.model.Order;
import com.wagdynavas.sushi2go.model.OrderItem;
import com.wagdynavas.sushi2go.model.Product;
import com.wagdynavas.sushi2go.service.CheckoutService;
import com.wagdynavas.sushi2go.service.CustomerService;
import com.wagdynavas.sushi2go.service.OrderItemService;
import com.wagdynavas.sushi2go.service.OrderService;
import com.wagdynavas.sushi2go.util.NumberUtil;
import com.wagdynavas.sushi2go.util.SessionUtil;
import com.wagdynavas.sushi2go.util.type.OrderTypes;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@Log4j2
@RequiredArgsConstructor
public class CheckoutController {

    @Value("${stripe.test.key}")
    private  String stripePublicKey;

    @Value("${wn.order.null.error.message}")
    private String emptyOrderMessage;

    @Value("${wn.order.restaurant.location.error.message}")
    private String chooseRestaurantLocationErrorMessage;


    private final BigDecimal taxPercentage = new BigDecimal(13);//HST consisting of 5% GST and 8% PST
    public static final String ORDER = "order";

    private final CheckoutService checkoutService;
    private final OrderService orderService;
    private final CustomerService customerService;
    private final OrderItemService orderItemService;



    @GetMapping("/checkout")
    public ModelAndView checkout(HttpServletRequest request) {
        Order checkoutOrder  = (Order) request.getSession().getAttribute(ORDER);
        if (checkoutOrder == null) {
            checkoutOrder = new Order();
        }
        String tipParameter =  request.getParameter("tipPercentage");
        if(tipParameter == null) {
            BigDecimal orderTipPercentage = checkoutOrder.getTipPercentage();
            if(orderTipPercentage != null) {
                tipParameter = orderTipPercentage.toString();
            } else {
                tipParameter = "15";
            }
        }
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

        if (checkoutOrder.getOrderId() != null ) {
            view.addObject("orderSaved", Boolean.TRUE); //disable form if is true
        }

        view.setViewName("checkout/checkout");
        view.addObject("tip", tip);
        view.addObject("tax", taxes);
        view.addObject("subTotal", subTotal);
        view.addObject("tipPercentage", tipPercentage);//used to keep an Active class on btn-tip checkout.html
        view.addObject("checkoutOrder", checkoutOrder);
        view.addObject("restaurantBranch", checkoutOrder.getRestaurantBranch());
        view.addObject("cartQuantity", SessionUtil.getCartQuantity(request));//Used to keep track of how many item are in the cart
        return view;
    }




    @PostMapping("/create-checkout-session")
    @ResponseBody
    public Map<String, String> create( HttpServletRequest request) throws StripeException, OrderNotFondException {
        log.info("Creating Stripe session.");
        Order checkoutOrder  = (Order) request.getSession().getAttribute(ORDER);
        Optional<Order> savedOrder = orderService.getOrderById(checkoutOrder.getOrderId());

        if(savedOrder.isEmpty()) {
            throw new OrderNotFondException("Order id is not valid: " + checkoutOrder.getOrderId());
        }

        Order finalOrder = savedOrder.get();
        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setCustomerEmail(checkoutOrder.getCustomer().getCustomerEmail())
                .setSuccessUrl("http://localhost:8080/checkout/succeeded")
                .setCancelUrl("http://localhost:8080/")
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(
                                SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("cad")
                                .setUnitAmount(checkoutService.convertOrderTotalAmountToStripeUnitAmount(finalOrder))
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
        log.info("Stripe session created: " + session.getId());
        

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
        Order checkoutOrder  = (Order) request.getSession().getAttribute(ORDER);
        ModelAndView view = new ModelAndView();

         List<Product> products = checkoutOrder.getProducts()
                 .stream()
                 .filter(p -> !Objects.equals(p.getId(), productId))
                 .toList();

        checkoutOrder.setProducts(products);
        request.setAttribute(ORDER, checkoutOrder);
        view.addObject(ORDER, checkoutOrder);
        view.setViewName("redirect:/checkout");
        return view;
    }


    @GetMapping("/checkout/succeeded")
    public ModelAndView checkoutSucceeded() {
       return new ModelAndView("/checkout/succeeded");
    }



    @PostMapping("/checkout/save-and-continue")
    public ModelAndView saveOrder(@Valid Order checkoutOrder, BindingResult result, HttpServletRequest request)  {
        log.debug("Saving order information");
        Order sessionOrder  = (Order) request.getSession().getAttribute(ORDER);
        if(sessionOrder == null || sessionOrder.getProducts().isEmpty()) {
            result.addError(new ObjectError(ORDER, emptyOrderMessage));
        }
        String stringTipPercentage = request.getParameter("options");
        String restaurantBranch = request.getParameter("locations");
        if (restaurantBranch == null) {
            result.addError(new ObjectError(ORDER, chooseRestaurantLocationErrorMessage));
        }
        ModelAndView view = new ModelAndView();
        if (result.hasErrors()) {
            //TODO Deal with input error
            view.addObject( "checkoutOrder" ,checkoutOrder);
            view.setViewName("checkout/checkout");
        } else {
            sessionOrder.setRestaurantBranch(restaurantBranch);
            sessionOrder.setTipPercentage(new BigDecimal(stringTipPercentage));
            Customer customer =  customerService.saveCustomer(checkoutOrder.getCustomer());
            sessionOrder.setCustomer(customer);
            sessionOrder.setStatus(OrderTypes.NEW.getValue());
            sessionOrder.setOrderDate(LocalDateTime.now());



            Order savedOrder = orderService.saveOrder(sessionOrder);

            for(Product product : sessionOrder.getProducts()) {
                OrderItem orderItem = new OrderItem();

                orderItem.setOrderId(savedOrder);
                orderItem.setName(product.getProductName());
                orderItem.setQuantity(product.getQuantity());
                orderItem.setInstructions(product.getCustomerInstructions());
                orderItemService.saveOrderItem(orderItem);
            }
            checkoutOrder.setOrderId(savedOrder.getOrderId());
            view.setViewName("redirect:/checkout");
        }
        return view;
    }

    @GetMapping("/checkout/location/{restaurantLocation}")
    public ModelAndView changeLocation(@PathVariable String restaurantLocation, HttpServletRequest request) {
        ModelAndView view = new ModelAndView();
        String restaurantBranch = "restaurantBranch";
        Order sessionOrder  = (Order) request.getSession().getAttribute(ORDER);
        if (sessionOrder == null) {
            sessionOrder = new Order();
        }
        sessionOrder.setRestaurantBranch(restaurantLocation);



        request.setAttribute(restaurantBranch, restaurantLocation);
        request.setAttribute(ORDER, sessionOrder);
        view.addObject(restaurantBranch, restaurantLocation);
        view.addObject(ORDER, sessionOrder);
        view.setViewName("redirect:/checkout");
        return view;
    }
}
