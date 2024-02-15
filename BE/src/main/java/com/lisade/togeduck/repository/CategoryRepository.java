package com.lisade.togeduck.repository;

import com.lisade.togeduck.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
