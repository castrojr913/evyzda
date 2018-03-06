package esvyda.markwiliams.helper;

import android.support.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created: 3/5/18.
 * Author: jesus.castro
 */

public class DateHelper {

    // Ref: https://stackoverflow.com/questions/4772425/change-date-format-in-a-java-string

    public static final String TIME_YYYYMMDD = "MM/dd/yyyy";
    public static final String TIME_HHMMZ = "h:mm a";
    public static final String TIME_YYYMMDD_HHMM = "yyyy-MM-dd HH:mm:ss.SSS";

    public static String dateToString(@NonNull Date date, @NonNull String format) {
        return new SimpleDateFormat(format, Locale.getDefault()).format(date);
    }

    public static String calendarToString(@NonNull Calendar date, @NonNull String format) {
        return new SimpleDateFormat(format, Locale.getDefault()).format(date.getTime());
    }

    public static Calendar stringToDate(@NonNull String string, @NonNull String format) {
        Calendar result = null;
        try {

            SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.US);
            Date date = formatter.parse(string);
            if (date != null) {
                result = Calendar.getInstance();
                result.setTime(date);
            }
        } catch (ParseException e) {
            LogHelper.getInstance().exception(DateHelper.class, e);
        }
        return result;
    }

    public static String buildTimeText(int hourOfDay, int minute, boolean isAM) {
        int hour = hourOfDay == 0 ? 12 : hourOfDay > 12 ? hourOfDay - 12 : hourOfDay;
        return String.format("%s%s:%s%s %s",
                hour < 10 ? "0" : "", hour,
                minute < 10 ? "0" : "", minute, isAM ? "AM" : "PM");
    }

    public static String buildDateText(int year, int monthOfYear, int dayOfMonth) {
        return String.format("%s%s/%s%s/%s",
                (monthOfYear + 1) < 10 ? "0" : "" , monthOfYear + 1,
                dayOfMonth < 10 ? "0" : "", dayOfMonth, year);
    }

}
