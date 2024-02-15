package com.lisade.togeduck.service;

import com.lisade.togeduck.entity.Category;
import com.lisade.togeduck.repository.CategoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> getList() {
        return categoryRepository.findAll();
    }
}
