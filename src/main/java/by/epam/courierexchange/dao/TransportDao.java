package by.epam.courierexchange.dao;

import by.epam.courierexchange.entity.Transport;
import by.epam.courierexchange.exception.DaoException;

import java.util.List;

public interface TransportDao extends BaseDao<Long, Transport>{
    List<Transport> selectByType(Integer type) throws DaoException;
    List<Transport> selectBySpeed(Integer speed) throws DaoException;
    List<Transport> selectByWeight(Integer weight) throws DaoException;
}
