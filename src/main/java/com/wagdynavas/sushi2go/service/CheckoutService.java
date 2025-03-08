package com.wagdynavas.sushi2go.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.TaxRate;
import com.stripe.param.TaxRateCreateParams;
import com.wagdynavas.sushi2go.model.Order;
import com.wagdynavas.sushi2go.model.Product;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CheckoutService {

    @Value("${stripe.test.key}")
    private  String stripePublicKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripePublicKey;
    }


    public BigDecimal calculateTotalAmountFromOrder(Order order) {
        BigDecimal totalAmount = BigDecimal.ZERO.setScale(2);
        if (order != null) {
            List<Product> products = order.getProducts();
            if(products != null) {
                totalAmount = products.stream()
                        .map(Product::getPrice)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
            }
        }

        return totalAmount;
    }


    public Long convertOrderTotalAmountToStripeUnitAmount(Order order) {
        return order.getTotalAmount().multiply(BigDecimal.valueOf(100)).longValue();
    }


    public TaxRate createTaxRate() throws StripeException {
        TaxRateCreateParams params = TaxRateCreateParams.builder()
                        .setDisplayName("Taxes")
                        .setInclusive(true)
                        .setPercentage(new BigDecimal("13"))
                        .setCountry("CA")
                        .setState("ON")
                        .build();

        return TaxRate.create(params);
    }


}
