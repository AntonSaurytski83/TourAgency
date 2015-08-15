package com.epam.tour.entity;

import java.io.Serializable;

/**
 * The type Role.
 */
public class Role extends Entity implements Serializable{


    /**
     * The constant ROLE_ADMIN.
     */
    public static final String ROLE_ADMIN = "admin";
    /**
     * The constant ROLE_CLIENT.
     */
    public static final String ROLE_CLIENT = "client";
    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = -6481105593392463256L;

    /**
     * The Rolename.
     */
    private String rolename;


    /**
     * Gets rolename.
     *
     * @return the rolename
     */
    public String getRolename() {
        return rolename;
    }

    /**
     * Sets rolename.
     *
     * @param rolename the rolename
     */
    public void setRolename(String rolename) {
        this.rolename = rolename;
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
        if (!(o instanceof Role)) return false;

        Role role = (Role) o;

        return !(getRolename() != null ? !getRolename().equals(role.getRolename()) : role.getRolename() != null);

    }

    /**
     * Hash code.
     *
     * @return the int
     */
    @Override
    public int hashCode() {
        return getRolename() != null ? getRolename().hashCode() : 0;
    }

    /**
     * To string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Role{");
        sb.append("rolename='").append(rolename).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
