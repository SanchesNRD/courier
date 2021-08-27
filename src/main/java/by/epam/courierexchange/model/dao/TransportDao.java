package by.epam.courierexchange.model.dao;

import by.epam.courierexchange.model.entity.CourierTransport;
import by.epam.courierexchange.model.entity.Transport;
import by.epam.courierexchange.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface TransportDao extends BaseDao<Long, Transport>{
    List<Transport> selectByType(Integer type) throws DaoException;
    List<Transport> selectBySpeed(Integer speed) throws DaoException;
    List<Transport> selectByWeight(Integer weight) throws DaoException;

    Optional<CourierTransport> selectCourierTransportById(Long id) throws DaoException;
}
