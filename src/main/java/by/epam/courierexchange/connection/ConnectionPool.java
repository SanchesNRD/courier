package by.epam.courierexchange.connection;

import by.epam.courierexchange.exception.DatabaseConnectionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class ConnectionPool {
    private final static Logger logger = LogManager.getLogger();
    private static final String POOL_RESOURCE = "datares/pool.properties";
    private final static int DEFAULT_POOL_SIZE = 16;
    private final static Lock lock = new ReentrantLock(true);
    private final static AtomicBoolean instanceInitialized = new AtomicBoolean(false);
    private static ConnectionPool instance;

    private BlockingDeque<ProxyConnection> freeConnection;
    private BlockingDeque<ProxyConnection> givenAwayConnection;


    private ConnectionPool(){
        Properties properties = new Properties();
        int poolSize;
        try(InputStream inputStream = ConnectionPool.class.getClassLoader().getResourceAsStream(POOL_RESOURCE)){
            properties.load(inputStream);
            poolSize = properties.get("poolsize") != null
                    ? Integer.parseInt((String) properties.get("poolsize"))
                    : DEFAULT_POOL_SIZE;
            freeConnection = new LinkedBlockingDeque<>(poolSize);
            givenAwayConnection = new LinkedBlockingDeque<>();
            for(int i = 0; i < poolSize; i++){
                ProxyConnection proxyConnection = ConnectionFactory.creatConnection();
                freeConnection.put(proxyConnection);
            }
        }catch (IOException e) {
            logger.warn("Property file not found. file path = " + POOL_RESOURCE);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Error with current thread", e);
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

    public void releaseConnection(Connection connection){
        if(connection.getClass()==ProxyConnection.class){
            givenAwayConnection.remove(connection);
            try {
                freeConnection.put((ProxyConnection) connection);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.error("Error with current thread" + e);
            }
        }else{
            logger.error("Wrong connection is detected");
            throw new RuntimeException("Wrong connection is detected:" + connection.getClass() +
                    "should be ProxyConnection ");
        }
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
