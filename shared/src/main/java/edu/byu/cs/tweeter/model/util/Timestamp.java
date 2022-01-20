package edu.byu.cs.tweeter.model.util;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Timestamp {
    private static final String COMPARE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    private static final String DISPLAY_FORMAT = "EEE LLL dd HH:mm:ss z YYYY";
    private static final DateTimeFormatter COMPARE_FORMATTER = DateTimeFormatter.ofPattern(COMPARE_FORMAT);
    private static final DateTimeFormatter DISPLAY_FORMATTER = DateTimeFormatter.ofPattern(DISPLAY_FORMAT);

    public static String getDatetime() {
        return ZonedDateTime.now().format(COMPARE_FORMATTER);
    }

    public static String getDisplayString(String compareString) {
        return longToDisplayString(stringToLong(compareString));
    }

    private static long stringToLong(String datetimeString) {
        LocalDateTime localDateTime = LocalDateTime.parse(datetimeString,COMPARE_FORMATTER);
        return localDateTime.toEpochSecond(ZonedDateTime.now().getOffset());
    }

    private static String longToCompareString(long time) {
        return longToString(time, COMPARE_FORMATTER);
    }

    private static String longToDisplayString(long time) {
        return longToString(time, DISPLAY_FORMATTER);
    }

    private static String longToString(long time, DateTimeFormatter formatter) {
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(time, 0, zonedDateTime.getOffset());
        zonedDateTime = ZonedDateTime.of(localDateTime, zonedDateTime.getZone());

        return zonedDateTime.format(formatter);
    }

    public static boolean isWithinRange(String startTime, String endTime, long duration) {
        long start = stringToLong(startTime);
        long end = stringToLong(endTime);

        if (start > end) throw new IllegalArgumentException("End time must be later than start time.");

        return (end - start) < duration;
    }

    public static long getDuration(int hours) {
        return getDuration(hours, 0);
    }

    public static long getDuration(int hours, int minutes) {
        return getDuration(hours, minutes, 0);
    }

    public static long getDuration(int hours, int minutes, int seconds) {
        return 60L * (60L * hours + minutes) + seconds;
    }

    public static void main(String[] args) {

        System.out.println("Hello world");
        String zonedTimeCompare = Timestamp.getDatetime();
        String zonedTimeDisplay = Timestamp.getDisplayString(zonedTimeCompare);

        System.out.println("Compare: " + zonedTimeCompare);
        System.out.println("Display: " + zonedTimeDisplay);

        System.out.println();
        long date1 = Timestamp.stringToLong(zonedTimeCompare);
        long date2 = date1 + getDuration(3, 20);
        System.out.println("Compare long 1: " + date1);
        System.out.println("Compare long 2: " + date2);
        System.out.println("Compare1 " + longToCompareString(date1));
        System.out.println("Compare2 " + longToCompareString(date2));

        System.out.println();
        System.out.println("Display1 " + longToDisplayString(date1));
        System.out.println("Display2 " + longToDisplayString(date2));

        long durationShort = getDuration(3);
        long durationLong = getDuration(3, 30);
        System.out.println("Duration " + durationShort + ": " + Timestamp.isWithinRange(zonedTimeCompare, longToCompareString(date2), durationShort));
        System.out.println("Duration " + durationLong + ": " + Timestamp.isWithinRange(zonedTimeCompare, longToCompareString(date2), durationLong));
    }
}
