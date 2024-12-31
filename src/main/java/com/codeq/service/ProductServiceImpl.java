package com.codeq.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.codeq.entity.Product;
import com.codeq.exception.TransientDBAccessException;
import com.codeq.model.Constants;
import com.codeq.repository.ProductRepository;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    public ProductServiceImpl(final ProductRepository repository) {

        this.repository = repository;
    }

    @Override
    public Product get(
            @NotNull Integer id) {

        try {
            return this.repository.findById(id)
                    .orElseThrow();
        } catch (NoSuchElementException e) {
            throw new TransientDBAccessException(e.getLocalizedMessage());
        }
    }

    @Override
    public List<Product> getAll() {

        try {
            return this.repository.findAll();
        } catch (Exception ex) {
            throw new TransientDBAccessException(ex.getLocalizedMessage());
        }
    }

    @Override
    public String save(
            @NotNull Product product) {

        try {
            this.repository.save(product);
            return Constants.PRODUCT_SAVED;
        } catch (IllegalArgumentException | OptimisticLockingFailureException e) {
            throw new TransientDBAccessException(e.getLocalizedMessage());
        }

    }

    @Override
    public String update(
            @NotNull Integer id,
            @NotNull Product product) {

        try {
            Product prod = this.repository.findById(id)
                    .orElseThrow();

            prod.setId(id);
            prod.setName(product.getName());
            prod.setPrice(product.getPrice());
            prod.setQuantity(product.getQuantity());
            this.repository.save(prod);
            return Constants.PRODUCT_UPDATED;
        } catch (NoSuchElementException | IllegalArgumentException | OptimisticLockingFailureException e) {
            throw new TransientDBAccessException(e.getLocalizedMessage());
        }
    }

    @Override
    public String delete(
            @NotNull Integer id) {

        try {
            Product product = this.repository.findById(id)
                    .orElseThrow();
            this.repository.delete(product);
            return Constants.PRODUCT_DELETED;
        } catch (NoSuchElementException | IllegalArgumentException | OptimisticLockingFailureException e) {
            throw new TransientDBAccessException(e.getLocalizedMessage());
        }
    }

    @Override
    public Product findByName(
            @NotBlank String name) {

        try {
            return this.repository.findByName(name)
                    .orElseThrow();
        } catch (NoSuchElementException e) {
            throw new TransientDBAccessException(e.getLocalizedMessage());
        }
    }

}
