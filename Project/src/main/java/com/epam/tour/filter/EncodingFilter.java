package com.epam.tour.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * The type Encoding filter.
 */
public class EncodingFilter implements Filter {

    /**
     * Destroy void.
     */
    @Override
    public void destroy() {
        // blank
    }

    /**
     * Do filter.
     *
     * @param request the request
     * @param response the response
     * @param chain the chain
     * @throws ServletException the servlet exception
     * @throws UnsupportedEncodingException the unsupported encoding exception
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException,  UnsupportedEncodingException {

        String encoding = request.getCharacterEncoding();
        if (!"UTF-8".equalsIgnoreCase(encoding)) {
            request.setCharacterEncoding("UTF-8");
        }
        try {
            chain.doFilter(request, response);
        } catch (IOException e) {
            throw new UnsupportedEncodingException(e.getMessage());
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
