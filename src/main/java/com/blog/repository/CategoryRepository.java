package com.blog.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.blog.model.Category;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Integer> {

	List<Category> findAll(Sort by);

}
