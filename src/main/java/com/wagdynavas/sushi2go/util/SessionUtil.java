package com.wagdynavas.sushi2go.util;

import com.wagdynavas.sushi2go.model.Order;
import com.wagdynavas.sushi2go.model.Product;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
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
