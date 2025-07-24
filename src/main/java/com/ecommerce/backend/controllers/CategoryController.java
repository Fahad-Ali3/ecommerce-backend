package com.ecommerce.backend.controllers;

import com.ecommerce.backend.dto.CategoryDTO;
import com.ecommerce.backend.service.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/category")
@Tag(name = "Category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> getCategories(
            @RequestParam(defaultValue = "0") int page,        // Default page is 0
            @RequestParam(defaultValue = "10") int size,       // Default size is 10
            @RequestParam(defaultValue = "categoryId") String sortBy) {  // Default sorting by categoryId

        // Get the paginated and sorted categories from the service
        Page<CategoryDTO> categoryPage = categoryService.getAllCategories(page, size, sortBy);

        // Prepare the response
        Map<String, Object> response = new HashMap<>();
        response.put("content", categoryPage.getContent());  // List of categories on the current page
        response.put("currentPage", categoryPage.getNumber());  // Current page number
        response.put("totalPages", categoryPage.getTotalPages());  // Total number of pages
        response.put("totalItems", categoryPage.getTotalElements());  // Total number of categories
        response.put("start", Math.max(categoryPage.getNumber() * categoryPage.getSize() + 1, 0));  // Starting index of the current page
        response.put("end", Math.min((categoryPage.getNumber() + 1) * categoryPage.getSize(), (int) categoryPage.getTotalElements()));  // Ending index of the current page
        response.put("sorted", categoryPage.getSort().isSorted());  // Whether the results are sorted

        // Return the response with a 200 OK status
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/{id}")

    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id){
        return new ResponseEntity<>(categoryService.getCategoryById(id),HttpStatus.OK);
    }

    @PostMapping("/")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<CategoryDTO> saveCategory(@Valid @RequestBody CategoryDTO categoryDTO){
        return new ResponseEntity<>(categoryService.createCategory(categoryDTO),HttpStatus.CREATED);
        
    }

    @PutMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @PathVariable Long id,@RequestBody CategoryDTO categoryDTO){
        return new ResponseEntity<>(categoryService.updateCategory(id,categoryDTO),HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long id){
        return new ResponseEntity<>(categoryService.deleteCategory(id),HttpStatus.OK);
    }


}
