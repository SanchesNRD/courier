package by.epam.courierexchange.model.service;

import by.epam.courierexchange.exception.ServiceException;
import by.epam.courierexchange.model.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> authorization(String login, String password) throws ServiceException;
    boolean registration(User user) throws ServiceException;
    boolean updatePassword(User user) throws ServiceException;

}
