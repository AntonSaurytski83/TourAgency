package com.epam.tour.command;

import com.epam.tour.dao.ClientDAO;
import com.epam.tour.dao.TourDAO;
import com.epam.tour.entity.Tour;
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
 * The type View tours command.
 */
public class ViewToursCommand implements ActionCommand {

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

        TourDAO dao = TourDAO.getInstance();
        try {
            List<Tour> tours = dao.findAll();
            User client = AuthenticationLogic.user(request);
            boolean regular = ClientDAO.isRegularClient(client);
            request.setAttribute(ConstantsHelper.REGULAR, regular);
            request.setAttribute(ConstantsHelper.TOURS, tours);
        } catch (DAOLogicalException e) {
            LOG.info("Logical error detected!: ", e);
        } catch (DAOTechnicalException | ConnectionPoolException e) {
            LOG.info("Technical error detected!: ", e);
            return null;
        }
        return PathManager.INSTANCE.getString("path.page.tours");
    }
}
