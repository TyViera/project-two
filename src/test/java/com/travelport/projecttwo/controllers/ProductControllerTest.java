package com.travelport.projecttwo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelport.projecttwo.controllers.dtos.product.ProductRequestDto;
import com.travelport.projecttwo.exceptions.ProductHasSalesException;
import com.travelport.projecttwo.services.IProductService;
import com.travelport.projecttwo.services.domainModels.ProductDomain;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
class ProductControllerTest {

    @MockBean
    private IProductService productService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getProducts_shouldReturnOkAndList() throws Exception {
        // GIVEN
        when(productService.getProducts()).thenReturn(List.of());

        // WHEN
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void getProductById_whenProductExists_shouldReturnOkAndProduct() throws Exception {
        // GIVEN
        var productDomain = new ProductDomain();
        productDomain.setId("123");
        productDomain.setCode("PROD1");
        productDomain.setName("Laptop");
        productDomain.setStock(10);

        when(productService.getProductById("123")).thenReturn(Optional.of(productDomain));

        // WHEN
        mockMvc.perform(get("/products/123"))
                // THEN
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("123"))
                .andExpect(jsonPath("$.code").value("PROD1"))
                .andExpect(jsonPath("$.name").value("Laptop"));
    }

    @Test
    void getProductById_whenProductDoesNotExist_shouldReturnNotFound() throws Exception {
        // GIVEN
        when(productService.getProductById("999")).thenReturn(Optional.empty());

        // WHEN & THEN
        mockMvc.perform(get("/products/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createProduct_shouldReturnCreated() throws Exception {
        // GIVEN
        var requestDto = new ProductRequestDto();
        requestDto.setCode("PROD1");
        requestDto.setName("Laptop");

        var createdProductDomain = new ProductDomain();
        createdProductDomain.setId("123");
        createdProductDomain.setCode("PROD1");
        createdProductDomain.setName("Laptop");
        createdProductDomain.setStock(0);

        when(productService.createProduct(any())).thenReturn(createdProductDomain);

        // WHEN
        mockMvc.perform(post("/products")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(requestDto)))
                // THEN
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("123"))
                .andExpect(jsonPath("$.code").value("PROD1"))
                .andExpect(jsonPath("$.name").value("Laptop"));
    }

    @Test
    void updateProduct_whenProductExists_shouldReturnOkAndProduct() throws Exception {
        // GIVEN
        var requestDto = new ProductRequestDto();
        requestDto.setCode("PROD1");
        requestDto.setName("Laptop");

        var updatedProductDomain = new ProductDomain();
        updatedProductDomain.setId("123");
        updatedProductDomain.setCode("PROD1");
        updatedProductDomain.setName("Laptop");

        when(productService.updateProduct(eq("123"), any())).thenReturn(updatedProductDomain);

        // WHEN
        mockMvc.perform(put("/products/123")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(requestDto)))
                // THEN
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("123"))
                .andExpect(jsonPath("$.code").value("PROD1"))
                .andExpect(jsonPath("$.name").value("Laptop"));
    }

    @Test
    void updateProduct_whenProductDoesNotExist_shouldReturnNotFound() throws Exception {
        // GIVEN
        var requestDto = new ProductRequestDto();
        requestDto.setCode("PROD1");
        requestDto.setName("Laptop");

        when(productService.updateProduct(eq("999"), any())).thenThrow(new IllegalArgumentException());

        // WHEN & THEN
        mockMvc.perform(put("/products/999")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteProduct_whenOk_shouldReturn204() throws Exception {
        mockMvc.perform(delete("/products/123"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteProduct_whenNotFound_shouldReturn404() throws Exception {
        // GIVEN
        Mockito.doThrow(new IllegalArgumentException("Not found"))
                .when(productService).deleteProduct("999");

        // WHEN & THEN
        mockMvc.perform(delete("/products/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteProduct_whenProductHasSales_shouldReturn422() throws Exception {
        // GIVEN
        Mockito.doThrow(new ProductHasSalesException("Product has sales"))
                .when(productService).deleteProduct("123");

        // WHEN & THEN
        mockMvc.perform(delete("/products/123"))
                .andExpect(status().isUnprocessableEntity());
    }
}