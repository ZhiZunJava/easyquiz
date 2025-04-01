package com.can.easyquiz.config.property;

public class Cookie {

    /**
     * Gets name.
     *
     * @return the name
     */
    public static String getName() {
        return "easyquiz";
    }

    /**
     * Gets interval.
     *
     * @return the interval
     */
    public static Integer getInterval() {
        return 30 * 24 * 60 * 60;
    }
}
