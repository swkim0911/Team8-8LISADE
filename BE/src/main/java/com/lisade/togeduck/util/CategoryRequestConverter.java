package com.lisade.togeduck.util;

import com.lisade.togeduck.entity.Category;
import org.springframework.core.convert.converter.Converter;

public class CategoryRequestConverter implements Converter<String, Category> {

    @Override
    public Category convert(String code) {
        return Category.of(code);
    }
}
