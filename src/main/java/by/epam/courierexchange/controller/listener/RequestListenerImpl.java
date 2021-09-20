//package by.epam.courierexchange.controller.listener;
//
//import by.epam.courierexchange.controller.command.PagePath;
//import by.epam.courierexchange.controller.command.RequestParameter;
//import by.epam.courierexchange.controller.command.SessionAttribute;
//import jakarta.servlet.ServletRequest;
//import jakarta.servlet.ServletRequestEvent;
//import jakarta.servlet.ServletRequestListener;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpSession;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import java.io.UnsupportedEncodingException;
//
//public class RequestListenerImpl implements ServletRequestListener {
//    private static final Logger logger = LogManager.getLogger();
//
//    @Override
//    public void requestInitialized(ServletRequestEvent sre) {
//        ServletRequest servletRequest = sre.getServletRequest();
//        try {
//            servletRequest.setCharacterEncoding("UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            logger.error("Incorrect encoding", e);
//        }
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        String command = request.getParameter(RequestParameter.COMMAND);
//        if (command != null) {
//            if (command.compareToIgnoreCase("change_locale_command") != 0) {
//                //String prevRequest = PagePath.CONTROLLER_URL + request.getQueryString();
//                HttpSession session = request.getSession(true);
//                //session.setAttribute(SessionAttribute.PREVIOUS_REQUEST, prevRequest);
//            }
//        }
//    }
//}
