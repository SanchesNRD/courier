package by.epam.courierexchange.model.dao.impl;

import by.epam.courierexchange.model.connection.ConnectionPool;
import by.epam.courierexchange.model.dao.ClientDao;
import by.epam.courierexchange.model.entity.Client;
import by.epam.courierexchange.model.entity.ClientProduct;
import by.epam.courierexchange.model.entity.User;
import by.epam.courierexchange.model.entity.UserStatus;
import by.epam.courierexchange.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.epam.courierexchange.model.dao.ColumnName.*;

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

    //client_product
    private static final String SQL_SELECT_ALL_CLIENT_PRODUCT="""
            SELECT client_id, product_id 
            FROM client_product
            """;
    private static final String SQL_SELECT_CLIENT_PRODUCT_BY_ID="""
            SELECT client_id, product_id
            FROM client_product WHERE client_id=?
            """;
    private static final String SQL_DELETE_CLIENT_PRODUCT_BY_ID=
            "DELETE FROM client_product WHERE client_id=?";
    private static final String SQL_INSERT_CLIENT_PRODUCT="""
            INSERT INTO client_product (client_id, product_id)
            VALUES (?,?)
            """;
    private static final String SQL_UPDATE_CLIENT_PRODUCT="""
            UPDATE client_product SET product_id=?
            WHERE client_id=?
            """;

    @Override
    public Optional<Client> selectClientByLogin(String loginPattern) throws DaoException {
        try(
                Connection connection = connectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_SELECT_CLIENT_BY_LOGIN))
        {
            statement.setString(1, loginPattern);
            ResultSet resultSet = statement.executeQuery();
            if(!resultSet.next()){
                return Optional.empty();
            } else{
                Client client = new Client.ClientBuilder()
                        .setAddress(resultSet.getLong(ADDRESS_ID))
                        .setBuilder(new User.UserBuilder()
                                .setId(resultSet.getLong(USER_ID))
                                .setLogin(resultSet.getString(USER_LOGIN))
                                .setPassword(resultSet.getString(USER_PASSWORD))
                                .setMail(resultSet.getString(USER_MAIL))
                                .setName(resultSet.getString(USER_NAME))
                                .setSurname(resultSet.getString(USER_SURNAME))
                                .setPhone(resultSet.getString(USER_PHONE))
                                .setUserStatus(UserStatus.parseStatus(resultSet.getShort(STATUS_ID))))
                        .build();
                return Optional.of(client);
            }
        } catch (SQLException e){
            logger.error("SQL exception in method selectClientByLogin ", e);
            throw new DaoException("SQL exception in method selectClientByLogin ", e);
        }
    }

    @Override
    public List<Client> selectClientsByStatus(Integer id) throws DaoException {
        List<Client> clients = new ArrayList<>();
        try(
                Connection connection = connectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_SELECT_CLIENTS_BY_STATUS))
        {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Client client = new Client.ClientBuilder()
                        .setAddress(resultSet.getLong(ADDRESS_ID))
                        .setBuilder(new User.UserBuilder()
                                .setId(resultSet.getLong(USER_ID))
                                .setLogin(resultSet.getString(USER_LOGIN))
                                .setPassword(resultSet.getString(USER_PASSWORD))
                                .setMail(resultSet.getString(USER_MAIL))
                                .setName(resultSet.getString(USER_NAME))
                                .setSurname(resultSet.getString(USER_SURNAME))
                                .setPhone(resultSet.getString(USER_PHONE))
                                .setUserStatus(UserStatus.parseStatus(resultSet.getShort(STATUS_ID))))
                        .build();
                clients.add(client);
            }
        } catch (SQLException e){
            logger.error("SQL exception in method selectClientsByStatus ", e);
            throw new DaoException("SQL exception in method selectClientsByStatus ", e);
        }
        return clients;
    }

    @Override
    public List<Client> selectAll() throws DaoException {
        List<Client> clients = new ArrayList<>();
        try(
                Connection connection = connectionPool.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_CLIENTS))
        {
            while (resultSet.next()){
                Client client = new Client.ClientBuilder()
                        .setAddress(resultSet.getLong(ADDRESS_ID))
                        .setBuilder(new User.UserBuilder()
                                .setId(resultSet.getLong(USER_ID))
                                .setLogin(resultSet.getString(USER_LOGIN))
                                .setPassword(resultSet.getString(USER_PASSWORD))
                                .setMail(resultSet.getString(USER_MAIL))
                                .setName(resultSet.getString(USER_NAME))
                                .setSurname(resultSet.getString(USER_SURNAME))
                                .setPhone(resultSet.getString(USER_PHONE))
                                .setUserStatus(UserStatus.parseStatus(resultSet.getShort(STATUS_ID))))
                        .build();
                clients.add(client);
            }
        } catch (SQLException e){
            logger.error("SQL exception in method selectAllClients ", e);
            throw new DaoException("SQL exception in method selectAllClients ", e);
        }
        return clients;
    }

    @Override
    public Optional<Client> selectById(Long id) throws DaoException {
        try(
                Connection connection = connectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_SELECT_CLIENT_BY_ID);){
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if(!resultSet.next()){
                return Optional.empty();
            }else{
                Client client = new Client.ClientBuilder()
                        .setAddress(resultSet.getLong(ADDRESS_ID))
                        .setBuilder(new User.UserBuilder()
                                .setId(resultSet.getLong(USER_ID))
                                .setLogin(resultSet.getString(USER_LOGIN))
                                .setPassword(resultSet.getString(USER_PASSWORD))
                                .setMail(resultSet.getString(USER_MAIL))
                                .setName(resultSet.getString(USER_NAME))
                                .setSurname(resultSet.getString(USER_SURNAME))
                                .setPhone(resultSet.getString(USER_PHONE))
                                .setUserStatus(UserStatus.parseStatus(resultSet.getShort(STATUS_ID))))
                        .build();
                return Optional.of(client);
            }
        } catch (SQLException e){
            logger.error("SQL exception in method selectClientById ", e);
            throw new DaoException("SQL exception in method selectClientById ", e);
        }
    }

    @Override
    public boolean deleteById(Long id) throws DaoException {
        try(
                Connection connection = connectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_DELETE_CLIENT_BY_ID))
        {
            statement.setLong(1, id);
            return statement.execute();
        } catch (SQLException e){
            logger.error("SQL exception in method deleteClientById ", e);
            throw new DaoException("SQL exception in method deleteClientById ", e);
        }
    }

    @Override
    public boolean create(Client client) throws DaoException {
        try(
                Connection connection = connectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_INSERT_CLIENT))
        {
            statement.setLong(1, client.getId());
            statement.setLong(2, client.getAddress());
            return statement.execute();
        } catch (SQLException e){
            logger.error("SQL exception in method createClient ", e);
            throw new DaoException("SQL exception in method createClient ", e);
        }
    }

    @Override
    public int update(Client client) throws DaoException {
        try(
                Connection connection = connectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_CLIENT))
        {
            statement.setLong(1, client.getAddress());
            statement.setLong(2, client.getId());
            return statement.executeUpdate();
        } catch (SQLException e){
            logger.error("SQL exception in method updateClient ", e);
            throw new DaoException("SQL exception in method updateClient ", e);
        }
    }

    //client_product
    @Override
    public List<ClientProduct> selectAllClientProduct() throws DaoException {
        List<ClientProduct> clientProducts = new ArrayList<>();
        try(
                Connection connection = connectionPool.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_CLIENT_PRODUCT))
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
    public Optional<ClientProduct> selectClientProductById(Long id) throws DaoException {
        ClientProduct clientProduct = new ClientProduct();
        try(
                Connection connection = connectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_SELECT_CLIENT_PRODUCT_BY_ID))
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
    public boolean deleteClientProductById(Long id) throws DaoException {
        try(
                Connection connection = connectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_DELETE_CLIENT_PRODUCT_BY_ID))
        {
            statement.setLong(1,id);
            return statement.execute();
        } catch (SQLException e){
            logger.error("SQL exception in method deleteClientProductById ", e);
            throw new DaoException("SQL exception in method deleteClientProductById ", e);
        }
    }

    @Override
    public boolean createClientProduct(ClientProduct clientProduct) throws DaoException {
        try(
                Connection connection = connectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_INSERT_CLIENT_PRODUCT))
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
    public int updateClientProduct(ClientProduct clientProduct) throws DaoException {
        try(
                Connection connection = connectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_CLIENT_PRODUCT))
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
