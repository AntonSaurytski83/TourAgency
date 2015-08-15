package com.epam.tour.entity;

import java.io.Serializable;

/**
 * The type Tour.
 */
public class Tour extends Entity implements Serializable {


    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = 3247694199643732647L;
    /**
     * The Tourname.
     */
    private String tourname;

    /**
     * The Details.
     */
    private String details;

    /**
     * The Price.
     */
    private int price;

    /**
     * The Hot.
     */
    private boolean hot;

    /**
     * The Regular discount.
     */
    private int regularDiscount;

    /**
     * The Type.
     */
    private TourType type = TourType.VACATION;

    /**
     * Gets type.
     *
     * @return the type
     */
    public TourType getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(TourType type) {
        this.type = type;
    }

    /**
     * Gets tourname.
     *
     * @return the tourname
     */
    public String getTourname() {
        return tourname;
    }

    /**
     * Sets tourname.
     *
     * @param tourname the tourname
     */
    public void setTourname(String tourname) {
        this.tourname = tourname;
    }

    /**
     * Gets details.
     *
     * @return the details
     */
    public String getDetails() {
        return details;
    }

    /**
     * Sets details.
     *
     * @param details the details
     */
    public void setDetails(String details) {
        this.details = details;
    }

    /**
     * Gets price.
     *
     * @return the price
     */
    public int getPrice() {
        return price;
    }

    /**
     * Sets price.
     *
     * @param price the price
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * Is hot.
     *
     * @return the boolean
     */
    public boolean isHot() {
        return hot;
    }

    /**
     * Sets hot.
     *
     * @param hot the hot
     */
    public void setHot(boolean hot) {
        this.hot = hot;
    }

    /**
     * Gets regular discount.
     *
     * @return the regular discount
     */
    public int getRegularDiscount() {
        return regularDiscount;
    }

    /**
     * Sets regular discount.
     *
     * @param regularDiscount the regular discount
     */
    public void setRegularDiscount(int regularDiscount) {
        this.regularDiscount = regularDiscount;
    }

    /**
     * Equals boolean.
     *
     * @param o the o
     * @return the boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tour)) return false;

        Tour tour = (Tour) o;

        if (getPrice() != tour.getPrice()) return false;
        if (isHot() != tour.isHot()) return false;
        if (getRegularDiscount() != tour.getRegularDiscount()) return false;
        if (getTourname() != null ? !getTourname().equals(tour.getTourname()) : tour.getTourname() != null)
            return false;
        if (getDetails() != null ? !getDetails().equals(tour.getDetails()) : tour.getDetails() != null) return false;
        return getType() == tour.getType();

    }

    /**
     * Hash code.
     *
     * @return the int
     */
    @Override
    public int hashCode() {
        int result = getTourname() != null ? getTourname().hashCode() : 0;
        result = 31 * result + (getDetails() != null ? getDetails().hashCode() : 0);
        result = 31 * result + getPrice();
        result = 31 * result + (isHot() ? 1 : 0);
        result = 31 * result + getRegularDiscount();
        result = 31 * result + (getType() != null ? getType().hashCode() : 0);
        return result;
    }

    /**
     * To string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Tour{");
        sb.append("tourname='").append(tourname).append('\'');
        sb.append(", details='").append(details).append('\'');
        sb.append(", price=").append(price);
        sb.append(", hot=").append(hot);
        sb.append(", regularDiscount=").append(regularDiscount);
        sb.append(", type=").append(type);
        sb.append('}');
        return sb.toString();
    }
}
