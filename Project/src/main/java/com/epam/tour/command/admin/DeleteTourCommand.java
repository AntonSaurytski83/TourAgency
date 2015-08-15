package com.epam.tour.command.admin;

import com.epam.tour.command.ActionCommand;
import com.epam.tour.dao.TourDAO;
import com.epam.tour.entity.Role;
import com.epam.tour.entity.Tour;
import com.epam.tour.entity.User;
import com.epam.tour.exception.ConnectionPoolException;
import com.epam.tour.exception.DAOLogicalException;
import com.epam.tour.exception.DAOTechnicalException;
import com.epam.tour.helper.ConstantsHelper;
import com.epam.tour.notification.creator.NotificationCreator;
import com.epam.tour.notification.entity.Notification;
import com.epam.tour.notification.entity.Notification.Type;
import com.epam.tour.notification.service.NotificationService;
import com.epam.tour.resource.LocaleManager;
import com.epam.tour.resource.PathManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;

/**
 * The type Delete tour command.
 */
public class DeleteTourCommand implements ActionCommand {

    /**
     * The constant LOG.
     */
    private static final Logger LOG = Logger.getRootLogger();
    /**
     * The constant ERROR_INVALID_PARAMETER.
     */
    public static final String ERROR_INVALID_PARAMETER = "error.invalid_parameter";
    /**
     * The constant ERROR_DB_NO_SUCH_RECORD.
     */
    public static final String ERROR_DB_NO_SUCH_RECORD = "error.db.no_such_record";

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
    public String execute(HttpServletRequest request)  {
        String param = request.getParameter(ConstantsHelper.ID);
        if (param != null) {
            Notification notification = null;
            Locale locale = LocaleManager.INSTANCE.resolveLocale(request);
            try {
                int id = Integer.parseInt(param);
                TourDAO dao = TourDAO.getInstance();
                if (dao.delete(id)) {
                    List<Tour> tours = dao.findAll();
                    request.setAttribute(ConstantsHelper.TOURS, tours);
                    LOG.info("Tour " + id + " delete success");
                    notification = NotificationCreator.createFromProperty("info.db.delete_success", locale);
                }
            } catch (NumberFormatException e) {
                LOG.error("Invalid parameter", e);
                notification = NotificationCreator.createFromProperty(ERROR_INVALID_PARAMETER, Type.ERROR, locale);
            } catch (DAOTechnicalException | ConnectionPoolException e) {
                LOG.info("Technical error detected!: ", e);
                return null;
            } catch (DAOLogicalException e) {
                LOG.error("No such record", e);
                notification = NotificationCreator.createFromProperty(ERROR_DB_NO_SUCH_RECORD, Type.ERROR, locale);
            } finally {
                if (notification != null) {
                    NotificationService.push(request.getSession(), notification);
                }
            }
        }

        return PathManager.INSTANCE.getString(ConstantsHelper.PATH_PAGE_ADMIN_MANAGER);
    }
}
