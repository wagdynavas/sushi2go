package com.wagdynavas.sushi2go.util.type;

public enum RestaurantBranch {

    ROYAL_YORK("Bloor & Royal York (Royal York)", "ROYAL_YORK"),
    DUNDAS("Dundas & Kipling (Dundas)", "DUNDAS"),
    QUEENSWAY("Queensway & Islington (Queensway)", "QUEENSWAY");


    private String label;
    private String value;


    RestaurantBranch(String label, String value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
