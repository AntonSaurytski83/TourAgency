package com.epam.tour.dao;

import com.epam.tour.connection.ConnectionPool;
import com.epam.tour.entity.Role;
import com.epam.tour.entity.User;
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
 * The type User dAO.
 */
public class UserDAO extends AbstractDAO<Integer, User> {

    /**
     * The constant SELECT_ALL.
     */
    private static final String SELECT_ALL = "SELECT user.id, user.username, user.password, user.role_id, role.rolename FROM user JOIN role ON user.role_id = role.id";
    /**
     * The constant FIND_BY_ID.
     */
    private static final String FIND_BY_ID = "SELECT user.id, user.username, user.password, user.role_id, role.rolename FROM user JOIN role ON user.role_id = role.id WHERE user.id = ?";
    /**
     * The constant FIND_BY_LOGIN_PASSWORD.
     */
    private static final String FIND_BY_LOGIN_PASSWORD = "SELECT user.id, user.username, user.password, user.role_id, role.rolename FROM user JOIN role ON user.role_id = role.id WHERE user.username = ? AND user.password = ?";
    /**
     * The constant DELETE_BY_ID.
     */
    private static final String DELETE_BY_ID = "DELETE user WHERE id = ?";
    /**
     * The constant CREATE_USER.
     */
    private static final String CREATE_USER = "INSERT INTO user (username, password, role_id) VALUES(?, ?, ?)";
    /**
     * The constant UPDATE_USER.
     */
    private static final String UPDATE_USER = "UPDATE users SET username = ?, password = ?, role_id = ? WHERE id = ?";
    /**
     * The constant USER_NOT_FOUND.
     */
    private static final String USER_NOT_FOUND = "User not found";


    /**
     * Instantiates a new User dAO.
     */
    private UserDAO() {
    }

    /**
     * The type User dAO holder.
     */
    public static class UserDAOHolder {
        /**
         * The constant HOLDER_INSTANCE.
         */
        public static final UserDAO HOLDER_INSTANCE = new UserDAO();
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static UserDAO getInstance() {
        return UserDAOHolder.HOLDER_INSTANCE;
    }


    /**
     * Find all.
     *
     * @return the list
     * @throws DAOTechnicalException the dAO technical exception
     * @throws ConnectionPoolException the connection pool exception
     */
    @Override
    public List<User> findAll() throws DAOTechnicalException, ConnectionPoolException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        List<User> users = new LinkedList<>();
        if (connection != null) {
            PreparedStatement statement = null;
            try {
                statement = connection.prepareStatement(SELECT_ALL);
                ResultSet set = statement.executeQuery();
                while (set.next()) {
                    User user = createEntity(set);
                    users.add(user);
                }
                return users;
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
     * @return the user
     * @throws DAOLogicalException the dAO logical exception
     * @throws DAOTechnicalException the dAO technical exception
     * @throws ConnectionPoolException the connection pool exception
     */
    @Override
    public User findById(Integer id) throws DAOLogicalException, DAOTechnicalException, ConnectionPoolException {
        User user = null;
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
                        user = createEntity(set);
                    } else {
                        throw new DAOLogicalException(USER_NOT_FOUND);
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
        return user;
    }


    /**
     * Find by login and password.
     *
     * @param login the login
     * @param password the password
     * @return the user
     * @throws DAOLogicalException the dAO logical exception
     * @throws DAOTechnicalException the dAO technical exception
     * @throws ConnectionPoolException the connection pool exception
     */
    public User findByLoginAndPassword(String login, String password) throws DAOLogicalException, DAOTechnicalException, ConnectionPoolException {
        User user = null;
        if (login != null && password != null) {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            Connection connection = connectionPool.getConnection();
            if (connection != null) {
                PreparedStatement statement = null;
                try {
                    connection.setAutoCommit(false);
                    statement = connection.prepareStatement(FIND_BY_LOGIN_PASSWORD);
                    statement.setString(1, login);
                    statement.setString(2, password);

                    ResultSet set = statement.executeQuery();
                    connection.commit();
                    connection.setAutoCommit(true);
                    if (set.next()) {
                        user = createEntity(set);
                    } else {
                        throw new DAOLogicalException(USER_NOT_FOUND);
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
        return user;
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

        if (id != null) {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            Connection connection = connectionPool.getConnection();
            if (connection != null) {
                PreparedStatement statement = null;
                try {
                    statement = connection.prepareStatement(DELETE_BY_ID);
                    statement.setInt(1, id.intValue());
                    int affected = statement.executeUpdate();
                    return affected > 0;
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
     * @throws ConnectionPoolException the connection pool exception
     */
    @Override
    public boolean delete(User entity) throws DAOLogicalException, DAOTechnicalException, ConnectionPoolException {
        if (entity != null) {
            return delete(entity.getId());
        } else {
            throw new DAOLogicalException(INVALID_DATA);
        }
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
    public boolean create(User entity) throws DAOLogicalException, DAOTechnicalException, ConnectionPoolException {
        if (entity != null) {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            Connection connection = connectionPool.getConnection();
            if (connection != null) {
                PreparedStatement statement = null;
                try {
                    connection.setAutoCommit(false);
                    statement = connection.prepareStatement(CREATE_USER);
                    statement.setString(1, entity.getUsername());
                    statement.setString(2, entity.getPassword());
                    statement.setInt(3, entity.getRole().getId());
                    statement.addBatch();
                    int[] affected = statement.executeBatch();
                    connection.commit();
                    connection.setAutoCommit(true);
                    if (affected.length > 0) {
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
     * Update boolean.
     *
     * @param entity the entity
     * @return the boolean
     * @throws DAOLogicalException the dAO logical exception
     * @throws DAOTechnicalException the dAO technical exception
     * @throws ConnectionPoolException the connection pool exception
     */
    @Override
    public boolean update(User entity) throws DAOLogicalException, DAOTechnicalException, ConnectionPoolException {
        if (entity != null) {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            Connection connection = connectionPool.getConnection();
            if (connection != null) {
                PreparedStatement statement = null;
                try {
                    connection.setAutoCommit(false);
                    statement = connection.prepareStatement(UPDATE_USER);
                    statement.setString(1, entity.getUsername());
                    statement.setString(2, entity.getPassword());
                    statement.setInt(3, entity.getRole().getId());
                    statement.setInt(4, entity.getId());
                    statement.addBatch();
                    int[] affected = statement.executeBatch();
                    connection.commit();
                    connection.setAutoCommit(true);
                    if (affected.length > 0) {
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
            throw new DAOLogicalException(NO_ROWS_AFFECTED);
        }
    }


    /**
     * Create entity.
     *
     * @param set the set
     * @return the user
     * @throws SQLException the sQL exception
     */
    @Override
    public User createEntity(ResultSet set) throws SQLException {

        User user = new User();
        user.setId(set.getInt(ConstantsHelper.ID));
        user.setUsername(set.getString(ConstantsHelper.USERNAME));
        user.setPassword(set.getString(ConstantsHelper.PASSWORD));

        Role role = new Role();
        role.setId(set.getInt(ConstantsHelper.ROLE_ID));
        role.setRolename(set.getString(ConstantsHelper.ROLENAME));

        user.setRole(role);

        return user;
    }
}
