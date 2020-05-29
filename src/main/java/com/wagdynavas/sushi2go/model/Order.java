package com.wagdynavas.sushi2go.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORD_ID")
    private Long orderId;

    @OneToOne
    @JoinColumn(name = "ORD_USER", nullable = false)
    private User user;

    @Column(name = "ORD_STATUS", nullable = false)
    private String status;

    @Column(name = "ORD_DATE", nullable = false)
    private LocalDate orderDate;

    //private PaymentDetails paymentDetails;
    //TODO: Payment_Details. criar objecto e verificar como funcina todo o mecanoismo  de pagamentos com cartao de cretito


    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (orderId != null ? !orderId.equals(order.orderId) : order.orderId != null) return false;
        if (user != null ? !user.equals(order.user) : order.user != null) return false;
        if (status != null ? !status.equals(order.status) : order.status != null) return false;
        return orderDate != null ? orderDate.equals(order.orderDate) : order.orderDate == null;
    }

    @Override
    public int hashCode() {
        int result = orderId != null ? orderId.hashCode() : 0;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (orderDate != null ? orderDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", user=" + user +
                ", status='" + status + '\'' +
                ", orderDate=" + orderDate +
                '}';
    }
}


