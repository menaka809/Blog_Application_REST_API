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
import org.springframework.web.bind.annotation.RestController;

import com.example.blogapprestapi.payload.CategoryDto;
import com.example.blogapprestapi.service.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/categories")
@Tag(name="CRUD Rest APIs for Category Resource")
public class CategoryController {

	private CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		super();
		this.categoryService = categoryService;
	}

	// create Category
	@Operation(summary = "Create Category REST API",description = "Create Category REST API is used to save the category into database")
	@ApiResponse(responseCode = "201",description = "Http status 201 CREATED")
	@SecurityRequirement(name="Bear Authentication")
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {
		CategoryDto response = categoryService.addCategory(categoryDto);

		return new ResponseEntity<CategoryDto>(response, HttpStatus.CREATED);

	}

	// get Category by Id
	@Operation(summary = "Get Category REST API",description = "Get Category REST API is used to get the category from database")
	@ApiResponse(responseCode = "200",description = "Http status 200 OK")
	@GetMapping("/{id}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable Long id) {
		CategoryDto getResponse = categoryService.getCategoryById(id);

		return new ResponseEntity<CategoryDto>(getResponse, HttpStatus.OK);

	}

	// get All Categories
	@Operation(summary = "Get All Category REST API",description = "Get All Category REST API is used to get the All categories from database")
	@ApiResponse(responseCode = "200",description = "Http status 200 OK")
	@GetMapping
	public ResponseEntity<List<CategoryDto>> getCategory() {
		List<CategoryDto> getCategories = categoryService.getAllCategory();

		return new ResponseEntity<List<CategoryDto>>(getCategories, HttpStatus.OK);

	}

	// update Category
	@Operation(summary = "Update Category REST API",description = "Update Category REST API is used to Update the category in database")
	@ApiResponse(responseCode = "200",description = "Http status 200 OK")
	@SecurityRequirement(name="Bear Authentication")
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<CategoryDto> updateCategory(@PathVariable Long id, @RequestBody CategoryDto categoryDto) {
		CategoryDto updateCategory = categoryService.updateCategory(id, categoryDto);

		return new ResponseEntity<CategoryDto>(updateCategory, HttpStatus.OK);

	}

	// delete Category
	@Operation(summary = "Delete Category REST API",description = "Delete  Category REST API is used to Delete  the category from database")
	@ApiResponse(responseCode = "200",description = "Http status 200 OK")
	@SecurityRequirement(name="Bear Authentication")
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<CategoryDto> deleteCategory(@PathVariable Long id) {
		CategoryDto deleteCategory = categoryService.deleteCategory(id);

		return new ResponseEntity<CategoryDto>(deleteCategory, HttpStatus.OK);

	}

}
