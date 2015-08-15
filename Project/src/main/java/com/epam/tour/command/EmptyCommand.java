package com.epam.tour.command;

import com.epam.tour.entity.User;
import com.epam.tour.resource.PathManager;

import javax.servlet.http.HttpServletRequest;

/**
 * This command is used if empty or wrong command is specified
 */
public class EmptyCommand implements ActionCommand {

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
     * default command that render main page
     *
     *
     * @param request the request
     * @return the string
     */
    @Override
    public String execute(HttpServletRequest request)  {
        return PathManager.INSTANCE.getString("path.page.main");
    }
}
