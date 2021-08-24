package by.epam.courierexchange.controller;

import java.io.*;
import java.util.List;

import by.epam.courierexchange.exception.DaoException;
import by.epam.courierexchange.model.dao.AddressDao;
import by.epam.courierexchange.model.dao.impl.AddressDaoImpl;
import by.epam.courierexchange.model.entity.Address;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet(name = "helloServlet", urlPatterns = "/controller")
public class Controller extends HttpServlet {
    public void init() {

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");

        String n = request.getParameter("num");
        int number = Integer.parseInt(n) * 7;
        request.setAttribute("res", number);
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    public void destroy() {
    }
}