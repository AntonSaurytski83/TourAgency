package com.epam.tour.command;

import com.epam.tour.entity.User;
import com.epam.tour.logic.AuthenticationLogic;
import com.epam.tour.resource.PathManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * The type Logout command.
 */
public class LogoutCommand implements ActionCommand {

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
    public String execute(HttpServletRequest request)  {
        final User user = (User) request.getSession().getAttribute(AuthenticationLogic.SESSION_VAR);
        if (user != null) {
            AuthenticationLogic.logout(request.getSession());
            LOG.info("Logged out: " + user.getUsername());

        }
        return PathManager.INSTANCE.getString("path.page.main");
    }
}
