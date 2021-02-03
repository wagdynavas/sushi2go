package com.wagdynavas.sushi2go.util;

import com.wagdynavas.sushi2go.model.Order;
import com.wagdynavas.sushi2go.model.Product;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class SessionUtil {

    public static Integer getCartQuantity(HttpServletRequest request) {
        Order order = (Order) request.getSession().getAttribute("order");
        Integer cartQuantity = 0;

        if(order != null) {
            List<Product> products = order.getProducts();
            if (products != null) {
                for(Product product : products ) {
                    cartQuantity += product.getQuantity();
                }
            }
        }

        return cartQuantity;
    }
}
