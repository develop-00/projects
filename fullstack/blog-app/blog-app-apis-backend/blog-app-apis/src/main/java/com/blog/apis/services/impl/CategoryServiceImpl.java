package com.blog.apis.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.apis.entities.Category;
import com.blog.apis.exceptions.ResourceNotFoundException;
import com.blog.apis.payloads.CategoryDTO;
import com.blog.apis.repositories.CategoryRepo;
import com.blog.apis.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private ModelMapper modelMapper;

	// create
	@Override
	public CategoryDTO createCategory(CategoryDTO categoryDTO) {

		Category category = this.modelMapper.map(categoryDTO, Category.class);
		Category addedCategory = this.categoryRepo.save(category);
		return this.modelMapper.map(addedCategory, CategoryDTO.class);
	}

	// update
	@Override
	public CategoryDTO updateCategory(CategoryDTO categoryDTO, Integer categoryId) {

		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "category Id", String.valueOf(categoryId)));

		category.setCategoryTitle(categoryDTO.getCategoryTitle());
		category.setCategoryDescription(categoryDTO.getCategoryDescription());
		Category updatedCategory = this.categoryRepo.save(category);

		return this.modelMapper.map(updatedCategory, CategoryDTO.class);
	}

	// delete
	@Override
	public void deleteCategory(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "category Id", String.valueOf(categoryId)));
		this.categoryRepo.delete(category);
	}

	// get
	@Override
	public CategoryDTO getCategory(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "category Id", String.valueOf(categoryId)));
		return this.modelMapper.map(category, CategoryDTO.class);
	}

	// getAll
	@Override
	public List<CategoryDTO> getAllCategories() {
		List<Category> allCategories = this.categoryRepo.findAll();
		List<CategoryDTO> allCategoriesDTO = allCategories.stream()
				.map(category -> this.modelMapper.map(category, CategoryDTO.class)).collect(Collectors.toList());
		return allCategoriesDTO;
	}
}
