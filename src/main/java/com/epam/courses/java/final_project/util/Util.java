package com.epam.courses.java.final_project.util;

public class Util {

    public static String transformDate(String oldDate){
        return oldDate.substring(6) + "-" +
                oldDate.substring(0, 2) + "-" +
                oldDate.substring(3, 5);
    }
}
