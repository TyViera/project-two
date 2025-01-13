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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private TestDataUtil testDataUtil;

  @AfterEach
  void afterEach() throws SQLException {
    testDataUtil.clearData();
  }

  @Test
  @DisplayName("Given empty database, when getAll, then return Ok (200)")
  void testGetAll_emptyDatabase_returnOk() throws Exception {
    mockMvc.perform(get("/products")).andExpect(status().isOk());
  }

  @Test
  @DisplayName("Given product exists, when getAll, then return Ok (200)")
  void testGetAll_productExists_returnOk() throws Exception {
    testDataUtil.insertSampleProduct();
    mockMvc.perform(get("/products")).andExpect(status().isOk());
  }

  @Test
  @DisplayName("Given new product, when save, then return Created (201)")
  void testSave_returnCreated() throws Exception {

    mockMvc.perform(post("/products")
        .contentType("application/json")
        .accept("application/json")
        .content("""
          {
            "name": "New product",
            "code": "25P001"
          }
        """)
      ).andExpect(status().isCreated());
  }

  @Test
  @DisplayName("Given new product with code already existing, when save, then return Unprocessable Entity (422)")
  void testSave_productCodeAlreadyExists_returnUnprocessableEntity() throws Exception {
    testDataUtil.insertSampleProduct();

    mockMvc.perform(post("/products")
        .contentType("application/json")
        .accept("application/json")
        .content(
            """
                {
                    "name": "Product 2",
                    "code": "%s"
                }
              """.formatted(testDataUtil.getSampleProductCode())
        )
    ).andExpect(status().isUnprocessableEntity());
  }

  @Test
  @DisplayName("Given product not exists, when getById, then return Not Found (404)")
  void testGetById_productNotExists_returnNotFound() throws Exception {
    var id = UUID.randomUUID().toString();
    mockMvc.perform(get("/products/" + id)).andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("Given product exists, when getById, then return Ok (200)")
  void testGetById_productExists_returnOk() throws Exception {
    testDataUtil.insertSampleProduct();
    mockMvc.perform(get("/products/" + testDataUtil.getSampleProductId())).andExpect(status().isOk());
  }

  @Test
  @DisplayName("Given product not exists, when updateById, then return Not Found (404)")
  void testUpdateById_productNotExists_returnNotFound() throws Exception {
    var id = UUID.randomUUID().toString();

    mockMvc.perform(put("/products/" + id)
        .contentType("application/json")
        .accept("application/json")
        .content("""
          {
            "name": "New name",
            "code": "25P001"
          }
        """)
    ).andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("Given product exists, when updateById, then return Ok (200)")
  void testUpdateById_productExists_returnOk() throws Exception {
    testDataUtil.insertSampleProduct();

    mockMvc.perform(put("/products/" + testDataUtil.getSampleProductId())
        .contentType("application/json")
        .accept("application/json")
        .content("""
          {
            "name": "New name",
            "code": "25P001"
          }
        """)
    ).andExpect(status().isOk());
  }

  @Test
  @DisplayName("Given product exists, when updateById with an already existing code, then return Unprocessable Entity (422)")
  void testUpdateById_productExistsAndNewCodeAlreadyExists_returnUnprocessableEntity() throws Exception {
    var id1 = UUID.randomUUID().toString();
    var id2 = UUID.randomUUID().toString();
    testDataUtil.insertProduct(id1, "Product 1", "25P001", 10);
    testDataUtil.insertProduct(id2, "Product 2", "25P002", 10);

    mockMvc.perform(put("/products/" + id2)
        .contentType("application/json")
        .accept("application/json")
        .content("""
          {
            "name": "New name",
            "code": "25P001"
          }
        """)
    ).andExpect(status().isUnprocessableEntity());
  }

  @Test
  @DisplayName("Given product not exists, when deleteById, then return Not Found (404)")
  void testDeleteById_productNotExists_returnNotFound() throws Exception {
    var id = UUID.randomUUID().toString();

    mockMvc.perform(delete("/products/" + id)).andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("Given product exists and has been sold, when deleteById, then return Unprocessable Entity (422)")
  void testDeleteById_productExistsAndHasBeenSold_returnUnprocessableEntity() throws Exception {
    testDataUtil.insertSampleOrder();

    mockMvc.perform(delete("/products/" + testDataUtil.getSampleProductId())).andExpect(status().isUnprocessableEntity());
  }

  @Test
  @DisplayName("Given product exists, when deleteById, then return Ok (200)")
  void testDeleteById_productExists_returnOk() throws Exception {
    testDataUtil.insertSampleProduct();

    mockMvc.perform(delete("/products/" + testDataUtil.getSampleProductId())).andExpect(status().isOk());
  }

  @Test
  @DisplayName("Given product not exists, when getStockById, then return Not Found (404)")
  void testGetStockById_productNotExists_returnNotFound() throws Exception {
    var id = UUID.randomUUID().toString();

    mockMvc.perform(get("/products/" + id + "/stock")).andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("Given product exists, when getStockById, then return Ok (200)")
  void testGetStockById_productExists_returnOk() throws Exception {
    testDataUtil.insertSampleProduct();

    mockMvc.perform(get("/products/" + testDataUtil.getSampleProductId() + "/stock")).andExpect(status().isOk());
  }
}