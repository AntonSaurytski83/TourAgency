package com.epam.tour.command.admin;

import com.epam.tour.command.ActionCommand;
import com.epam.tour.dao.OrderDAO;
import com.epam.tour.entity.Order;
import com.epam.tour.entity.Role;
import com.epam.tour.entity.User;
import com.epam.tour.exception.ConnectionPoolException;
import com.epam.tour.exception.DAOLogicalException;
import com.epam.tour.exception.DAOTechnicalException;
import com.epam.tour.helper.ConstantsHelper;
import com.epam.tour.resource.PathManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * The type Orders command.
 */
public class OrdersCommand implements ActionCommand {

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
            Role role = user.getRole();
            if (role != null && Role.ROLE_ADMIN.equals(role.getRolename())) {
                return true;
            }
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

        OrderDAO dao = OrderDAO.getInstance();
        if (request.getParameter(ConstantsHelper.ID) != null && request.getParameter(ConstantsHelper.PAID) != null) {
            try {
                int id = Integer.parseInt(request.getParameter(ConstantsHelper.ID));
                boolean paid = Boolean.parseBoolean(request.getParameter(ConstantsHelper.PAID));
                dao.updatePaid(id, paid);
                LOG.info("Update paid successfully");
            } catch (DAOLogicalException | NumberFormatException e) {
                LOG.info("Logical error detected!: ", e);
            } catch (DAOTechnicalException | ConnectionPoolException e) {
                LOG.info("Technical error detected!: ", e);
                return null;
            }
        }
        try {
            List<Order> orders = dao.findAll();
            request.setAttribute(ConstantsHelper.ORDERS, orders);
        } catch (DAOTechnicalException | ConnectionPoolException e) {
            LOG.info("Technical error detected!: ", e);
            return null;
        }
        return PathManager.INSTANCE.getString("path.page.admin.orders");
    }
}
