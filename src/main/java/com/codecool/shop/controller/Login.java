package com.codecool.shop.controller;

import com.codecool.shop.dao.implementation.DataManager;
import com.codecool.shop.model.Customer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@WebServlet(urlPatterns = {"/login"})
public class Login extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String eMail = req.getParameter("email");
        String password = req.getParameter("password");
        DataManager dataManager = DataManager.getInstance();
        Customer customer = dataManager.getCustomerDataStore().findByEmail(eMail);

        boolean matched = false;
        try {
            matched = Util.validatePassword(password, customer.getPassword());
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        if (matched) {
            HttpSession session = req.getSession();
            session.setAttribute("user", customer);
        }

        resp.sendRedirect("/");
    }
}
