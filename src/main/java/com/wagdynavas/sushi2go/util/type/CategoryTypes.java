package com.wagdynavas.sushi2go.util.type;

public enum CategoryTypes  {
    SIDE_ORDER("Side Order", "side_order"),
    SOUP_AND_SALAD("Soup And Salad", "soup_and_salad"),
    APPETIZER("Appetizer", "appetizer"),
    NIGIRI_A_LA_CARTE("Nigiri A La Carte", "nigiri_a_la_carte"),
    SASHIMI_A_LA_CARTE("Sashimi A La Carte", "sashimi_a_la_carte"),
    MAKI("Maki", "maki"),
    HAND_ROLL("Hand Roll", "hand_roll"),
    VEGETABLE_MAKI("Vegetarian Maki", "vegetable_maki"),
    FUNKY_ROLLS("Funky Roll", "funky_roll"),
    DINNER_ENTREES("Dinner Entrees", "dinner_entrees"),
    BENTO_BOX("Bento Box", "bento_box"),
    LUNCH_SUSHI("Lunch Sushi", "lunch_sushi") ,
    LUNCH_ENTREES("Lunch Entrees", "lunch_entrees"),
    LUNCH_NOODLES("Lunch Noodles", "lunch_noodles"),
    LUNCH_BENTO_BOX("Lunch Bento Box", "lunch_bento_box"),
    NIGIRI_AND_MAKI_SUSHI("Nigiri And Maki", "nigiri_and_maki_sushi"),
    SASHIMI_NIGIRI_MAKI("Sashimi Nigiri and Maki", "sashimi_nigiri_maki"),
    SASHIMI("Sashimi", "sashimi"),
    DESSERT("Dessert", "dessert"),
    BEVERAGES("Beverages", "beverages"),
    LUNCH("Lunch", "lunch"),
    DINNER("Dinner", "dinner");


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
