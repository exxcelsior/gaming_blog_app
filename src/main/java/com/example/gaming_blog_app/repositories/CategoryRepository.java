package com.example.gaming_blog_app.repositories;

import com.example.gaming_blog_app.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAll();

    List<Category> findAllByOrderByTitleAsc();

    Category findByTitle(String title);
}
