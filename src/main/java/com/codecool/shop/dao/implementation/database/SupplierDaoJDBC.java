package com.codecool.shop.dao.implementation.database;

import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.DBConnection;
import com.codecool.shop.model.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierDaoJDBC implements SupplierDao {

    public SupplierDaoJDBC(){
        }

    @Override
    public void add(Supplier supplier) {

    }

    @Override
    public Supplier find(int id) {
        try {
            Connection conn = DBConnection.getInstance().getConnection();
            String sql = "SELECT name FROM suppliers WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()){
                return null;
            }
            String name = rs.getString(1);
            Supplier supplier = new Supplier(name);
            supplier.setId(id);
            return supplier;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding supplier with id: " + id, e);
        }
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<Supplier> getAll() {
        try {
            Connection conn = DBConnection.getInstance().getConnection();
            String sql = "SELECT id, name FROM suppliers";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            List<Supplier> result = new ArrayList<>();
            while (rs.next()){
                int id = rs.getInt(1);
                String name = rs.getString(2);
                Supplier supplier = new Supplier(name);
                supplier.setId(id);
                result.add(supplier);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching all suppliers", e);
        }
    }
}
