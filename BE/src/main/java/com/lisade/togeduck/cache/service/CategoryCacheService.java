package com.lisade.togeduck.cache.service;

import com.lisade.togeduck.cache.repository.CategoryCacheRepository;
import com.lisade.togeduck.cache.value.CategoryCacheValue;
import com.lisade.togeduck.entity.Category;
import com.lisade.togeduck.exception.CategoryNotFoundException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryCacheService {

    public static final String CATEGORY_CACHE_ID = "category";
    private final CategoryCacheRepository categoryCacheRepository;

    public void save(List<Category> categories) {
        CategoryCacheValue categoryCacheValue = new CategoryCacheValue(categories);
        categoryCacheRepository.save(categoryCacheValue);
    }

    public CategoryCacheValue get() {
        Optional<CategoryCacheValue> optionalCategoryCacheValue = categoryCacheRepository.findById(
            CATEGORY_CACHE_ID);
        return optionalCategoryCacheValue.orElseThrow(CategoryNotFoundException::new);
    }
}
