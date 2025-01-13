package com.travelport.projecttwo.controller;

import com.travelport.projecttwo.util.TestDataUtil;
import org.junit.jupiter.api.*;
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
class ClientControllerTest {

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
    mockMvc.perform(get("/clients")).andExpect(status().isOk());
  }

  @Test
  @DisplayName("Given client exists, when getAll, then return Ok (200)")
  void testGetAll_clientExists_returnOk() throws Exception {
    testDataUtil.insertSampleClient();
    mockMvc.perform(get("/clients")).andExpect(status().isOk());
  }

  @Test
  @DisplayName("Given new client, when save, then return Created (201)")
  void testSave_returnCreated() throws Exception {
    mockMvc.perform(post("/clients")
        .header("Content-Type", "application/json")
        .header("Accept", "application/json")
        .content("""
            {
                "nif": "41000000D",
                "name": "John Doe",
                "address": "Barcelona"
            }
            """)
        ).andExpect(status().is(201));
  }

  @Test
  @DisplayName("Given client not exists, when getById, then return Not Found (404)")
  void testGetById_clientNotExists_returnNotFound() throws Exception {
    var id = UUID.randomUUID().toString();
    mockMvc.perform(get("/clients/" + id)).andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("Given client exists, when getById, then return Ok (200)")
  void testGetById_clientExists_returnOk() throws Exception {
    testDataUtil.insertSampleClient();
    mockMvc.perform(get("/clients/" + testDataUtil.getSampleClientId())).andExpect(status().isOk());
  }

  @Test
  @DisplayName("Given client not exists, when updateById, then return Not Found (404)")
  void testUpdateById_clientNotExists_returnNotFound() throws Exception {
    var id = UUID.randomUUID().toString();

    mockMvc.perform(put("/clients/" + id)
        .header("Content-Type", "application/json")
        .header("Accept", "application/json")
        .content("""
            {
                "nif": "41000000D",
                "name": "John Doe",
                "address": "Barcelona"
            }
            """)
    ).andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("Given client exists, when updateById, then return Ok (200)")
  void testUpdateById_clientExists_returnOk() throws Exception {
    testDataUtil.insertSampleClient();

    mockMvc.perform(put("/clients/" + testDataUtil.getSampleClientId())
        .header("Content-Type", "application/json")
        .header("Accept", "application/json")
        .content("""
            {
                "nif": "41000000A",
                "name": "John Doe 2",
                "address": "Spain"
            }
            """)
        ).andExpect(status().isOk());
  }

  @Test
  @DisplayName("Given client not exists, when deleteById, then return Not Found (404)")
  void testDeleteById_clientNotExists_returnNotFound() throws Exception {
    var id = UUID.randomUUID().toString();

    mockMvc.perform(delete("/clients/" + id)).andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("Given client exists and has orders, when deleteById, then return Unprocessable Entity (422)")
  void testDeleteById_clientExistsWithOrders_returnUnprocessableEntity() throws Exception {
    testDataUtil.insertSampleOrder();

    mockMvc.perform(delete("/clients/" + testDataUtil.getSampleClientId())).andExpect(status().isUnprocessableEntity());
  }

  @Test
  @DisplayName("Given client exists, when deleteById, then return Ok (200)")
  void testDeleteById_clientExists_returnOk() throws Exception {
    testDataUtil.insertSampleClient();

    mockMvc.perform(delete("/clients/" + testDataUtil.getSampleClientId())).andExpect(status().isOk());
  }

  @Test
  @DisplayName("Given no auth header, when getSalesByClientId, then return Unauthorized (401)")
  void testGetSalesByClientId_noAuth_returnUnauthorized() throws Exception {
    var id = UUID.randomUUID().toString();

    mockMvc.perform(get("/clients/" + id + "/sales")).andExpect(status().isUnauthorized());
  }

  @Test
  @DisplayName("Given valid auth header and client not exists, when getSalesByClientId, then return Not Found (404)")
  void testGetSalesByClientId_AuthClientNotExists_returnNotFound() throws Exception {
    var id = UUID.randomUUID().toString();

    mockMvc.perform(get("/clients/" + id + "/sales")
        .header("Authorization", testDataUtil.getAuthHeaderValue())
        .accept("application/json")
    ).andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("Given valid auth header and client exists, when getSalesByClientId, then return Ok (200)")
  void testGetSalesByClientId_AuthClientExists_returnOk() throws Exception {
    testDataUtil.insertSampleClient();

    mockMvc.perform(get("/clients/" + testDataUtil.getSampleClientId() + "/sales")
        .header("Authorization", testDataUtil.getAuthHeaderValue())
        .accept("application/json")
    ).andExpect(status().isOk());
  }
}