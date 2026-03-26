package com.aws.learn.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aws.learn.dto.ProductDto;
import com.aws.learn.model.Product;
import com.aws.learn.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final S3Service s3Service;

    public void saveProduct(ProductDto dto, MultipartFile file) throws Exception {
        String savedToS3 = s3Service.uploadToS3(file);
        Product product = new Product();
        product.setDescription(dto.getDescription());
        product.setImageUrl(savedToS3);
        product.setPrice(dto.getPrice());
        product.setProdName(dto.getProdName());
        this.productRepository.save(product);
    }

    public List<ProductDto> getAllProducts() {
        return this.productRepository.findAll().stream().map(doc -> {
            ProductDto dto = new ProductDto();
            dto.setDescription(doc.getDescription());
            dto.setProdName(doc.getProdName());
            dto.setPrice(doc.getPrice());
            dto.setImageUrl(doc.getImageUrl());
            return dto;
        }).toList();
    }

    public void saveAllProduct(List<ProductDto> productDto) {
        List<Product> list = productDto.stream().map(dto->{
            Product product = Product.builder().prodName(dto.getProdName()).description(dto.getDescription()).imageUrl(dto.getImageUrl()).price(dto.getPrice()).build();
            return product;
        }).toList();
        System.out.println(list);
        this.productRepository.saveAll(list);
    }
}
