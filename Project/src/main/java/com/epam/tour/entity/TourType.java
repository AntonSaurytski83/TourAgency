package com.epam.tour.entity;

/**
 * The enum Tour type.
 */
public enum TourType {
    /**
     * The VACATION.
     */
    VACATION(1), /**
     * The SHOPPING.
     */
    SHOPPING(2), /**
     * The EXCURSION.
     */
    EXCURSION(3);

    /**
     * The Id.
     */
    private final int id;

    /**
     * Instantiates a new Tour type.
     *
     * @param id the id
     */
    TourType(int id) {
        this.id = id;
    }

    /**
     * Find by id.
     *
     * @param id the id
     * @return the tour type
     */
    public static TourType findById(int id) {
        for (TourType type : TourType.values()) {
            if (type.id == id) {
                return type;
            }
        }

        return null;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets display name.
     *
     * @return the display name
     */
    public String getDisplayName() {
        return name().toLowerCase();
    }
}
