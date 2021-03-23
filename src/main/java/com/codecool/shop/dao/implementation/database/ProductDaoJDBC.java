package com.codecool.shop.dao.implementation.database;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.DBConnection;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoJDBC implements ProductDao {
    private SupplierDao supplierDao;
    private ProductCategoryDao categoryDao;

    public ProductDaoJDBC( SupplierDao supplierDao, ProductCategoryDao productCategoryDao){
        this.supplierDao = supplierDao;
        this.categoryDao = productCategoryDao;
    }

    @Override
    public void add(Product product) {

    }

    @Override
    public Product find(int id) {
        try {
            Connection conn = DBConnection.getInstance().getConnection();
            String sql = "SELECT id, name, description, price, category_id, supplier_id FROM products WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()){
                return null;
            }
            return createProductFromRS(rs);
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching product with id: " + id, e);
        }
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<Product> getAll() {
        try {
            Connection conn = DBConnection.getInstance().getConnection();
            String sql = "SELECT id, name, description, price, category_id, supplier_id FROM products";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            List<Product> result = new ArrayList<>();
            while (rs.next()){
                Product product = createProductFromRS(rs);
                result.add(product);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching all products", e);
        }
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        int supplierId = supplier.getId();
        try {
            Connection conn = DBConnection.getInstance().getConnection();
            String sql = "SELECT id, name, description, price, category_id, supplier_id FROM products WHERE supplier_id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, supplierId);
            ResultSet rs = st.executeQuery();
            List<Product> result = new ArrayList<>();
            while (rs.next()){
                Product product = createProductFromRS(rs);
                result.add(product);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching products by supplier " + supplier.getName(), e);
        }
    }


    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        int categoryId = productCategory.getId();
        try {
            Connection conn = DBConnection.getInstance().getConnection();
            String sql = "SELECT id, name, description, price, category_id, supplier_id FROM products WHERE category_id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, categoryId);
            ResultSet rs = st.executeQuery();
            List<Product> result = new ArrayList<>();
            while (rs.next()){
                Product product = createProductFromRS(rs);
                result.add(product);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching products by category " + productCategory.getName(), e);
        }
    }

    private Product createProductFromRS(ResultSet rs) throws SQLException {
        int id = rs.getInt(1);
        String name = rs.getString(2);
        String description = rs.getString(3);
        float price = (float) rs.getInt(4);
        int categoryId = rs.getInt(5);
        int supplierId = rs.getInt(6);
        ProductCategory category = categoryDao.find(categoryId);
        Supplier supplier = supplierDao.find(supplierId);
        Product product =  new Product(name, price, "USD", description, category, supplier);
        product.setId(id);
        return product;
    }
}
