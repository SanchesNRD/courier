package by.epam.courierexchange.dao.impl;

import by.epam.courierexchange.connection.ConnectionPool;
import by.epam.courierexchange.dao.ColumnName;
import by.epam.courierexchange.dao.TransportDao;
import by.epam.courierexchange.entity.Transport;
import by.epam.courierexchange.entity.TransportType;
import by.epam.courierexchange.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TransportDaoImpl implements TransportDao {
    private static final Logger logger = LogManager.getLogger();
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private static final String SQL_SELECT_ALL="""
            SELECT id, name, average_speed, image, max_product_weight, type_id 
            FROM transports
            """;
    private static final String SQL_SELECT_BY_ID="""
            SELECT id, name, average_speed, image, max_product_weight, type_id 
            FROM transports WHERE id=?
            """;
    private static final String SQL_SELECT_BY_TYPE="""
            SELECT id, name, average_speed, image, max_product_weight, type_id 
            FROM transports WHERE type_id=?
            """;
    private static final String SQL_SELECT_BY_SPEED="""
            SELECT id, name, average_speed, image, max_product_weight, type_id 
            FROM transports WHERE average_speed>=?
            """;
    private static final String SQL_SELECT_BY_WEIGHT="""
            SELECT id, name, average_speed, image, max_product_weight, type_id 
            FROM transports WHERE max_product_WEIGHT>=?
            """;
    private static final String SQL_DELETE_BY_ID=
            "DELETE FROM transports WHERE id=?";
    private static final String SQL_INSERT="""
            INSERT INTO transports(id, name, average_speed, image, max_product_weight, type_id) 
            VALUES (?,?,?,?,?,?)
            """;
    private static final String SQL_UPDATE="""
            UPDATE transports SET name=?, average_speed=?, image=?, 
            max_product_weight=?, type_id=? WHERE id=?
            """;

    @Override
    public List<Transport> selectAll() throws DaoException {
        Connection connection = null;
        Statement statement = null;
        List<Transport> transports = new ArrayList<>();
        try{
            connection = connectionPool.getConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL);
            while(resultSet.next()){
                Transport transport = new Transport();
                transport.setId(resultSet.getLong(ColumnName.ID));
                transport.setName(resultSet.getString(ColumnName.TRANSPORT_NAME));
                transport.setAverageSpeed(resultSet.getInt(ColumnName.TRANSPORT_AVERAGE_SPEED));
                transport.setImage((resultSet.getBinaryStream(ColumnName.TRANSPORT_IMAGE)));
                transport.setMaxProductWeight(resultSet.getInt(ColumnName.TRANSPORT_MAX_PRODUCT_WEIGHT));
                transport.setTransportType(TransportType.parseType(resultSet.getShort(ColumnName.TYPE_ID)));
                transports.add(transport);
            }
        } catch (SQLException e){
            logger.error("SQL exception in method selectAllTransports ", e);
            throw new DaoException("SQL exception in method selectAllTransports ", e);
        } finally {
            close(statement);
            close(connection);
        }
        return transports;
    }

    @Override
    public Optional<Transport> selectById(Long id) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        Transport transport = new Transport();
        try{
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if(!resultSet.next()){
                return Optional.empty();
            }else{
                transport.setId(resultSet.getLong(ColumnName.ID));
                transport.setName(resultSet.getString(ColumnName.TRANSPORT_NAME));
                transport.setAverageSpeed(resultSet.getInt(ColumnName.TRANSPORT_AVERAGE_SPEED));
                transport.setImage((resultSet.getBinaryStream(ColumnName.TRANSPORT_IMAGE)));
                transport.setMaxProductWeight(resultSet.getInt(ColumnName.TRANSPORT_MAX_PRODUCT_WEIGHT));
                transport.setTransportType(TransportType.parseType(resultSet.getShort(ColumnName.TYPE_ID)));
                return Optional.of(transport);
            }
        } catch (SQLException e){
            logger.error("SQL exception in method selectTransportById ", e);
            throw new DaoException("SQL exception in method selectTransportById ", e);
        } finally {
            close(statement);
            close(connection);
        }
    }

    @Override
    public List<Transport> selectByType(Integer type) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        List<Transport> transports = new ArrayList<>();
        try{
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_BY_TYPE);
            statement.setInt(1, type);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                Transport transport = new Transport();
                transport.setId(resultSet.getLong(ColumnName.ID));
                transport.setName(resultSet.getString(ColumnName.TRANSPORT_NAME));
                transport.setAverageSpeed(resultSet.getInt(ColumnName.TRANSPORT_AVERAGE_SPEED));
                transport.setImage((resultSet.getBinaryStream(ColumnName.TRANSPORT_IMAGE)));
                transport.setMaxProductWeight(resultSet.getInt(ColumnName.TRANSPORT_MAX_PRODUCT_WEIGHT));
                transport.setTransportType(TransportType.parseType(resultSet.getShort(ColumnName.TYPE_ID)));
                transports.add(transport);
            }
        } catch (SQLException e){
            logger.error("SQL exception in method selectTransportByType ", e);
            throw new DaoException("SQL exception in method selectTransportByType ", e);
        } finally {
            close(statement);
            close(connection);
        }
        return transports;
    }

    @Override
    public List<Transport> selectBySpeed(Integer speed) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        List<Transport> transports = new ArrayList<>();
        try{
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_BY_SPEED);
            statement.setInt(1, speed);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                Transport transport = new Transport();
                transport.setId(resultSet.getLong(ColumnName.ID));
                transport.setName(resultSet.getString(ColumnName.TRANSPORT_NAME));
                transport.setAverageSpeed(resultSet.getInt(ColumnName.TRANSPORT_AVERAGE_SPEED));
                transport.setImage((resultSet.getBinaryStream(ColumnName.TRANSPORT_IMAGE)));
                transport.setMaxProductWeight(resultSet.getInt(ColumnName.TRANSPORT_MAX_PRODUCT_WEIGHT));
                transport.setTransportType(TransportType.parseType(resultSet.getShort(ColumnName.TYPE_ID)));
                transports.add(transport);
            }
        } catch (SQLException e){
            logger.error("SQL exception in method selectTransportBySpeed ", e);
            throw new DaoException("SQL exception in method selectTransportBySpeed ", e);
        } finally {
            close(statement);
            close(connection);
        }
        return transports;
    }

    @Override
    public List<Transport> selectByWeight(Integer weight) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        List<Transport> transports = new ArrayList<>();
        try{
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_BY_WEIGHT);
            statement.setInt(1, weight);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                Transport transport = new Transport();
                transport.setId(resultSet.getLong(ColumnName.ID));
                transport.setName(resultSet.getString(ColumnName.TRANSPORT_NAME));
                transport.setAverageSpeed(resultSet.getInt(ColumnName.TRANSPORT_AVERAGE_SPEED));
                transport.setImage((resultSet.getBinaryStream(ColumnName.TRANSPORT_IMAGE)));
                transport.setMaxProductWeight(resultSet.getInt(ColumnName.TRANSPORT_MAX_PRODUCT_WEIGHT));
                transport.setTransportType(TransportType.parseType(resultSet.getShort(ColumnName.TYPE_ID)));
                transports.add(transport);
            }
        } catch (SQLException e){
            logger.error("SQL exception in method selectTransportByWeight ", e);
            throw new DaoException("SQL exception in method selectTransportByWeight ", e);
        } finally {
            close(statement);
            close(connection);
        }
        return transports;
    }

    @Override
    public boolean deleteById(Long id) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SQL_DELETE_BY_ID);
            statement.setLong(1, id);
            return statement.execute();
        } catch (SQLException e){
            logger.error("SQL exception in method deleteTransportById ", e);
            throw new DaoException("SQL exception in method deleteTransportById ", e);
        } finally {
            close(connection);
            close(statement);
        }
    }

    @Override
    public boolean create(Transport transport) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SQL_INSERT);
            statement.setLong(1, transport.getId());
            statement.setString(2, transport.getName());
            statement.setInt(3, transport.getAverageSpeed());
            statement.setAsciiStream(4, transport.getImage());
            statement.setInt(5, transport.getMaxProductWeight());
            statement.setInt(6, transport.getTransportType().getId());
            return statement.execute();
        } catch (SQLException e){
            logger.error("SQL exception in method createTransport ", e);
            throw new DaoException("SQL exception in method createTransport ", e);
        } finally {
            close(connection);
            close(statement);
        }
    }

    @Override
    public int update(Transport transport) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SQL_UPDATE);
            statement.setString(1, transport.getName());
            statement.setInt(2, transport.getAverageSpeed());
            statement.setAsciiStream(3, transport.getImage());
            statement.setInt(4, transport.getMaxProductWeight());
            statement.setInt(5, transport.getTransportType().getId());
            statement.setLong(6, transport.getId());
            return statement.executeUpdate();
        } catch (SQLException e){
            logger.error("SQL exception in method updateTransport ", e);
            throw new DaoException("SQL exception in method updateTransport ", e);
        } finally {
            close(connection);
            close(statement);
        }
    }
}
