package com.ecommerce.backend.repositories;

import com.ecommerce.backend.model.Category;
import com.ecommerce.backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product,Long> {

    List<Product> findByCategory(Category category);
}
