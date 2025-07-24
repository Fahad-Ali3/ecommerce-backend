package com.ecommerce.backend.mapping;

import com.ecommerce.backend.dto.CategoryDTO;
import com.ecommerce.backend.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryMapping {


    @Autowired
    private ProductMapping productMapping;

    public CategoryDTO toCategoryDTO(Category category){
        CategoryDTO categoryDTO=new CategoryDTO();
        categoryDTO.setCategoryId(category.getCategoryId());
        categoryDTO.setDescription(category.getDescription());
        categoryDTO.setName(category.getName());
        if(category.getProducts()!=null) {
            categoryDTO.setProductDTOS(category.getProducts().stream().map(productMapping::toProductDTO).toList());
        }
        return categoryDTO;
    }

    public Category toCategory(CategoryDTO categoryDTO){
        Category category=new Category();
        category.setCategoryId(categoryDTO.getCategoryId());
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());

        if(categoryDTO.getProductDTOS()!=null) {
            category.setProducts(categoryDTO.getProductDTOS().stream().map(productMapping::toProduct).toList());
        }

        return category;
    }

}
