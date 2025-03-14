package com.wagdynavas.sushi2go.controllers;

import com.wagdynavas.sushi2go.model.Order;
import com.wagdynavas.sushi2go.model.Product;
import com.wagdynavas.sushi2go.service.CheckoutService;
import com.wagdynavas.sushi2go.service.CustomerService;
import com.wagdynavas.sushi2go.service.OrderItemService;
import com.wagdynavas.sushi2go.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = {CheckoutRestController.class, CheckoutController.class})
@AutoConfigureMockMvc(addFilters = false)
class CheckoutControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Mock
    HttpServletRequest request;

    @Mock
    HttpSession httpSession;

    @Mock
    Order order;

    @MockitoBean
    CheckoutService checkoutService;
    @MockitoBean
    OrderService orderService;
    @MockitoBean
    CustomerService customerService;
    @MockitoBean
    OrderItemService orderItemService;

    @Test
   void get_tip_value_test() throws Exception {

        mockMvc.perform(get("/checkout/tips?tipPercentage=15&subTotal=10.00"))
                .andExpect(status().isOk())
                .andExpect(content().json("1.50"));
   }

    @Test
    void get_total_amount_value_test() throws Exception {

        mockMvc.perform(get("/checkout/totalAmount?tipPercentage=15&taxPercentage=13&subTotal=10.00"))
                .andExpect(status().isOk())
                .andExpect(content().json("12.80"));
    }

    @Test
    void checkout_order_test() throws Exception {
        when(request.getSession()).thenReturn(httpSession);
        when(httpSession.getAttribute(anyString())).thenReturn(order);
        when(order.getTipPercentage()).thenReturn(new BigDecimal(15));
        when(order.getRestaurantBranch()).thenReturn("Bloor");
        when(checkoutService.calculateTotalAmountFromOrder(any())).thenReturn(BigDecimal.ZERO);

        mockMvc.perform(get("/checkout"))
                .andExpect(status().isOk());
    }

    private Order getOrder() {
        Order order = new Order();
        Product product = new Product();

        product.setQuantity(2);
        order.setProduct(product);

        return order;
    }

}
