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
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class ConnectionPool {
    private final static Logger logger = LogManager.getLogger();
    private static final String POOL_RESOURCE = "datares/database.properties";
    private static final String PROPERTIES_POOLSIZE = "poolsize";
    private final static int DEFAULT_POOL_SIZE = 4;
    private final static Lock lock = new ReentrantLock(true);
    private final static AtomicBoolean instanceInitialized = new AtomicBoolean(false);
    private static ConnectionPool instance;

    private BlockingQueue<ProxyConnection> freeConnection;
    private BlockingQueue<ProxyConnection> givenAwayConnection;


    private ConnectionPool(){
        Properties properties = new Properties();
        int poolSize;
        try(InputStream inputStream = ConnectionPool.class.getClassLoader().getResourceAsStream(POOL_RESOURCE)){
            properties.load(inputStream);
            poolSize = properties.get(PROPERTIES_POOLSIZE) != null
                    ? Integer.parseInt((String) properties.get(PROPERTIES_POOLSIZE))
                    : DEFAULT_POOL_SIZE;
            freeConnection = new LinkedBlockingDeque<>(poolSize);
            givenAwayConnection = new LinkedBlockingDeque<>();
            for(int i = 0; i < poolSize; i++){
                ProxyConnection proxyConnection = ConnectionFactory.createConnection();
                freeConnection.add(proxyConnection);
            }
        }catch (IOException e) {
            logger.fatal("Unable to read database properties", e);
            throw new RuntimeException("Unable to read database properties", e);
        } catch (NumberFormatException e) {
            logger.fatal("Unable to configure parameters of connection pool", e);
            throw new RuntimeException("Unable to configure parameters of connection pool", e);
        } catch (DatabaseConnectionException e) {
            logger.error("Connection could not creat", e);
        }
        if(freeConnection.isEmpty()){
           logger.fatal("Connection could not create. Pool is empty");
           throw new RuntimeException("Connection could not create. Pool is empty");
        }
    }

    public static ConnectionPool getInstance() {
        if(!instanceInitialized.get()){
            lock.lock();
            try {
                if (instance == null) {
                    instance = new ConnectionPool();
                    instanceInitialized.getAndSet(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }


    public Connection getConnection(){
        ProxyConnection connection = null;
        try {
            connection = freeConnection.take();
            givenAwayConnection.put(connection);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Error with current thread" + e);
        }
        return connection;
    }

    public boolean releaseConnection(Connection connection){
        boolean result;
        if(connection.getClass()==ProxyConnection.class){
            givenAwayConnection.remove(connection);
            try {
                freeConnection.put((ProxyConnection) connection);
                result = true;
            } catch (InterruptedException e) {
                result = false;
                Thread.currentThread().interrupt();
                logger.error("Error with current thread" + e);
            }
        }else{
            result = false;
            logger.warn("Wrong connection is detected:" + connection.getClass() +
                    "should be ProxyConnection ");
        }
        return result;
    }

    public void destroyPool(){
        for(int i = 0; i < DEFAULT_POOL_SIZE; i++){
            try {
                freeConnection.take().reallyClose();
            } catch (SQLException e) {
                logger.error("Exception in connection close method", e);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.error("Error with current thread", e);
            }
        }
        deregisterDrivers();
    }

    private void deregisterDrivers(){
        DriverManager.getDrivers().asIterator().forEachRemaining(driver -> {
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                logger.error("Error with deregister driver ", e);
            }
        });
    }
}
