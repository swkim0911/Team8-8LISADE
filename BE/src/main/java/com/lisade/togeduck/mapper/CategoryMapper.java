package com.lisade.togeduck.mapper;

import com.lisade.togeduck.dto.response.CategoryResponse;
import com.lisade.togeduck.entity.Category;
import java.util.List;

public class CategoryMapper {

    public static CategoryResponse toCategoryResponse(List<Category> categories) {
        return CategoryResponse.builder()
            .numberOfCategories(categories.size())
            .categories(categories)
            .build();
    }
}
