package com.epam.tour.filter;

import com.epam.tour.resource.PathManager;
import org.apache.log4j.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The type Jsp protection filter.
 */
public class JspProtectionFilter implements Filter {


    /**
     * The constant LOG.
     */
    private static final Logger LOG = Logger.getRootLogger();
    /**
     * The constant STATUS.
     */
    public static final int STATUS = 404;

    /**
     * Init void.
     *
     * @param filterConfig the filter config
     * @throws ServletException the servlet exception
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // blank
    }

    /**
     * Do filter.
     *
     * @param req the req
     * @param resp the resp
     * @param chain the chain
     * @throws IOException the iO exception
     * @throws ServletException the servlet exception
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) resp;
        response.setStatus(STATUS);
        LOG.error("Someone tried to access jsp file directly");
        req.getRequestDispatcher(PathManager.INSTANCE.getString("path.error404")).forward(req, resp);
    }

    /**
     * Destroy void.
     */
    @Override
    public void destroy() {
        // blank
    }
}
