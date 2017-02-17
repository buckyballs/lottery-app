package com.oci.util;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by maqsoodi on 1/30/2017.
 */
public class DateTimeUtils {

    public static String calDuration(Date pastDate, Date futureDate) {
        String duration = null;
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
        try {
            DateTime jodapastDate = new DateTime(pastDate);
            DateTime jodaFutureDate = new DateTime(futureDate);
            Period period = new Period(jodapastDate, jodaFutureDate);
            String days = period.getDays() != 0 ? String.valueOf(period.getDays()) + " DAYS, " : "";
            String hours = period.getHours() != 0 ? String.valueOf(period.getHours()) + " HOURS, " : "";
            String minutes = period.getMinutes() != 0 ? String.valueOf(period.getMinutes()) + " MINUTES, " : "";
            String seconds = period.getSeconds() != 0 ? String.valueOf(period.getSeconds()) + " SECONDS " : "";
            duration = days + hours + minutes + seconds;
            return duration;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return duration;
    }

    /**
     * @param date
     * @param duration in milliseconds
     * @return Java Date object
     */
    public static Date plusDuration(Date date, long duration) {
        return new DateTime(date).plus(duration).toDate();
    }

    public static Date minusDuration(Date date, long duration) {
        return new DateTime(date).minus(duration).toDate();
    }

    public static String getDateString(Date date) {
        DateTime dt = new DateTime(date);
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        return fmt.print(dt);
    }
}
