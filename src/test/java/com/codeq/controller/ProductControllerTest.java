package com.codeq.controller;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.codeq.ProductServiceApplication;
import com.codeq.dto.ProductRequestDto;
import com.codeq.dto.ProductResponseDto;
import com.codeq.processor.ProductProcessor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringJUnitConfig
@SpringBootTest(classes = ProductServiceApplication.class)
@AutoConfigureMockMvc
@WebAppConfiguration
@ActiveProfiles("mock")
class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ProductProcessor processor;

    private static String URL = "/api/v1/products";

    @Test
    void getProductByIdTest()
            throws Exception {

        // --- Arrange --
        Integer id = Integer.valueOf(1);
        when(processor.fetchById(id)).thenReturn(getProductResponseDto());
        // --- Act --
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URL + "/{id}/productId", id)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder)
                .andReturn();
        MockHttpServletResponse response = result.getResponse();
        // --- Assert --
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void getProductByIdNullResponseTest()
            throws Exception {

        // --- Arrange --
        Integer id = Integer.valueOf(1);
        doReturn(null).when(processor)
                .fetchById(id);

        // --- act --
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URL + "/{id}/productId", id)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder)
                .andReturn();
        MockHttpServletResponse response = result.getResponse();

        // --- Assert --
        assertEquals(HttpStatus.OK.value(), response.getStatus());

    }

    @Test
    void getProductsTest()
            throws Exception {

        // --- Arrange --
        when(processor.fetchAll()).thenReturn(List.of(getProductResponseDto()));

        // --- Act--
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URL)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder)
                .andReturn();
        MockHttpServletResponse response = result.getResponse();

        // --- Assert --
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void getProductsEmptyTest()
            throws Exception {

        // --- Arrange--
        when(processor.fetchAll()).thenReturn(Collections.emptyList());
        // --- Act
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URL)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder)
                .andReturn();

        MockHttpServletResponse response = result.getResponse();

        // --- Assert --
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void saveProductTest()
            throws Exception {

        // --- Arrange--
        when(processor.save(mock(ProductRequestDto.class))).thenReturn(anyString());

        // --- Act--
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"TV\",\"price\":45000.0,\"quantity\":10}");

        MvcResult result = mockMvc.perform(requestBuilder)
                .andReturn();
        MockHttpServletResponse response = result.getResponse();

        // --- Assert --
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    private static ProductResponseDto getProductResponseDto() {

        return ProductResponseDto.builder()
                .id(1)
                .name("TV")
                .price(45000.0)
                .quantity(10)
                .build();
    }

    private static ProductRequestDto getProductRequestDto() {

        return ProductRequestDto.builder()
                .name("TV")
                .price(45000.0)
                .quantity(10)
                .build();
    }

}
