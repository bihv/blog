package com.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.blog.utility.Constant;

@Controller
public class ViewController {
	@GetMapping(value = Constant.VIEW_CATEGORY)
	public String viewCategory() {
		return "admin/category";
	}
	
	@GetMapping(value = Constant.VIEW_POST)
	public String viewSongs() {
		return "admin/post";
	}
	
	@GetMapping(value = Constant.VIEW_ADMIN)
	public String viewHome() {
		return "admin/index";
	}
	
	@GetMapping(value = "view")
	public String viewTestUpload() {
		return "NewFile";
	}
}
