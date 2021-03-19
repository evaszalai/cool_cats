package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.DataManager;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = {"/category"})
public class ProductByCategory extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        Gson gson = new Gson();
        DataManager datamanager = DataManager.getInstance();
        ProductDao productDataStore = datamanager.getProductDataStore();
        ProductCategoryDao productCategoryDataStore = datamanager.getProductCategoryDataStore();
        out.println(gson.toJson(productDataStore.getBy(productCategoryDataStore.find(Integer.parseInt(req.getParameter("id"))))));
    }
}
