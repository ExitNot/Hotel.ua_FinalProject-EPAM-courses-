package com.epam.courses.java.final_project.util;

public class Util {

    public static String transformDate(String oldDate){
        if (oldDate == null)
            return null;
        return oldDate.substring(6) + "-" +
                oldDate.substring(0, 2) + "-" +
                oldDate.substring(3, 5);
    }

    public static double calcPrice() {
        return 0.0;
    }
}
