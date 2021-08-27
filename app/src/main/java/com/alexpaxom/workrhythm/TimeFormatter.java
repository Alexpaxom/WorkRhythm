package com.alexpaxom.workrhythm;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class TimeFormatter {
    private static final int convertMillisecondsToDays = 86400000;

    public static String intervalFromTime(Long elapsedTime) {
        DateFormat df = new SimpleDateFormat(" HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("GMT+0"));
        Integer elapsed_days = (int) (elapsedTime / convertMillisecondsToDays);
        return App.getInstance().getString(R.string.time_format_days) + " " + elapsed_days.toString()
                + " " +
                App.getInstance().getString(R.string.time_format_hours) + " " + df.format(elapsedTime%convertMillisecondsToDays);
    }

    public static String intervalGetDays(Long elapsedTime) {
        Integer elapsed_days = (int) (elapsedTime / convertMillisecondsToDays);
        return elapsed_days.toString();
    }

    public static String intervalGetHours(Long elapsedTime) {
        DateFormat df = new SimpleDateFormat(" HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("GMT+0"));

        return df.format(elapsedTime%convertMillisecondsToDays);
    }
}
