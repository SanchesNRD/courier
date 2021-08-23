package by.epam.courierexchange.dao.impl;

import by.epam.courierexchange.connection.ConnectionPool;
import by.epam.courierexchange.dao.ColumnName;
import by.epam.courierexchange.dao.OrderDao;
import by.epam.courierexchange.entity.Order;
import by.epam.courierexchange.entity.OrderStatus;
import by.epam.courierexchange.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderDaoImpl implements OrderDao {
    private static final Logger logger = LogManager.getLogger();
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private static final String SQL_SELECT_ALL="""
            SELECT id, client_id, product_id, transport_id, address_id, courier_id, date, status_id 
            FROM orders
            """;
    private static final String SQL_SELECT_BY_ID="""
            SELECT id, client_id, product_id, transport_id, address_id, courier_id, date, status_id 
            FROM orders WHERE id=?
            """;
    private static final String SQL_DELETE_BY_ID=
            "DELETE FROM orders WHERE id=?";
    private static final String SQL_INSERT=""" 
            INSERT INTO orders(id, client_id, product_id, transport_id, 
            address_id, courier_id, date, status_id) 
            VALUES (?,?,?,?,?,?,?,?)
            """;
    private static final String SQL_UPDATE="""
            UPDATE orders SET client_id=?, product_id=?, transport_id=?, 
            address_id=?, courier_id=?, date=?, status_id=? 
            WHERE id=?
            """;

    @Override
    public List<Order> selectAll() throws DaoException {
        Connection connection = null;
        Statement statement = null;
        List<Order> orders = new ArrayList<>();
        try{
            connection = connectionPool.getConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL);
            while(resultSet.next()){
                Order order = new Order();
                order.setId(resultSet.getLong(ColumnName.ID));
                order.setClient(resultSet.getLong(ColumnName.CLIENT_ID));
                order.setProduct(resultSet.getLong(ColumnName.PRODUCT_ID));
                order.setTransport(resultSet.getLong(ColumnName.TRANSPORT_ID));
                order.setAddress(resultSet.getLong(ColumnName.ADDRESS_ID));
                order.setCourier(resultSet.getLong(ColumnName.COURTIER_ID));
                order.setDate(resultSet.getDate(ColumnName.ORDER_DATE));
                order.setOrderStatus(OrderStatus.parseStatus(resultSet.getShort(ColumnName.STATUS_ID)));
                orders.add(order);
            }
        } catch (SQLException e){
            logger.error("SQL exception in method in selectAllOrders ", e);
            throw new DaoException("SQL exception in method in selectAllOrders ", e);
        } finally {
            close(statement);
            close(connection);
        }
        return orders;
    }

    @Override
    public Optional<Order> selectById(Long id) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        Order order = new Order();
        try{
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if(!resultSet.next()){
                return Optional.empty();
            }else{
                order.setId(resultSet.getLong(ColumnName.ID));
                order.setClient(resultSet.getLong(ColumnName.CLIENT_ID));
                order.setProduct(resultSet.getLong(ColumnName.PRODUCT_ID));
                order.setTransport(resultSet.getLong(ColumnName.TRANSPORT_ID));
                order.setAddress(resultSet.getLong(ColumnName.ADDRESS_ID));
                order.setCourier(resultSet.getLong(ColumnName.COURTIER_ID));
                order.setDate(resultSet.getDate(ColumnName.ORDER_DATE));
                order.setOrderStatus(OrderStatus.parseStatus(resultSet.getShort(ColumnName.STATUS_ID)));
            }
        } catch (SQLException e){
            logger.error("SQL exception in method in selectOrderById ", e);
            throw new DaoException("SQL exception in method in selectOrderById ", e);
        } finally {
            close(statement);
            close(connection);
        }
        return Optional.of(order);
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
            logger.error("SQL exception in method in deleteOrder ", e);
            throw new DaoException("SQL exception in method in deleteOrder ", e);
        } finally {
            close(connection);
            close(statement);
        }
    }

    @Override
    public boolean create(Order order) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SQL_INSERT);
            statement.setLong(1, order.getId());
            statement.setLong(2, order.getClient());
            statement.setLong(3, order.getProduct());
            statement.setLong(4, order.getTransport());
            statement.setLong(5, order.getAddress());
            statement.setLong(6, order.getCourier());
            statement.setDate(7, order.getDate());
            statement.setShort(8, order.getOrderStatus().getStatusId());
            return statement.execute();
        } catch (SQLException e){
            logger.error("SQL exception in method in createOrder ", e);
            throw new DaoException("SQL exception in method in createOrder ", e);
        } finally {
            close(connection);
            close(statement);
        }
    }

    @Override
    public int update(Order order) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SQL_UPDATE);
            statement.setLong(1, order.getClient());
            statement.setLong(2, order.getProduct());
            statement.setLong(3, order.getTransport());
            statement.setLong(4, order.getAddress());
            statement.setLong(5, order.getCourier());
            statement.setDate(6, order.getDate());
            statement.setShort(7, order.getOrderStatus().getStatusId());
            statement.setLong(8, order.getId());
            return statement.executeUpdate();
        } catch (SQLException e){
            logger.error("SQL exception in method in updateOrder ", e);
            throw new DaoException("SQL exception in method in updateOrder ", e);
        } finally {
            close(connection);
            close(statement);
        }
    }
}