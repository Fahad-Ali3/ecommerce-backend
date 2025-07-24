package com.ecommerce.backend.controllers;

import com.ecommerce.backend.repositories.ProductRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ecommerce.backend.dto.ProductDTO;
import com.ecommerce.backend.mapping.CategoryMapping;
import com.ecommerce.backend.service.CategoryService;
import com.ecommerce.backend.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Slf4j
@Tag(name = "Product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryMapping categoryMapping;


    @GetMapping("/product/")
    public ResponseEntity<Map<String, Object>> getProducts(
            @RequestParam(defaultValue = "0") int page,  // Default page number is 0 (first page)
            @RequestParam(defaultValue = "10") int size,  // Default page size is 10
            @RequestParam(defaultValue = "productId") String sortBy) {  // Default sorting by productId

        // Get the paginated and sorted products from the service
        Page<ProductDTO> productsPage = productService.getAllProducts(page, size, sortBy);

        // Prepare the response
        Map<String, Object> response = new HashMap<>();
        response.put("content", productsPage.getContent());  // List of products on the current page
        response.put("currentPage", productsPage.getNumber());  // Current page number
        response.put("totalPages", productsPage.getTotalPages());  // Total number of pages
        response.put("totalItems", productsPage.getTotalElements());  // Total number of products
        response.put("start", Math.max(productsPage.getNumber() * productsPage.getSize() + 1, 0));  // Starting index of the current page
        response.put("end", Math.min((productsPage.getNumber() + 1) * productsPage.getSize(), (int) productsPage.getTotalElements()));  // Ending index of the current page
        response.put("sorted", productsPage.getSort().isSorted());  // Whether the results are sorted

        // Return the response with a 200 OK status
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/product/{id}/")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id){

        System.out.println(productRepo.findByCategory(categoryMapping.toCategory(categoryService.getCategoryById(1L))));
        return new ResponseEntity<>(productService.getProductById(id),HttpStatus.OK);
    }

    @DeleteMapping("/product/{id}/")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long productId){
        return new ResponseEntity<>(productService.deleteProduct(productId),HttpStatus.OK);
    }

    @PutMapping("/product/{productId}/")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long productId,@Valid @RequestBody ProductDTO productDTO,@RequestParam(name = "category_id") Long categoryId){
        return new ResponseEntity<>(productService.updateProduct(productDTO,productId,categoryId),HttpStatus.CREATED);
    }

    @PostMapping("/category/{id}/product/")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> createProduct(@PathVariable Long id,
                                           @RequestParam("product") String productJson,
                                           @RequestParam("image") MultipartFile imageFile) {
        try {
            log.info("Received request to create product for category ID: {}", id);
            log.info("Product JSON: {}", productJson);
            log.info("Image file: {} ({} bytes)", imageFile.getOriginalFilename(), imageFile.getSize());

            // Convert product JSON string to ProductDTO
            ObjectMapper objectMapper = new ObjectMapper();
            ProductDTO productDTO = objectMapper.readValue(productJson, ProductDTO.class);
            log.info("Parsed ProductDTO: {}", productDTO);




            // Create the product using the productService
            ProductDTO createdProduct = productService.createProduct(productDTO, id);
            log.info("Product created successfully: {}", createdProduct);

            return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
        } catch (JsonProcessingException e) {
            log.error("Failed to parse product JSON: {}", productJson, e);
            return new ResponseEntity<>("Invalid product JSON: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            log.error("Failed to process image: {}", imageFile.getOriginalFilename(), e);
            return new ResponseEntity<>("Failed to process image: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            log.error("Unexpected error occurred", e);
            return new ResponseEntity<>("Unexpected error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String processImage(MultipartFile imageFile) throws IOException {
        if (imageFile.isEmpty()) {
            throw new RuntimeException("Failed to store image: File is empty.");
        }

        // Create the directory where the image will be stored
        Path path = Paths.get("images/uploads/");
        Files.createDirectories(path);

        // Create a unique image name (timestamp + original file name)
        String imageFileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
        Path destinationPath = path.resolve(imageFileName);

        // Save the image file to the destination path
        Files.copy(imageFile.getInputStream(), destinationPath, StandardCopyOption.REPLACE_EXISTING);

        // Return the image URL or path (modify this to suit your needs)
        return "/images/uploads/" + imageFileName;
    }

}
