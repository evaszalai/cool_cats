package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductCategoryDaoJDBC implements ProductCategoryDao {
    DataSource dataSource;

    public ProductCategoryDaoJDBC(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public void add(ProductCategory category) {

    }

    @Override
    public ProductCategory find(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT name, description FROM categories WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            st.executeQuery();
            ResultSet rs = st.getGeneratedKeys();
            if (!rs.next()){
                return null;
            }
            String name = rs.getString(1);
            String description = rs.getString(2);
            return new ProductCategory(name, description);
        } catch (SQLException e) {
            throw new RuntimeException("Error finding product category with id: " + id, e);
        }
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<ProductCategory> getAll() {
        try (Connection conn = dataSource.getConnection()){
            String sql = "SELECT name, description FROM categories";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            List<ProductCategory> result = new ArrayList<>();
            while (rs.next()){
                String name = rs.getString(1);
                String description = rs.getString(2);
                ProductCategory category = new ProductCategory(name, description);
                result.add(category);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching all product categories", e);
        }
    }
}