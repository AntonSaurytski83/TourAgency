package com.epam.tour.helper;

import com.epam.tour.command.ActionCommand;
import com.epam.tour.command.EmptyCommand;
import com.epam.tour.command.LoginCommand;
import com.epam.tour.command.LogoutCommand;
import com.epam.tour.command.ViewToursCommand;
import com.epam.tour.command.admin.AddTourCommand;
import com.epam.tour.command.admin.DeleteTourCommand;
import com.epam.tour.command.admin.ManagerCommand;
import com.epam.tour.command.admin.OrdersCommand;
import com.epam.tour.command.admin.UpdateTourCommand;
import com.epam.tour.command.client.AccountCommand;
import com.epam.tour.command.client.OrderCommand;

import javax.servlet.ServletRequest;
import java.util.HashMap;
import java.util.Map;


/**
 * The enum Request helper.
 */
public enum RequestHelper {
    /**
     * The INSTANCE.
     */
    INSTANCE;

    /**
     * The constant COMMAND_PARAMETER.
     */
    public static final String COMMAND_PARAMETER = "c";


    /**
     * The Commands.
     */
    private Map<String, ActionCommand> commands = new HashMap<>();

    {
        //Everyone commands
        commands.put(ConstantsHelper.LOGIN, new LoginCommand());
        commands.put(ConstantsHelper.LOGOUT, new LogoutCommand());
        commands.put(ConstantsHelper.TOURS, new ViewToursCommand());
        //Client commands
        commands.put(ConstantsHelper.ACCOUNT, new AccountCommand());
        commands.put(ConstantsHelper.ORDER, new OrderCommand());
        //Admin commands
        commands.put(ConstantsHelper.MANAGER, new ManagerCommand());
        commands.put(ConstantsHelper.ADD_TOUR, new AddTourCommand());
        commands.put(ConstantsHelper.DELETE_TOUR, new DeleteTourCommand());
        commands.put(ConstantsHelper.UPDATE_TOUR, new UpdateTourCommand());
        commands.put(ConstantsHelper.ORDERS, new OrdersCommand());
    }

    /**
     * Gets command.
     *
     * @param request the request
     * @return the command
     */
    public ActionCommand getCommand(ServletRequest request) {
        String action = request.getParameter(COMMAND_PARAMETER);
        return getCommand(action);
    }

    /**
     * Gets command.
     *
     * @param action the action
     * @return the command
     */
    public ActionCommand getCommand(String action) {

        ActionCommand command = commands.get(action);

        if (command == null) {
            command = new EmptyCommand();
        }

        return command;
    }
}
