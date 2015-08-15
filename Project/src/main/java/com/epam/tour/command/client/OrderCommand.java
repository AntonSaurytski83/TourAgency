package com.epam.tour.command.client;

import com.epam.tour.command.ActionCommand;
import com.epam.tour.command.admin.DeleteTourCommand;
import com.epam.tour.dao.ClientDAO;
import com.epam.tour.dao.OrderDAO;
import com.epam.tour.dao.TourDAO;
import com.epam.tour.entity.Order;
import com.epam.tour.entity.Role;
import com.epam.tour.entity.Tour;
import com.epam.tour.entity.User;
import com.epam.tour.exception.ConnectionPoolException;
import com.epam.tour.exception.DAOLogicalException;
import com.epam.tour.exception.DAOTechnicalException;
import com.epam.tour.helper.ConstantsHelper;
import com.epam.tour.logic.AuthenticationLogic;
import com.epam.tour.notification.creator.NotificationCreator;
import com.epam.tour.notification.entity.Notification;
import com.epam.tour.notification.entity.Notification.Type;
import com.epam.tour.notification.service.NotificationService;
import com.epam.tour.resource.LocaleManager;
import com.epam.tour.resource.PathManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Locale;

/**
 * The type Order command.
 */
public class OrderCommand implements ActionCommand {


    /**
     * The constant LOG.
     */
    private static final Logger LOG = Logger.getRootLogger();

    /**
     * Logic orders.
     *
     * @param user the user
     * @param tour the tour
     * @param amount the amount
     * @return the boolean
     */
    public static boolean logicOrders(User user, Tour tour, double amount) {

        if (user != null && tour != null) {
            Order order = new Order();
            order.setUser(user);
            order.setTour(tour);

            order.setAmount(amount);
            order.setDateTime(new Date());

            OrderDAO dao = OrderDAO.getInstance();
            try {
                return dao.create(order);
            } catch (DAOTechnicalException | ConnectionPoolException e) {
                LOG.info("Technical error detected!: ", e);
                return false;
            }
        } else {
            LOG.error("Invalid data user: " + user + ", tour: " + tour);
            return false;
        }

    }

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
    public String execute(HttpServletRequest request)  {

        User user = AuthenticationLogic.user(request);
        int id;
        Locale locale = LocaleManager.INSTANCE.resolveLocale(request);
        try {
            id = Integer.parseInt(request.getParameter(ConstantsHelper.ID));
        } catch (NumberFormatException ignored) {
            NotificationCreator.createFromProperty(DeleteTourCommand.ERROR_INVALID_PARAMETER, Type.WARNING, locale);
            return PathManager.INSTANCE.getString(ConstantsHelper.PATH_PAGE_ADMIN_MANAGER);
        }
        Notification notification = null;
        try {
            TourDAO tourDAO = TourDAO.getInstance();
            Tour tour = tourDAO.findById(id);
            boolean regular = ClientDAO.isRegularClient(user);
            double amount = regular ? (double) tour.getPrice() - ((double) tour.getPrice() * (double) tour.getRegularDiscount() * 0.01) : (double) tour.getPrice();
            request.setAttribute(ConstantsHelper.AMOUNT, amount);
            request.setAttribute(ConstantsHelper.TOUR, tour);
            if ("1".equals(request.getParameter("confirm"))) {
                boolean result = logicOrders(user, tour, amount);
                if (result) {
                    return PathManager.INSTANCE.getString("path.page.client.complete");
                } else {
                    notification = NotificationCreator.createFromProperty("unknown error", locale);
                }
            }
        } catch (DAOLogicalException e) {
            LOG.info("Logical error detected!: ", e);
        } catch (DAOTechnicalException | ConnectionPoolException e) {
            LOG.info("Technical error detected!: ", e);
            return null;
        } finally {
            if (notification != null) {
                NotificationService.push(request.getSession(), notification);
            }
        }
        return PathManager.INSTANCE.getString("path.page.client.order");
    }
}
