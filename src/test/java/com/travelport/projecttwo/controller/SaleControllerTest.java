package com.travelport.projecttwo.controller;

import com.travelport.projecttwo.util.TestDataUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.SQLException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SaleControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private TestDataUtil testDataUtil;

  @BeforeEach
  void beforeEach() throws SQLException {
    testDataUtil.clearData();
  }

  @Test
  @DisplayName("Given no authentication, when createSale, then return Unauthorized (401)")
  void testCreateSale_noAuth_returnUnauthorized() throws Exception {
    mockMvc.perform(post("/sales")
        .contentType("application/json")
        .content("""
          {
            "client": {
              "id": "%s"
            },
            "products": [
              {
                "id": "%s",
                "quantity": 5
              }
            ]
          }
        """.formatted(testDataUtil.getSampleClientId(), testDataUtil.getSampleProductId()))
    ).andExpect(status().isUnauthorized());
  }

  @Test
  @DisplayName("Given valid auth and client not exists and product not exists, when createSale, then return Not Found (404)")
  void testCreateSale_validAuthAndClientNotExistAndProductNotExists_returnNotFound() throws Exception {
    mockMvc.perform(post("/sales")
        .header("Authorization", testDataUtil.getAuthHeaderValue())
        .contentType("application/json")
        .content("""
          {
            "client": {
              "id": "%s"
            },
            "products": [
              {
                "id": "%s",
                "quantity": 5
              }
            ]
          }
        """.formatted(testDataUtil.getSampleClientId(), testDataUtil.getSampleProductId()))
    ).andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("Given valid auth and client exists and product not exists, when createSale, then return Not Found (404)")
  void testCreateSale_validAuthAndClientExistsAndProductNotExists_returnNotFound() throws Exception {
    testDataUtil.insertSampleClient();

    mockMvc.perform(post("/sales")
        .header("Authorization", testDataUtil.getAuthHeaderValue())
        .contentType("application/json")
        .content("""
          {
            "client": {
              "id": "%s"
            },
            "products": [
              {
                "id": "%s",
                "quantity": 5
              }
            ]
          }
        """.formatted(testDataUtil.getSampleClientId(), testDataUtil.getSampleProductId()))
    ).andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("Given valid auth and client exists and product exists, when createSale, then returnCreated (201)")
  void testCreateSale_validAuthAndClientExistsAndProductExists_returnCreated() throws Exception {
    testDataUtil.insertSampleClient();
    testDataUtil.insertSampleProduct();

    mockMvc.perform(post("/sales")
        .header("Authorization", testDataUtil.getAuthHeaderValue())
        .contentType("application/json")
        .content("""
          {
            "client": {
              "id": "%s"
            },
            "products": [
              {
                "id": "%s",
                "quantity": 5
              }
            ]
          }
        """.formatted(testDataUtil.getSampleClientId(), testDataUtil.getSampleProductId()))
    ).andExpect(status().isCreated());
  }

  @Test
  @DisplayName("Given valid auth and client exists and product exists and product stock is not enough, when createSale, then return Conflict (409)")
  void testCreateSale_productStockIsNotEnough_returnConflict() throws Exception {
    testDataUtil.insertSampleClient();
    testDataUtil.insertSampleProduct();

    mockMvc.perform(post("/sales")
        .header("Authorization", testDataUtil.getAuthHeaderValue())
        .contentType("application/json")
        .content("""
          {
            "client": {
              "id": "%s"
            },
            "products": [
              {
                "id": "%s",
                "quantity": 50
              }
            ]
          }
        """.formatted(testDataUtil.getSampleClientId(), testDataUtil.getSampleProductId()))
    ).andExpect(status().isConflict());
  }

  @Test
  @DisplayName("Given no authentication, when getSalesByClientId, then return Unauthorized (401)")
  void testGetMostSoldProducts_noAuth_returnUnauthorized() throws Exception {
    mockMvc.perform(get("/sales/most-sold-products")).andExpect(status().isUnauthorized());
  }

  @Test
  @DisplayName("Given valid auth and empty database, when getSalesByClientId, then return Ok (200)")
  void testGetMostSoldProducts_validAuthAndEmptyDatabase_returnOk() throws Exception {
    mockMvc.perform(get("/sales/most-sold-products")
        .header("Authorization", testDataUtil.getAuthHeaderValue())
        .accept("application/json")
    ).andExpect(status().isOk());
  }

  @Test
  @DisplayName("Given valid auth and products have been sold, when getSalesByClientId, then return Ok (200)")
  void testGetMostSoldProducts_validAuthAndCreatedOrders_returnOk() throws Exception {
    testDataUtil.insertSampleOrder();

    mockMvc.perform(get("/sales/most-sold-products")
        .header("Authorization", testDataUtil.getAuthHeaderValue())
        .accept("application/json")
    ).andExpect(status().isOk());
  }
}