package by.epam.courierexchange.model.dao.impl;

import by.epam.courierexchange.model.connection.ConnectionPool;
import by.epam.courierexchange.model.dao.ClientProductDao;
import by.epam.courierexchange.model.dao.ColumnName;
import by.epam.courierexchange.model.entity.ClientProduct;
import by.epam.courierexchange.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static by.epam.courierexchange.model.dao.ColumnName.*;

public class ClientProductDaoImpl implements ClientProductDao {
    private static final Logger logger = LogManager.getLogger();
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private static final String SQL_SELECT_ALL="""
            SELECT client_id, product_id 
            FROM client_product
            """;
    private static final String SQL_SELECT_BY_ID="""
            SELECT client_id, product_id
            FROM client_product WHERE client_id=?
            """;
    private static final String SQL_DELETE_BY_ID=
            "DELETE FROM client_product WHERE client_id=?";
    private static final String SQL_INSERT="""
            INSERT INTO client_product (client_id, product_id)
            VALUES (?,?)
            """;
    private static final String SQL_UPDATE="""
            UPDATE client_product SET product_id=?
            WHERE client_id=?
            """;

    @Override
    public List<ClientProduct> selectAll() throws DaoException {
        List<ClientProduct> clientProducts = new ArrayList<>();
        try(
                Connection connection = connectionPool.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL))
        {
            while (resultSet.next()){
               ClientProduct clientProduct = new ClientProduct();
               clientProduct.setClient(resultSet.getLong(CLIENT_ID));
               clientProduct.setProduct(resultSet.getLong(PRODUCT_ID));
               clientProducts.add(clientProduct);
            }
        } catch (SQLException e){
            logger.error("SQL exception in method selectAllClientProducts ", e);
            throw new DaoException("SQL exception in method selectAllClientProducts ", e);
        }
        return clientProducts;
    }

    @Override
    public Optional<ClientProduct> selectById(Long id) throws DaoException {
        ClientProduct clientProduct = new ClientProduct();
        try(
                Connection connection = connectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID))
        {
            statement.setLong(1,id);
            ResultSet resultSet = statement.executeQuery();
            if(!resultSet.next()) {
                return Optional.empty();
            }else{
                clientProduct.setClient(resultSet.getLong(CLIENT_ID));
                clientProduct.setProduct(resultSet.getLong(PRODUCT_ID));
                return Optional.of(clientProduct);
            }
        } catch (SQLException e){
            logger.error("SQL exception in method selectClientProductById ", e);
            throw new DaoException("SQL exception in method selectClientProductById ", e);
        }
    }

    @Override
    public boolean deleteById(Long id) throws DaoException {
        try(
                Connection connection = connectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_DELETE_BY_ID))
        {
            statement.setLong(1,id);
            return statement.execute();
        } catch (SQLException e){
            logger.error("SQL exception in method deleteClientProductById ", e);
            throw new DaoException("SQL exception in method deleteClientProductById ", e);
        }
    }

    @Override
    public boolean create(ClientProduct clientProduct) throws DaoException {
        try(
                Connection connection = connectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_INSERT))
        {
            statement.setLong(1,clientProduct.getClient());
            statement.setLong(2, clientProduct.getProduct());
            return statement.execute();
        } catch (SQLException e){
            logger.error("SQL exception in method createClientProduct ", e);
            throw new DaoException("SQL exception in method createClientProduct ", e);
        }
    }

    @Override
    public int update(ClientProduct clientProduct) throws DaoException {
        try(
                Connection connection = connectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_UPDATE))
        {
            statement.setLong(1,clientProduct.getProduct());
            statement.setLong(2, clientProduct.getClient());
            return statement.executeUpdate();
        } catch (SQLException e){
            logger.error("SQL exception in method createClientProduct ", e);
            throw new DaoException("SQL exception in method createClientProduct ", e);
        }
    }
}
