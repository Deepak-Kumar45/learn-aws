package com.aws.learn.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.aws.learn.dto.ProductDto;
import com.aws.learn.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> saveProduct(
            @RequestPart ProductDto productDto,
            @RequestPart(value = "s3file", required = false) MultipartFile file) {
        try {
            productService.saveProduct(productDto, file);
            return ResponseEntity.status(HttpStatus.OK).body("Product Added.");
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @PostMapping("/all")
    public ResponseEntity<String> saveAllProduct(@RequestBody List<ProductDto> productDto) {
        try {
            productService.saveAllProduct(productDto);
            return ResponseEntity.status(HttpStatus.OK).body("All Product Added.");
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getProducts() {
        try {
            List<ProductDto> allProducts = productService.getAllProducts();
            return ResponseEntity.status(HttpStatus.OK).body(allProducts);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

}
