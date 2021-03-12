package com.wagdynavas.sushi2go.exceptions;

public class OrderNotFondException extends RuntimeException{


    public OrderNotFondException (String message) {
        super(message);
    }

    public OrderNotFondException() {

    }
}
