package com.epam.courses.java.final_project.util;

import com.epam.courses.java.final_project.model.RoomType;

import java.sql.Date;
import java.util.Calendar;

/**
 * Utility static functions
 *
 * @author Kostiantyn Kolchenko
 */
public class Util {

    /**
     * Function to transform Date string from web page(js) to sql format {@code Date} string.
     * [mm/dd/yyyy] -> [yyyy-mm-dd]
     *
     * @param oldDate date string to transform
     * @return today's date
     * @see Date
     */
    public static String transformDate(String oldDate) {
        if (oldDate == null)
            return null;
        return oldDate.substring(6) + "-" +
                oldDate.substring(0, 2) + "-" +
                oldDate.substring(3, 5);
    }

    /**
     * Function returns today's date.
     *
     * @return today's date
     */
    public static Date getToday() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return new Date(c.getTimeInMillis());
    }

    /**
     * Function for calculating room price.
     *
     * @param capacity       room capacity
     * @param childrenAmount amount of children
     * @param rc             room class
     * @return room price
     */
    public static double calcPrice(int capacity, int childrenAmount, RoomType.RoomClass rc) {
        int out = 0;

        if (capacity == 1) capacity++;
        switch (rc) {
            case Standard:
                out += 1000 * (capacity / 2);
                if (childrenAmount > 0) out = (out / 100) * 80;
                break;
            case Upgraded:
                out += 1200 * (capacity / 2);
                if (childrenAmount > 0) out = (out / 100) * 80;
                break;
            case Deluxe:
                out += 1500 * (capacity / 2);
                if (childrenAmount > 0) out = (out / 100) * 80;
                break;
            case Suite:
                out += 2000 * (capacity / 2);
                if (childrenAmount > 0) out = (out / 100) * 80;
        }
        return out;
    }
}
