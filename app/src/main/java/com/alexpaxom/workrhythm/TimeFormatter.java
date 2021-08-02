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
        return "Days: "+elapsed_days.toString() + " Hours:" +df.format(elapsedTime%convertMillisecondsToDays);
    }
}
