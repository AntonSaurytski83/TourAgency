package com.epam.tour.listener;

import com.epam.tour.connection.ConnectionPool;
import com.epam.tour.resource.LocaleManager;
import com.mysql.jdbc.AbandonedConnectionCleanupThread;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Locale;

/**
 * The type Application listener.
 */
public class ApplicationListener implements ServletContextListener,
        HttpSessionListener, HttpSessionAttributeListener, ServletRequestListener {

    /**
     * The constant LOG.
     */
    private static final Logger LOG = Logger.getRootLogger();


    /**
     * Context initialized.
     *
     * @param sce the sce
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {

        ServletContext context = sce.getServletContext();
        context.setAttribute("locales", LocaleManager.INSTANCE.getLocales());

        Locale.setDefault(Locale.ENGLISH);

    }

    /**
     * Context destroyed.
     *
     * @param sce the sce
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {

        ConnectionPool connectionPool;
        connectionPool = ConnectionPool.getInstance();
        connectionPool.shutDown();

//  ShutDown Abandoned Connection  :  memory leaks after Tomcat stops
        try {
            AbandonedConnectionCleanupThread.shutdown();
            LOG.info("Abandoned connection shut down!");
        } catch (InterruptedException e) {
            LOG.warn("SEVERE problem cleaning up: " + e.getMessage());
            e.printStackTrace();
        }
//  This manually deregisters JDBC driver, which prevents Tomcat  from complaining about memory leaks this class
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
                LOG.info(String.format("deregistering jdbc driver: %s ", driver));
            } catch (SQLException e) {
                LOG.error(String.format("Error deregistering driver: %s ", driver), e);
            }
        }
    }


    /**
     * Session created.
     *
     * @param se the se
     */
// -------------------------------------------------------
    // HttpSessionListener implementation
    // -------------------------------------------------------
    @Override
    public void sessionCreated(HttpSessionEvent se) {

    }


    /**
     * Session destroyed.
     *
     * @param se the se
     */
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {

    }

    // -------------------------------------------------------
    // HttpSessionAttributeListener implementation
    // -------------------------------------------------------

    /**
     * Attribute added.
     *
     * @param sbe the sbe
     */
    @Override
    public void attributeAdded(HttpSessionBindingEvent sbe) {
    }

    /**
     * Attribute removed.
     *
     * @param sbe the sbe
     */
    @Override
    public void attributeRemoved(HttpSessionBindingEvent sbe) {
    }

    /**
     * Attribute replaced.
     *
     * @param sbe the sbe
     */
    @Override
    public void attributeReplaced(HttpSessionBindingEvent sbe) {

    }

    /**
     * Request destroyed.
     *
     * @param sre the sre
     */
    @Override
    public void requestDestroyed(ServletRequestEvent sre) {

    }

    /**
     * Request initialized.
     *
     * @param sre the sre
     */
    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        ServletRequest request = sre.getServletRequest();
        Locale locale = LocaleManager.INSTANCE.resolveLocale(request);
        request.setAttribute("locale", locale);
    }
}
