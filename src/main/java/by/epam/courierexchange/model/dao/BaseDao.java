package by.epam.courierexchange.model.dao;

import by.epam.courierexchange.model.entity.AbstractEntity;
import by.epam.courierexchange.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public interface BaseDao<K, T extends AbstractEntity> {
    static final Logger logger= LogManager.getLogger();
    List<T> selectAll() throws DaoException;
    Optional<T> selectById(K id) throws DaoException;
    boolean deleteById(K id) throws DaoException;
    boolean create(T t) throws DaoException;
    int update(T t) throws DaoException;

    default void close(Connection connection){

        try {
            if (connection != null) {
                connection.close();
            }
        }
        catch (SQLException e) {
            logger.error("Error with closing connection");
        }
    }

    default void close(Statement statement){

        try {
            if(statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            logger.error("Error with closing statement");
        }
    }
}
