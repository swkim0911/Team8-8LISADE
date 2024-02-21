package com.lisade.togeduck.service;

import com.lisade.togeduck.dto.response.CategoryResponse;
import com.lisade.togeduck.entity.Category;
import com.lisade.togeduck.exception.InternalServerException;
import com.lisade.togeduck.mapper.CategoryMapper;
import com.lisade.togeduck.repository.CategoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryResponse getList() {
        List<Category> categories = categoryRepository.findAll();
        validateCategories(categories);
        return CategoryMapper.toCategoryResponse(categories);
    }

    public List<Long> getIds() {
        return categoryRepository.findIds();
    }

    private void validateCategories(List<Category> categories) {
        if (categories.isEmpty()) {
            throw new InternalServerException();
        }
    }
}
