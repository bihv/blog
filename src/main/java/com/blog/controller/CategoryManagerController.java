package com.blog.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import com.blog.model.Category;
import com.blog.service.CategoryService;
import com.blog.utility.Constant;

@RestController
public class CategoryManagerController {
	private CategoryService categoryService;

	@Autowired
	public CategoryManagerController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@GetMapping(value = Constant.FIND_ALL_CATEGORY)
	public ResponseEntity<List<Category>> findAllCategory() {
		List<Category> categories = categoryService.findAllCategory();
		return new ResponseEntity<List<Category>>(categories, HttpStatus.OK);
	}

	@GetMapping(value = Constant.FIND_CATEGORY_BY_ID, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Category> getCategoryById(@PathVariable("id") Integer id) {
		Optional<Category> category = categoryService.findById(id);

		if (!category.isPresent()) {
			return new ResponseEntity<Category>(category.get(), HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Category>(category.get(), HttpStatus.OK);
	}

	@PostMapping(value = Constant.CREATE_CATEGORY)
	public ResponseEntity<Category> createCatrgory(@RequestBody Category category, UriComponentsBuilder builder) {
		categoryService.save(category);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(builder.path("/categories/{id}").buildAndExpand(category.getId()).toUri());
		return new ResponseEntity<Category>(category, HttpStatus.CREATED);
	}

	@PutMapping(value = Constant.UPDATE_CATEGORY)
	public ResponseEntity<Category> updateCategory(@PathVariable("id") Integer id, @RequestBody Category category) {
		Optional<Category> currentCategory = categoryService.findById(id);
		if (!currentCategory.isPresent()) {
			return new ResponseEntity<Category>(HttpStatus.NO_CONTENT);
		}
		currentCategory.get().setName(category.getName());

		categoryService.save(currentCategory.get());
		return new ResponseEntity<Category>(currentCategory.get(), HttpStatus.OK);
	}

	@DeleteMapping(value = Constant.DELETE_CATEGORY)
	public ResponseEntity<Category> deleteCategory(@PathVariable("id") Integer id) {
		Optional<Category> category = categoryService.findById(id);
		if (!category.isPresent()) {
			return new ResponseEntity<Category>(HttpStatus.NOT_FOUND);
		}

		categoryService.remove(category.get());
		return new ResponseEntity<Category>(HttpStatus.NO_CONTENT);
	}

	@GetMapping(value = Constant.COUNT_CATEGORY, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Long> getTotalCategory() {
		long amount = 0;
		amount = categoryService.count();
		return new ResponseEntity<Long>(amount, HttpStatus.OK);
	}
}
