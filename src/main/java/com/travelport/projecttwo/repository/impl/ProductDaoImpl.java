package com.travelport.projecttwo.repository.impl;

import com.travelport.projecttwo.entities.ProductEntity;
import com.travelport.projecttwo.repository.ProductDao;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductDaoImpl implements ProductDao {

    private final JdbcTemplate jdbcTemplate;

    public ProductDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ProductEntity> getProducts() {
        String query = "SELECT * FROM products";
        return jdbcTemplate.query(query, (rs, row) ->
                new ProductEntity(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("code"),
                        rs.getInt("stock")
                    )
                );
    }

    @Override
    public Optional<ProductEntity> getProduct(String id) {
        String query = "SELECT * FROM products WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(query, (rs, row) -> {
                ProductEntity product = new ProductEntity(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("code"),
                        rs.getInt("stock")
                );
                return Optional.of(product);
            });
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void createProduct(ProductEntity product) {
        String insert = "INSERT INTO products (id, name, code) VALUES (?, ?, ?)";
        jdbcTemplate.update(insert, product.getId(), product.getName(), product.getCode());
    }

    @Override
    public Optional<ProductEntity> updateProduct(String id, ProductEntity product) {
        String update = "UPDATE products SET name = ?, code = ? WHERE id = ?";
        int updateCount = jdbcTemplate.update(update, product.getName(), product.getCode(), id);
        if (updateCount == 1) {
            return Optional.of(product);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public int deleteProduct(String id) {
        String deleteSql = "DELETE FROM products WHERE id = ?";
        return jdbcTemplate.update(deleteSql, id);
    }

    @Override
    public int getAssociatedSalesCount(String id) {
        String query = "SELECT COUNT(*) FROM sales_products WHERE product_id = ?";
        return jdbcTemplate.queryForObject(query, Integer.class, id);
    }
}
