package com.travelport.projecttwo.controller;

import com.travelport.projecttwo.util.TestDataUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.SQLException;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PurchaseControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private TestDataUtil testDataUtil;

  @AfterEach
  void afterEach() throws SQLException {
    testDataUtil.clearData();
  }

  @Test
  @DisplayName("Given no authentication, when renewStock, then return Unauthorized (401)")
  void testRenewStock_noAuth_returnUnauthorized() throws Exception {
    var id = UUID.randomUUID().toString();

    mockMvc.perform(post("/purchases")
        .contentType("application/json")
        .content("""
          {
            "supplier": "amazon",
            "products": [
              {
                "id": "%s",
                "quantity": 1
              }
            ]
          }
        """.formatted(id))
    ).andExpect(status().isUnauthorized());
  }

  @Test
  @DisplayName("Given valid auth and product not exists, when renewStock, then return Not Found (404)")
  void testRenewStock_validAuthProductNotExists_returnNotFound() throws Exception {
    var id = UUID.randomUUID().toString();

    mockMvc.perform(post("/purchases")
        .header("Authorization", testDataUtil.getAuthHeaderValue())
        .contentType("application/json")
        .content("""
          {
            "supplier": "amazon",
            "products": [
              {
                "id": "%s",
                "quantity": 1
              }
            ]
          }
        """.formatted(id))
    ).andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("Given valid auth and product exists, when renewStock, then return Created (201)")
  void testRenewStock_validAuthProductExists_returnCreated() throws Exception {
    testDataUtil.insertSampleProduct();

    mockMvc.perform(post("/purchases")
        .header("Authorization", testDataUtil.getAuthHeaderValue())
        .contentType("application/json")
        .content("""
          {
            "supplier": "amazon",
            "products": [
              {
                "id": "%s",
                "quantity": 1
              }
            ]
          }
        """.formatted(testDataUtil.getSampleProductId()))
    ).andExpect(status().isCreated());
  }

}