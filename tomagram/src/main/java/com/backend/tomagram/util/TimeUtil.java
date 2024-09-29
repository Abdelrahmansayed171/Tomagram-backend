package com.backend.tomagram.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class TimeUtil {
    /*
     * Converts LocalDateTime data type into unix timestamp compatible with redis
     *
     * @param datetime to be converted
     * @return Unix Timestamp (seconds since epoch)
     */
    public static long toUnixTimestamp(LocalDateTime dateTime){
        return dateTime.toEpochSecond(ZoneOffset.UTC);
    }

    /*
     * Converts Unix timestamp into LocalDateTime timestamp data type
     *
     * @param datetime in seconds to be converted
     * @return LocalDateTime Timestamp (seconds since epoch)
     */
    public static LocalDateTime fromLocalDateTime(long datetime){
        return LocalDateTime.ofEpochSecond(datetime,0,ZoneOffset.UTC);
    }

    /*
     * Converts String datatype into LocalDateTime data type
     *
     * @param datetime in String LocalDateTime to be converted
     * @return LocalDateTime Timestamp
     */
    public static LocalDateTime fromString(String dateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
        return LocalDateTime.parse(dateTime, formatter);
    }

    /*
     * Converts String datatype into long unix timestamp
     *
     * @param String datetime in LocalDateTime  format to be converted
     * @return long createdAt
     */
    public static long stringToUnixTimestamp(String createdAt){
        LocalDateTime dateTime = TimeUtil.fromString(createdAt);
        return TimeUtil.toUnixTimestamp(dateTime);
    }

    /*
     * Converts LocalDateTime into a String format without 'T' between date and time
     * Example format: "yyyy-MM-dd HH:mm:ss.SSSSSS"
     *
     * @param dateTime the LocalDateTime to be formatted
     * @return formatted String in the pattern "yyyy-MM-dd HH:mm:ss.SSSSSS"
     */
    public static String toCustomString(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
        return dateTime.format(formatter);
    }
}
