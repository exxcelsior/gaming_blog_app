package com.example.gaming_blog_app.services;

import com.example.gaming_blog_app.models.Category;
import com.example.gaming_blog_app.models.Comment;
import com.example.gaming_blog_app.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public List<Category> getAllInAscOrder() {
        return categoryRepository.findAllByOrderByTitleAsc();
    }

    public Category getByTitle(String title) {
        return categoryRepository.findByTitle(title);
    }

}
