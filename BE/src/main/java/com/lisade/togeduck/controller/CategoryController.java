package com.lisade.togeduck.controller;

import com.lisade.togeduck.dto.response.CategoryResponse;
import com.lisade.togeduck.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public CategoryResponse getList() {
        return categoryService.getList();
    }
}
