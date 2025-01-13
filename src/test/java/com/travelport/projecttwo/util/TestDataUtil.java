package com.travelport.projecttwo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class TestDataUtil {

  @Autowired
  private DataSource dataSource;

  private final String sampleClientId = UUID.randomUUID().toString();
  private final String sampleProductId = UUID.randomUUID().toString();
  private final String sampleProductCode = "25P001";
  private final String sampleSaleId = UUID.randomUUID().toString();

  public String getAuthHeaderValue() {
    return "Basic Y2FybG9zOmNhcmxvcw==";
  }

  public String getSampleClientId() {
    return sampleClientId;
  }

  public String getSampleProductId() {
    return sampleProductId;
  }

  public String getSampleProductCode() {
    return sampleProductCode;
  }

  public String getSampleSaleId() {
    return sampleSaleId;
  }

  public void insertClient(String id, String name, String nif, String address) throws SQLException {
    try (var conn = dataSource.getConnection()) {
      try (var statement = conn.prepareStatement("INSERT INTO clients (id, name, nif, address) VALUES (?, ?, ?, ?)")) {
        statement.setString(1, id);
        statement.setString(2, name);
        statement.setString(3, nif);
        statement.setString(4, address);

        statement.executeUpdate();
      }
    }
  }

  public void insertProduct(String id, String name, String code, Integer stock) throws SQLException {
    try (var conn = dataSource.getConnection()) {
      try (var statement = conn.prepareStatement("INSERT INTO products (id, name, code, stock) VALUES (?, ?, ?, ?)")) {
        statement.setString(1, id);
        statement.setString(2, name);
        statement.setString(3, code);
        statement.setInt(4, stock);

        statement.executeUpdate();
      }
    }
  }

  public void insertOrder(String saleId, String clientId, String productId) throws SQLException {
    try (var conn = dataSource.getConnection()) {
      try (var statement = conn.prepareStatement("INSERT INTO sales (id, client_id) VALUES (?, ?)")) {
        statement.setString(1, saleId);
        statement.setString(2, clientId);

        statement.executeUpdate();
      }
    }

    try (var conn = dataSource.getConnection()) {
      try (var statement = conn.prepareStatement("INSERT INTO sales_details (sale_id, product_id, quantity) VALUES (?, ?, ?)")) {
        statement.setString(1, saleId);
        statement.setString(2, productId);
        statement.setInt(3, 1);

        statement.executeUpdate();
      }
    }
  }

  public void insertSampleClient() throws SQLException {
    insertClient(sampleClientId, "John Doe", "41000000D", "Barcelona");
  }

  public void insertSampleProduct() throws SQLException {
    insertProduct(sampleProductId, "Product 001", sampleProductCode, 10);
  }

  public void insertSampleOrder() throws SQLException {
    insertSampleClient();
    insertSampleProduct();
    insertOrder(sampleSaleId, sampleClientId, sampleProductId);
  }

  public void clearClients() throws SQLException {
    try (var conn = dataSource.getConnection()) {
      try (var statement = conn.prepareStatement("DELETE FROM clients")) {
        statement.executeUpdate();
      }
    }
  }

  public void clearProducts() throws SQLException {
    try (var conn = dataSource.getConnection()) {
      try (var statement = conn.prepareStatement("DELETE FROM products")) {
        statement.executeUpdate();
      }
    }
  }

  public void clearSales() throws SQLException {
    try (var conn = dataSource.getConnection()) {
      try (var statement = conn.prepareStatement("DELETE FROM sales_details")) {
        statement.executeUpdate();
      }
    }

    try (var conn = dataSource.getConnection()) {
      try (var statement = conn.prepareStatement("DELETE FROM sales")) {
        statement.executeUpdate();
      }
    }
  }

  public void clearData() throws SQLException {
    clearSales();
    clearProducts();
    clearClients();
  }

}
