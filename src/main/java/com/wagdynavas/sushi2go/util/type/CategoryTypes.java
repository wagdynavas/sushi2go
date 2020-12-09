package com.wagdynavas.sushi2go.util.type;

public enum CategoryTypes  {
    LUNCH_SUSHI("Lunch Sushi", "lunch_sushi") ,
    LUNCH_ENTREES("Lunch Entrees", "lunch_entrees"),
    LUNCH_NOODLES("Lunch Noodles", "lunch_noodles"),
    LUNCH_BENTO_BOX("Lunch Bento Box", "lunch_bento_box"),
    DINNER("Dinner", "dinner"),
    FUNKY_ROLLS("Funky Roll", "funky_roll"),
    NIGIRI_SUSHI("Nigiri Sushi", "nigiri_sushi"),
    MAKI_SUSHI("Maki Sushi", "maki_sushi"),
    VEGETABLE_MAKI("Vegetable maki", "vegetable_maki"),
    NIGIRI_AND_MAKI_SUSHI("Nigiri And Maki Sushi", "nigiri_and_maki_sushi"),
    SASHIMI_NIGIRI_MAKI("Sashimi Nigiri and Maki", "sashimi_nigiri_maki"),
    SASHIMI("Sashimi", "sashimi"),
    APPETIZER("Appetizer", "appetizer"),
    DESSERT("Dessert", "dessert"),
    SOUP_AND_SALAD("Soup And Salad", "soup_and_salad"),
    SIDE_ORDER("side Order", "side_order"),
    BENTO_BOX("Bento Box", "bento_box");


    private String name;
    private String value;


    CategoryTypes(String name, String value) {
        this.name = name;
        this.value = value;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
