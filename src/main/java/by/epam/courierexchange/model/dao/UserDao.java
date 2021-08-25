package by.epam.courierexchange.model.dao;

import by.epam.courierexchange.exception.DaoException;
import by.epam.courierexchange.model.entity.User;

import java.util.Optional;

public interface UserDao extends BaseDao<Long, User>{
    Optional<User> selectByLogin(String login) throws DaoException;
}
