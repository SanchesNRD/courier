package by.epam.courierexchange.model.dao;

import by.epam.courierexchange.model.entity.Courier;
import by.epam.courierexchange.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface CourierDao extends BaseDao<Long, Courier>{
    Optional<Courier> selectCourierByLogin(String loginPattern) throws DaoException;
    List<Courier> selectCourierByRating(Double rating) throws DaoException;
}
