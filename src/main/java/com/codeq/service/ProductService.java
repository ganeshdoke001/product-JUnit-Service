package com.codeq.service;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.codeq.entity.Product;

public interface ProductService {

    /**
     * @param id
     * @return
     */
    Product get(
            @NotNull Integer id);

    List<Product> getAll();

    /**
     * @param product
     * @return
     */
    String save(
            @NotNull Product product);

    /**
     * @param id
     * @param product
     * @return
     */
    String update(
            @NotNull Integer id,
            @NotNull Product product);

    /**
     * @param id
     * @return
     */
    String delete(
            @NotNull Integer id);

    /**
     * @param name
     * @return
     */
    Product findByName(
            @NotBlank String name);
}
