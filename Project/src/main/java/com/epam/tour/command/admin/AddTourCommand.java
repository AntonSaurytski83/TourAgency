package com.epam.tour.command.admin;

import com.epam.tour.builder.TourBuilder;
import com.epam.tour.command.ActionCommand;
import com.epam.tour.dao.TourDAO;
import com.epam.tour.entity.Role;
import com.epam.tour.entity.Tour;
import com.epam.tour.entity.TourType;
import com.epam.tour.entity.User;
import com.epam.tour.exception.BuildException;
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
 * The type Add tour command.
 */
public class AddTourCommand implements ActionCommand {

    /**
     * The constant LOG.
     */
    private static final Logger LOG = Logger.getRootLogger();
    /**
     * The constant ADD_TOUR_INVALID_FORM_DATA.
     */
    public static final String ADD_TOUR_INVALID_FORM_DATA = "add_tour.invalid_form_data";

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

        request.setAttribute(ConstantsHelper.TOUR_TYPES, TourType.values());
        Locale locale = LocaleManager.INSTANCE.resolveLocale(request);
        Tour tour = new Tour();
        if (request.getParameter(ConstantsHelper.SUBMIT) != null) {
            Notification notification = null;
            try {
                TourBuilder.getInstance().build(request.getParameterMap(), tour);
                TourDAO dao = TourDAO.getInstance();
                if (dao.create(tour)) {
                    notification = NotificationCreator.createFromProperty("info.db.create_success", locale);
                    List<Tour> tours = dao.findAll();
                    request.setAttribute(ConstantsHelper.TOURS, tours);
                    LOG.info("Tour " + tour.getTourname() + " successfully added");
                    return PathManager.INSTANCE.getString(ConstantsHelper.PATH_PAGE_ADMIN_MANAGER);
                }
            } catch (BuildException e) {
                LOG.info("Invalid form data!", e);
                notification = NotificationCreator.createFromProperty(ADD_TOUR_INVALID_FORM_DATA, Type.ERROR, locale);
            } catch (DAOTechnicalException | ConnectionPoolException e) {
                LOG.info("Technical error detected!: ", e);
                return null;
            } catch (DAOLogicalException e) {
                LOG.error("Tour " + tour.getTourname() + " can`t added", e);
                notification = new Notification(e.getMessage(), Type.WARNING);
            } finally {
                if (notification != null) {
                    NotificationService.push(request.getSession(), notification);
                }
            }
        }
        request.setAttribute(ConstantsHelper.TOUR, tour);
        return PathManager.INSTANCE.getString("path.page.admin.add_tour");
    }
}
