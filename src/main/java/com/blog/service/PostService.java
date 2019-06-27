package com.blog.service;

import java.util.List;
import java.util.Optional;

import com.blog.model.Post;

public interface PostService {
	List<Post> findAll();

	Optional<Post> findById(Integer id);

	void save(Post post);

	void remove(Post post);

	long count();
}
