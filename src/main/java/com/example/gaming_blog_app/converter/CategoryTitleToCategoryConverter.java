package com.example.gaming_blog_app.converter;

import com.example.gaming_blog_app.models.Category;
import com.example.gaming_blog_app.repositories.CategoryRepository;
import com.example.gaming_blog_app.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CategoryTitleToCategoryConverter implements Converter<String, Category> {
    @Autowired
    private CategoryService categoryService;

    public Category convert(String source) {
        return categoryService.getByTitle(source);
    }
}
