package com.codeq.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codeq.dto.ApiResponse;
import com.codeq.dto.ProductRequestDto;
import com.codeq.dto.ProductResponseDto;
import com.codeq.processor.ProductProcessor;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "/api/v1/products")
public class ProductController {

    private final ProductProcessor processor;

    public ProductController(final ProductProcessor processor) {

        this.processor = processor;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponseDto>>> fetchAll() {

        final List<ProductResponseDto> products = processor.fetchAll();
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(products));
    }

    @GetMapping(path = "/{id}/productId")
    public ResponseEntity<ApiResponse<ProductResponseDto>> fetchById(
            @PathVariable Integer id) {

        final ProductResponseDto product = processor.fetchById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(product));
    }

    @GetMapping(path = "/{name}/productName")
    public ResponseEntity<ApiResponse<ProductResponseDto>> fetchByName(
            @PathVariable String name) {

        final ProductResponseDto product = processor.fetchByName(name);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(product));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<String>> save(
            @RequestBody ProductRequestDto requestDto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(processor.save(requestDto)));
    }

    @PutMapping()
    public ResponseEntity<ApiResponse<String>> update(
            @RequestParam Integer id,
            @RequestBody ProductRequestDto requestDto) {

        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(ApiResponse.success(processor.update(id, requestDto)));
    }

    @DeleteMapping()
    public ResponseEntity<ApiResponse<String>> delete(
            @RequestParam Integer id) {

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(ApiResponse.success(processor.delete(id)));
    }
}
