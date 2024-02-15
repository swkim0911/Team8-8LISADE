package com.lisade.togeduck.dto.response;

import com.lisade.togeduck.entity.Category;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CategoryResponse {

    Integer numberOfCategories;
    List<Category> categories;
}
