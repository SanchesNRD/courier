package by.epam.courierexchange.model.dao;

import by.epam.courierexchange.model.entity.ClientProduct;
import by.epam.courierexchange.model.entity.Product;
import by.epam.courierexchange.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface ProductDao extends BaseDao<Long, Product>{
    List<Product> selectByType(Integer type) throws DaoException;
    Optional<ClientProduct> selectClientProductById(Long id) throws DaoException;
}
