package com.example.blogapprestapi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.blogapprestapi.payload.PostDto;
import com.example.blogapprestapi.payload.PostResponse;
import com.example.blogapprestapi.service.PostService;
import com.example.blogapprestapi.utils.AppConstant;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/posts")
@Tag(name="CRUD Rest APIs for Post Resource")
public class PostController {
	
	private PostService postService;
	
	
	
	public PostController(PostService postService) {
		super();
		this.postService = postService;
	}


	
	// create Post
	@Operation(summary = "Create Post REST API",description = "Create Post REST API is used to save the Post into database")
	@ApiResponse(responseCode = "201",description = "Http status 201 CREATED")
	@SecurityRequirement(name="Bear Authentication")
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<PostDto> addPost(@Valid @RequestBody PostDto postDto ) {
		
		PostDto response =postService.createPost(postDto);
		
		return new ResponseEntity<PostDto>(response,HttpStatus.CREATED);
		
		
	}
	
	// get Post By Id
	@Operation(summary = "Get Post REST API",description = "Get Post REST API is used to get the Post from database")
	@ApiResponse(responseCode = "200",description = "Http status 200 OK")
	@GetMapping("/{id}")
	public ResponseEntity<PostDto> getPost(@PathVariable Long id ) {
		
		PostDto getPostResponse =postService.getPost(id);
		
		return new ResponseEntity<PostDto>(getPostResponse,HttpStatus.OK);
		
		
	}
	
	// get All Posts
	@Operation(summary = "Get All Post REST API",description = "Get All Post REST API is used to get the All Posts from database")
	@ApiResponse(responseCode = "200",description = "Http status 200 OK")
	@GetMapping
	public ResponseEntity<PostResponse> getAllPost(@RequestParam(defaultValue = AppConstant.DEFAULT_PAGE_NUMBER ,required = false) int pageNo,@RequestParam(defaultValue =AppConstant.DEFAULT_PAGE_SIZE ,required = false) int pageSize ,
			@RequestParam(defaultValue = AppConstant.DEFAULT_PAGE_SORT_BY ,required = false) String sortBy,
			@RequestParam(defaultValue = AppConstant.DEFAULT_PAGE_SORT_DIRECTION ,required = false) String sortDir) {
		
		PostResponse getAllPostResponses =postService.getAllPost(pageNo,pageSize,sortBy,sortDir);
		
		return new ResponseEntity<PostResponse>(getAllPostResponses,HttpStatus.OK);
		
		
	}
	
	// get All Posts by Category Id
	@Operation(summary = "Get Post by Category REST API",description = "Get Post by Category is used to get the All Post by Category from database")
	@ApiResponse(responseCode = "200",description = "Http status 200 OK")
	@GetMapping("/category/{categoryId}")
	public ResponseEntity<List<PostDto>> getAllPostByCategory(@PathVariable Long categoryId ) {
		
		List<PostDto> getAllPost =postService.getAllPostByCategory(categoryId);
		
		return new ResponseEntity<List<PostDto>> (getAllPost,HttpStatus.OK);
		
		
	}
	
	// delete Post 
	@Operation(summary = "Delete Post REST API",description = "Delete  Post REST API is used to Delete  the Post from database")
	@ApiResponse(responseCode = "200",description = "Http status 200 OK")
	@SecurityRequirement(name="Bear Authentication")
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<PostDto> deletePost(@PathVariable Long id ) {
		
		PostDto deletePost =postService.deletePost(id);
		
		return new ResponseEntity<PostDto>(deletePost,HttpStatus.OK);
		
		
	}
	
	// update Post 
	@Operation(summary = "Update Post REST API",description = "Update Post REST API is used to Update the Post in database")
	@ApiResponse(responseCode = "200",description = "Http status 200 OK")
	@SecurityRequirement(name="Bear Authentication")
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<PostDto> updatePost(@PathVariable Long id ,@Valid  @RequestBody PostDto postDto) {
		
		PostDto updatePost =postService.updatePost(id, postDto);
		
		return new ResponseEntity<PostDto>(updatePost,HttpStatus.CREATED);
		
		
	}
	
	

}
