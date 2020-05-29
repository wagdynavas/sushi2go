package com.wagdynavas.sushi2go.repository;

import com.wagdynavas.sushi2go.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
