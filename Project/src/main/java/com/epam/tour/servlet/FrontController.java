package com.epam.tour.servlet;

import com.epam.tour.command.ActionCommand;
import com.epam.tour.helper.RequestHelper;
import com.epam.tour.resource.PathManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.MissingResourceException;

/**
 * The type Front controller.
 */
public class FrontController extends HttpServlet {

    /**
     * The constant LOG.
     */
    private static final Logger LOG = Logger.getRootLogger();
    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = -4203462103499487575L;
    /**
     * The Error page path.
     */
    private String errorPagePath;

    /**
     * Init void.
     *
     * @throws ServletException the servlet exception
     */
    @Override
    public void init() throws ServletException {
        try {
            errorPagePath = PathManager.INSTANCE.getString("path.error500");
        } catch (MissingResourceException e) {
            LOG.error(e);
        }
    }

    /**
     * Do post.
     *
     * @param request the request
     * @param response the response
     * @throws ServletException the servlet exception
     * @throws IOException the iO exception
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Do get.
     *
     * @param request the request
     * @param response the response
     * @throws ServletException the servlet exception
     * @throws IOException the iO exception
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Process request.
     *
     * @param request the request
     * @param response the response
     * @throws ServletException the servlet exception
     * @throws IOException the iO exception
     */
    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ActionCommand command = RequestHelper.INSTANCE.getCommand(request);

            LOG.info("Command: " + command.getClass().getSimpleName());
            String page = command.execute(request);
            if (page != null) {
                request.getRequestDispatcher(page).forward(request, response);
            } else   {
                request.getRequestDispatcher(errorPagePath).forward(request, response);
            }

        }
    }


