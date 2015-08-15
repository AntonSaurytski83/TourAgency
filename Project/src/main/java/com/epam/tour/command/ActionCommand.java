package com.epam.tour.command;

import com.epam.tour.entity.User;

import javax.servlet.http.HttpServletRequest;

/**
 * The interface Action command.
 */
public interface ActionCommand {


    /**
     * Check the access of user, return true if the user has access to
     * this command, otherwise return false
     *
     * @param user the user
     * @return the boolean
     */
    boolean checkAccess(User user);


     /**
     * This method reads a command from the request
     * and processes it. The result will be given as
     * a page to forward to
     *
     * @param request the request
     * @return the string
     */
    String execute(HttpServletRequest request);

}
