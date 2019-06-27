package com.blog.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blog.model.Category;
import com.blog.repository.CategoryRepository;
import com.blog.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	private CategoryRepository categoryRepository;

	@Autowired
	public CategoryServiceImpl(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	@Override
	public List<Category> findAllCategory() {
		/* return (List<Category>) categoryRepository.findAll(); */
		return (List<Category>) categoryRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
	}

	@Override
	public Optional<Category> findById(Integer id) {
		return categoryRepository.findById(id);
	}

	@Override
	public void save(Category category) {
		categoryRepository.save(category);
	}

	@Override
	public void remove(Category category) {
		categoryRepository.delete(category);
	}

	@Override
	public long count() {
		return categoryRepository.count();
	}

}
