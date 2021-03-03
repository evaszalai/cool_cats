package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.ProductInCart;
import com.codecool.shop.model.Product;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;


@WebServlet(urlPatterns = {"/cart"})
public class CartController extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductInCart productId = new Gson().fromJson(req.getReader(), ProductInCart.class);
        System.out.println(productId.getId() + " - " + productId.getAction());
        HttpSession session = req.getSession();
        HashMap<Integer, Integer> productsInCart;
        if (session.getAttribute("productsInCart") == null) {
            productsInCart = new HashMap<Integer, Integer>();
        } else {
            productsInCart = (HashMap<Integer, Integer>) session.getAttribute("productsInCart");
        }
        if (productsInCart.size() == 0) {
            productsInCart.put(productId.getId(), 1);
        } else {
            if (!productsInCart.containsKey(productId.getId())) {
                productsInCart.put(productId.getId(), 1);
            } else {
                int newQuantity = 0;
                if (productId.getAction().equals("up")) {
                    newQuantity = productsInCart.get(productId.getId()) + 1;
                } else {
                    newQuantity = productsInCart.get(productId.getId()) - 1;
                }
                if (newQuantity == 0) {
                    productsInCart.remove(productId.getId());
                } else {
                    productsInCart.put(productId.getId(), newQuantity);
                }
            }
        }
        session.setAttribute("productsInCart", productsInCart);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter out = resp.getWriter();

        out.println("[{\"answer\": \"OK!\"}]");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        ProductDao productDataStore = ProductDaoMem.getInstance();
        List<Product> productsInCart = new ArrayList<Product>();
        HashMap<Integer, Integer> productsIdAndQuantity = (HashMap<Integer, Integer>) session.getAttribute("productsInCart");
        for (Map.Entry<Integer, Integer> entry : productsIdAndQuantity.entrySet()) {
            Integer key = entry.getKey();
            Integer value = entry.getValue();
            Product product = productDataStore.find(key);
            product.setQuantity(value);
            productsInCart.add(product);
        }

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        Gson gson = new Gson();
        out.println(gson.toJson(productsInCart));
    }
}
