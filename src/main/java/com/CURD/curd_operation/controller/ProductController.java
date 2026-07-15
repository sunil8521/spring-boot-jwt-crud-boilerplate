package com.CURD.curd_operation.controller;

import com.CURD.curd_operation.dto.CreateProductDto;
import com.CURD.curd_operation.dto.PatchProductDto;
import com.CURD.curd_operation.dto.ProductDto;
import com.CURD.curd_operation.security.CustomUserDetails;
import com.CURD.curd_operation.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(
            @Valid @RequestBody CreateProductDto request,
            @AuthenticationPrincipal CustomUserDetails currentUser) {
        ProductDto created = productService.createProduct(request, currentUser.getUser());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductDto> patchProduct(
            @PathVariable Long id,
            @Valid @RequestBody PatchProductDto request,
            @AuthenticationPrincipal CustomUserDetails currentUser) {
        ProductDto updated = productService.patchProduct(id, request, currentUser.getUser());
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails currentUser) {
        productService.deleteProduct(id, currentUser.getUser());
        return ResponseEntity.noContent().build();
    }
}
