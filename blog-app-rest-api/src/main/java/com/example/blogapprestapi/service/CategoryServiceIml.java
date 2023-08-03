package com.example.blogapprestapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.blogapprestapi.exception.ResourceNotFoundException;
import com.example.blogapprestapi.model.Category;
import com.example.blogapprestapi.payload.CategoryDto;
import com.example.blogapprestapi.repository.CategoryRepository;

@Service
public class CategoryServiceIml implements CategoryService {

	private CategoryRepository categoryRepository;
	private ModelMapper mapper;

	public CategoryServiceIml(CategoryRepository categoryRepository, ModelMapper mapper) {
		super();
		this.categoryRepository = categoryRepository;
		this.mapper = mapper;
	}

	// catgory dto map to cateogry entitiy
	private Category mapToEntity(CategoryDto categoryDto) {
		Category category = mapper.map(categoryDto, Category.class);
		return category;
	}

	// catgory entitiy map to cateogry dto
	private CategoryDto mapToDto(Category category) {
		CategoryDto categoryDto = mapper.map(category, CategoryDto.class);
		return categoryDto;
	}

	@Override
	public CategoryDto addCategory(CategoryDto categoryDto) {
		Category category = mapToEntity(categoryDto);
		Category response = categoryRepository.save(category);
		return mapToDto(response);

	}

	@Override
	public Category getCategory(Long categoryId) {
		Category category = categoryRepository.findById(categoryId).orElseThrow(() ->

		new ResourceNotFoundException("category", "id", categoryId));

		return category;

	}

	@Override
	public CategoryDto getCategoryById(Long categoryId) {
		Category category = getCategory(categoryId);
		return mapToDto(category);
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		List<CategoryDto> categories = categoryRepository.findAll().stream().map((category) -> mapToDto(category))
				.collect(Collectors.toList());
		return categories;
	}

	@Override
	public CategoryDto deleteCategory(Long categoryId) {
		Category category = getCategory(categoryId);
		categoryRepository.delete(category);
		return mapToDto(category);
	}

	@Override
	public CategoryDto updateCategory(Long categoryId, CategoryDto categoryDto) {
		Category category = getCategory(categoryId);
		category.setTitle(categoryDto.getTitle());
		category.setDescription(categoryDto.getContent());
		category.setContent(categoryDto.getContent());
		Category updateCategory = categoryRepository.save(category);

		return mapToDto(updateCategory);
	}

}
