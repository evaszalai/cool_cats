package com.codecool.shop.controller;

import com.codecool.shop.dao.implementation.ProductInCart;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;


@WebServlet(urlPatterns = {"/cart"})
public class CartController extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductInCart productId = new Gson().fromJson(req.getReader(), ProductInCart.class);

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
                int newQuantity = productsInCart.get(productId.getId()) + 1;
                productsInCart.put(productId.getId(), newQuantity);
            } else {
                productsInCart.put(productId.getId(), 1);
            }
        }
        session.setAttribute("productsInCart", productsInCart);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter out = resp.getWriter();

        out.println("{'answer': 'OK!'}");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

    }
}
