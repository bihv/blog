package com.blog.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blog.model.Post;
import com.blog.repository.PostRepository;
import com.blog.service.PostService;

@Service
public class PostServiceImpl implements PostService {

	private PostRepository repository;

	@Autowired
	public PostServiceImpl(PostRepository repository) {
		this.repository = repository;
	}

	@Override
	public List<Post> findAll() {
		return repository.findAll(Sort.by(Sort.Direction.DESC, "id"));
	}

	@Override
	public Optional<Post> findById(Integer id) {
		return repository.findById(id);
	}

	@Override
	public void save(Post item) {
		repository.save(item);
	}

	@Override
	public void remove(Post item) {
		repository.delete(item);
	}

	@Override
	public long count() {
		return repository.count();
	}

}
