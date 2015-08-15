package com.epam.tour.dao;

import com.epam.tour.connection.ConnectionPool;
import com.epam.tour.entity.Tour;
import com.epam.tour.entity.TourType;
import com.epam.tour.exception.ConnectionPoolException;
import com.epam.tour.exception.DAOLogicalException;
import com.epam.tour.exception.DAOTechnicalException;
import com.epam.tour.helper.ConstantsHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * The type Tour dAO.
 */
public class TourDAO extends AbstractDAO<Integer, Tour> {

    /**
     * The constant SELECT_ALL.
     */
    private static final String SELECT_ALL = "SELECT id, tourname, details, hot, price, regular_discount, type FROM tour";
    /**
     * The constant FIND_BY_ID.
     */
    private static final String FIND_BY_ID = "SELECT id, tourname, details, hot, price, regular_discount, type FROM tour WHERE id = ?";
    /**
     * The constant CREATE_TOUR.
     */
    private static final String CREATE_TOUR = "INSERT INTO tour(tourname, details, hot, price, regular_discount, type) VALUES(?, ?, ?, ?, ?, ?)";
    /**
     * The constant UPDATE_BY_ID.
     */
    private static final String UPDATE_BY_ID = "UPDATE tour SET tourname=?, details=?, hot=?, price=?, regular_discount=?, type=? WHERE id=?";
    /**
     * The constant DELETE_TOUR_BY_ID.
     */
    private static final String DELETE_TOUR_BY_ID = "DELETE FROM tour WHERE id = ?";


    /**
     * Instantiates a new Tour dAO.
     */
    private TourDAO() {
    }


    /**
     * The type Tour dAO holder.
     */
    public static class TourDAOHolder {
        /**
         * The constant HOLDER_INSTANCE.
         */
        public static final TourDAO HOLDER_INSTANCE = new TourDAO();
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static TourDAO getInstance() {
        return TourDAOHolder.HOLDER_INSTANCE;
    }


    /**
     * Find all.
     *
     * @return the list
     * @throws DAOTechnicalException the dAO technical exception
     * @throws ConnectionPoolException the connection pool exception
     */
    @Override
    public List<Tour> findAll() throws DAOTechnicalException, ConnectionPoolException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        List<Tour> tours = new LinkedList<>();
        if (connection != null) {
            PreparedStatement statement = null;
            try {
                statement = connection.prepareStatement(SELECT_ALL);
                ResultSet set = statement.executeQuery();
                while (set.next()) {
                    Tour tour = createEntity(set);
                    tours.add(tour);
                }
                return tours;
            } catch (SQLException e) {
                throw new DAOTechnicalException(e);
            } finally {
                close(connection, statement, connectionPool);
            }
        } else {
            throw new DAOTechnicalException(NO_CONNECTION);
        }
    }

    /**
     * Find by id.
     *
     * @param id the id
     * @return the tour
     * @throws DAOLogicalException the dAO logical exception
     * @throws DAOTechnicalException the dAO technical exception
     * @throws ConnectionPoolException the connection pool exception
     */
    @Override
    public Tour findById(Integer id) throws DAOLogicalException, DAOTechnicalException, ConnectionPoolException {
        if (id != null) {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            Connection connection = connectionPool.getConnection();
            if (connection != null) {
                PreparedStatement statement = null;
                try {
                    statement = connection.prepareStatement(FIND_BY_ID);
                    statement.setInt(1, id.intValue());
                    ResultSet set = statement.executeQuery();
                    if (set.next()) {
                        return createEntity(set);
                    } else {
                        throw new DAOLogicalException(ENTITY_WAS_NOT_FOUND);
                    }
                } catch (SQLException e) {
                    throw new DAOTechnicalException(e);
                } finally {
                    close(connection,statement,connectionPool);
                }
            } else {
                throw new DAOTechnicalException(NO_CONNECTION);
            }
        } else {
            throw new DAOLogicalException(INVALID_DATA);
        }
    }

    /**
     * Delete boolean.
     *
     * @param id the id
     * @return the boolean
     * @throws DAOLogicalException the dAO logical exception
     * @throws DAOTechnicalException the dAO technical exception
     * @throws ConnectionPoolException the connection pool exception
     */
    @Override
    public boolean delete(Integer id) throws DAOLogicalException, DAOTechnicalException, ConnectionPoolException {
        if (null != id) {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            Connection connection = connectionPool.getConnection();
            if (connection != null) {
                PreparedStatement statement = null;
                try {
                    connection.setAutoCommit(false);
                    statement = connection.prepareStatement(DELETE_TOUR_BY_ID);
                    statement.setInt(1, id.intValue());
                    statement.addBatch();
                    int[] affected = statement.executeBatch();
                    connection.commit();
                    connection.setAutoCommit(true);
                    if (affected.length > 0) {
                        return true;
                    } else {
                        throw new DAOLogicalException("No such record in database");
                    }
                } catch (SQLException e) {
                    throw new DAOTechnicalException(e);
                } finally {
                    close(connection, statement, connectionPool);
                }
            } else {
                throw new DAOTechnicalException(NO_CONNECTION);
            }
        } else {
            throw new DAOLogicalException(INVALID_DATA);
        }
    }

    /**
     * Delete boolean.
     *
     * @param entity the entity
     * @return the boolean
     * @throws DAOLogicalException the dAO logical exception
     * @throws DAOTechnicalException the dAO technical exception
     */
    @Override
    public boolean delete(Tour entity) throws DAOLogicalException, DAOTechnicalException {
        throw new UnsupportedOperationException();
    }

    /**
     * Create boolean.
     *
     * @param entity the entity
     * @return the boolean
     * @throws DAOLogicalException the dAO logical exception
     * @throws DAOTechnicalException the dAO technical exception
     * @throws ConnectionPoolException the connection pool exception
     */
    @Override
    public boolean create(Tour entity) throws DAOLogicalException, DAOTechnicalException, ConnectionPoolException {
        if (entity != null) {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            Connection connection = connectionPool.getConnection();
            if (connection != null) {
                PreparedStatement statement = null;
                try {
                    connection.setAutoCommit(false);
                    statement = connection.prepareStatement(CREATE_TOUR);
                    statement.setString(1, entity.getTourname());
                    statement.setString(2, entity.getDetails());
                    statement.setBoolean(3, entity.isHot());
                    statement.setInt(4, entity.getPrice());
                    statement.setInt(5, entity.getRegularDiscount());
                    statement.setInt(6, entity.getType().getId());
                    statement.addBatch();
                    int[] affected = statement.executeBatch();
                    connection.commit();
                    connection.setAutoCommit(true);
                    return affected.length > 0;
                } catch (SQLException e) {
                    throw new DAOTechnicalException(e);
                } finally {
                    close(connection, statement, connectionPool);
                }
            } else {
                throw new DAOTechnicalException(NO_CONNECTION);
            }
        }
        return false;
    }

    /**
     * Update boolean.
     *
     * @param entity the entity
     * @return the boolean
     * @throws DAOLogicalException the dAO logical exception
     * @throws DAOTechnicalException the dAO technical exception
     * @throws ConnectionPoolException the connection pool exception
     */

    @Override
    public boolean update(Tour entity) throws DAOLogicalException, DAOTechnicalException, ConnectionPoolException {
        if (entity != null) {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            Connection connection = connectionPool.getConnection();
            if (connection != null) {
                PreparedStatement statement = null;
                try {
                    connection.setAutoCommit(false);
                    statement = connection.prepareStatement(UPDATE_BY_ID);
                    statement.setString(1, entity.getTourname());
                    statement.setString(2, entity.getDetails());
                    statement.setBoolean(3, entity.isHot());
                    statement.setInt(4, entity.getPrice());
                    statement.setInt(5, entity.getRegularDiscount());
                    statement.setInt(6, entity.getType().getId());
                    statement.setInt(7, entity.getId());
                    int affected = statement.executeUpdate();
                    connection.commit();
                    connection.setAutoCommit(true);
                    if (affected > 0) {
                        return true;
                    } else {
                        throw new DAOLogicalException(NO_ROWS_AFFECTED);
                    }
                } catch (SQLException e) {
                    throw new DAOTechnicalException(e);
                } finally {
                    close(connection, statement, connectionPool);
                }
            } else {
                throw new DAOTechnicalException(NO_CONNECTION);
            }
        } else {
            throw new DAOLogicalException(INVALID_DATA);
        }
    }

    /**
     * Create entity.
     *
     * @param set the set
     * @return the tour
     * @throws SQLException the sQL exception
     */

    @Override
    public Tour createEntity(ResultSet set) throws SQLException {
        Tour tour = new Tour();

        tour.setId(set.getInt(ConstantsHelper.ID));
        tour.setDetails(set.getString(ConstantsHelper.DETAILS));

        int hot = set.getInt(ConstantsHelper.HOT);
        tour.setHot(hot == 1);

        tour.setPrice(set.getInt(ConstantsHelper.PRICE));
        tour.setTourname(set.getString(ConstantsHelper.TOURNAME));
        tour.setRegularDiscount(set.getInt(ConstantsHelper.REGULAR_DISCOUNT));

        TourType type = TourType.findById(set.getInt(ConstantsHelper.TYPE));
        tour.setType(type);

        return tour;
    }


}
