package com.wagdynavas.sushi2go.controllers;

import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.Order;
import com.stripe.model.StripeObject;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.wagdynavas.sushi2go.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/orders")
public class OrderRestController {


    private final OrderService orderService;

    @Value("${stripe.test.key}")
    private  String stripePublicKey;

    @Value("${stripe.endpoint.secret}")
    private String endpointSecret;

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripePublicKey;
    }


    /**
     * Change ORder status when payment is confirmed
     * @param data
     * @param request
     * @return
     */
    @PostMapping("/fulfill")
    public ResponseEntity<Order> setPaidOrder(@RequestBody String data, HttpServletRequest request) {
        String payload = data;
        String sigHeader = request.getHeader("Stripe-Signature");
        Event event = null;

        try {
            event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
            // Handle the checkout.session.completed event
            if ("checkout.session.completed".equals(event.getType())) {
                Optional<StripeObject> optionalSession =  event.getDataObjectDeserializer().getObject();
                Session session = (Session) optionalSession.get();


                // Fulfill the purchase...
                orderService.fulfillOrder(session);
            }
        } catch (com.google.gson.JsonSyntaxException e){
            log.error("Invalid payload", payload);
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (SignatureVerificationException e) {
            log.error("Invalid Stripe signature " +  sigHeader, sigHeader);
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }



        return new ResponseEntity<>(HttpStatus.OK);
    }


}
