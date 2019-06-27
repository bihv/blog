package com.blog.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.blog.model.Post;

@Repository
public interface PostRepository extends CrudRepository<Post, Integer> {
	List<Post> findAll(Sort by);

	long count();
}
