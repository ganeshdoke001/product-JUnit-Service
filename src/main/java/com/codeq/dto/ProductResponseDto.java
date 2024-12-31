package com.codeq.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDto {

    private Integer id;
    private String name;
    private Double price;
    private Integer quantity;
}
