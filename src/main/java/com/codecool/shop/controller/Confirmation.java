package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.OrderDaoMem;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.model.Customer;
import com.codecool.shop.model.Order;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.mail.MessagingException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet(urlPatterns = "/confirmation")
public class Confirmation extends HttpServlet {
    GsonBuilder builder = new GsonBuilder();
    Gson gson = builder.create();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        OrderDao orderDataStore = OrderDaoMem.getInstance();

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        WebContext context = new WebContext(request, response, request.getServletContext());

        int orderId = (int) session.getAttribute("orderId");
        context.setVariable("orderId", orderId);
        Order order = orderDataStore.find(orderId);
        Customer customer = order.getCustomer();

        context.setVariable("customer", customer);
        context.setVariable("date", order.getDate());
        context.setVariable("amount", order.getTotalPrice());
        context.setVariable("products", order.getProductList());

        String filename = "order" + String.valueOf(order.getId()) + ".json";
        String jsonString = gson.toJson(order);
        try (FileWriter file = new FileWriter(filename)){
            file.write(jsonString);
            file.flush();
        }

        String email = customer.getEmail();
        String message = order.toString();

        try {
            CreateEmail.sendMail(email, message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        session.invalidate();
        engine.process("confirmation.html", context, response.getWriter());
    }
}
