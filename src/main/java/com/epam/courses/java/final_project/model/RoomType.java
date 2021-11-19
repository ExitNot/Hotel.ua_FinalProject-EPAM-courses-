package com.epam.courses.java.final_project.model;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RoomType {

    private long id;
    private int capacity;
    private String bedsType;  // S - single, D - double. String in form ("1D, 2S") etc...
    private RoomClass roomClass;
    private String description;
    private List<Image> imgPaths;

    public RoomType(long id, int capacity, String bedsType, RoomClass roomClass, String description) {
        this.id = id;
        this.capacity = capacity;
        this.bedsType = bedsType;
        this.roomClass = roomClass;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getBedsType() {
        return bedsType;
    }

    public String getParsedBedsType(){
        StringBuilder sb = new StringBuilder();
        Matcher match = Pattern.compile("\\d[SDT]").matcher(bedsType);

        while (match.find()){
            if (sb.length() > 0)
                sb.append(" and ");
            String type = match.group();
            String bed = " bed";

            if (type.charAt(0) != '1') {
                sb.append(type.charAt(0));
                bed = bed + "s";
            } else if (type.substring(1).equals("T")) {
                bed = bed + "s";
            }
            switch (type.substring(1)){
                case "S":
                    sb.append("single");
                    break;
                case "D":
                    sb.append("double");
                    break;
                case "T":
                    sb.append("twin");
                    break;
            }
            sb.append(bed);
        }
        return sb.toString();
    }

    public void setBedsType(String bedType) {
        this.bedsType = bedType;
    }

    public RoomClass getRoomClass() {
        return roomClass;
    }

    public void setRoomClass(RoomClass roomClass) {
        this.roomClass = roomClass;
    }

    public List<Image> getImgPaths() {
        return imgPaths;
    }

    public void setImgPaths(List<Image> imgPaths) {
        this.imgPaths = imgPaths;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "RoomType{" +
                "roomClass=" + roomClass.name() +
                '}';
    }

    public enum RoomClass{
        Standard(1), Deluxe(2), Suite(3);

        int value;

        RoomClass(int num) {
            value = num;
        }

        public int getValue() {
            return value;
        }

        public static RoomClass getRoomClass(int num){
            if (num == 3)
                return Suite;
            else if (num == 2)
                return Deluxe;
            return Standard;
        }
    }
}
