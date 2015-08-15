package com.epam.tour.dao;

import com.epam.tour.connection.ConnectionPool;
import com.epam.tour.entity.Role;
import com.epam.tour.entity.User;
import com.epam.tour.exception.ConnectionPoolException;
import com.epam.tour.exception.DAOLogicalException;
import com.epam.tour.exception.DAOTechnicalException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The type Client dAO.
 */
public class ClientDAO {

    /**
     * The constant SQL_QUERY.
     */
    private static final String SQL_QUERY = "SELECT client_info.is_regular FROM user  LEFT JOIN client_info ON user.id = client_info.user_id WHERE user.id = ?";
    /**
     * The constant SQL_ERROR.
     */
    private static final String SQL_ERROR = "Sql error";
    /**
     * The constant NO_CONNECTION_MESSAGE.
     */
    private static final String NO_CONNECTION_MESSAGE = "Unable to get connection";

    /**
     * Instantiates a new Client dAO.
     */
    private ClientDAO() {
    }

    /**
     * Is regular client.
     *
     * @param user the user
     * @return the boolean
     * @throws DAOTechnicalException the dAO technical exception
     * @throws DAOLogicalException the dAO logical exception
     * @throws ConnectionPoolException the connection pool exception
     */
    public static boolean isRegularClient(User user) throws DAOTechnicalException, DAOLogicalException, ConnectionPoolException {
        return user != null && user.getRole().getRolename().equals(Role.ROLE_CLIENT) && isRegular(user.getId());
    }


    /**
     * Is regular.
     *
     * @param id the id
     * @return the boolean
     * @throws DAOLogicalException the dAO logical exception
     * @throws DAOTechnicalException the dAO technical exception
     * @throws ConnectionPoolException the connection pool exception
     */
    private static boolean isRegular(Integer id) throws DAOLogicalException, DAOTechnicalException, ConnectionPoolException {
        if (id != null) {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            Connection connection = connectionPool.getConnection();
            if (connection != null) {
                PreparedStatement statement = null;
                try {
                    statement = connection.prepareStatement(SQL_QUERY);
                    statement.setInt(1, id.intValue());
                    ResultSet resultSet = statement.executeQuery();
                    if (resultSet.next()) {
                        return resultSet.getBoolean("is_regular");
                    } else {
                        throw new DAOLogicalException(SQL_ERROR);
                    }
                } catch (SQLException e) {
                    throw new DAOTechnicalException(e);
                } finally {
                    AbstractDAO.close(connection,statement,connectionPool);
                }
            } else {
                throw new DAOTechnicalException(NO_CONNECTION_MESSAGE);
            }
        }
        return false;
    }
}
