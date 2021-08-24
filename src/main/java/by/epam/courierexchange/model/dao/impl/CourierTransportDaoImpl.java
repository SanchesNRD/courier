package by.epam.courierexchange.model.dao.impl;

import by.epam.courierexchange.model.connection.ConnectionPool;
import by.epam.courierexchange.model.dao.ColumnName;
import by.epam.courierexchange.model.dao.CourierTransportDao;
import by.epam.courierexchange.model.entity.CourierTransport;
import by.epam.courierexchange.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CourierTransportDaoImpl implements CourierTransportDao {
    private static final Logger logger = LogManager.getLogger();
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private static final String SQL_SELECT_ALL="""
            SELECT courier_id, transport_id 
            FROM courier_transport
            """;
    private static final String SQL_SELECT_BY_ID="""
            SELECT courier_id, transport_id 
            FROM courier_transport WHERE courier_id=?
            """;
    private static final String SQL_DELETE_BY_ID=
            "DELETE FROM courier_transport WHERE courier_id=?";
    private static final String SQL_INSERT="""
            INSERT INTO courier_transport (courier_id, transport_id)
            VALUES (?,?)
            """;
    private static final String SQL_UPDATE= """
            UPDATE courier_transport SET transport_id=?
            WHERE courier_id=?
            """;

    @Override
    public List<CourierTransport> selectAll() throws DaoException {
        List<CourierTransport> courierTransports = new ArrayList<>();
        try(
                Connection connection = connectionPool.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL);)
        {
            while (resultSet.next()){
                CourierTransport courierTransport = new CourierTransport();
                courierTransport.setCourier(resultSet.getLong(ColumnName.COURTIER_ID));
                courierTransport.setTransport(resultSet.getLong(ColumnName.TRANSPORT_ID));
                courierTransports.add(courierTransport);
            }
        } catch (SQLException e){
            logger.error("SQL exception in method in selectAllCourierTransport ", e);
            throw new DaoException("SQL exception in method in selectAllCourierTransport ", e);
        }
        return courierTransports;
    }

    @Override
    public Optional<CourierTransport> selectById(Long id) throws DaoException {
        CourierTransport courierTransport = new CourierTransport();
        try(
                Connection connection = connectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID);)
        {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()){
                return Optional.empty();
            }else{
                courierTransport.setCourier(resultSet.getLong(ColumnName.COURTIER_ID));
                courierTransport.setTransport(resultSet.getLong(ColumnName.TRANSPORT_ID));
                return Optional.of(courierTransport);
            }
        } catch (SQLException e){
            logger.error("SQL exception in method in selectCourierTransportById ", e);
            throw new DaoException("SQL exception in method in selectCourierTransportById ", e);
        }
    }

    @Override
    public boolean deleteById(Long id) throws DaoException {
        try(
                Connection connection = connectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_DELETE_BY_ID);)
        {
            statement.setLong(1,id);
            return statement.execute();
        } catch (SQLException e){
            logger.error("SQL exception in method in deleteCourierTransport ", e);
            throw new DaoException("SQL exception in method in deleteCourierTransport ", e);
        }
    }

    @Override
    public boolean create(CourierTransport courierTransport) throws DaoException {
        try(
                Connection connection = connectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_INSERT);)
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
    public int update(CourierTransport courierTransport) throws DaoException {
        try(
                Connection connection = connectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_UPDATE);)
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
