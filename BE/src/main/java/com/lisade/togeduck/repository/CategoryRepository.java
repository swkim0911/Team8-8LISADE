package com.lisade.togeduck.repository;

import com.lisade.togeduck.entity.Category;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("select c.id from Category as c")
    List<Long> findIds();
}
