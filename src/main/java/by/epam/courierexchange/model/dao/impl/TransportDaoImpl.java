package by.epam.courierexchange.model.dao.impl;

import by.epam.courierexchange.model.connection.ConnectionPool;
import by.epam.courierexchange.model.dao.TransportDao;
import by.epam.courierexchange.model.entity.CourierTransport;
import by.epam.courierexchange.model.entity.Transport;
import by.epam.courierexchange.model.entity.TransportType;
import by.epam.courierexchange.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static by.epam.courierexchange.model.dao.ColumnName.*;

public class TransportDaoImpl implements TransportDao {
    private static final Logger logger = LogManager.getLogger();
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final TransportDaoImpl instance = new TransportDaoImpl();

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
    private static final String SQL_SELECT_COURIER_TRANSPORT_BY_ID="""
            SELECT courier_id, transport_id 
            FROM courier_transport WHERE transport_id=?
            """;

    private TransportDaoImpl(){}

    public static TransportDaoImpl getInstance(){
        return instance;
    }

    @Override
    public List<Transport> selectAll() throws DaoException {
        List<Transport> transports = new ArrayList<>();
        try(
                Connection connection = connectionPool.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL))
        {
            while(resultSet.next()){
                Transport transport = new Transport.TransportBuilder()
                        .setId(resultSet.getLong(ID))
                        .setName(resultSet.getString(TRANSPORT_NAME))
                        .setAverageSpeed(resultSet.getInt(TRANSPORT_AVERAGE_SPEED))
                        .setImage((resultSet.getBinaryStream(TRANSPORT_IMAGE)))
                        .setMaxProductWeight(resultSet.getInt(TRANSPORT_MAX_PRODUCT_WEIGHT))
                        .setTransportType(TransportType.parseType(resultSet.getShort(TYPE_ID)))
                        .build();
                transports.add(transport);
            }
        } catch (SQLException e){
            logger.error("SQL exception in method selectAllTransports ", e);
            throw new DaoException("SQL exception in method selectAllTransports ", e);
        }
        return transports;
    }

    @Override
    public Optional<Transport> selectById(Long id) throws DaoException {
        try(
                Connection connection = connectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID))
        {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if(!resultSet.next()){
                return Optional.empty();
            }else{
                Transport transport = new Transport.TransportBuilder()
                        .setId(resultSet.getLong(ID))
                        .setName(resultSet.getString(TRANSPORT_NAME))
                        .setAverageSpeed(resultSet.getInt(TRANSPORT_AVERAGE_SPEED))
                        .setImage((resultSet.getBinaryStream(TRANSPORT_IMAGE)))
                        .setMaxProductWeight(resultSet.getInt(TRANSPORT_MAX_PRODUCT_WEIGHT))
                        .setTransportType(TransportType.parseType(resultSet.getShort(TYPE_ID)))
                        .build();
                return Optional.of(transport);
            }
        } catch (SQLException e){
            logger.error("SQL exception in method selectTransportById ", e);
            throw new DaoException("SQL exception in method selectTransportById ", e);
        }
    }

    @Override
    public List<Transport> selectByType(Integer type) throws DaoException {
        List<Transport> transports = new ArrayList<>();
        try(
                Connection connection = connectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_TYPE))
        {
            statement.setInt(1, type);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                Transport transport = new Transport.TransportBuilder()
                        .setId(resultSet.getLong(ID))
                        .setName(resultSet.getString(TRANSPORT_NAME))
                        .setAverageSpeed(resultSet.getInt(TRANSPORT_AVERAGE_SPEED))
                        .setImage((resultSet.getBinaryStream(TRANSPORT_IMAGE)))
                        .setMaxProductWeight(resultSet.getInt(TRANSPORT_MAX_PRODUCT_WEIGHT))
                        .setTransportType(TransportType.parseType(resultSet.getShort(TYPE_ID)))
                        .build();
                transports.add(transport);
            }
        } catch (SQLException e){
            logger.error("SQL exception in method selectTransportByType ", e);
            throw new DaoException("SQL exception in method selectTransportByType ", e);
        }
        return transports;
    }

    @Override
    public List<Transport> selectBySpeed(Integer speed) throws DaoException {
        List<Transport> transports = new ArrayList<>();
        try(
                Connection connection = connectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_SPEED))
        {
            statement.setInt(1, speed);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                Transport transport = new Transport.TransportBuilder()
                        .setId(resultSet.getLong(ID))
                        .setName(resultSet.getString(TRANSPORT_NAME))
                        .setAverageSpeed(resultSet.getInt(TRANSPORT_AVERAGE_SPEED))
                        .setImage((resultSet.getBinaryStream(TRANSPORT_IMAGE)))
                        .setMaxProductWeight(resultSet.getInt(TRANSPORT_MAX_PRODUCT_WEIGHT))
                        .setTransportType(TransportType.parseType(resultSet.getShort(TYPE_ID)))
                        .build();
                transports.add(transport);
            }
        } catch (SQLException e){
            logger.error("SQL exception in method selectTransportBySpeed ", e);
            throw new DaoException("SQL exception in method selectTransportBySpeed ", e);
        }
        return transports;
    }

    @Override
    public List<Transport> selectByWeight(Integer weight) throws DaoException {
        List<Transport> transports = new ArrayList<>();
        try(
                Connection connection = connectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_WEIGHT))
        {
            statement.setInt(1, weight);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                Transport transport = new Transport.TransportBuilder()
                        .setId(resultSet.getLong(ID))
                        .setName(resultSet.getString(TRANSPORT_NAME))
                        .setAverageSpeed(resultSet.getInt(TRANSPORT_AVERAGE_SPEED))
                        .setImage((resultSet.getBinaryStream(TRANSPORT_IMAGE)))
                        .setMaxProductWeight(resultSet.getInt(TRANSPORT_MAX_PRODUCT_WEIGHT))
                        .setTransportType(TransportType.parseType(resultSet.getShort(TYPE_ID)))
                        .build();
                transports.add(transport);
            }
        } catch (SQLException e){
            logger.error("SQL exception in method selectTransportByWeight ", e);
            throw new DaoException("SQL exception in method selectTransportByWeight ", e);
        }
        return transports;
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
            logger.error("SQL exception in method deleteTransportById ", e);
            throw new DaoException("SQL exception in method deleteTransportById ", e);
        }
    }

    @Override
    public boolean create(Transport transport) throws DaoException {
        try(
                Connection connection = connectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_INSERT))
        {
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
        }
    }

    @Override
    public int update(Transport transport) throws DaoException {
        try(
                Connection connection = connectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_UPDATE))
        {
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
        }
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
}
