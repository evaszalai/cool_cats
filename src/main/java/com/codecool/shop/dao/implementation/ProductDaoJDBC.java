package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoJDBC implements ProductDao {
    private DataSource dataSource;
    private SupplierDao supplierDao;
    private ProductCategoryDao categoryDao;

    public ProductDaoJDBC(DataSource dataSource, SupplierDao supplierDao, ProductCategoryDao productCategoryDao){
        this.dataSource = dataSource;
        this.supplierDao = supplierDao;
        this.categoryDao = productCategoryDao;
    }

    @Override
    public void add(Product product) {

    }

    @Override
    public Product find(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT name, description, price, category_id, supplier_id FROM products WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            st.executeQuery();
            ResultSet rs = st.getGeneratedKeys();
            if (!rs.next()){
                return null;
            }
            return createProductFromRS(rs);
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading book with id: " + id, e);
        }
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<Product> getAll() {
        try (Connection conn = dataSource.getConnection()){
            String sql = "SELECT name, description, price, category_id, supplier_id FROM products";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            List<Product> result = new ArrayList<>();
            while (rs.next()){
                Product product = createProductFromRS(rs);
                result.add(product);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading all books", e);
        }
    }

    private Product createProductFromRS(ResultSet rs) throws SQLException {
        String name = rs.getString(1);
        String description = rs.getString(2);
        float price = (float) rs.getInt(3);
        int categoryId = rs.getInt(4);
        int supplierId = rs.getInt(5);
        ProductCategory category = categoryDao.find(categoryId);
        Supplier supplier = supplierDao.find(supplierId);
        return new Product(name, price, "USD", description, category, supplier);
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        int supplierId = supplier.getId();
        try (Connection conn = dataSource.getConnection()){
            String sql = "SELECT name, description, price, category_id, supplier_id FROM products WHERE supplier_id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, supplierId);
            st.executeQuery();
            ResultSet rs = st.getGeneratedKeys();
            List<Product> result = new ArrayList<>();
            while (rs.next()){
                Product product = createProductFromRS(rs);
                result.add(product);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading all books", e);
        }
    }


    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        int categoryId = productCategory.getId();
        try (Connection conn = dataSource.getConnection()){
            String sql = "SELECT name, description, price, category_id, supplier_id FROM products WHERE category_id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, categoryId);
            st.executeQuery();
            ResultSet rs = st.getGeneratedKeys();
            List<Product> result = new ArrayList<>();
            while (rs.next()){
                Product product = createProductFromRS(rs);
                result.add(product);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading all books", e);
        }
    }
}
