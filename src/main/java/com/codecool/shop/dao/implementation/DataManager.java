package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.*;
import com.codecool.shop.dao.implementation.database.CustomerDaoJDBC;
import com.codecool.shop.dao.implementation.database.ProductCategoryDaoJDBC;
import com.codecool.shop.dao.implementation.database.ProductDaoJDBC;
import com.codecool.shop.dao.implementation.database.SupplierDaoJDBC;
import com.codecool.shop.dao.implementation.memory.*;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
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
            DBConnection dbConnection = DBConnection.getInstance();
            dbConnection.setDatabase(dbName);
            dbConnection.setUser(userName);
            dbConnection.setPassword(password);
            this.productCategoryDataStore = new ProductCategoryDaoJDBC();
            this.supplierDataStore = new SupplierDaoJDBC();
            this.productDataStore = new ProductDaoJDBC(supplierDataStore, productCategoryDataStore);
            this.customerDataStore = new CustomerDaoJDBC();
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

}
