package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.model.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Util {
    public static List<Product> collectProductWithQuantity(HashMap<Integer, Integer> productsIdAndQuantity) {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        List<Product> productsInCart = new ArrayList<Product>();
        for (Map.Entry<Integer, Integer> entry : productsIdAndQuantity.entrySet()) {
            Integer key = entry.getKey();
            Integer value = entry.getValue();
            Product product = productDataStore.find(key);
            product.setQuantity(value);
            productsInCart.add(product);
        }
        return productsInCart;
    }
    public static float getTotalPriceOfProducts(List<Product> products) {
        float totalPrice = 0;
        for (Product product : products) {
            totalPrice += (product.getDefaultPrice()*product.getQuantity());
        }
        return totalPrice;
    }
}
