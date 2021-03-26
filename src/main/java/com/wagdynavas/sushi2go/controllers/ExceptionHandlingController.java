package com.wagdynavas.sushi2go.controllers;

import com.wagdynavas.sushi2go.exceptions.OrderNotFondException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Controller
public class ExceptionHandlingController {

    @ExceptionHandler(value = {OrderNotFondException.class})
    public String orderError() {
        return "error/error";
    }
}
