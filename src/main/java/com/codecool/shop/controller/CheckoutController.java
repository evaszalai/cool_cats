package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.model.Product;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = {"/checkout"})
public class CheckoutController extends HttpServlet {

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
        float totalPrice = 0;
        for (Product product : productsInCart) {
            totalPrice += (product.getDefaultPrice()*product.getQuantity());
        }

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("products", productsInCart);
        context.setVariable("totalPrice", totalPrice);
        context.setVariable("currency", productsInCart.get(0).getDefaultCurrency());

        engine.process("checkout.html", context, resp.getWriter());
    }

}
