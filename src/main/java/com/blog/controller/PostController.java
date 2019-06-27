package com.blog.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.blog.model.Post;
import com.blog.service.PostService;
import com.blog.utility.Constant;

@RestController
public class PostController {
	
	private PostService service;
	
	@Autowired
	public PostController(PostService service) {
		this.service = service;
	}

	@GetMapping(value = Constant.FIND_ALL_POST)
	public ResponseEntity<List<Post>> getAllPOST(){
		List<Post> items = service.findAll();
		return new ResponseEntity<List<Post>>(items, HttpStatus.OK);
	}
	
	@GetMapping(value = Constant.FIND_POST_BY_ID)
	public ResponseEntity<Post> getPOSTById(@PathVariable("id") Integer id){
		Optional<Post> items = service.findById(id);
		if(!items.isPresent()) {
			return new ResponseEntity<Post>(items.get(), HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Post>(items.get(), HttpStatus.OK);
	}
	
	@PostMapping(value = Constant.CREATE_POST)
	public ResponseEntity<Post> addPOST(@RequestBody Post item){
		service.save(item);
		return new ResponseEntity<Post>(item, HttpStatus.CREATED);
	}
	
	@PutMapping(value = Constant.UPDATE_POST)
	public ResponseEntity<Post> updatePOST(@RequestBody Post item, @PathVariable("id") Integer id){
		Optional<Post> currentItem = service.findById(id);
		if(!currentItem.isPresent()) {
			return new ResponseEntity<Post>(currentItem.get(), HttpStatus.NO_CONTENT);
		}
		currentItem.get().setCategory(item.getCategory());
		currentItem.get().setAuthor(item.getAuthor());
		currentItem.get().setContent(item.getContent());
		currentItem.get().setTitle(item.getTitle());
		service.save(currentItem.get());
		return new ResponseEntity<Post>(currentItem.get(), HttpStatus.OK);
	}
	
	@DeleteMapping(value = Constant.DELETE_POST)
	public ResponseEntity<Post> deletePOST(@PathVariable("id") Integer id){
		Optional<Post> item = service.findById(id);
		if(!item.isPresent()) {
			return new ResponseEntity<Post>(HttpStatus.NOT_FOUND);
		}
		service.remove(item.get());
		return new ResponseEntity<Post>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping(value = Constant.COUNT_POST)
	public ResponseEntity<Long> getTotalPOST(){
		long amount = service.count();
		return new ResponseEntity<Long>(amount, HttpStatus.OK);
	}
}
