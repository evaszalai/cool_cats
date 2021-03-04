package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.dao.implementation.OrderDaoMem;
import com.codecool.shop.model.Order;
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
import java.util.HashMap;
import java.util.List;

@WebServlet(urlPatterns = {"/checkout"})
public class CheckoutController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        List<Product> productsInCart = Util.collectProductWithQuantity((HashMap<Integer, Integer>) session.getAttribute("productsInCart"));
        float totalPrice = Util.getTotalPriceOfProducts(productsInCart);

        Order newOrder = new Order(productsInCart, totalPrice);
        OrderDao orderDataStore = OrderDaoMem.getInstance();
        orderDataStore.add(newOrder);
        System.out.println(newOrder.getId());
        session.setAttribute("orderId", newOrder.getId());

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("products", productsInCart);
        context.setVariable("totalPrice", totalPrice);
        context.setVariable("currency", productsInCart.get(0).getDefaultCurrency());

        engine.process("checkout.html", context, resp.getWriter());
    }

}
