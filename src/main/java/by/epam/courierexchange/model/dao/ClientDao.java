package by.epam.courierexchange.model.dao;

import by.epam.courierexchange.model.entity.Client;
import by.epam.courierexchange.exception.DaoException;
import by.epam.courierexchange.model.entity.ClientProduct;

import java.util.List;
import java.util.Optional;

public interface ClientDao extends BaseDao<Long, Client>{
    Optional<Client> selectClientByLogin(String loginPattern) throws DaoException;
    List<Client> selectClientsByStatus(Integer id) throws DaoException;

    List<ClientProduct> selectAllClientProduct() throws DaoException;
    Optional<ClientProduct> selectClientProductById(Long id) throws DaoException;
    boolean deleteClientProductById(Long id) throws DaoException;
    boolean createClientProduct(ClientProduct clientProduct) throws DaoException;
    int updateClientProduct(ClientProduct clientProduct) throws DaoException;
}
