package com.ecommerce.backend.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CategoryDTO {

    private Long categoryId; // Auto-generated, not validated

    @NotEmpty(message = "Category name must not be empty")
    private String name;

    private String description;


    private List<ProductDTO> productDTOS;
}
