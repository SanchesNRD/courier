package by.epam.courierexchange.model.dao.impl;

import by.epam.courierexchange.model.connection.ConnectionPool;
import by.epam.courierexchange.model.dao.AddressDao;
import by.epam.courierexchange.model.dao.ColumnName;
import by.epam.courierexchange.model.entity.Address;
import by.epam.courierexchange.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AddressDaoImpl implements AddressDao {
    private static final Logger logger = LogManager.getLogger();
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private static final String SQL_SELECT_ADDRESS_BY_CITY = """
            SELECT id, country, city, street, street_number, apartment
            FROM addresses WHERE city=?
            """;
    private static final String SQL_SELECT_ALL_ADDRESSES = """
            SELECT id, country, city, street, street_number, apartment
            FROM addresses
            """;
    private static final String SQL_SELECT_ADDRESS_BY_ID = """
            SELECT id, country, city, street, street_number, apartment 
            FROM addresses WHERE id=?
            """;
    private static final String SQL_DELETE_ADDRESS_BY_ID =
            "DELETE FROM addresses WHERE id=?";
    private static final String SQL_CREAT_ADDRESS= """
            INSERT INTO addresses (country, city, street, street_number, apartment)
            VALUES (?,?,?,?,?)""";
    private static final String SQL_UPDATE_ADDRESS= """
            UPDATE addresses SET country=?, city=?, street=?, street_number=?, apartment=?
            WHERE id=?
            """;


    @Override
    public List<Address> selectByCity(String patternCity) throws DaoException {
        List<Address> addresses = new ArrayList<>();

        try(
                Connection connection = connectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ADDRESS_BY_CITY);)
        {
            statement.setString(1, patternCity);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Address address = new Address();
                address.setId(resultSet.getLong(ColumnName.ID));
                address.setCountry(resultSet.getString(ColumnName.ADDRESS_COUNTRY));
                address.setCity(resultSet.getString(ColumnName.ADDRESS_CITY));
                address.setStreet(resultSet.getString(ColumnName.ADDRESS_STREET));
                address.setStreet_number(resultSet.getInt(ColumnName.ADDRESS_STREET_NUMBER));
                address.setApartment(resultSet.getInt(ColumnName.ADDRESS_APARTMENT));
                addresses.add(address);
            }
        } catch(SQLException e){
            logger.error("SQL exception in method selectAddressByCity ", e);
            throw new DaoException("SQL exception in method selectAddressByCity ", e);
        }
        return addresses;
    }

    @Override
    public List<Address> selectAll() throws DaoException {
        List<Address> addresses = new ArrayList<>();
        try(
                Connection connection = connectionPool.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_ADDRESSES);)
        {
            while (resultSet.next()){
                Address address = new Address();
                address.setId(resultSet.getLong(ColumnName.ID));
                address.setCountry(resultSet.getString(ColumnName.ADDRESS_COUNTRY));
                address.setCity(resultSet.getString(ColumnName.ADDRESS_CITY));
                address.setStreet(resultSet.getString(ColumnName.ADDRESS_STREET));
                address.setStreet_number(resultSet.getInt(ColumnName.ADDRESS_STREET_NUMBER));
                address.setApartment(resultSet.getInt(ColumnName.ADDRESS_APARTMENT));
                addresses.add(address);
            }
        } catch(SQLException e){
            logger.error("SQL exception in method selectAllAddress ", e);
            throw new DaoException("SQL exception in method selectAllAddress ", e);
        }
        return addresses;
    }

    @Override
    public Optional<Address> selectById(Long id) throws DaoException{
        Address address = new Address();
        try(
                Connection connection = connectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ADDRESS_BY_ID);)
        {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if(!resultSet.next()){
                return Optional.empty();
            }else {
                address.setId(resultSet.getLong(ColumnName.ID));
                address.setCountry(resultSet.getString(ColumnName.ADDRESS_COUNTRY));
                address.setCity(resultSet.getString(ColumnName.ADDRESS_CITY));
                address.setStreet(resultSet.getString(ColumnName.ADDRESS_STREET));
                address.setStreet_number(resultSet.getInt(ColumnName.ADDRESS_STREET_NUMBER));
                address.setApartment(resultSet.getInt(ColumnName.ADDRESS_APARTMENT));
            }
        } catch(SQLException e){
            logger.error("SQL exception in method selectAddressById ", e);
            throw new DaoException("SQL exception in method selectAddressById ", e);
        }
        return Optional.of(address);
    }

    @Override
    public boolean deleteById(Long id) throws DaoException {
        try(
                Connection connection = connectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_DELETE_ADDRESS_BY_ID);)
        {
            statement.setLong(1, id);
            return statement.execute();
        } catch (SQLException e){
            logger.error("SQL exception in method deleteAddressById ", e);
            throw new DaoException("SQL exception in method deleteAddressById ", e);
        }
    }

    @Override
    public boolean create(Address address) throws DaoException {
        try(
                Connection connection = connectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_CREAT_ADDRESS);)
        {
            statement.setString(1, address.getCountry());
            statement.setString(2, address.getCity());
            statement.setString(3, address.getStreet());
            statement.setInt(4, address.getStreet_number());
            statement.setInt(5, address.getApartment());
            return statement.execute();
        } catch (SQLException e){
            logger.error("SQL exception in method createAddress ", e);
            throw new DaoException("SQL exception in method createAddress ", e);
        }
    }

    @Override
    public int update(Address address) throws DaoException {
        try(
                Connection connection = connectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_ADDRESS);)
        {
            statement.setString(1, address.getCountry());
            statement.setString(2, address.getCity());
            statement.setString(3, address.getStreet());
            statement.setInt(4, address.getStreet_number());
            statement.setInt(5, address.getApartment());
            statement.setLong(6, address.getId());
            return statement.executeUpdate();
        } catch (SQLException e){
            logger.error("SQL exception in method updateAddress ", e);
            throw new DaoException("SQL exception in method updateAddress ", e);
        }
    }
}
