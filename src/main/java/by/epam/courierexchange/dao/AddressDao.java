package by.epam.courierexchange.dao;

import by.epam.courierexchange.entity.Address;
import by.epam.courierexchange.exception.DaoException;

import java.util.List;

public interface AddressDao extends BaseDao<Long, Address>{
    List<Address> selectByCity(String patternCountry) throws DaoException;
}
