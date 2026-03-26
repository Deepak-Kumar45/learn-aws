package com.aws.learn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aws.learn.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
