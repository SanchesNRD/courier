package by.epam.courierexchange.model.service.impl;

import by.epam.courierexchange.exception.DaoException;
import by.epam.courierexchange.exception.ServiceException;
import by.epam.courierexchange.model.dao.impl.UserDaoImpl;
import by.epam.courierexchange.model.entity.User;
import by.epam.courierexchange.model.service.UserService;
import by.epam.courierexchange.model.validator.UserValidator;
import by.epam.courierexchange.util.PasswordEncryption;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger();
    private static final UserServiceImpl instance = new UserServiceImpl();
    private static final UserDaoImpl userDao = UserDaoImpl.getInstance();

    public UserServiceImpl() {
    }

    public static UserServiceImpl getInstance() {
        return instance;
    }


    @Override
    public Optional<User> authorization(String login, String password) throws ServiceException {
        Optional<User> optionalUser;
        User user;
        if (!UserValidator.loginIsValid(login) && !UserValidator.passwordIsValid(password)){
            return Optional.empty();
        }

        try {
            optionalUser = userDao.selectByLogin(login);
            if(optionalUser.isEmpty()){
                return Optional.empty();
            }
            user = optionalUser.get();
            String plainPass = user.getPassword();
            String hashedPass = PasswordEncryption.encode(password);
            if(!PasswordEncryption.matches(plainPass, hashedPass)){
                return Optional.empty();
            }
        } catch (DaoException e) {
            logger.error("Exception while working with the database ", e);
            throw new ServiceException("exception while working with the database ", e);
        }
        return optionalUser;
    }

    @Override
    public boolean registration(User user) throws ServiceException {
        if(!UserValidator.loginIsValid(user.getLogin()) &&
                !UserValidator.passwordIsValid(user.getPassword())){
            return false;
        }

        try{
            return userDao.create(user);
        } catch (DaoException e){
            logger.error("Exception wile working with database ", e);
            throw new ServiceException("Exception wile working with database ", e);
        }
    }

    @Override
    public boolean updatePassword(User user) throws ServiceException {
        // TODO: 11.09.2021 !!!
        return false;
    }
}
