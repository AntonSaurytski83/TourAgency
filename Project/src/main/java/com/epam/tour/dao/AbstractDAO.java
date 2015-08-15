package com.epam.tour.dao;

import com.epam.tour.connection.ConnectionPool;
import com.epam.tour.entity.Entity;
import com.epam.tour.exception.ConnectionPoolException;
import com.epam.tour.exception.DAOLogicalException;
import com.epam.tour.exception.DAOTechnicalException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * The type Abstract dAO.
 * @param <K>  the type parameter
 * @param <T>  the type parameter
 */
abstract class AbstractDAO<K, T extends Entity> {


    /**
     * The constant NO_CONNECTION.
     */
   public static final String NO_CONNECTION = "Unable to get connection with database";
    /**
     * The constant ENTITY_WAS_NOT_FOUND.
     */
    public static final String ENTITY_WAS_NOT_FOUND = "No entity with such id";
    /**
     * The constant INVALID_DATA.
     */
    public static final String INVALID_DATA = "Null or invalid parameter(s)";
    /**
     * The constant NO_ROWS_AFFECTED.
     */
    public static final String NO_ROWS_AFFECTED = "No rows affected";

    /**
     * The constant LOG.
     */
    private static final Logger LOG = Logger.getRootLogger();

    /**
     * Find all.
     *
     * @return the list
     * @throws DAOLogicalException the dAO logical exception
     * @throws DAOTechnicalException the dAO technical exception
     * @throws ConnectionPoolException the connection pool exception
     */
    public abstract List<T> findAll() throws DAOLogicalException, DAOTechnicalException, ConnectionPoolException;

    /**
     * Find by id.
     *
     * @param id the id
     * @return the t
     * @throws DAOLogicalException the dAO logical exception
     * @throws DAOTechnicalException the dAO technical exception
     * @throws ConnectionPoolException the connection pool exception
     */
    public abstract T findById(K id) throws DAOLogicalException, DAOTechnicalException, ConnectionPoolException;

    /**
     * Delete boolean.
     *
     * @param id the id
     * @return the boolean
     * @throws DAOLogicalException the dAO logical exception
     * @throws DAOTechnicalException the dAO technical exception
     * @throws ConnectionPoolException the connection pool exception
     */
    public abstract boolean delete(K id) throws DAOLogicalException, DAOTechnicalException, ConnectionPoolException;

    /**
     * Delete boolean.
     *
     * @param entity the entity
     * @return the boolean
     * @throws DAOLogicalException the dAO logical exception
     * @throws DAOTechnicalException the dAO technical exception
     * @throws ConnectionPoolException the connection pool exception
     */
    public abstract boolean delete(T entity) throws DAOLogicalException, DAOTechnicalException, ConnectionPoolException;

    /**
     * Create boolean.
     *
     * @param entity the entity
     * @return the boolean
     * @throws DAOLogicalException the dAO logical exception
     * @throws DAOTechnicalException the dAO technical exception
     * @throws ConnectionPoolException the connection pool exception
     */
    public abstract boolean create(T entity) throws DAOLogicalException, DAOTechnicalException, ConnectionPoolException;

    /**
     * Update boolean.
     *
     * @param entity the entity
     * @return the boolean
     * @throws DAOLogicalException the dAO logical exception
     * @throws DAOTechnicalException the dAO technical exception
     * @throws ConnectionPoolException the connection pool exception
     */
    public abstract boolean update(T entity) throws DAOLogicalException, DAOTechnicalException, ConnectionPoolException;

    /**
     * Create entity.
     *
     * @param set the set
     * @return the t
     * @throws SQLException the sQL exception
     */
    public abstract T createEntity(ResultSet set) throws SQLException;


    /**
     * Close void.
     *
     * @param cn the cn
     * @param st the st
     * @param cp the cp
     */
    public static void close(Connection cn, PreparedStatement st, ConnectionPool cp){
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                LOG.error(e);
            }
        }
        cp.release(cn);
    }
}