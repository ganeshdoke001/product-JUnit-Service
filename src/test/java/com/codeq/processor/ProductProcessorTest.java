package com.codeq.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.codeq.dto.ProductRequestDto;
import com.codeq.dto.ProductResponseDto;
import com.codeq.entity.Product;
import com.codeq.exception.TerminalProcessException;
import com.codeq.service.ProductService;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@SpringJUnitConfig
class ProductProcessorTest {

    @InjectMocks
    private ProductProcessor processor;

    @Mock
    private ProductService service;

    @ParameterizedTest
    @MethodSource("getProductArg")
    void fetchAll(
            List<Product> products,
            List<ProductResponseDto> expected) {

        when(service.getAll()).thenReturn(products);
        List<ProductResponseDto> actual = processor.fetchAll();
        assertEquals(actual, expected);
    }

    @Test
    void fetchByIdTest() {

        Integer id = Integer.valueOf(1);
        Product product = getProducts().stream()
                .filter(x -> x.getId() == id)
                .findFirst()
                .get();
        ProductResponseDto expected = getProductResponseDtoList().stream()
                .filter(m -> m.getId() == id)
                .findFirst()
                .get();

        when(service.get(id)).thenReturn(product);
        ProductResponseDto actual = processor.fetchById(id);
        verify(service, times(1)).get(eq(id));
        assertEquals(actual, expected);
    }

    @Test
    void fetchByIdExceptionTest() {

        assertThrows(TerminalProcessException.class, () -> processor.fetchById(null));
        verifyNoInteractions(service);
    }

    @Test
    void saveTest() {

        ProductRequestDto requestDto = getProductRequest();
        processor.save(requestDto);
        ArgumentCaptor<Product> capture = ArgumentCaptor.forClass(Product.class);
        verify(service, times(1)).save(capture.capture());
        Product captureRequestDtos = capture.getValue();
        assertEquals(requestDto.getName(), captureRequestDtos.getName());
        assertEquals(requestDto.getPrice(), captureRequestDtos.getPrice());
        assertDoesNotThrow(() -> TerminalProcessException.class, () -> processor.save(eq(requestDto)));

    }

    @Test
    void saveExceptionTest() {

        assertThrows(TerminalProcessException.class, () -> processor.save(null));
        verifyNoInteractions(service);
    }

    @Test
    void updateTest() {

        ProductRequestDto requestDto = getProductRequest();
        Integer id = Integer.valueOf(1);
        processor.update(id, requestDto);
        ArgumentCaptor<Product> productCapture = ArgumentCaptor.forClass(Product.class);
        verify(service, times(1)).update(eq(id), productCapture.capture());
        assertEquals(requestDto.getName(), productCapture.getValue()
                .getName());
        assertEquals(requestDto.getPrice(), productCapture.getValue()
                .getPrice());
        assertEquals(requestDto.getQuantity(), productCapture.getValue()
                .getQuantity());

        assertDoesNotThrow(() -> TerminalProcessException.class, () -> processor.update(anyInt(), eq(requestDto)));
    }

    @Test
    void updateExrequestception() {

        assertThrows(TerminalProcessException.class, () -> processor.update(null, null));
        verifyNoInteractions(service);
    }

    @Test
    void delete() {

        Integer id = Integer.valueOf(1);
        processor.delete(id);
        ArgumentCaptor<Integer> capture = ArgumentCaptor.forClass(Integer.class);
        verify(service, times(1)).delete(capture.capture());
        assertEquals(id, capture.getValue());

        processor.delete(anyInt());
        verify(service, times(2)).delete(anyInt());

        assertDoesNotThrow(() -> TerminalProcessException.class, () -> processor.delete(anyInt()));

    }

    @Test
    void fetchByNameTest() {

        String name = "TV";
        ProductResponseDto expected = getProductResponseDtoList().stream()
                .filter(x -> name.equalsIgnoreCase(x.getName()))
                .findFirst()
                .get();
        when(service.findByName(name)).thenReturn(getProducts().stream()
                .filter(m -> name.equalsIgnoreCase(m.getName()))
                .findFirst()
                .get());
        ProductResponseDto actual = processor.fetchByName(name);
        assertEquals(actual, expected);
    }

    @Test
    void fetchByNameExceptionTest() {

        assertThrows(TerminalProcessException.class, () -> processor.fetchByName(null));
        assertThrows(TerminalProcessException.class, () -> processor.fetchByName(""));
        verifyNoInteractions(service);
    }

    @Test
    void deleteExceptionTest() {

        assertThrows(TerminalProcessException.class, () -> processor.delete(null));
        verifyNoInteractions(service);
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

    private static List<ProductResponseDto> getProductResponseDtoList() {

        List<ProductResponseDto> productResponseDtoList = new ArrayList<>();
        productResponseDtoList.add(ProductResponseDto.builder()
                .id(1)
                .name("TV")
                .price(45000.0)
                .quantity(10)
                .build());
        productResponseDtoList.add(ProductResponseDto.builder()
                .id(2)
                .name("Iphone")
                .price(145000.0)
                .quantity(5)
                .build());
        return productResponseDtoList;
    }

    ProductRequestDto getProductRequest() {

        return ProductRequestDto.builder()
                .name("TV")
                .price(45000.0)
                .quantity(10)
                .build();
    }

    private static Stream<Arguments> getProductArg() {

        Stream.Builder<Arguments> builder = Stream.builder();
        builder.add(Arguments.of(getProducts(), getProductResponseDtoList()));
        return builder.build();

    }
}
