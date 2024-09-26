package com.backend.feedservice.util;

import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

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

}
