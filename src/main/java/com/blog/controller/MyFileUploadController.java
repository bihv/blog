package com.blog.controller;

import java.io.File;
import javax.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
 
@Controller
public class MyFileUploadController {
	@Autowired
	ServletContext context;
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public String handleTinyMCEUpload(@RequestParam("files") MultipartFile files[]) {
	    String filePath = "/uploads/" + files[0].getOriginalFilename();
	    String result = uploadFilesFromTinyMCE(files);
	    System.out.println(result);
	    return "{\"location\":\"" + filePath + "\"}";

	}

	private String uploadFilesFromTinyMCE(MultipartFile files[]) {
	    try {
	        String folder = context.getRealPath("/") + "/uploads/";
	        System.out.println("folder: " + folder);
	        StringBuffer result = new StringBuffer();
	        result.append("Uploading of File(s) ");

	        for (int i = 0; i < files.length; i++) {
	            if (!files[i].isEmpty()) {

	                try {
	                    boolean created = false;

	                    try {
	                        File theDir = new File(folder);
	                        theDir.mkdir();
	                        created = true;
	                    } catch (SecurityException se) {
	                        se.printStackTrace();
	                    }
	                    if (created) {
	                        System.out.println("DIR created");
	                    }
	                    String path = "";
	                    path = folder + files[i].getOriginalFilename();
	                    File destination = new File(path);
	                    System.out.println("--> " + destination);
	                    files[i].transferTo(destination);
	                    result.append(files[i].getOriginalFilename() + " Succsess. ");
	                } catch (Exception e) {
	                    throw new RuntimeException("Product Image saving failed", e);
	                }

	            } else
	                result.append(files[i].getOriginalFilename() + " Failed. ");

	        }

	        return result.toString();

	    } catch (Exception e) {
	        return "Error Occured while uploading files." + " => " + e.getMessage();
	    }
	}
}