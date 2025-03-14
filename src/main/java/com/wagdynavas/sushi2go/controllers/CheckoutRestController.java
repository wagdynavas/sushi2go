package com.wagdynavas.sushi2go.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

import static com.wagdynavas.sushi2go.util.NumberUtil.calculatePercentage;

@RestController
public class CheckoutRestController {


    @GetMapping("/checkout/tips")
    public BigDecimal getTipValue(@RequestParam(value = "tipPercentage") BigDecimal tipPercentage, @RequestParam(value = "subTotal") BigDecimal subTotal) {
        return calculatePercentage(tipPercentage, subTotal);
    }


    @GetMapping("/checkout/totalAmount")
    public BigDecimal getTotalAmountValue(@RequestParam(value = "tipPercentage") BigDecimal tipPercentage,
                                          @RequestParam(value = "taxPercentage") BigDecimal taxPercentage,
                                          @RequestParam(value = "subTotal") BigDecimal subTotal) {
        BigDecimal tipValue = calculatePercentage(tipPercentage, subTotal);
        BigDecimal tax = calculatePercentage(taxPercentage, subTotal);
        return  subTotal.add(tax).add(tipValue);
    }
}
