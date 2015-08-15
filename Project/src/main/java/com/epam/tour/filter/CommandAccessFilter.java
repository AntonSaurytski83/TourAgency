package com.epam.tour.filter;

import com.epam.tour.command.ActionCommand;
import com.epam.tour.entity.User;
import com.epam.tour.helper.RequestHelper;
import com.epam.tour.logic.AuthenticationLogic;
import com.epam.tour.resource.PathManager;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The type Command access filter.
 */
public class CommandAccessFilter implements Filter {


    /**
     * The constant STATUS.
     */
    public static final int STATUS = 403;
    /**
     * The constant LOG.
     */
    private  static final Logger LOG = Logger.getRootLogger();

    /**
     * Destroy void.
     */
    @Override
    public void destroy() {
        //blank
    }

    /**
     * Do filter.
     *
     * @param req the req
     * @param resp the resp
     * @param chain the chain
     * @throws ServletException the servlet exception
     * @throws IOException the iO exception
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        RequestHelper requestHelper = RequestHelper.INSTANCE;
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        ActionCommand command = requestHelper.getCommand(request);
        User user = AuthenticationLogic.user(request);
        if (command.checkAccess(user)) {
            chain.doFilter(req, resp);
        } else {
            response.setStatus(STATUS);
            LOG.error(String.format("Access denied for %s to the following command: %s", (user != null) ? user : "anonymous user", command));
            request.getRequestDispatcher(PathManager.INSTANCE.getString("path.error403")).forward(req, resp);
        }
    }

    /**
     * Init void.
     *
     * @param config the config
     * @throws ServletException the servlet exception
     */
    @Override
    public void init(FilterConfig config) throws ServletException {
        // blank
    }

}
