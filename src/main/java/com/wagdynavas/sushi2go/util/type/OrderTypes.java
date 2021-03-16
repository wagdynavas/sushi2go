package com.wagdynavas.sushi2go.util.type;

public enum OrderTypes {

    NEW("NEW"),
    PAID("PAID"),
    ACCEPTED("ACCEPTED"),
    CREATED("CREATED"),
    NOT_AVAILABLE("NOT_AVAILABLE"),
    DELIVERED("DELIVERED");

    private String value;


    OrderTypes(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
