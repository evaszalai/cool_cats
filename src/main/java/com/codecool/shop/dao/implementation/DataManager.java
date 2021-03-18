package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.*;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class DataManager {
    private static final String[] resources = new String[]{"src/main/resources/suppliers.csv", "src/main/resources/productcategories.csv", "src/main/resources/products.csv"};
    private ProductDao productDataStore;
    private ProductCategoryDao productCategoryDataStore;
    private SupplierDao supplierDataStore;
    private OrderDao orderDataStore;
    private CustomerDao customerDataStore;
    private DataSource dataSource;
    private static DataManager instance;

    private DataManager(){
    }

    public static DataManager getInstance(){
        if (instance == null){
            instance = new DataManager();
        }
        return instance;
    }

    public void setup() {
        Properties dataProps = new Properties();
        try {
            String dataSourceConfigPath = "src/main/resources/connection.properties";
            dataProps.load(new FileInputStream(dataSourceConfigPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String dao = dataProps.getProperty("dao");

        if (dao.equals("memory")){
            this.productDataStore = ProductDaoMem.getInstance();
            this.productCategoryDataStore = ProductCategoryDaoMem.getInstance();
            this.supplierDataStore = SupplierDaoMem.getInstance();
            this.orderDataStore = OrderDaoMem.getInstance();
            this.customerDataStore = CustomerDaoMem.getInstance();
            getSuppliers();
            getProductCategories();
            getProducts();
        } else if (dao.equals("jdbc")) {
            String userName = dataProps.getProperty("user");
            String dbName = dataProps.getProperty("database");
            String password = dataProps.getProperty("password");
            try {
                this.dataSource = connect(dbName, userName, password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            this.productCategoryDataStore = new ProductCategoryDaoJDBC(dataSource);
            this.supplierDataStore = new SupplierDaoJDBC(dataSource);
            this.productDataStore = new ProductDaoJDBC(dataSource, supplierDataStore, productCategoryDataStore);
        }
    }

    public ProductDao getProductDataStore() {
        return productDataStore;
    }

    public ProductCategoryDao getProductCategoryDataStore() {
        return productCategoryDataStore;
    }

    public SupplierDao getSupplierDataStore() {
        return supplierDataStore;
    }

    public OrderDao getOrderDataStore() {
        return orderDataStore;
    }

    public CustomerDao getCustomerDataStore() {
        return customerDataStore;
    }

    private void getSuppliers() {
        List<List<String>> records = getRecords(resources[0]);
        for (List<String> record : records) {
            supplierDataStore.add(new Supplier(record.get(0)));
        }
    }

    private void getProductCategories() {
        List<List<String>> records = getRecords(resources[1]);
        for (List<String> record : records) {
            productCategoryDataStore.add(new ProductCategory(record.get(0), record.get(1)));
        }
    }

    private void getProducts() {
        List<List<String>> records = getRecords(resources[2]);
        for (List<String> record : records) {
            productDataStore.add(new Product(record.get(0),
                    Float.parseFloat(record.get(1)),
                    record.get(2),
                    record.get(3),
                    productCategoryDataStore.find(Integer.parseInt(record.get(4))),
                    supplierDataStore.find(Integer.parseInt(record.get(5)))));
        }
    }

    private List<List<String>> getRecords(String fileName) {
        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                records.add(Arrays.asList(values));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return records;
    }

    private DataSource connect(String dbName, String userName, String password) throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setDatabaseName(dbName);
        dataSource.setUser(userName);
        dataSource.setPassword(password);
        System.out.println("Trying to connect...");
        dataSource.getConnection().close();
        System.out.println("Connection OK");
        return dataSource;
    }
}
