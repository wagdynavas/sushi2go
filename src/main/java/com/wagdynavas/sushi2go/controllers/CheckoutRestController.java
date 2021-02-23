package com.wagdynavas.sushi2go.controllers;

import com.wagdynavas.sushi2go.service.CheckoutService;
import com.wagdynavas.sushi2go.util.NumberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class CheckoutRestController {


    @GetMapping("/checkout/tips")
    @ResponseBody
    public BigDecimal getTipValue(@RequestParam(value = "tipPercentage") BigDecimal tipPercentage, @RequestParam(value = "subTotal") BigDecimal subTotal) {
        return NumberUtil.calculatePercentage(tipPercentage, subTotal);
    }


    @GetMapping("/checkout/totalAmount")
    @ResponseBody
    public BigDecimal getTotalAmountValue(@RequestParam(value = "tipPercentage") BigDecimal tipPercentage, @RequestParam(value = "tax") BigDecimal tax,
                                          @RequestParam(value = "subTotal") BigDecimal subTotal) {
        BigDecimal tipValue = NumberUtil.calculatePercentage(tipPercentage, subTotal);
        return  subTotal.add(tax).add(tipValue);
    }
}
