package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.ProductDTO;
import org.springframework.data.domain.Page;

public interface ProductService {

    public ProductDTO createProduct(ProductDTO productDTO,Long categoryId);
    public ProductDTO updateProduct(ProductDTO productDTO,Long productId,Long categoryId);
    public ProductDTO deleteProduct(Long productId);
    public ProductDTO getProductById(Long productId);
    public Page<ProductDTO> getAllProducts(Integer page, Integer size, String sortBy);
}
