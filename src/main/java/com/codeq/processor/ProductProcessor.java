package com.codeq.processor;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import io.micrometer.common.util.StringUtils;

import com.codeq.dto.ProductRequestDto;
import com.codeq.dto.ProductResponseDto;
import com.codeq.entity.Product;
import com.codeq.exception.TerminalProcessException;
import com.codeq.service.ProductService;

@Service
public class ProductProcessor {

    private final ProductService service;

    public ProductProcessor(final ProductService service) {

        this.service = service;
    }

    public List<ProductResponseDto> fetchAll() {

        return this.service.getAll()
                .stream()
                .filter(Objects::nonNull)
                .map(this::mapToProductResponseDto)
                .collect(Collectors.toList());

    }

    public ProductResponseDto fetchById(
            Integer id) {

        if (ObjectUtils.isEmpty(id)) {
            throw new TerminalProcessException("Please provide a valid productId");
        }

        return this.mapToProductResponseDto(this.service.get(id));

    }

    public ProductResponseDto fetchByName(
            String name) {

        if (StringUtils.isBlank(name)) {
            throw new TerminalProcessException("Please provide a valid productId");
        }

        return this.mapToProductResponseDto(this.service.findByName(name));
    }

    public String save(
            ProductRequestDto request) {

        if (ObjectUtils.isEmpty(request)) {
            throw new TerminalProcessException("Please provide a valid product request");
        }

        return this.service.save(this.mapToProduct(request));

    }

    public String update(
            Integer id,
            ProductRequestDto request) {

        if (ObjectUtils.isEmpty(request) && ObjectUtils.isEmpty(id)) {
            throw new TerminalProcessException("Please provide a valid productId and product request");
        }
        return this.service.update(id, this.mapToProduct(request));

    }

    public String delete(
            Integer id) {

        if (ObjectUtils.isEmpty(id)) {
            throw new TerminalProcessException("Please provide a valid productId ");
        }
        return this.service.delete(id);

    }

    private Product mapToProduct(
            ProductRequestDto request) {

        return Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .build();
    }

    private ProductResponseDto mapToProductResponseDto(
            Product product) {

        return ProductResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .build();
    }

}
