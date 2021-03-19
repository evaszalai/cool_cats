package com.codecool.shop.dao.implementation.database;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.implementation.DBConnection;
import com.codecool.shop.model.ProductCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductCategoryDaoJDBC implements ProductCategoryDao {

    public ProductCategoryDaoJDBC(){
    }

    @Override
    public void add(ProductCategory category) {

    }

    @Override
    public ProductCategory find(int id) {
        try {
            Connection conn = DBConnection.getInstance().getConnection();
            String sql = "SELECT name, description FROM categories WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()){
                return null;
            }
            String name = rs.getString(1);
            String description = rs.getString(2);
            ProductCategory category = new ProductCategory(name, description);
            category.setId(id);
            return category;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding product category with id: " + id, e);
        }
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<ProductCategory> getAll() {
        try {
            Connection conn = DBConnection.getInstance().getConnection();
            String sql = "SELECT id, name, description FROM categories";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            List<ProductCategory> result = new ArrayList<>();
            while (rs.next()){
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String description = rs.getString(3);
                ProductCategory category = new ProductCategory(name, description);
                category.setId(id);
                result.add(category);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching all product categories", e);
        }
    }
}
