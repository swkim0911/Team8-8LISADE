package com.lisade.togeduck.service;

import com.lisade.togeduck.cache.service.CategoryCacheService;
import com.lisade.togeduck.cache.value.CategoryCacheValue;
import com.lisade.togeduck.dto.response.CategoryResponse;
import com.lisade.togeduck.entity.Category;
import com.lisade.togeduck.exception.CategoryNotFoundException;
import com.lisade.togeduck.mapper.CategoryMapper;
import com.lisade.togeduck.repository.CategoryRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryCacheService categoryCacheService;

    public CategoryResponse getList() {
        List<Category> categories = categoryCacheService.get()
            .map(CategoryCacheValue::getCategories)
            .orElseGet(this::fetchAndCacheCategories);

        return CategoryMapper.toCategoryResponse(categories);
    }

    private List<Category> fetchAndCacheCategories() {
        List<Category> categories = categoryRepository.findAll();
        categoryCacheService.save(categories);
        validateCategories(categories);
        return categories;
    }

    private void validateCategories(List<Category> categories) {
        if (categories.isEmpty()) {
            throw new CategoryNotFoundException();
        }
    }

    public Category get(Long categoryId) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        return optionalCategory.orElseThrow(CategoryNotFoundException::new);
    }
}
