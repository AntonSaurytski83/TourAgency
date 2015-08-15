package com.epam.tour.command.admin;

import com.epam.tour.builder.TourBuilder;
import com.epam.tour.command.ActionCommand;
import com.epam.tour.dao.TourDAO;
import com.epam.tour.entity.Role;
import com.epam.tour.entity.Tour;
import com.epam.tour.entity.TourType;
import com.epam.tour.entity.User;
import com.epam.tour.exception.*;
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
 * The type Update tour command.
 */
public class UpdateTourCommand implements ActionCommand {

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

        request.setAttribute(ConstantsHelper.TOUR_TYPES, TourType.values());
        Notification notification = null;
        Locale locale = LocaleManager.INSTANCE.resolveLocale(request);
        Tour tour = new Tour();
        TourDAO dao = TourDAO.getInstance();
        int id;
        try {
            try {
                id = Integer.parseInt(request.getParameter(ConstantsHelper.ID));
            } catch (NumberFormatException e) {
                LOG.error("Invalid parameter ID!", e);
                notification = NotificationCreator.createFromProperty(DeleteTourCommand.ERROR_INVALID_PARAMETER, Type.ERROR, locale);
                return PathManager.INSTANCE.getString(ConstantsHelper.PATH_PAGE_ADMIN_MANAGER);
            }
            if (request.getParameter(ConstantsHelper.SUBMIT) != null) {
                tour = new Tour();
                tour.setId(id);
                try {
                    TourBuilder.getInstance().build(request.getParameterMap(), tour);
                    if (dao.update(tour)) {
                        LOG.info("Tour update success");
                        notification = NotificationCreator.createFromProperty("info.db.update_success", locale);
                        List<Tour> tours = dao.findAll();
                        request.setAttribute(ConstantsHelper.TOURS, tours);
                        return PathManager.INSTANCE.getString(ConstantsHelper.PATH_PAGE_ADMIN_MANAGER);
                    }

                } catch (BuildException e) {
                    LOG.error("Build error!", e);
                    notification = NotificationCreator.createFromProperty(AddTourCommand.ADD_TOUR_INVALID_FORM_DATA, Type.ERROR, locale);
                } catch (DAOTechnicalException | ConnectionPoolException e) {
                    LOG.info("Technical error detected!: ", e);
                    return null;
                } catch (DAOLogicalException e) {
                    LOG.error("Invalid data!", e);
                    notification = new Notification(e.getMessage(), Type.ERROR);
                }
            } else {
                try {
                    tour = dao.findById(id);
                } catch (DAOTechnicalException | ConnectionPoolException e) {
                    LOG.info("Technical error detected!: ", e);
                    return null;
                } catch (DAOLogicalException e) {
                    LOG.error("Record not find!", e);
                    notification = NotificationCreator.createFromProperty(DeleteTourCommand.ERROR_DB_NO_SUCH_RECORD, Type.ERROR, locale);
                    return PathManager.INSTANCE.getString(ConstantsHelper.PATH_PAGE_ADMIN_MANAGER);
                }
            }
        } finally {
            if (notification != null) {
                NotificationService.push(request.getSession(), notification);
            }
        }
        request.setAttribute(ConstantsHelper.TOUR, tour);
        return PathManager.INSTANCE.getString("path.page.admin.update_tour");
    }
}
