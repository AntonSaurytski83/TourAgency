package com.epam.tour.command.client;

import com.epam.tour.command.ActionCommand;
import com.epam.tour.dao.OrderDAO;
import com.epam.tour.entity.Order;
import com.epam.tour.entity.Role;
import com.epam.tour.entity.User;
import com.epam.tour.exception.ConnectionPoolException;
import com.epam.tour.exception.DAOLogicalException;
import com.epam.tour.exception.DAOTechnicalException;
import com.epam.tour.helper.ConstantsHelper;
import com.epam.tour.logic.AuthenticationLogic;
import com.epam.tour.resource.PathManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * The type Account command.
 */
public class AccountCommand implements ActionCommand {

    /**
     * The constant LOG.
     */
    private static final Logger LOG = Logger.getRootLogger();

    /**
     * Check access.
     *
     * @param user the user
     * @return the boolean
     */

    @Override
    public boolean checkAccess(User user) {
        if (user != null) {
            String rolename = user.getRole().getRolename();
            return rolename.equals(Role.ROLE_CLIENT);
        }
        return false;
    }


    /**
     * Execute string.
     *
     * @param request the request
     * @return the string
     */
    @Override
    public String execute(HttpServletRequest request) {
        User user = AuthenticationLogic.user(request);
        try {
            List<Order> orders = OrderDAO.getInstance().findOrdersForUser(user);
            request.setAttribute(ConstantsHelper.ORDERS, orders);
        } catch (DAOLogicalException e) {
            LOG.info("Logical error detected!: ", e);
        } catch (DAOTechnicalException | ConnectionPoolException e) {
            LOG.info("Technical error detected!: ", e);
            return null;
        }
        return PathManager.INSTANCE.getString(ConstantsHelper.PATH_PAGE_CLIENT_ACCOUNT);
    }
}
