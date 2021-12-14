package com.epam.courses.java.final_project.model;

import com.epam.courses.java.final_project.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.epam.courses.java.final_project.util.constant.Constant.LOG_TRACE;

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

    private static final Logger log = LogManager.getLogger(LOG_TRACE);

    private long id;
    private int capacity;
    private String bedsType;  // S - single, D - double. String in form ("1D, 2S") etc...
    private RoomClass roomClass;
    private String descriptionPath;
    private HashMap<String, String> descriptions;  // <lang -> description>
    private String[] amenities;
    private List<Image> imgPaths;
    private String lang = "en";

    public RoomType(long id, int capacity, String bedsType, RoomClass roomClass, String description) {
        descriptions = new HashMap<>();
        this.id = id;
        this.capacity = capacity;
        this.bedsType = bedsType;
        this.roomClass = roomClass;
        descriptionPath = description;
        for (String lang : List.of("en", "ru")) {
            String text = new BufferedReader(
                    new InputStreamReader(
                            Util.CTX.getResourceAsStream("/description/desc_01_" + lang + ".txt"), StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));
            descriptions.put(lang, text);
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
     * if EN localisation
     */
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

    /**
     * Beds type parser, that was described in class documentation.
     * Parse string of type [1S, 1T] into [1 single bed and 1 twin beds]
     * if RU localisation (separate function because of jsp usage)
     */
    public String getParsedBedsTypeRU() {
        StringBuilder sb = new StringBuilder();
        Matcher match = Pattern.compile("\\d[SDT]").matcher(bedsType);

        while (match.find()) {
            if (sb.length() > 0)
                sb.append(" \u0438 ");
            String type = match.group();
            String bed = " \u043A\u0440\u043E\u0432\u0430\u0442\u044C";

            if (type.charAt(0) != '1') {
                sb.append(type.charAt(0)).append(" ");
                bed = " \u043A\u0440\u043E\u0432\u0430\u0442\u0438";
            }
            switch (type.substring(1)) {
                case "S":
                    sb.append("\u043E\u0434\u043D\u043E\u043C\u0435\u0441\u0442\u043D\u0430\u044F");
                    break;
                case "D":
                    sb.append("\u0434\u0432\u0443\u0441\u043F\u0430\u043B\u044C\u043D\u0430\u044F");
                    break;
                case "T":
                    sb.append("\u0434\u0432\u043E\u0439\u043D\u0430\u044F");
                    break;
            }
            if (type.charAt(0) != '1') {
                sb.replace(sb.length() - 2, sb.length(), "\u044B\u0435");
                bed = " \u043A\u0440\u043E\u0432\u0430\u0442\u0438";
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

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        if (lang == null)
            return;
        this.lang = lang.substring(0, 2);
    }

    public String getDescriptionOnLang() {
        if (descriptionPath.equals("none"))
            return "";
        generateAmenities(lang);
        return descriptions.get(lang);
    }

    public String getDescription() {
        return descriptionPath;
    }

    public void setDescription(String descriptionPath) {
        this.descriptionPath = descriptionPath;
    }

    public String[] getAmenities() {
        return amenities;
    }

    private void generateAmenities(String lang) {
        String src = descriptions.get(lang);
        int amenIndex = src.indexOf("AMENITIES");

        if (amenIndex == -1) {
            amenities = null;
        } else {
            descriptions.put(lang, src.substring(0, amenIndex));
            amenities = src.substring(amenIndex + 10).split(" \\| ");
        }
    }

    //    public String[] getAmenities() {
//        return amenities;
//    }
//
//    public void setAmenities(String[] amenities) {
//        this.amenities = amenities;
//    }

    @Override
    public String toString() {
        return "RoomType{" +
                "id=" + id +
                ", capacity=" + capacity +
                ", bedsType='" + bedsType + '\'' +
                ", roomClass=" + roomClass +
                ", description='" + descriptionPath + '\'' +
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
     */
    public enum RoomClass {
        Standard(1), Upgraded(2), Deluxe(3), Suite(4);

        int value;

        RoomClass(int num) {
            value = num;
        }

        public int getValue() {
            return value;
        }

        public String getLcName() {  // get lowercase name
            return name().toLowerCase(Locale.ROOT);
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
