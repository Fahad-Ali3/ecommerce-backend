package com.ecommerce.backend.mapping;

import com.ecommerce.backend.exceptions.ResourceNotFoundException;
import com.ecommerce.backend.repositories.CategoryRepo;
import com.ecommerce.backend.dto.ProductDTO;
import com.ecommerce.backend.model.Category;
import com.ecommerce.backend.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductMapping {


    @Autowired
    private CategoryRepo categoryRepo;

    public ProductDTO toProductDTO(Product product){
        ProductDTO productDTO=new ProductDTO();
        productDTO.setProductId(product.getProductId());
        productDTO.setPrice(product.getPrice());
        productDTO.setCategoryId(product.getCategory().getCategoryId());
        productDTO.setCategoryName(product.getCategory().getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setQuantity(product.getQuantity());
        productDTO.setLocalDateTime(product.getLocalDateTime());
        productDTO.setImageUrl(product.getImageUrl());
        productDTO.setProductName(product.getProductName());

        return productDTO;
    }

    public Product toProduct(ProductDTO productDTO){
        Category category=categoryRepo.findById(productDTO.getCategoryId()).orElseThrow(()->new ResourceNotFoundException("Category", productDTO.getCategoryId(),"Product-ID"));
        Product product=new Product();
        product.setCategory(category);
        product.setImageUrl(productDTO.getImageUrl());
        product.setLocalDateTime(productDTO.getLocalDateTime());
        product.setPrice(productDTO.getPrice());
        product.setQuantity(productDTO.getQuantity());
        product.setProductName(productDTO.getProductName());
        product.setProductId(productDTO.getProductId());
        product.setDescription(product.getDescription());
        return product;

    }
}
