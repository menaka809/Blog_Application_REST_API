package com.example.blogapprestapi.service;

import java.util.List;

import com.example.blogapprestapi.model.Category;
import com.example.blogapprestapi.payload.CategoryDto;

public interface CategoryService {
	
	CategoryDto addCategory(CategoryDto categoryDto);
	Category getCategory(Long categoryId);
	CategoryDto getCategoryById(Long categoryId);
	List<CategoryDto> getAllCategory();
	CategoryDto deleteCategory(Long categoryId);
	CategoryDto updateCategory(Long categoryId,CategoryDto categoryDto);
	

}
