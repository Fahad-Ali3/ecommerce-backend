package com.ecommerce.backend.service.impl;

import com.ecommerce.backend.exceptions.ResourceNotFoundException;
import com.ecommerce.backend.repositories.CategoryRepo;
import com.ecommerce.backend.dto.CategoryDTO;
import com.ecommerce.backend.mapping.CategoryMapping;
import com.ecommerce.backend.model.Category;
import com.ecommerce.backend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private CategoryMapping categoryMapping;


    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category savedCategory=categoryRepo.save(categoryMapping.toCategory(categoryDTO));
        return categoryMapping.toCategoryDTO(savedCategory);
    }

    @Override
    public CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDTO) {
        Category category=categoryMapping.toCategory(getCategoryById(categoryId));
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        categoryRepo.save(category);
        return categoryMapping.toCategoryDTO(category);
    }

    @Override
    public CategoryDTO deleteCategory(Long categoryId) {
        Category category=categoryMapping.toCategory(getCategoryById(categoryId));
        categoryRepo.delete(category);
        return categoryMapping.toCategoryDTO(category);
    }

    @Override
    public CategoryDTO getCategoryById(Long categoryId) {
        Category category=categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category",categoryId,"Category-ID"));
        return categoryMapping.toCategoryDTO(category);
    }

    @Override
    public Page<CategoryDTO> getAllCategories(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Category> categoryPage = categoryRepo.findAll(pageable);


        return categoryPage.map(categoryMapping::toCategoryDTO);
    }

}
