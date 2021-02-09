package com.wagdynavas.sushi2go.service;

import com.stripe.Stripe;
import com.stripe.model.Charge;
import com.wagdynavas.sushi2go.model.Order;
import com.wagdynavas.sushi2go.model.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class CheckoutService {

    @Value("${stripe.test.key}")
    private  String stripePublicKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripePublicKey;
    }


    public Charge charge(Order checkoutOrder) {
        return null;
    }



    public BigDecimal calculateTotalAmountFromOrder(Order order) {
        BigDecimal totalAmount = BigDecimal.ZERO.setScale(2);
        if (order != null) {
            List<Product> products = order.getProducts();
            if(products != null) {
                totalAmount = products.stream().map(product ->  product.getPrice()).reduce(BigDecimal.ZERO, BigDecimal::add);
            }
        }

        return totalAmount;
    }


    public Long convertOrderTotalAmountToStripeUnitAmount(Order order) {
        return order.getTotalAmount().multiply(BigDecimal.valueOf(100)).longValue();
    }


    /**
     * Calculate tax value
     *
     * @param totalAmount Bill value before tax
     * @return tax value
     */
    public BigDecimal calculateOrderTax(BigDecimal totalAmount) {
        BigDecimal texValue;
        final BigDecimal tax = new BigDecimal(13);
        texValue = totalAmount.multiply(tax).divide(new BigDecimal(100), 2, RoundingMode.CEILING);
        return texValue;
    }
}
