package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataManager {
    private static final String[] resources = new String[]{"src/main/resources/suppliers.csv", "src/main/resources/productcategories.csv", "src/main/resources/products.csv"};
    private ProductDao productDataStore;
    private ProductCategoryDao productCategoryDataStore;
    private SupplierDao supplierDataStore;
    private OrderDao orderDataStore;
    private CustomerDaoMem customerDataStore;

    public void setup() {
        productDataStore = ProductDaoMem.getInstance();
        productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        supplierDataStore = SupplierDaoMem.getInstance();
        orderDataStore = OrderDaoMem.getInstance();
        customerDataStore = CustomerDaoMem.getInstance();

        getSuppliers();
        getProductCategories();
        getProducts();
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
