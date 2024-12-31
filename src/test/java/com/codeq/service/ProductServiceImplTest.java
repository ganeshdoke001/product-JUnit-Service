package com.codeq.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.codeq.entity.Product;
import com.codeq.exception.TransientDBAccessException;
import com.codeq.repository.ProductRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringJUnitConfig(classes = ProductServiceImplTest.class)
class ProductServiceImplTest {

    @InjectMocks
    private ProductServiceImpl serviceImpl;

    @Mock
    private ProductRepository repository;

    private Product product;

    @BeforeEach
    public void setUp() {

        serviceImpl = new ProductServiceImpl(repository);
        product = getProducts().stream()
                .filter(x -> x.getId() == 1)
                .findFirst()
                .get();
    }

    @Test
    void getProductsTest() {

        // Given
        final List<Product> expected = getProducts();
        doReturn(expected).when(repository)
                .findAll();
        // When
        final List<Product> actual = serviceImpl.getAll();
        // Then
        assertEquals(actual, expected);
    }

    @Test
    void getProductsExceptionTest() {

        when(repository.findAll()).thenThrow(new TransientDBAccessException("Error"));
        assertThrows(TransientDBAccessException.class, () -> serviceImpl.getAll());

    }

    @Test
    void getProductTest() {

        doReturn(getProduct()).when(repository)
                .findById(Integer.valueOf(1));

        final Product actual = serviceImpl.get(Integer.valueOf(1));

        assertEquals(actual, getProduct().get());

    }

    @Test
    void getProductExceptionTest() {

        when(repository.findById(anyInt())).thenThrow(new NoSuchElementException("Error"));
        assertThrows(TransientDBAccessException.class, () -> serviceImpl.get(anyInt()));
    }

    @Test
    void saveTest() {

        when(repository.save(product)).thenReturn(product);
        assertNotNull(serviceImpl.save(product));
    }

    @Test
    void saveExceptionTest() {

        when(repository.save(null)).thenThrow(new IllegalArgumentException("Error"));
        assertThrows(TransientDBAccessException.class, () -> serviceImpl.save(null));
        assertThrows(TransientDBAccessException.class, () -> serviceImpl.save(any(Product.class)));
    }

    @Test
    void updateTest() {

        final Integer id = Integer.valueOf(1);

        when(repository.findById(id)).thenReturn(getProduct());
        when(repository.save(getProduct().get())).thenReturn(product);
        assertNotNull(serviceImpl.update(id, product));
    }

    @Test
    void updateExceptionTest() {

        final Integer id = Integer.valueOf(1);
        when(repository.findById(id)).thenReturn(getProduct());
        when(repository.save(any(Product.class))).thenThrow(new IllegalArgumentException("Error"));
        assertThrows(TransientDBAccessException.class, () -> serviceImpl.update(id, product));

        when(repository.findById(id)).thenReturn(getProduct());
        when(repository.save(any(Product.class))).thenThrow(new OptimisticLockingFailureException("Error"));
        assertThrows(TransientDBAccessException.class, () -> serviceImpl.update(id, product));

        when(repository.findById(id)).thenThrow(new NoSuchElementException("Error"));
        assertThrows(TransientDBAccessException.class, () -> serviceImpl.update(id, product));

    }

    @Test
    void findByNameTest() {

        final String name = "TV";
        final Optional<Product> product = getProduct().stream()
                .filter(x -> name.equalsIgnoreCase(x.getName()))
                .findFirst();
        when(repository.findByName(name)).thenReturn(product);
        assertEquals(serviceImpl.findByName(name), product.get());
    }

    @Test
    void findByNameExceptionTest() {

        when(repository.findByName(anyString())).thenThrow(new NoSuchElementException("Error"));
        assertThrows(TransientDBAccessException.class, () -> serviceImpl.findByName(anyString()));
    }

    @Test
    void deleteTest() {

        final Integer id = Integer.valueOf(1);
        Optional<Product> prod = getProduct();
        when(repository.findById(id)).thenReturn(prod);
        serviceImpl.delete(id);
        verify(repository, times(1)).delete(prod.get());;
    }

    @Test
    void deleteExceptionTest() {

        final Integer id = Integer.valueOf(1);
        Optional<Product> prod = getProduct();
        when(repository.findById(id)).thenThrow(new NoSuchElementException("Error"));
        assertThrows(TransientDBAccessException.class, () -> serviceImpl.delete(id));

        doReturn(prod).when(repository)
                .findById(id);
        doThrow(new IllegalArgumentException("Error")).when(repository)
                .delete(prod.get());
        assertThrows(TransientDBAccessException.class, () -> serviceImpl.delete(id));
        doThrow(new OptimisticLockingFailureException("Error")).when(repository)
                .delete(prod.get());
        assertThrows(TransientDBAccessException.class, () -> serviceImpl.delete(id));
    }

    private static Optional<Product> getProduct() {

        return getProducts().stream()
                .filter(x -> x.getId() == 1)
                .findFirst();
    }

    private static List<Product> getProducts() {

        Product product = new Product();

        product.setId(1);
        product.setName("TV");
        product.setPrice(45000.0);
        product.setQuantity(10);

        List<Product> products = new ArrayList<>();
        products.add(product);

        product = new Product();
        product.setId(2);
        product.setName("Iphone");
        product.setPrice(145000.0);
        product.setQuantity(5);

        products.add(product);

        return products;
    }

}
