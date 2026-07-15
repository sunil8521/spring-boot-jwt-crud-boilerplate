package com.CURD.curd_operation.service;

import com.CURD.curd_operation.dto.CreateProductDto;
import com.CURD.curd_operation.dto.PatchProductDto;
import com.CURD.curd_operation.dto.ProductDto;
import com.CURD.curd_operation.dto.UserDto;
import com.CURD.curd_operation.entities.Product;
import com.CURD.curd_operation.entities.Role;
import com.CURD.curd_operation.entities.User;
import com.CURD.curd_operation.exception.ResourceNotFoundException;
import com.CURD.curd_operation.exception.UnauthorizedAccessException;
import com.CURD.curd_operation.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public ProductDto createProduct(CreateProductDto request, User user) {
        Product product = new Product();
        product.setName(request.getName());
        product.setUser(user);

        Product savedProduct = productRepository.save(product);
        return mapToDto(savedProduct);
    }


    public List<ProductDto> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }


    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        return mapToDto(product);
    }

    @Transactional
    public ProductDto patchProduct(Long id, PatchProductDto request, User currentUser) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        // Allow update only if current user is the owner or an ADMIN
        if (!product.getUser().getId().equals(currentUser.getId()) && currentUser.getRole() != Role.ADMIN) {
            throw new UnauthorizedAccessException("You are not authorized to update this product");
        }

        if (request.getName() != null) {
            product.setName(request.getName());
        }

        Product updatedProduct = productRepository.save(product);
        return mapToDto(updatedProduct);
    }

    @Transactional
    public void deleteProduct(Long id, User currentUser) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        // Allow delete only if current user is the owner or an ADMIN
        if (!product.getUser().getId().equals(currentUser.getId()) && currentUser.getRole() != Role.ADMIN) {
            throw new UnauthorizedAccessException("You are not authorized to delete this product");
        }

        productRepository.delete(product);
    }

    private ProductDto mapToDto(Product product) {
        User owner = product.getUser();
        UserDto ownerDto = new UserDto(owner.getId(), owner.getUsername(), owner.getRole());
        return new ProductDto(product.getId(), product.getName(), ownerDto);
    }
}
