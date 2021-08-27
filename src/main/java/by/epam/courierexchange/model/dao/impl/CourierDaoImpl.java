package by.epam.courierexchange.model.dao.impl;

import by.epam.courierexchange.model.connection.ConnectionPool;
import by.epam.courierexchange.model.dao.CourierDao;
import by.epam.courierexchange.model.entity.*;
import by.epam.courierexchange.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.epam.courierexchange.model.dao.ColumnName.*;

public class CourierDaoImpl implements CourierDao {
    private static final Logger logger = LogManager.getLogger();
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final CourierDaoImpl instance = new CourierDaoImpl();

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
    //for table courier_transport
    private static final String SQL_SELECT_ALL_COURIER_TRANSPORT="""
            SELECT courier_id, transport_id 
            FROM courier_transport
            """;
    private static final String SQL_SELECT_COURIER_TRANSPORT_BY_ID="""
            SELECT courier_id, transport_id 
            FROM courier_transport WHERE courier_id=?
            """;
    private static final String SQL_DELETE_COURIER_TRANSPORT_BY_ID=
            "DELETE FROM courier_transport WHERE courier_id=?";
    private static final String SQL_INSERT_COURIER_TRANSPORT="""
            INSERT INTO courier_transport (courier_id, transport_id)
            VALUES (?,?)
            """;
    private static final String SQL_UPDATE_COURIER_TRANSPORT= """
            UPDATE courier_transport SET transport_id=?
            WHERE courier_id=?
            """;

    private CourierDaoImpl(){}

    public static CourierDaoImpl getInstance(){
        return instance;
    }

    @Override
    public List<Courier> selectAll() throws DaoException {
        List<Courier> couriers = new ArrayList<>();
        try(
                Connection connection = connectionPool.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_COURIER))
        {
            while (resultSet.next()){
                Courier courier = new Courier.CourierBuilder()
                        .setRating(resultSet.getLong(COURIER_RATING))
                        .setBuilder(new User.UserBuilder()
                                .setId(resultSet.getLong(USER_ID))
                                .setLogin(resultSet.getString(USER_LOGIN))
                                .setPassword(resultSet.getString(USER_PASSWORD))
                                .setMail(resultSet.getString(USER_MAIL))
                                .setName(resultSet.getString(USER_NAME))
                                .setSurname(resultSet.getString(USER_SURNAME))
                                .setPhone(resultSet.getString(USER_PHONE))
                                .setUserStatus(UserStatus.parseStatus(resultSet.getShort(STATUS_ID))))
                        .build();
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
        try(
                Connection connection = connectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID))
        {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if(!resultSet.next()){
                return Optional.empty();
            }else {
                Courier courier = new Courier.CourierBuilder()
                        .setRating(resultSet.getLong(COURIER_RATING))
                        .setBuilder(new User.UserBuilder()
                                .setId(resultSet.getLong(USER_ID))
                                .setLogin(resultSet.getString(USER_LOGIN))
                                .setPassword(resultSet.getString(USER_PASSWORD))
                                .setMail(resultSet.getString(USER_MAIL))
                                .setName(resultSet.getString(USER_NAME))
                                .setSurname(resultSet.getString(USER_SURNAME))
                                .setPhone(resultSet.getString(USER_PHONE))
                                .setUserStatus(UserStatus.parseStatus(resultSet.getShort(STATUS_ID))))
                        .build();
                return Optional.of(courier);
            }
        } catch (SQLException e){
            logger.error("SQL exception in method in selectCourierById ", e);
            throw new DaoException("SQL exception in method in selectCourierById ", e);
        }
    }

    @Override
    public Optional<Courier> selectCourierByLogin(String loginPattern) throws DaoException {
        try(
                Connection connection = connectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_LOGIN))
        {
            statement.setString(1, loginPattern);
            ResultSet resultSet = statement.executeQuery();
            if(!resultSet.next()){
                return Optional.empty();
            }else {
                Courier courier = new Courier.CourierBuilder()
                        .setRating(resultSet.getLong(COURIER_RATING))
                        .setBuilder(new User.UserBuilder()
                                .setId(resultSet.getLong(USER_ID))
                                .setLogin(resultSet.getString(USER_LOGIN))
                                .setPassword(resultSet.getString(USER_PASSWORD))
                                .setMail(resultSet.getString(USER_MAIL))
                                .setName(resultSet.getString(USER_NAME))
                                .setSurname(resultSet.getString(USER_SURNAME))
                                .setPhone(resultSet.getString(USER_PHONE))
                                .setUserStatus(UserStatus.parseStatus(resultSet.getShort(STATUS_ID))))
                        .build();
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
                PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_RATING))
        {
            statement.setDouble(1, rating);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Courier courier = new Courier.CourierBuilder()
                        .setRating(resultSet.getLong(COURIER_RATING))
                        .setBuilder(new User.UserBuilder()
                                .setId(resultSet.getLong(USER_ID))
                                .setLogin(resultSet.getString(USER_LOGIN))
                                .setPassword(resultSet.getString(USER_PASSWORD))
                                .setMail(resultSet.getString(USER_MAIL))
                                .setName(resultSet.getString(USER_NAME))
                                .setSurname(resultSet.getString(USER_SURNAME))
                                .setPhone(resultSet.getString(USER_PHONE))
                                .setUserStatus(UserStatus.parseStatus(resultSet.getShort(STATUS_ID))))
                        .build();
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
                PreparedStatement statement = connection.prepareStatement(SQL_DELETE_BY_ID))
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
                PreparedStatement statement = connection.prepareStatement(SQL_INSERT))
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
                PreparedStatement statement = connection.prepareStatement(SQL_UPDATE))
        {
            statement.setDouble(1, courier.getRating());
            statement.setLong(2, courier.getId());
            return statement.executeUpdate();
        } catch (SQLException e){
            logger.error("SQL exception in method in updateCourier ", e);
            throw new DaoException("SQL exception in method in updateCourier ", e);
        }
    }

    //courier_transport
    @Override
    public List<CourierTransport> selectAllCourierTransport() throws DaoException {
        List<CourierTransport> courierTransports = new ArrayList<>();
        try(
                Connection connection = connectionPool.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_COURIER_TRANSPORT))
        {
            while (resultSet.next()){
                CourierTransport courierTransport = new CourierTransport();
                courierTransport.setCourier(resultSet.getLong(COURTIER_ID));
                courierTransport.setTransport(resultSet.getLong(TRANSPORT_ID));
                courierTransports.add(courierTransport);
            }
        } catch (SQLException e){
            logger.error("SQL exception in method in selectAllCourierTransport ", e);
            throw new DaoException("SQL exception in method in selectAllCourierTransport ", e);
        }
        return courierTransports;
    }

    @Override
    public Optional<CourierTransport> selectCourierTransportById(Long id) throws DaoException {
        CourierTransport courierTransport = new CourierTransport();
        try(
                Connection connection = connectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_SELECT_COURIER_TRANSPORT_BY_ID))
        {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()){
                return Optional.empty();
            }else{
                courierTransport.setCourier(resultSet.getLong(COURTIER_ID));
                courierTransport.setTransport(resultSet.getLong(TRANSPORT_ID));
                return Optional.of(courierTransport);
            }
        } catch (SQLException e){
            logger.error("SQL exception in method in selectCourierTransportById ", e);
            throw new DaoException("SQL exception in method in selectCourierTransportById ", e);
        }
    }

    @Override
    public boolean deleteCourierTransportById(Long id) throws DaoException {
        try(
                Connection connection = connectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_DELETE_COURIER_TRANSPORT_BY_ID))
        {
            statement.setLong(1,id);
            return statement.execute();
        } catch (SQLException e){
            logger.error("SQL exception in method in deleteCourierTransport ", e);
            throw new DaoException("SQL exception in method in deleteCourierTransport ", e);
        }
    }

    @Override
    public boolean createCourierTransport(CourierTransport courierTransport) throws DaoException {
        try(
                Connection connection = connectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_INSERT_COURIER_TRANSPORT))
        {
            statement.setLong(1,courierTransport.getCourier());
            statement.setLong(2, courierTransport.getTransport());
            return statement.execute();
        } catch (SQLException e){
            logger.error("SQL exception in method in createCourierTransport ", e);
            throw new DaoException("SQL exception in method in createCourierTransport ", e);
        }
    }

    @Override
    public int updateCourierTransport(CourierTransport courierTransport) throws DaoException {
        try(
                Connection connection = connectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_COURIER_TRANSPORT))
        {
            statement.setLong(1, courierTransport.getTransport());
            statement.setLong(2,courierTransport.getCourier());
            return statement.executeUpdate();
        } catch (SQLException e){
            logger.error("SQL exception in method in updateCourierTransport ", e);
            throw new DaoException("SQL exception in method in updateCourierTransport ", e);
        }
    }
}
