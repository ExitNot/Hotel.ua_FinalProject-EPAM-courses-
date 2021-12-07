package com.epam.courses.java.final_project.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The {@code RoomType} class represent corresponding entity from database.
 * RoomType entity must have room class, which represent level of comfortable of this room.
 * <p>
 * ~ {@code Standard} represent the most basic type of room offered by the hotel.
 * <p>
 * ~ {@code Upgraded} represent room step above standard.
 * <p>
 * ~ {@code Deluxe} Moderate and superior rooms, deluxe accommodations combine a desirable view
 * with a number of luxurious amenities.
 * <p>
 * ~ {@code Suite} offers a separate rooms. Suit for families.
 * <p>
 * Also each room have beds type. This type is storing in db as
 * string of type [1T, 2S, 1D] => (1 twin bed, 2 single beds, 1 double bed).
 * This string is being parsed inside this class in to sentence useful for web.
 *
 * @author Kostiantyn Kolchenko
 */
public class RoomType {

    private long id;
    private int capacity;
    private String bedsType;  // S - single, D - double. String in form ("1D, 2S") etc...
    private RoomClass roomClass;
    private String description;
    private String[] amenities;
    private List<Image> imgPaths;

    public RoomType(long id, int capacity, String bedsType, RoomClass roomClass, String description) {
        this.id = id;
        this.capacity = capacity;
        this.bedsType = bedsType;
        this.roomClass = roomClass;
        int amenIndex = description.indexOf("AMENITIES");
        if (amenIndex == -1) {
            this.description = description;
            amenities = null;
        } else {
            this.description = description.substring(0, amenIndex);
            amenities = description.substring(amenIndex + 10).split(" \\| ");
        }

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

    /**
     * Beds type parser, that was described in class documentation.
     * Parse string of type [1S, 1T] into [1 single bed and 1 twin beds]
     * */
    public String getParsedBedsType() {
        StringBuilder sb = new StringBuilder();
        Matcher match = Pattern.compile("\\d[SDT]").matcher(bedsType);

        while (match.find()) {
            if (sb.length() > 0)
                sb.append(" and ");
            String type = match.group();
            String bed = " bed";

            if (type.charAt(0) != '1') {
                sb.append(type.charAt(0)).append(" ");
                bed = bed + "s";
            } else if (type.substring(1).equals("T")) {
                bed = bed + "s";
            }
            switch (type.substring(1)) {
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

    public String getAmountOfImg() {
        return String.valueOf(imgPaths.size() - 1);
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

    public String[] getAmenities() {
        return amenities;
    }

    public void setAmenities(String[] amenities) {
        this.amenities = amenities;
    }

    @Override
    public String toString() {
        return "RoomType{" +
                "id=" + id +
                ", capacity=" + capacity +
                ", bedsType='" + bedsType + '\'' +
                ", roomClass=" + roomClass +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomType roomType = (RoomType) o;
        return id == roomType.id && capacity == roomType.capacity && bedsType.equals(roomType.bedsType) && roomClass == roomType.roomClass;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, capacity, bedsType, roomClass);
    }

    /**
     * Enum for room class. Have special getter for getting corresponding value from db.
     * */
    public enum RoomClass {
        Standard(1), Upgraded(2), Deluxe(3), Suite(4);

        int value;

        RoomClass(int num) {
            value = num;
        }

        public int getValue() {
            return value;
        }

        public static RoomClass getRoomClass(int num) {
            if (num == 4)
                return Suite;
            if (num == 3)
                return Deluxe;
            else if (num == 2)
                return Upgraded;
            return Standard;
        }
    }
}
