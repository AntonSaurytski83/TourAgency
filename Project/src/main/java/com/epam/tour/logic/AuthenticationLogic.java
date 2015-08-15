package com.epam.tour.logic;

import com.epam.tour.dao.UserDAO;
import com.epam.tour.entity.User;
import com.epam.tour.exception.AuthenticationLogicalException;
import com.epam.tour.exception.ConnectionPoolException;
import com.epam.tour.exception.DAOLogicalException;
import com.epam.tour.exception.DAOTechnicalException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * The type Authentication logic.
 */
public final class AuthenticationLogic  {


    /**
     * The constant LOG.
     */
    private static final Logger LOG = Logger.getRootLogger();

    /**
     * The constant SESSION_VAR.
     */
    public static final String SESSION_VAR = "_user";


    /**
     * Instantiates a new Authentication logic.
     */
    private AuthenticationLogic() {
    }

    /**
     * Authenticate user.
     *
     * @param login the login
     * @param password the password
     * @return the user
     * @throws AuthenticationLogicalException the authentication logical exception
     */
    public static User authenticate(String login, String password) throws AuthenticationLogicalException {
        if (login != null && password != null) {
           final String hash = DigestUtils.md5Hex(password);
            UserDAO dao = UserDAO.getInstance();
            try {
                return dao.findByLoginAndPassword(login, hash);
            } catch (DAOLogicalException e) {
                throw new AuthenticationLogicalException(e);
            }
            catch (DAOTechnicalException | ConnectionPoolException e) {
                LOG.info("Technical error detected!: ", e);
                return null;
            }
        }
        return null;
    }


    /**
     * Logout void.
     *
     * @param session the session
     */
    public static void logout(HttpSession session) {
        session.invalidate();
    }

    /**
     * User user.
     *
     * @param request the request
     * @return the user
     */
    public static User user(HttpServletRequest request) {
        final HttpSession session = request.getSession();
        final Object ob = session.getAttribute(SESSION_VAR);
        return ((ob != null) && ob.getClass().equals(User.class)) ? (User) ob : null;
    }
}
