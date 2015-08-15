package com.epam.tour.dao;

import com.epam.tour.connection.ConnectionPool;
import com.epam.tour.entity.Order;
import com.epam.tour.entity.Tour;
import com.epam.tour.entity.User;
import com.epam.tour.exception.ConnectionPoolException;
import com.epam.tour.exception.DAOLogicalException;
import com.epam.tour.exception.DAOTechnicalException;
import com.epam.tour.helper.ConstantsHelper;
import com.epam.tour.resource.DBConfigurationManager;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * The type Order dAO.
 */
public class OrderDAO extends AbstractDAO<Integer, Order> {

    /**
     * The constant SELECT_USER_ORDERS.
     */
    private static final String SELECT_USER_ORDERS = "SELECT order.id, order.paid, user.username, tour.tourname, order.amount, order.purchase_date FROM `tour_purchase` `order` JOIN `tour` ON order.tour_id = tour.id JOIN user ON user.id = order.client_id WHERE order.client_id = ?";
    /**
     * The constant EMPTY_USER.
     */
    private static final String EMPTY_USER = "EMPTY USER";
    /**
     * The constant UPDATE_ORDER.
     */
    private static final String UPDATE_ORDER = "UPDATE tour_purchase SET paid=? WHERE id =?";
    /**
     * The constant CREATE_ORDER.
     */
    private static final String CREATE_ORDER = "INSERT INTO tour_purchase (tour_id, paid,client_id, amount, purchase_date) VALUES (?, 0, ?, ?, ?)";
    /**
     * The constant SELECT_ORDERS.
     */
    private static final String SELECT_ORDERS = "SELECT order.id, order.paid, user.username, tour.tourname, order.amount, order.purchase_date FROM `tour_purchase` `order` JOIN `tour` ON order.tour_id = tour.id JOIN user ON user.id = order.client_id";


    /**
     * Instantiates a new Order dAO.
     */
    private OrderDAO() {

    }

    /**
     * The type Order dAO holder.
     */
    public static class OrderDAOHolder {
        /**
         * The constant HOLDER_INSTANCE.
         */
        public static final OrderDAO HOLDER_INSTANCE = new OrderDAO();
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static OrderDAO getInstance() {
        return OrderDAOHolder.HOLDER_INSTANCE;
    }


    /**
     * Find all.
     *
     * @return the list
     * @throws DAOTechnicalException the dAO technical exception
     * @throws ConnectionPoolException the connection pool exception
     */
    @Override
    public List<Order> findAll() throws DAOTechnicalException, ConnectionPoolException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        List<Order> orders = new LinkedList<>();
        if (connection != null) {
            PreparedStatement statement = null;
            try {
                statement = connection.prepareStatement(SELECT_ORDERS);
                ResultSet set = statement.executeQuery();
                while (set.next()) {
                    Order order = createOrder(set);
                    orders.add(order);
                }
                return orders;
            } catch (SQLException e) {
                throw new DAOTechnicalException(e);
            } finally {
                close(connection,statement,connectionPool);
            }
        } else {
            throw new DAOTechnicalException(NO_CONNECTION);
        }
    }

    /**
     * Find orders for user.
     *
     * @param user the user
     * @return the list
     * @throws DAOLogicalException the dAO logical exception
     * @throws DAOTechnicalException the dAO technical exception
     * @throws ConnectionPoolException the connection pool exception
     */
    public List<Order> findOrdersForUser(User user) throws DAOLogicalException, DAOTechnicalException, ConnectionPoolException {
        if (user != null) {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            Connection connection = connectionPool.getConnection();
            List<Order> orders = new LinkedList<>();
            if (connection != null) {
                PreparedStatement statement = null;
                try {
                    statement = connection.prepareStatement(SELECT_USER_ORDERS);
                    statement.setInt(1, user.getId());
                    ResultSet set = statement.executeQuery();
                    while (set.next()) {
                        Order order = createOrder(set);
                        orders.add(order);
                    }
                    return orders;
                } catch (SQLException e) {
                    throw new DAOTechnicalException(e);
                } finally {
                    close(connection, statement, connectionPool);
                }
            } else {
                throw new DAOTechnicalException(NO_CONNECTION);
            }
        } else {
            throw new DAOLogicalException(EMPTY_USER);
        }

    }

    /**
     * Create order.
     *
     * @param set the set
     * @return the order
     * @throws SQLException the sQL exception
     */
    private Order createOrder(ResultSet set) throws SQLException {
        Order order = new Order();
        order.setId(set.getInt(ConstantsHelper.ID));
        order.setAmount(set.getDouble(ConstantsHelper.AMOUNT));
        order.setDateTime(set.getTimestamp(ConstantsHelper.PURCHASE_DATE));

        Tour tour = new Tour();
        tour.setTourname(set.getString(ConstantsHelper.TOURNAME));
        order.setTour(tour);

        order.setPaid(set.getBoolean(ConstantsHelper.PAID));
        User user = new User();
        user.setUsername(set.getString(DBConfigurationManager.DATABASE_USERNAME));
        order.setUser(user);

        return order;
    }

    /**
     * Create boolean.
     *
     * @param entity the entity
     * @return the boolean
     * @throws DAOTechnicalException the dAO technical exception
     * @throws ConnectionPoolException the connection pool exception
     */
    @Override
    public boolean create(Order entity) throws DAOTechnicalException, ConnectionPoolException {
        if (entity != null) {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            Connection connection = connectionPool.getConnection();
            if (connection != null) {
                PreparedStatement statement = null;
                try {
                    connection.setAutoCommit(false);
                    statement = connection.prepareStatement(CREATE_ORDER);
                    statement.setInt(1, entity.getTour().getId());
                    statement.setInt(2, entity.getUser().getId());
                    statement.setDouble(3, entity.getAmount());
                    statement.setDate(4, new Date(entity.getDateTime().getTime()));
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
     * Update paid.
     *
     * @param id the id
     * @param paid the paid
     * @throws DAOLogicalException the dAO logical exception
     * @throws DAOTechnicalException the dAO technical exception
     * @throws ConnectionPoolException the connection pool exception
     */
    public void updatePaid(Integer id, boolean paid) throws DAOLogicalException, DAOTechnicalException, ConnectionPoolException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        if (connection != null) {
            PreparedStatement statement = null;
            try {
                connection.setAutoCommit(false);
                statement = connection.prepareStatement(UPDATE_ORDER);
                statement.setBoolean(1, paid);
                statement.setInt(2, id.intValue());
                statement.addBatch();
                int[] affected = statement.executeBatch();
                connection.commit();
                connection.setAutoCommit(true);
                if (affected.length < 0) {
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
    }

    /**
     * Update boolean.
     *
     * @param entity the entity
     * @return the boolean
     * @throws DAOLogicalException the dAO logical exception
     * @throws DAOTechnicalException the dAO technical exception
     */
    @Override
    public boolean update(Order entity) throws DAOLogicalException, DAOTechnicalException {
        throw new UnsupportedOperationException();
    }

    /**
     * Find by id.
     *
     * @param id the id
     * @return the order
     * @throws DAOLogicalException the dAO logical exception
     * @throws DAOTechnicalException the dAO technical exception
     */
    @Override
    public Order findById(Integer id) throws DAOLogicalException, DAOTechnicalException {
        throw new UnsupportedOperationException();
    }

    /**
     * Delete boolean.
     *
     * @param id the id
     * @return the boolean
     * @throws DAOLogicalException the dAO logical exception
     * @throws DAOTechnicalException the dAO technical exception
     */
    @Override
    public boolean delete(Integer id) throws DAOLogicalException, DAOTechnicalException {
        throw new UnsupportedOperationException();
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
    public boolean delete(Order entity) throws DAOLogicalException, DAOTechnicalException {
        throw new UnsupportedOperationException();
    }

    /**
     * Create entity.
     *
     * @param set the set
     * @return the order
     * @throws SQLException the sQL exception
     */
    @Override
    public Order createEntity(ResultSet set) throws SQLException {
        Order order = new Order();
        order.setId(set.getInt(ConstantsHelper.ID));
        order.setAmount(set.getDouble(ConstantsHelper.AMOUNT));
        order.setDateTime(set.getTimestamp(ConstantsHelper.PURCHASE_DATE));
        order.setPaid(set.getBoolean(ConstantsHelper.PAID));

        return order;
    }
}
