package com.codecool.shop.config;

import com.codecool.shop.dao.implementation.*;
import org.apache.log4j.BasicConfigurator;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.postgresql.ds.PGSimpleDataSource;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

@WebListener
public class Initializer implements ServletContextListener {

    private DataSource dataSource;
    private ProductDao productDao;
    private SupplierDao supplierDao;
    private ProductCategoryDao productCategoryDao;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String dataSourceConfigPath = "src/main/resources/connection.properties";

        Properties dataProps = new Properties();
        try {
            dataProps.load(new FileInputStream(dataSourceConfigPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String dao = dataProps.getProperty("dao");

        if (dao.equals("memory")){
            this.productDao = ProductDaoMem.getInstance();
            this.productCategoryDao = ProductCategoryDaoMem.getInstance();
            this.supplierDao = SupplierDaoMem.getInstance();
        } else if (dao.equals("jdbc")){
            String userName = dataProps.getProperty("user");
            String dbName = dataProps.getProperty("database");
            String password = dataProps.getProperty("password");
            try {
                this.dataSource = connect(dbName, userName, password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            this.productCategoryDao = new ProductCategoryDaoJDBC(dataSource);
            this.supplierDao = new SupplierDaoJDBC(dataSource);
            this.productDao =  new ProductDaoJDBC(dataSource, supplierDao, productCategoryDao);
        }

        //setting up a new supplier
        //Supplier africa = new Supplier("Africa");
        //supplierDataStore.add(africa);

        //setting up a new product category
        //ProductCategory small = new ProductCategory("Small", "Up to 8 kg");
        //productCategoryDataStore.add(small);

        //setting up products and printing it
        //productDataStore.add(new Product("Tiger (Panthera tigris)", 89000, "USD", "Tigers are perhaps the most recognisable of all the cats. They typically have rusty-reddish to brown-rusty coats, a whitish medial and ventral area, a white fringe that surrounds the face, and stripes that vary from brown or gray to pure black. Native to much of eastern and southern Asia, the tiger is an apex predator and an obligate carnivore. Reaching up to 4 metres (13 ft) in total length and weighing up to 300 kilograms (660 pounds)", big, asia));

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
