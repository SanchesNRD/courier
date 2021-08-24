package by.epam.courierexchange.model.dao.impl;

import by.epam.courierexchange.model.connection.ConnectionPool;
import by.epam.courierexchange.model.dao.ColumnName;
import by.epam.courierexchange.model.dao.CourierDao;
import by.epam.courierexchange.model.entity.Courier;
import by.epam.courierexchange.model.entity.UserStatus;
import by.epam.courierexchange.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CourierDaoImpl implements CourierDao {
    private static final Logger logger = LogManager.getLogger();
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private static final String SQL_SELECT_ALL_COURIER="""
            SELECT couriers.id, couriers.rating, users.login, users.mail, 
            users.name, users.surname, users.phone, users.status_id 
            FROM couriers, users
            """;
    private static final String SQL_SELECT_BY_ID="""
            SELECT couriers.id, couriers.rating, users.login, users.mail, 
            users.name, users.surname, users.phone, users.status_id 
            FROM couriers, users WHERE couriers.id=?
            """;
    private static final String SQL_SELECT_BY_LOGIN="""
            SELECT couriers.id, couriers.rating, users.login, users.mail, 
            users.name, users.surname, users.phone, users.status_id 
            FROM couriers, users WHERE users.login=?
            """;
    private static final String SQL_SELECT_BY_RATING="""
            SELECT couriers.id, couriers.rating, users.login, users.mail, 
            users.name, users.surname, users.phone, users.status_id 
            FROM couriers, users WHERE couriers.rating>=?
            """;
    private static final String SQL_DELETE_BY_ID=
            "DELETE FROM couriers WHERE id=?";
    private static final String SQL_INSERT=
            "INSERT INTO couriers (id, rating) VALUES (?,?)";
    private static final String SQL_UPDATE=
            "UPDATE couriers SET rating=? WHERE id=?";

    @Override
    public List<Courier> selectAll() throws DaoException {
        List<Courier> couriers = new ArrayList<>();
        try(
                Connection connection = connectionPool.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_COURIER);)
        {
            while (resultSet.next()){
                Courier courier = new Courier();
                courier.setId(resultSet.getLong(ColumnName.USER_ID));
                courier.setRating(resultSet.getDouble(ColumnName.ADDRESS_ID));
                courier.setLogin(resultSet.getString(ColumnName.USER_LOGIN));
                courier.setPassword(resultSet.getString(ColumnName.USER_PASSWORD));
                courier.setMail(resultSet.getString(ColumnName.USER_MAIL));
                courier.setName(resultSet.getString(ColumnName.USER_NAME));
                courier.setSurname(resultSet.getString(ColumnName.USER_SURNAME));
                courier.setPhone(resultSet.getString(ColumnName.USER_PHONE));
                courier.setUserStatus(UserStatus.parseStatus(resultSet.getShort(ColumnName.STATUS_ID)));
                couriers.add(courier);
            }
        } catch (SQLException e){
            logger.error("SQL exception in method in selectAllCourier ", e);
            throw new DaoException("SQL exception in method in selectAllCourier ", e);
        }
        return couriers;
    }

    @Override
    public Optional<Courier> selectById(Long id) throws DaoException {
        Courier courier = new Courier();
        try(
                Connection connection = connectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID);)
        {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if(!resultSet.next()){
                return Optional.empty();
            }else {
                courier.setId(resultSet.getLong(ColumnName.USER_ID));
                courier.setRating(resultSet.getDouble(ColumnName.ADDRESS_ID));
                courier.setLogin(resultSet.getString(ColumnName.USER_LOGIN));
                courier.setPassword(resultSet.getString(ColumnName.USER_PASSWORD));
                courier.setMail(resultSet.getString(ColumnName.USER_MAIL));
                courier.setName(resultSet.getString(ColumnName.USER_NAME));
                courier.setSurname(resultSet.getString(ColumnName.USER_SURNAME));
                courier.setPhone(resultSet.getString(ColumnName.USER_PHONE));
                courier.setUserStatus(UserStatus.parseStatus(resultSet.getShort(ColumnName.STATUS_ID)));
                return Optional.of(courier);
            }
        } catch (SQLException e){
            logger.error("SQL exception in method in selectCourierById ", e);
            throw new DaoException("SQL exception in method in selectCourierById ", e);
        }
    }

    @Override
    public Optional<Courier> selectCourierByLogin(String loginPattern) throws DaoException {
        Courier courier = new Courier();
        try(
                Connection connection = connectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_LOGIN);)
        {
            statement.setString(1, loginPattern);
            ResultSet resultSet = statement.executeQuery();
            if(!resultSet.next()){
                return Optional.empty();
            }else {
                courier.setId(resultSet.getLong(ColumnName.USER_ID));
                courier.setRating(resultSet.getDouble(ColumnName.ADDRESS_ID));
                courier.setLogin(resultSet.getString(ColumnName.USER_LOGIN));
                courier.setPassword(resultSet.getString(ColumnName.USER_PASSWORD));
                courier.setMail(resultSet.getString(ColumnName.USER_MAIL));
                courier.setName(resultSet.getString(ColumnName.USER_NAME));
                courier.setSurname(resultSet.getString(ColumnName.USER_SURNAME));
                courier.setPhone(resultSet.getString(ColumnName.USER_PHONE));
                courier.setUserStatus(UserStatus.parseStatus(resultSet.getShort(ColumnName.STATUS_ID)));
                return Optional.of(courier);
            }
        } catch (SQLException e){
            logger.error("SQL exception in method in selectCourierByLogin ", e);
            throw new DaoException("SQL exception in method in selectCourierByLogin ", e);
        }
    }

    @Override
    public List<Courier> selectCourierByRating(Double rating) throws DaoException {
        List<Courier> couriers = new ArrayList<>();
        try(
                Connection connection = connectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_RATING);)
        {
            statement.setDouble(1, rating);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Courier courier = new Courier();
                courier.setId(resultSet.getLong(ColumnName.USER_ID));
                courier.setRating(resultSet.getDouble(ColumnName.ADDRESS_ID));
                courier.setLogin(resultSet.getString(ColumnName.USER_LOGIN));
                courier.setPassword(resultSet.getString(ColumnName.USER_PASSWORD));
                courier.setMail(resultSet.getString(ColumnName.USER_MAIL));
                courier.setName(resultSet.getString(ColumnName.USER_NAME));
                courier.setSurname(resultSet.getString(ColumnName.USER_SURNAME));
                courier.setPhone(resultSet.getString(ColumnName.USER_PHONE));
                courier.setUserStatus(UserStatus.parseStatus(resultSet.getShort(ColumnName.STATUS_ID)));
                couriers.add(courier);
            }
        } catch (SQLException e){
            logger.error("SQL exception in method in selectCourierByRating ", e);
            throw new DaoException("SQL exception in method in selectCourierByRating ", e);
        }
        return couriers;
    }

    @Override
    public boolean deleteById(Long id) throws DaoException {
        try(
                Connection connection = connectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_DELETE_BY_ID);)
        {
            statement.setLong(1, id);
            return statement.execute();
        } catch (SQLException e){
            logger.error("SQL exception in method in deleteCourierById ", e);
            throw new DaoException("SQL exception in method in deleteCourierById ", e);
        }
    }

    @Override
    public boolean create(Courier courier) throws DaoException {
        try(
                Connection connection = connectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_INSERT);)
        {
            statement.setLong(1, courier.getId());
            statement.setDouble(2, courier.getRating());
            return statement.execute();
        } catch (SQLException e){
            logger.error("SQL exception in method in createCourier ", e);
            throw new DaoException("SQL exception in method in createCourier ", e);
        }
    }

    @Override
    public int update(Courier courier) throws DaoException {
        try(
                Connection connection = connectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_UPDATE);)
        {
            statement.setDouble(1, courier.getRating());
            statement.setLong(2, courier.getId());
            return statement.executeUpdate();
        } catch (SQLException e){
            logger.error("SQL exception in method in updateCourier ", e);
            throw new DaoException("SQL exception in method in updateCourier ", e);
        }
    }
}
