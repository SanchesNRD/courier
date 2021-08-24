package by.epam.courierexchange.servlet;

import java.io.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet(name = "helloServlet", urlPatterns = "/controller")
public class HelloServlet extends HttpServlet {
    final static Logger logger = LogManager.getLogger(HelloServlet.class);

    public HelloServlet() {
        super();
    }

    public void init() {

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");

        HelloServlet.logger.debug("debug level logging supported");
        response.getWriter().append("Servedat:").append(request.getContextPath());
        response.getWriter().append("Hello Suraj");

//        String n = request.getParameter("num");
//        int number = Integer.parseInt(n) * 7;
//        request.setAttribute("res", number);
//        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    public void destroy() {
    }
}