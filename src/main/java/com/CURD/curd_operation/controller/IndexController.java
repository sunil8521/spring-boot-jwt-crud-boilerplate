package com.CURD.curd_operation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class IndexController {

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> getProjectInfo() {
        Map<String, Object> info = new HashMap<>();
        info.put("projectName", "Spring Boot Clean CRUD & JWT Auth API");
        info.put("description", "A production-grade REST API template with user registration, authentication via stateless JWTs, and secure unidirectional Product CRUD.");
        info.put("version", "1.0.0");

        Map<String, Object> routes = new HashMap<>();

        // Auth routes
        Map<String, String> authRoutes = new HashMap<>();
        authRoutes.put("POST /api/v1/auth/register", "Create a new user account (returns JWT token)");
        authRoutes.put("POST /api/v1/auth/login", "Authenticate credentials and obtain JWT token");
        routes.put("authentication", authRoutes);

        // Product routes
        Map<String, String> productRoutes = new HashMap<>();
        productRoutes.put("GET /api/v1/products", "Fetch all products (Requires Bearer token)");
        productRoutes.put("GET /api/v1/products/{id}", "Fetch product by ID (Requires Bearer token)");
        productRoutes.put("POST /api/v1/products", "Create a new product (Requires Bearer token)");
        productRoutes.put("PATCH /api/v1/products/{id}", "Modify own product name (Requires Bearer token, owners or ADMIN only)");
        productRoutes.put("DELETE /api/v1/products/{id}", "Delete own product (Requires Bearer token, owners or ADMIN only)");
        routes.put("products", productRoutes);

        info.put("endpoints", routes);

        Map<String, String> authFlow = new HashMap<>();
        authFlow.put("headerFormat", "Authorization: Bearer <jwt_token>");
        authFlow.put("step1", "Register an account using POST /api/v1/auth/register");
        authFlow.put("step2", "Login using POST /api/v1/auth/login to get a JWT token");
        authFlow.put("step3", "Attach the token in the headers for all product operations");
        info.put("securityFlow", authFlow);

        return ResponseEntity.ok(info);
    }
}
