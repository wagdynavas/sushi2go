package com.wagdynavas.sushi2go.repository;

import com.wagdynavas.sushi2go.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
