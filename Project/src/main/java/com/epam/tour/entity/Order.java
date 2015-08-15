package com.epam.tour.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * The type Order.
 */
public class Order extends Entity implements Serializable {


    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = 2131437530109623077L;
    /**
     * The User.
     */
    private User user;

    /**
     * The Tour.
     */
    private Tour tour;

    /**
     * The Date time.
     */
    private Date dateTime;

    /**
     * The Amount.
     */
    private double amount;

    /**
     * The Paid.
     */
    private boolean paid;

    /**
     * Gets user.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets user.
     *
     * @param user the user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets tour.
     *
     * @return the tour
     */
    public Tour getTour() {
        return tour;
    }

    /**
     * Sets tour.
     *
     * @param tour the tour
     */
    public void setTour(Tour tour) {
        this.tour = tour;
    }

    /**
     * Gets date time.
     *
     * @return the date time
     */
    public Date getDateTime() {
        return (Date) dateTime.clone();
    }

    /**
     * Sets date time.
     *
     * @param dateTime the date time
     */
    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * Gets amount.
     *
     * @return the amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Sets amount.
     *
     * @param amount the amount
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Is paid.
     *
     * @return the boolean
     */
    public boolean isPaid() {
        return paid;
    }

    /**
     * Sets paid.
     *
     * @param paid the paid
     */
    public void setPaid(boolean paid) {
        this.paid = paid;
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
        if (!(o instanceof Order)) return false;

        Order order = (Order) o;

        if (Double.compare(order.getAmount(), getAmount()) != 0) return false;
        if (isPaid() != order.isPaid()) return false;
        if (getUser() != null ? !getUser().equals(order.getUser()) : order.getUser() != null) return false;
        if (getTour() != null ? !getTour().equals(order.getTour()) : order.getTour() != null) return false;
        return !(getDateTime() != null ? !getDateTime().equals(order.getDateTime()) : order.getDateTime() != null);

    }

    /**
     * Hash code.
     *
     * @return the int
     */
    @Override
    public int hashCode() {
        int result = getUser() != null ? getUser().hashCode() : 0;
        result = 31 * result + (getTour() != null ? getTour().hashCode() : 0);
        result = 31 * result + (getDateTime() != null ? getDateTime().hashCode() : 0);
        long temp = Double.doubleToLongBits(getAmount());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (isPaid() ? 1 : 0);
        return result;
    }

    /**
     * To string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Order{");
        sb.append("user=").append(user);
        sb.append(", tour=").append(tour);
        sb.append(", dateTime=").append(dateTime);
        sb.append(", amount=").append(amount);
        sb.append(", paid=").append(paid);
        sb.append('}');
        return sb.toString();
    }
}
