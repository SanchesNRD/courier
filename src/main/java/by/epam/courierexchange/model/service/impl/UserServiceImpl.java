package by.epam.courierexchange.model.service.impl;

import by.epam.courierexchange.exception.DaoException;
import by.epam.courierexchange.exception.ServiceException;
import by.epam.courierexchange.model.dao.impl.UserDaoImpl;
import by.epam.courierexchange.model.entity.User;
import by.epam.courierexchange.model.service.UserService;
import by.epam.courierexchange.model.validator.UserValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger();
    private static UserService instance;
    private static final UserDaoImpl userDao = UserDaoImpl.getInstance();

    private UserServiceImpl() {
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl();
        }
        return instance;
    }


    @Override
    public Optional<User> authorized(String login, String password) throws ServiceException {
        Optional<User> user;
        User temporaryUser;
        if (!UserValidator.loginIsValid(login) && !UserValidator.passwordIsValid(password)){
            return Optional.empty();
        }

        try {
            user = userDao.selectByLogin(login);
            if(user.isEmpty()){
                return Optional.empty();
            }
            temporaryUser = user.get();
            if(!password.equals(temporaryUser.getPassword())){
                return Optional.empty();
            }
        } catch (DaoException e) {
            logger.error("Exception while working with the database ", e);
            throw new ServiceException("exception while working with the database ", e);
        }
        return user;
    }
}
