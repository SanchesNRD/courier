package by.epam.courierexchange.dao.impl;

import by.epam.courierexchange.connection.ConnectionPool;
import by.epam.courierexchange.dao.ProductDao;
import by.epam.courierexchange.entity.Product;
import by.epam.courierexchange.entity.ProductType;
import by.epam.courierexchange.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductDaoImpl implements ProductDao {
    private static final Logger logger = LogManager.getLogger();
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private static final String SQL_SELECT_ALL="" +
            "SELECT id, weight, length, width, height, type_id " +
            "FROM products";
    private static final String SQL_SELECT_BY_ID="" +
            "SELECT id, weight, length, width, height, type_id " +
            "FROM products WHERE id=?";
    private static final String SQL_SELECT_BY_TYPE="" +
            "SELECT id, weight, length, width, height, type_id " +
            "FROM products WHERE type_id=?";
    private static final String SQL_DELETE_BY_ID="" +
            "DELETE FROM products WHERE id=?";
    private static final String SQL_INSERT="" +
            "INSERT INTO products(id, weight, length, width, height, type_id)" +
            "VALUES (?,?,?,?,?,?)";
    private static final String SQL_UPDATE="" +
            "UPDATE products SET weight=?, length=?, width=?, " +
            "height=?, type_id=? WHERE id=?";

    @Override
    public List<Product> selectAll() throws DaoException {
        Connection connection = null;
        Statement statement = null;
        List<Product> products = new ArrayList<>();
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/courier_exchange", "root", "root");
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL);
            while(resultSet.next()){
                Product product = new Product();
                product.setId(resultSet.getLong("id"));
                product.setWeight(resultSet.getInt("weight"));
                product.setLength(resultSet.getInt("length"));
                product.setWidth(resultSet.getInt("width"));
                product.setProductType(ProductType.parseType(resultSet.getShort("type_id")));
                products.add(product);
            }
        } catch (SQLException e){
            throw new DaoException(e);
        } finally {
            close(statement);
            close(connection);
        }
        return products;
    }

    @Override
    public Optional<Product> selectById(Long id) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        Product product = new Product();
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/courier_exchange", "root", "root");
            statement = connection.prepareStatement(SQL_SELECT_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if(!resultSet.next()){
                return Optional.empty();
            } else{
                product.setId(resultSet.getLong("id"));
                product.setWeight(resultSet.getInt("weight"));
                product.setLength(resultSet.getInt("length"));
                product.setWidth(resultSet.getInt("width"));
                product.setProductType(ProductType.parseType(resultSet.getShort("type_id")));
            }
        } catch (SQLException e){
            throw new DaoException(e);
        } finally {
            close(statement);
            close(connection);
        }
        return Optional.of(product);
    }

    @Override
    public List<Product> selectByType(Integer type) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        List<Product> products = new ArrayList<>();
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/courier_exchange", "root", "root");
            statement = connection.prepareStatement(SQL_SELECT_BY_TYPE);
            statement.setInt(1,type);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                Product product = new Product();
                product.setId(resultSet.getLong("id"));
                product.setWeight(resultSet.getInt("weight"));
                product.setLength(resultSet.getInt("length"));
                product.setWidth(resultSet.getInt("width"));
                product.setProductType(ProductType.parseType(resultSet.getShort("type_id")));
                products.add(product);
            }
        } catch (SQLException e){
            throw new DaoException(e);
        } finally {
            close(statement);
            close(connection);
        }
        return products;
    }

    @Override
    public boolean deleteById(Long id) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/courier_exchange", "root", "root");
            statement = connection.prepareStatement(SQL_DELETE_BY_ID);
            statement.setLong(1, id);
            return statement.execute();
        } catch (SQLException e){
            throw new DaoException(e);
        } finally {
            close(connection);
            close(statement);
        }
    }

    @Override
    public boolean create(Product product) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/courier_exchange", "root", "root");
            statement = connection.prepareStatement(SQL_INSERT);
            statement.setLong(1, product.getId());
            statement.setInt(2, product.getWeight());
            statement.setInt(3, product.getLength());
            statement.setInt(4, product.getWidth());
            statement.setInt(5, product.getHeight());
            statement.setInt(6, product.getProductType().getId());
            return statement.execute();
        } catch (SQLException e){
            throw new DaoException(e);
        } finally {
            close(connection);
            close(statement);
        }
    }

    @Override
    public int update(Product product) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/courier_exchange", "root", "root");
            statement = connection.prepareStatement(SQL_UPDATE);
            statement.setInt(1, product.getWeight());
            statement.setInt(2, product.getLength());
            statement.setInt(3, product.getWidth());
            statement.setInt(4, product.getHeight());
            statement.setInt(5, product.getProductType().getId());
            statement.setLong(6, product.getId());
            return statement.executeUpdate();
        } catch (SQLException e){
            throw new DaoException(e);
        } finally {
            close(connection);
            close(statement);
        }
    }
}
