package com.lisade.togeduck.service;

import com.lisade.togeduck.entity.Category;
import com.lisade.togeduck.exception.InternalServerException;
import com.lisade.togeduck.repository.CategoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> getList() {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            throw new InternalServerException();
        }
        return categoryRepository.findAll();
    }
}
