package com.blog.apis.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.blog.apis.payloads.CategoryDTO;


public interface CategoryService {

	CategoryDTO createCategory(CategoryDTO categoryDTO);

	CategoryDTO updateCategory(CategoryDTO categoryDTO, Integer categoryId);

	void deleteCategory(Integer categoryId);

	CategoryDTO getCategory(Integer categoryId);

	List<CategoryDTO> getAllCategories();

}
