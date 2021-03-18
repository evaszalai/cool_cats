package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.CustomerDao;
import com.codecool.shop.model.Customer;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class CustomerDaoJDBC implements CustomerDao {
    DataSource dataSource;

    public CustomerDaoJDBC(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(Customer customer) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO users (first_name, last_name, email, password, salt) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, customer.getFirstName());
            st.setString(2, customer.getLastName());
            st.setString(3, customer.getEmail());
            st.setString(4, customer.getPassword());
            st.setString(5, "");
            st.executeUpdate();
        } catch (SQLException throwables) {
            throw new RuntimeException("Error while adding new User.", throwables);
        }
    }

    @Override
    public Customer find(int id) {
        return null;
    }

    public Customer findByEmail(String eMail) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT first_name, last_name, password FROM users WHERE email LIKE ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, eMail);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                return null;
            }
            return new Customer(rs.getString(1), rs.getString(2), eMail, rs.getString(3));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void remove(int id) {

    }

    @Override
    public List<Customer> getAll() {
        return null;
    }
}
