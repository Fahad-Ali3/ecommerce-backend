package com.ecommerce.backend.service.impl;

import com.ecommerce.backend.exceptions.ResourceNotFoundException;
import com.ecommerce.backend.repositories.CategoryRepo;
import com.ecommerce.backend.repositories.ProductRepo;
import com.ecommerce.backend.dto.ProductDTO;
import com.ecommerce.backend.mapping.ProductMapping;
import com.ecommerce.backend.model.Category;
import com.ecommerce.backend.model.Product;
import com.ecommerce.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ProductMapping productMapping;

    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public ProductDTO createProduct(ProductDTO productDTO,Long categoryId) {
        Category category=categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category",categoryId,"Category-ID"));
        productDTO.setCategoryId(category.getCategoryId());
        productDTO.setCategoryName(category.getName());

        Product product=productRepo.save(productMapping.toProduct(productDTO));

        List<Product> products=category.getProducts();
        products.add(product);
        category=categoryRepo.save(category);

        return productMapping.toProductDTO(product);
    }

    @Override
    public ProductDTO updateProduct(ProductDTO productDTO, Long productId,Long categoryId) {
        Product product=productMapping.toProduct(getProductById(productId));
        Category category=categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category",categoryId,"Category-ID"));

        product.setProductName(productDTO.getProductName());
        product.setPrice(productDTO.getPrice());
        product.setDescription(productDTO.getDescription());
        product.setQuantity(productDTO.getQuantity());
        product.setLocalDateTime(LocalDateTime.now());
        product.setImageUrl(productDTO.getImageUrl());
        product.setCategory(category);
        productRepo.save(product);

        return productMapping.toProductDTO(product);
    }

    @Override
    public ProductDTO deleteProduct(Long productId) {
        Product product=productMapping.toProduct(getProductById(productId));
        productRepo.delete(product);
        return productMapping.toProductDTO(product);
    }

    @Override
    public ProductDTO getProductById(Long productId) {
        Product product=productRepo.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product",productId,"Product-ID"));
        return productMapping.toProductDTO(product);
    }

    @Override
    public Page<ProductDTO> getAllProducts(Integer page, Integer size, String sortBy) {


        // Create a Pageable object with page, size, and sorting information
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());

        // Retrieve the paginated and sorted results from the repository
        Page<Product> productPage = productRepo.findAll(pageable);

        // Convert the list of products into DTOs
        Page<ProductDTO> productDTOPage = productPage.map(productMapping::toProductDTO);

        return productDTOPage;
    }

}
