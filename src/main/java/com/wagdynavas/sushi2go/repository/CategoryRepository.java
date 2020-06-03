package com.wagdynavas.sushi2go.repository;

import com.wagdynavas.sushi2go.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
