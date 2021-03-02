package com.wagdynavas.sushi2go.repository;

import com.wagdynavas.sushi2go.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {


}
