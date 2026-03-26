package com.aws.learn.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(value = Include.NON_NULL)
public class ProductDto {
    private Long id;
    private String prodName;
    private String description;
    private Double price;
    private String imageUrl;
}
