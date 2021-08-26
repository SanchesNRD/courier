package by.epam.courierexchange.controller.command.impl;

import by.epam.courierexchange.controller.command.Command;
import by.epam.courierexchange.controller.command.CommandResult;
import by.epam.courierexchange.controller.command.SessionAttribute;
import by.epam.courierexchange.exception.ServiceException;
import by.epam.courierexchange.model.entity.User;
import by.epam.courierexchange.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static by.epam.courierexchange.controller.command.CommandResult.ResponseType.FORWARD;
import static by.epam.courierexchange.controller.command.CommandResult.ResponseType.REDIRECT;
import static by.epam.courierexchange.controller.command.PagePath.*;
import static by.epam.courierexchange.controller.command.RequestParameter.*;

public class LoginCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private UserServiceImpl userService;

    @Override
    public CommandResult execute(HttpServletRequest request) {
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);
        userService = UserServiceImpl.getInstance();
        CommandResult commandResult;

        try {
            Optional<User> optionalUser = userService.authorized(login, password);
            if(optionalUser.isPresent()){
                User user = optionalUser.get();
                HttpSession session = request.getSession(true);
                session.setAttribute(SessionAttribute.USER, user);
                //todo modify with status
                commandResult = new CommandResult(SUCCESS_PAGE, REDIRECT);
            }
            else{
                commandResult = new CommandResult(LOGIN_PAGE, FORWARD);
            }
        } catch (ServiceException e) {
            logger.error("", e);
            commandResult = new CommandResult(ERROR_PAGE, FORWARD);
        }
        return commandResult;
    }
}
