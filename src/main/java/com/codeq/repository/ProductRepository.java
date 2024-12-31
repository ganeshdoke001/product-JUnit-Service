package com.codeq.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codeq.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    /**
     * @param name
     * @return
     */
    Optional<Product> findByName(
            String name);
}
