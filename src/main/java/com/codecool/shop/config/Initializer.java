package com.codecool.shop.config;

import com.codecool.shop.dao.implementation.DataManager;
import org.apache.log4j.BasicConfigurator;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class Initializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();

        //setting up a new supplier
        Supplier africa = new Supplier("Africa");
        supplierDataStore.add(africa);
        Supplier asia = new Supplier("Asia");
        supplierDataStore.add(asia);
        Supplier southAmerica = new Supplier("South-America");
        supplierDataStore.add(southAmerica);

        //setting up a new product category
        ProductCategory small = new ProductCategory("Small", "Up to 8 kg");
        productCategoryDataStore.add(small);
        ProductCategory medium = new ProductCategory("Mid-size", "Between 8-25 kg");
        productCategoryDataStore.add(medium);
        ProductCategory big = new ProductCategory("Big", "Between 25-300 kg");
        productCategoryDataStore.add(big);

        //setting up products and printing it
        productDataStore.add(new Product("Tiger (Panthera tigris)", 89000, "USD", "Tigers are perhaps the most recognisable of all the cats. They typically have rusty-reddish to brown-rusty coats, a whitish medial and ventral area, a white fringe that surrounds the face, and stripes that vary from brown or gray to pure black. Native to much of eastern and southern Asia, the tiger is an apex predator and an obligate carnivore. Reaching up to 4 metres (13 ft) in total length and weighing up to 300 kilograms (660 pounds)", big, asia));
        productDataStore.add(new Product("Clouded leopard (Neofelis Nebulosa)", 45000, "USD", "A lot of people consider this mysterious medium-sized cat as the most beautiful one of the family of felines. The Clouded Leopard is found in Southeast Asia and it seems to be a cross between a big cat and a small cat. Remarkably little is known about it and it is assumed that its primary prey includes arboreal and terrestrial mammals, particularly gibbons, macaques, and civets supplemented by other small mammals, deer, birds, porcupines, and domestic livestock.", medium, asia));
        productDataStore.add(new Product("Ocelot (Leopardus Pardalis)", 19999, "USD", "The ocelot is a wild cat, of the group small cats, distributed over South and Central America and Mexico. The ocelot’s appearance is similar to the domestic cat, though its fur resembles that of a Jaguar or a clouded leopard and was once regarded as particularly valuable. As a result, hundreds of thousands of ocelots have been killed for their fur. The feline was clasified a vulnerable endangered species from 1972 until 1996, but is now rated least concern by the 2008 IUCN Red List.", small, southAmerica));
    }
}
