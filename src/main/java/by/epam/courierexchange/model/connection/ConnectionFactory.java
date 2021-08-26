package by.epam.courierexchange.model.connection;

import by.epam.courierexchange.exception.DatabaseConnectionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

class ConnectionFactory {
    private static final Logger logger = LogManager.getLogger();
    private static final String POOL_RESOURCE = "datares/database.properties";
    private static final String DRIVER_KEY = "db.driver";
    private static final String URL_KEY = "db.url";
    private static final String URL;
    private static final Properties properties = new Properties();

    static {
        try(InputStream inputStream = ConnectionPool.class.getClassLoader().getResourceAsStream(POOL_RESOURCE)) {
            properties.load(inputStream);
            String driveName = (String)properties.getProperty(DRIVER_KEY);
            Class.forName(driveName);
            URL = (String)properties.getProperty(URL_KEY);
        }catch (IOException e){
            logger.fatal("Property file not load. file path = " + POOL_RESOURCE, e);
            throw new RuntimeException("Property file not load. file path = " + POOL_RESOURCE, e);
        } catch (ClassNotFoundException e) {
            logger.fatal("Driver could not register.", e);
            throw new RuntimeException("Driver could not register.", e);
        }
    }

    static ProxyConnection createConnection() throws DatabaseConnectionException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, properties);
        } catch (SQLException e) {
            logger.error("Unable to establish connection with URL = " + URL, e);
            throw new DatabaseConnectionException("Unable to establish connection with URL = " + URL, e);
        }
        return new ProxyConnection(connection);
    }
}
