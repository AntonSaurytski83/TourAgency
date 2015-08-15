package com.epam.tour.builder;

import com.epam.tour.entity.Tour;
import com.epam.tour.entity.TourType;
import com.epam.tour.exception.BuildException;
import com.epam.tour.helper.ConstantsHelper;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Build a tour object from request
 */
public class TourBuilder {

    /**
     * The constant LOG.
     */
    private static final Logger LOG = Logger.getRootLogger();

    /**
     * The constant TOUR_NAME.
     */
    public static final Pattern TOUR_NAME =
            Pattern.compile("(\\A[A-Za-z0-9\\s]{3,30}\\z)|(\\A[А-ЯЁёа-я0-9\\s]{3,30}\\z)");
    /**
     * The constant TOUR_PRICE.
     */
    public static final Pattern TOUR_PRICE =
            Pattern.compile("[0-9]{2,6}");
    /**
     * The constant TOUR_DETAILS.
     */
    public static final Pattern TOUR_DETAILS =
            Pattern.compile("(\\A[a-zA-Zа-яЁёА-Я0-9\\*!\\?,\\.«»\\-:\"\\()\\^\\s]{6,1200}\\z)");
    /**
     * The constant TOUR_DISCOUNT.
     */
    public static final Pattern TOUR_DISCOUNT =
            Pattern.compile("[0-9]{1,2}");


    /**
     * Instantiates a new Tour builder.
     */
    private TourBuilder() {

    }

    /**
     * The type Tour builder holder.
     */
    public static class TourBuilderHolder {
        /**
         * The constant HOLDER_INSTANCE.
         */
        public static final TourBuilder HOLDER_INSTANCE = new TourBuilder();
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static TourBuilder getInstance() {
        return TourBuilderHolder.HOLDER_INSTANCE;
    }


    /**
     * Build void.
     *
     * @param map the map
     * @param tour the tour
     * @throws BuildException the build exception
     */
    public void build(Map<String, String[]> map, Tour tour) throws BuildException {
        try {
            final boolean tourname = !buildTourname(map.get(ConstantsHelper.TOURNAME), tour);
            final boolean price = !buildPrice(map.get(ConstantsHelper.PRICE), tour);
            final boolean details = !buildDetails(map.get(ConstantsHelper.DETAILS), tour);
            final boolean discount = !buildDiscount(map.get(ConstantsHelper.REGULAR_DISCOUNT), tour);
            final boolean hot = !buildHot(map.get(ConstantsHelper.HOT), tour);
            final boolean type = !buildType(map.get(ConstantsHelper.TYPE), tour);

            if (tourname || details || price) {
                throw new BuildException();
            } else if (discount || hot || type) {
                throw new BuildException();
            }
        } catch (UnsupportedEncodingException e) {
            LOG.error(e);
        }

    }

    /**
     * Build tourname.
     *
     * @param args the args
     * @param tour the tour
     * @return the boolean
     * @throws UnsupportedEncodingException the unsupported encoding exception
     */
    private boolean buildTourname(String[] args, Tour tour) throws UnsupportedEncodingException {
        if (args != null && args.length > 0) {
            String tourname = new String(args[0].getBytes("ISO-8859-1"), "UTF-8");
            if (TOUR_NAME.matcher(tourname).matches()) {
                tour.setTourname(tourname);
                return true;
            }
        }
        return false;
    }


    /**
     * Build price.
     *
     * @param args the args
     * @param tour the tour
     * @return the boolean
     */
    private boolean buildPrice(String[] args, Tour tour) {
        if (args != null && args.length > 0 && !args[0].isEmpty()) {
            try {
                int price = Integer.parseInt(args[0]);
                if (TOUR_PRICE.matcher(Integer.toString(price)).matches()) {
                    tour.setPrice(price);
                    return true;
                } else {
                    return false;
                }
            } catch (NumberFormatException e) {
                LOG.info(e);
                return false;
            }
        }
        return false;
    }

    /**
     * Build discount.
     *
     * @param args the args
     * @param tour the tour
     * @return the boolean
     */
    private boolean buildDiscount(String[] args, Tour tour) {
        if (args != null && args.length > 0 && !args[0].isEmpty()) {
            try {
                int discount = Integer.parseInt(args[0]);
                if (TOUR_DISCOUNT.matcher(Integer.toString(discount)).matches()) {
                    tour.setRegularDiscount(discount);
                    return true;
                } else {
                    return false;
                }
            } catch (NumberFormatException e) {
                LOG.info(e);
                return false;
            }
        }

        return false;
    }

    /**
     * Build hot.
     *
     * @param args the args
     * @param tour the tour
     * @return the boolean
     */
    private boolean buildHot(String[] args, Tour tour) {
        if (args != null && args.length > 0 && !args[0].isEmpty()) {
            tour.setHot(true);
        } else {
            tour.setHot(false);
        }
        return true;
    }

    /**
     * Build details.
     *
     * @param args the args
     * @param tour the tour
     * @return the boolean
     * @throws UnsupportedEncodingException the unsupported encoding exception
     */
    private boolean buildDetails(String[] args, Tour tour) throws UnsupportedEncodingException {
        if (args != null && args.length > 0) {
            String details = new String(args[0].getBytes("ISO-8859-1"), "UTF-8");
            if (TOUR_DETAILS.matcher(details).matches()) {
                tour.setDetails(details);
                return true;
            }
        }
        return false;
    }

    /**
     * Build type.
     *
     * @param args the args
     * @param tour the tour
     * @return the boolean
     */
    private boolean buildType(String[] args, Tour tour) {
        if (args != null && args.length > 0 && !args[0].isEmpty()) {
            try {
                final int typeID = Integer.parseInt(args[0]);
                final TourType type = TourType.findById(typeID);
                if (type != null) {
                    tour.setType(type);
                    return true;
                } else {
                    return false;
                }
            } catch (NumberFormatException e) {
                LOG.info(e);
                return false;
            }
        }

        return false;
    }


}
