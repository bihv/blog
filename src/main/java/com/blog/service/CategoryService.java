package com.blog.service;

import java.util.List;
import java.util.Optional;

import com.blog.model.Category;

public interface CategoryService {
	List<Category> findAllCategory();

    Optional<Category> findById(Integer id);

    void save(Category category);

    void remove(Category category);
    
    long count();
}
