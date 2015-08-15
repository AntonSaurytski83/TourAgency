package com.epam.tour.connection;

import com.epam.tour.exception.ConnectionPoolException;
import com.epam.tour.resource.DBConfigurationManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.epam.tour.resource.DBConfigurationManager.*;

/**
 * The type Connection pool.
 */
public class ConnectionPool {


    /**
     * The constant instance.
     */
    private static ConnectionPool instance = null;


    /**
     * The POOL _ sIZE.
     */
    private final int POOL_SIZE = 10;

    /**
     * The MAX _ wAITING _ tIME.
     */
    private final long MAX_WAITING_TIME = 2L;

    /**
     * The Db configuration manager.
     */
    private final DBConfigurationManager dbConfigurationManager = INSTANCE;

    /**
     * The constant LOG.
     */
    private static final Logger LOG = Logger.getRootLogger();


    /**
     * The constant lock.
     */
    private static Lock lock = new ReentrantLock(true);


    /**
     * The Connections.
     */
    private BlockingQueue<Connection> connections;

    /**
     * The Connection url.
     */
    private String connectionUrl = dbConfigurationManager.getString(DATABASE_CONNECTION_URL);
    /**
     * The Username.
     */
    private String username = dbConfigurationManager.getString(DATABASE_USERNAME);
    /**
     * The Password.
     */
    private String password = dbConfigurationManager.getString(DATABASE_PASSWORD);

    /**
     * Instantiates a new Connection pool.
     */
    private ConnectionPool() {

        LOG.info("Trying to create pool of connections...");
        connections = new LinkedBlockingQueue<>(POOL_SIZE);
        //Registers the given driver with the DriverManager. A newly-loaded driver class should call the method
        // registerDriver to make itself known to the DriverManager.

        try {
            Driver driverName = new com.mysql.jdbc.Driver();
            DriverManager.registerDriver(driverName);
            for (int i = 0; i < POOL_SIZE; i++) {
                connections.offer(createConnection());
            }
            LOG.info("Connection pool successfully initialized.");
        } catch (SQLException e) {
            LOG.fatal("Can't register driver: ");
            throw new ExceptionInInitializerError(e);
        }
    }


    /**
     * Create connection.
     *
     * @return the connection
     */
    private final Connection createConnection() {
        Connection connection;
        try {
            connection = DriverManager.getConnection(connectionUrl, username, password);
        } catch (SQLException e) {
            LOG.fatal("Can't create connection: ", e);
            throw new RuntimeException(e);
        }
        return connection;
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static ConnectionPool getInstance() {
        if (instance == null) {
            lock.lock();
            try {
                if (instance == null) {
                    instance = new ConnectionPool();
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }


    /**
     * Gets connection.
     *
     * @return the connection
     * @throws ConnectionPoolException the connection pool exception
     */

    public final Connection getConnection() throws ConnectionPoolException {
        try {
            Connection connection = connections.poll(MAX_WAITING_TIME, TimeUnit.SECONDS);
            if (connection != null) {
                LOG.info("Connection " + connection + " took from connection pool");
            } else {
                throw new ConnectionPoolException("Couldn't retrieve a connection from pool: ");
            }
            return connection;
        } catch (InterruptedException e) {
            LOG.error(e.getMessage());
            return null;
        }
    }


    /**
     * Release void.
     *
     * @param connection the connection
     */

    public final void release(Connection connection) {
        if (connection != null) {
            try {
                connections.put(connection);
                LOG.info("Connection " + connection + " returned to connection pool");
                LOG.info("There are(is) " + (connections.size() - connections.remainingCapacity()) + " connection(s) in the pool.");
            } catch (InterruptedException e) {
                LOG.error(e.getMessage());
            }
        }
    }


    /**
     * Shut down.
     */
    public final void shutDown() {

        Iterator<Connection> iterator = connections.iterator();
        while (iterator.hasNext()) {
            Connection connection = iterator.next();
            try {
                // close connection
                connection.close();
                // remove it to prevent the use of closed connection
                iterator.remove();
            } catch (SQLException e) {
                LOG.error("Couldn't close connection: " + e);
            }
        }

        LOG.info("Connection pool is shut down");
    }
}
