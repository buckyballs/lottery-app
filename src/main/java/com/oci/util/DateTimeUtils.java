package com.oci.util;

import org.joda.time.DateTime;
import org.joda.time.Period;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by maqsoodi on 1/30/2017.
 */
public class DateTimeUtils {

    public static String calDuration(Date pastDate, Date futureDate) {
        String duration = null;
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        try {
            DateTime jodapastDate = new DateTime(pastDate);
            DateTime jodaFutureDate = new DateTime(futureDate);
            Period period = new Period(jodapastDate, jodaFutureDate);
            duration = period.getDays() + " DAYS: " + period.getHours() + " HOURS: " + period.getMinutes() + " MINUTES: " + period.getSeconds() + " SECONDS: ";
            return duration;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return duration;
    }

    public static Date plusDuration(Date date, long duration) {
        return new DateTime(date).plus(duration).toDate();
    }

    public static Date minusDuration(Date date, long duration) {
        return new DateTime(date).minus(duration).toDate();
    }
}
