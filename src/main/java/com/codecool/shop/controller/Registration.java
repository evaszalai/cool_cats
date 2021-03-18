package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.DataManager;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.model.Customer;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@WebServlet(urlPatterns = {"/registration"})
public class Registration extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        DataManager dataManager = DataManager.getInstance();
        ProductDao productDataStore = dataManager.getProductDataStore();
        context.setVariable("products", productDataStore.getAll());
        engine.process("registration.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstName = req.getParameter("first-name");
        String lastName = req.getParameter("last-name");
        String eMail = req.getParameter("email");
        String password = req.getParameter("password");

        String generatedSecuredPasswordHash = null;
        try {
            generatedSecuredPasswordHash = Util.generateStorngPasswordHash(password);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }

        DataManager dataManager = DataManager.getInstance();
        dataManager.getCustomerDataStore().add(new Customer(firstName, lastName, eMail, generatedSecuredPasswordHash));

        resp.sendRedirect("/");
    }
}
