package com.epam.courses.java.final_project.util;

import java.sql.Date;
import java.util.Calendar;

public class Util {

    public static String transformDate(String oldDate){
        if (oldDate == null)
            return null;
        return oldDate.substring(6) + "-" +
                oldDate.substring(0, 2) + "-" +
                oldDate.substring(3, 5);
    }

    public static Date getToday(){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return new Date(c.getTimeInMillis());
    }

    public static double calcPrice() {
        return 0.0;
    }
}
