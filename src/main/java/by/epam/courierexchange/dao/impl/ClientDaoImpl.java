package by.epam.courierexchange.dao.impl;

import by.epam.courierexchange.connection.ConnectionPool;
import by.epam.courierexchange.dao.ClientDao;
import by.epam.courierexchange.dao.ColumnName;
import by.epam.courierexchange.entity.Client;
import by.epam.courierexchange.entity.UserStatus;
import by.epam.courierexchange.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientDaoImpl implements ClientDao {
    private static final Logger logger = LogManager.getLogger();
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private static final String SQL_SELECT_CLIENT_BY_LOGIN= """
            SELECT clients.id, clients.address_id, users.login, users.mail, 
            users.name, users.surname, users.phone, users.status_id 
            FROM clients, users WHERE users.login=?
            """;
    private static final String SQL_SELECT_CLIENTS_BY_STATUS= """
            SELECT clients.id, clients.address_id, users.login, users.mail, 
            users.name, users.surname, users.phone, users.status_id 
            FROM clients, users WHERE users.status_id=?
            """;
    private static final String SQL_SELECT_ALL_CLIENTS= """
            SELECT clients.id, address_id, login, password, mail, name, surname, phone, status_id
            FROM clients INNER JOIN users WHERE clients.id=users.id
            """;
    private static final String SQL_SELECT_CLIENT_BY_ID= """
            SELECT clients.id, address_id, login, password, mail, name, surname, phone, status_id 
            FROM clients INNER JOIN users WHERE clients.id=users.id AND clients.id=?
            """;
    private static final String SQL_DELETE_CLIENT_BY_ID=
            "DELETE FROM clients WHERE id=?";
    private static final String SQL_INSERT_CLIENT=
            "INSERT INTO clients (id, address_id) VALUES (?,?)";
    private static final String SQL_UPDATE_CLIENT=
            "UPDATE clients SET address_id=? WHERE id=?";

    @Override
    public Optional<Client> selectClientByLogin(String loginPattern) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        Client client = new Client();
        try{
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_CLIENT_BY_LOGIN);
            statement.setString(1, loginPattern);
            ResultSet resultSet = statement.executeQuery();
            if(!resultSet.next()){
                return Optional.empty();
            } else{
                client.setId(resultSet.getLong(ColumnName.USER_ID));
                client.setAddress(resultSet.getLong(ColumnName.ADDRESS_ID));
                client.setLogin(resultSet.getString(ColumnName.USER_LOGIN));
                client.setPassword(resultSet.getString(ColumnName.USER_PASSWORD));
                client.setMail(resultSet.getString(ColumnName.USER_MAIL));
                client.setName(resultSet.getString(ColumnName.USER_NAME));
                client.setSurname(resultSet.getString(ColumnName.USER_SURNAME));
                client.setPhone(resultSet.getString(ColumnName.USER_PHONE));
                client.setUserStatus(UserStatus.parseStatus(resultSet.getShort(ColumnName.STATUS_ID)));
            }
        } catch (SQLException e){
            logger.error("SQL exception in method selectClientByLogin ", e);
            throw new DaoException("SQL exception in method selectClientByLogin ", e);
        } finally {
            close(statement);
            close(connection);
        }
        return Optional.of(client);
    }

    @Override
    public List<Client> selectClientsByStatus(Integer id) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        List<Client> clients = new ArrayList<>();
        try{
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_CLIENTS_BY_STATUS);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Client client = new Client();
                client.setId(resultSet.getLong(ColumnName.USER_ID));
                client.setAddress(resultSet.getLong(ColumnName.ADDRESS_ID));
                client.setLogin(resultSet.getString(ColumnName.USER_LOGIN));
                client.setPassword(resultSet.getString(ColumnName.USER_PASSWORD));
                client.setMail(resultSet.getString(ColumnName.USER_MAIL));
                client.setName(resultSet.getString(ColumnName.USER_NAME));
                client.setSurname(resultSet.getString(ColumnName.USER_SURNAME));
                client.setPhone(resultSet.getString(ColumnName.USER_PHONE));
                client.setUserStatus(UserStatus.parseStatus(resultSet.getShort(ColumnName.STATUS_ID)));
                clients.add(client);
            }
        } catch (SQLException e){
            logger.error("SQL exception in method selectClientsByStatus ", e);
            throw new DaoException("SQL exception in method selectClientsByStatus ", e);
        } finally {
            close(statement);
            close(connection);
        }
        return clients;
    }

    @Override
    public List<Client> selectAll() throws DaoException {
        Connection connection = null;
        Statement statement = null;
        List<Client> clients = new ArrayList<>();
        try{
            connection = connectionPool.getConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_CLIENTS);
            while (resultSet.next()){
                Client client = new Client();
                client.setId(resultSet.getLong(ColumnName.USER_ID));
                client.setAddress(resultSet.getLong(ColumnName.ADDRESS_ID));
                client.setLogin(resultSet.getString(ColumnName.USER_LOGIN));
                client.setPassword(resultSet.getString(ColumnName.USER_PASSWORD));
                client.setMail(resultSet.getString(ColumnName.USER_MAIL));
                client.setName(resultSet.getString(ColumnName.USER_NAME));
                client.setSurname(resultSet.getString(ColumnName.USER_SURNAME));
                client.setPhone(resultSet.getString(ColumnName.USER_PHONE));
                client.setUserStatus(UserStatus.parseStatus(resultSet.getShort(ColumnName.STATUS_ID)));
                clients.add(client);
            }
        } catch (SQLException e){
            logger.error("SQL exception in method selectAllClients ", e);
            throw new DaoException("SQL exception in method selectAllClients ", e);
        } finally {
            close(statement);
            close(connection);
        }
        return clients;
    }

    @Override
    public Optional<Client> selectById(Long id) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        Client client = new Client();
        try{
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_CLIENT_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if(!resultSet.next()){
                return Optional.empty();
            }else{
                client.setId(resultSet.getLong(ColumnName.USER_ID));
                client.setAddress(resultSet.getLong(ColumnName.ADDRESS_ID));
                client.setLogin(resultSet.getString(ColumnName.USER_LOGIN));
                client.setPassword(resultSet.getString(ColumnName.USER_PASSWORD));
                client.setMail(resultSet.getString(ColumnName.USER_MAIL));
                client.setName(resultSet.getString(ColumnName.USER_NAME));
                client.setSurname(resultSet.getString(ColumnName.USER_SURNAME));
                client.setPhone(resultSet.getString(ColumnName.USER_PHONE));
                client.setUserStatus(UserStatus.parseStatus(resultSet.getShort(ColumnName.STATUS_ID)));
            }
        } catch (SQLException e){
            logger.error("SQL exception in method selectClientById ", e);
            throw new DaoException("SQL exception in method selectClientById ", e);
        } finally {
            close(statement);
            close(connection);
        }
        return Optional.of(client);
    }

    @Override
    public boolean deleteById(Long id) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SQL_DELETE_CLIENT_BY_ID);
            statement.setLong(1, id);
            return statement.execute();
        } catch (SQLException e){
            logger.error("SQL exception in method deleteClientById ", e);
            throw new DaoException("SQL exception in method deleteClientById ", e);
        } finally {
            close(connection);
            close(statement);
        }
    }

    @Override
    public boolean create(Client client) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SQL_INSERT_CLIENT);
            statement.setLong(1, client.getId());
            statement.setLong(2, client.getAddress());
            return statement.execute();
        } catch (SQLException e){
            logger.error("SQL exception in method createClient ", e);
            throw new DaoException("SQL exception in method createClient ", e);
        } finally {
            close(connection);
            close(statement);
        }
    }

    @Override
    public int update(Client client) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SQL_UPDATE_CLIENT);
            statement.setLong(1, client.getAddress());
            statement.setLong(2, client.getId());
            return statement.executeUpdate();
        } catch (SQLException e){
            logger.error("SQL exception in method updateClient ", e);
            throw new DaoException("SQL exception in method updateClient ", e);
        } finally {
            close(connection);
            close(statement);
        }
    }
}
