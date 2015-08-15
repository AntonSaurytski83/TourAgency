package com.epam.tour.command;

import com.epam.tour.dao.OrderDAO;
import com.epam.tour.dao.TourDAO;
import com.epam.tour.entity.Order;
import com.epam.tour.entity.Role;
import com.epam.tour.entity.Tour;
import com.epam.tour.entity.User;
import com.epam.tour.exception.AuthenticationLogicalException;
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
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Locale;

/**
 * The type Login command.
 */
public class LoginCommand implements ActionCommand {


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
        return true;
    }


    /**
     * Execute string.
     *
     * @param request the request
     * @return the string
     */
    @Override
    public String execute(HttpServletRequest request) {
        Locale locale = LocaleManager.INSTANCE.resolveLocale(request);
        String login = request.getParameter(ConstantsHelper.LOGIN);
        String password = request.getParameter(ConstantsHelper.PASSWORD);
        if (login != null && password != null) {
            Notification notification = null;
            try {
                User user = AuthenticationLogic.authenticate(login, password);
                if (user != null) {
                    HttpSession session = request.getSession();
                    session.setAttribute(AuthenticationLogic.SESSION_VAR, user);
                    LOG.info("Successful authentication by login: " + login);
                    notification = NotificationCreator.createFromProperty("info.auth.success", locale);

                    if (user.getRole().getRolename().equals(Role.ROLE_ADMIN)) {
                        List<Tour> tours = TourDAO.getInstance().findAll();
                        request.setAttribute(ConstantsHelper.TOURS, tours);
                        return PathManager.INSTANCE.getString(ConstantsHelper.PATH_PAGE_ADMIN_MANAGER);
                    } else if (user.getRole().getRolename().equals(Role.ROLE_CLIENT)) {
                        List<Order> orders = OrderDAO.getInstance().findOrdersForUser(user);
                        request.setAttribute(ConstantsHelper.ORDERS, orders);
                        return PathManager.INSTANCE.getString(ConstantsHelper.PATH_PAGE_CLIENT_ACCOUNT);
                    }
                } else {
                    LOG.error("User authentication error!");
                    return null;
                }
            } catch (DAOTechnicalException | ConnectionPoolException e) {
                LOG.info("Technical error detected!: ", e);
                return null;
            } catch (AuthenticationLogicalException | DAOLogicalException e) {
                LOG.info("Authentication fail by login: " + login, e);
                notification = NotificationCreator.createFromProperty("error.auth.invalid_login_pass", Type.ERROR, locale);
            } finally {
                if (notification != null) {
                    NotificationService.push(request.getSession(), notification);
                }
            }
        }
        return PathManager.INSTANCE.getString("path.page.login");
    }
}



