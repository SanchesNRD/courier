package by.epam.courierexchange.model.dao;

import by.epam.courierexchange.model.entity.Client;
import by.epam.courierexchange.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface ClientDao extends BaseDao<Long, Client>{
    Optional<Client> selectClientByLogin(String loginPattern) throws DaoException;
    List<Client> selectClientsByStatus(Integer id) throws DaoException;
}