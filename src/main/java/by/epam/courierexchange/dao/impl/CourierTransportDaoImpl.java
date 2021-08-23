package by.epam.courierexchange.dao.impl;

import by.epam.courierexchange.connection.ConnectionPool;
import by.epam.courierexchange.dao.ColumnName;
import by.epam.courierexchange.dao.CourierTransportDao;
import by.epam.courierexchange.entity.CourierTransport;
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
        Connection connection = null;
        Statement statement = null;
        List<CourierTransport> courierTransports = new ArrayList<>();
        try{
            connection = connectionPool.getConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL);
            while (resultSet.next()){
                CourierTransport courierTransport = new CourierTransport();
                courierTransport.setCourier(resultSet.getLong(ColumnName.COURTIER_ID));
                courierTransport.setTransport(resultSet.getLong(ColumnName.TRANSPORT_ID));
                courierTransports.add(courierTransport);
            }
        } catch (SQLException e){
            logger.error("SQL exception in method in selectAllCourierTransport ", e);
            throw new DaoException("SQL exception in method in selectAllCourierTransport ", e);
        } finally {
            close(statement);
            close(connection);
        }
        return courierTransports;
    }

    @Override
    public Optional<CourierTransport> selectById(Long id) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        CourierTransport courierTransport = new CourierTransport();
        try{
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_BY_ID);
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
        } finally {
            close(statement);
            close(connection);
        }
    }

    @Override
    public boolean deleteById(Long id) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SQL_DELETE_BY_ID);
            statement.setLong(1,id);
            return statement.execute();
        } catch (SQLException e){
            logger.error("SQL exception in method in deleteCourierTransport ", e);
            throw new DaoException("SQL exception in method in deleteCourierTransport ", e);
        } finally {
            close(statement);
            close(connection);
        }
    }

    @Override
    public boolean create(CourierTransport courierTransport) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SQL_INSERT);
            statement.setLong(1,courierTransport.getCourier());
            statement.setLong(2, courierTransport.getTransport());
            return statement.execute();
        } catch (SQLException e){
            logger.error("SQL exception in method in createCourierTransport ", e);
            throw new DaoException("SQL exception in method in createCourierTransport ", e);
        } finally {
            close(statement);
            close(connection);
        }
    }

    @Override
    public int update(CourierTransport courierTransport) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SQL_UPDATE);
            statement.setLong(1, courierTransport.getTransport());
            statement.setLong(2,courierTransport.getCourier());
            return statement.executeUpdate();
        } catch (SQLException e){
            logger.error("SQL exception in method in updateCourierTransport ", e);
            throw new DaoException("SQL exception in method in updateCourierTransport ", e);
        } finally {
            close(statement);
            close(connection);
        }
    }
}
