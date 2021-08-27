package by.epam.courierexchange.model.dao;

import by.epam.courierexchange.model.entity.Courier;
import by.epam.courierexchange.exception.DaoException;
import by.epam.courierexchange.model.entity.CourierTransport;

import java.util.List;
import java.util.Optional;

public interface CourierDao extends BaseDao<Long, Courier>{
    Optional<Courier> selectCourierByLogin(String loginPattern) throws DaoException;
    List<Courier> selectCourierByRating(Double rating) throws DaoException;

    List<CourierTransport> selectAllCourierTransport() throws DaoException;
    Optional<CourierTransport> selectCourierTransportById(Long id) throws DaoException;
    boolean deleteCourierTransportById(Long id) throws DaoException;
    boolean createCourierTransport(CourierTransport courierTransport) throws DaoException;
    int updateCourierTransport(CourierTransport courierTransport) throws DaoException;
}
