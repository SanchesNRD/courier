package by.epam.courierexchange.model.dao.impl;

import by.epam.courierexchange.model.connection.ConnectionPool;
import by.epam.courierexchange.model.dao.ColumnName;
import by.epam.courierexchange.model.dao.OrderDao;
import by.epam.courierexchange.model.entity.Order;
import by.epam.courierexchange.model.entity.OrderStatus;
import by.epam.courierexchange.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static by.epam.courierexchange.model.dao.ColumnName.*;

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
        List<Order> orders = new ArrayList<>();
        try(
                Connection connection = connectionPool.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL))
        {
            while(resultSet.next()){
                Order order = new Order();
                order.setId(resultSet.getLong(ID));
                order.setClient(resultSet.getLong(CLIENT_ID));
                order.setProduct(resultSet.getLong(PRODUCT_ID));
                order.setTransport(resultSet.getLong(TRANSPORT_ID));
                order.setAddress(resultSet.getLong(ADDRESS_ID));
                order.setCourier(resultSet.getLong(COURTIER_ID));
                order.setDate(resultSet.getDate(ORDER_DATE));
                order.setOrderStatus(OrderStatus.parseStatus(resultSet.getShort(STATUS_ID)));
                orders.add(order);
            }
        } catch (SQLException e){
            logger.error("SQL exception in method in selectAllOrders ", e);
            throw new DaoException("SQL exception in method in selectAllOrders ", e);
        }
        return orders;
    }

    @Override
    public Optional<Order> selectById(Long id) throws DaoException {
        Order order = new Order();
        try(
                Connection connection = connectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID))
        {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if(!resultSet.next()){
                return Optional.empty();
            }else{
                order.setId(resultSet.getLong(ID));
                order.setClient(resultSet.getLong(CLIENT_ID));
                order.setProduct(resultSet.getLong(PRODUCT_ID));
                order.setTransport(resultSet.getLong(TRANSPORT_ID));
                order.setAddress(resultSet.getLong(ADDRESS_ID));
                order.setCourier(resultSet.getLong(COURTIER_ID));
                order.setDate(resultSet.getDate(ORDER_DATE));
                order.setOrderStatus(OrderStatus.parseStatus(resultSet.getShort(STATUS_ID)));
            }
        } catch (SQLException e){
            logger.error("SQL exception in method in selectOrderById ", e);
            throw new DaoException("SQL exception in method in selectOrderById ", e);
        }
        return Optional.of(order);
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
            logger.error("SQL exception in method in deleteOrder ", e);
            throw new DaoException("SQL exception in method in deleteOrder ", e);
        }
    }

    @Override
    public boolean create(Order order) throws DaoException {
        try(
                Connection connection = connectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_INSERT))
        {
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
        }
    }

    @Override
    public int update(Order order) throws DaoException {
        try(
                Connection connection = connectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_UPDATE))
        {
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
        }
    }
}
