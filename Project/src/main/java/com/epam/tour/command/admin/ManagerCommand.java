package com.epam.tour.command.admin;

import com.epam.tour.command.ActionCommand;
import com.epam.tour.dao.TourDAO;
import com.epam.tour.entity.Role;
import com.epam.tour.entity.Tour;
import com.epam.tour.entity.User;
import com.epam.tour.exception.ConnectionPoolException;
import com.epam.tour.exception.DAOTechnicalException;
import com.epam.tour.helper.ConstantsHelper;
import com.epam.tour.resource.PathManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * The type Manager command.
 */
public class ManagerCommand implements ActionCommand {

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
        TourDAO dao = TourDAO.getInstance();
        try {
            List<Tour> tours = dao.findAll();
            request.setAttribute(ConstantsHelper.TOURS, tours);
        } catch (DAOTechnicalException | ConnectionPoolException e) {
            LOG.info("Technical error detected!: ", e);
            return null;
        }
        return PathManager.INSTANCE.getString(ConstantsHelper.PATH_PAGE_ADMIN_MANAGER);
    }
}
