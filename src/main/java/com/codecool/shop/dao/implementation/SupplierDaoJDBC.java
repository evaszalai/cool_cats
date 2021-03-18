package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierDaoJDBC implements SupplierDao {
    private DataSource dataSource;

    public SupplierDaoJDBC(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public void add(Supplier supplier) {

    }

    @Override
    public Supplier find(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT name FROM suppliers WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            st.executeQuery();
            ResultSet rs = st.getGeneratedKeys();
            if (!rs.next()){
                return null;
            }
            String name = rs.getString(1);
            return new Supplier(name);
        } catch (SQLException e) {
            throw new RuntimeException("Error finding supplier with id: " + id, e);
        }
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<Supplier> getAll() {
        try (Connection conn = dataSource.getConnection()){
            String sql = "SELECT name FROM suppliers";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            List<Supplier> result = new ArrayList<>();
            while (rs.next()){
                String name = rs.getString(1);
                Supplier supplier = new Supplier(name);
                result.add(supplier);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching all suppliers", e);
        }
    }
}
