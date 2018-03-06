package esvyda.markwiliams.helper;

import android.app.Activity;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;
import java.util.List;

import esvyda.markwiliams.ui.SpinnerAdapter;
import esvyda.markwiliams.ui.SpinnerItem;

/**
 * Created: 3/5/18.
 * Author: jesus.castro
 */

public class ViewHelper {

    public static SpinnerAdapter<SpinnerItem> buildSpinnerAdapter(Activity activity, List<SpinnerItem> items) {
        SpinnerItem[] array = new SpinnerItem[items.size()];
        return new SpinnerAdapter<>(activity, items.toArray(array));
    }

    public static void showTimePicker(Activity activity, String time, TimePickerDialog.OnTimeSetListener listener) {
        TimePickerDialog picker = TimePickerDialog.newInstance(listener, false);
        Calendar cal = DateHelper.stringToDate(time, DateHelper.TIME_HHMMZ);
        if (cal != null) {
            picker.setInitialSelection(cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE));
        }
        picker.show(activity.getFragmentManager(), activity.getLocalClassName());
    }

    public static void showDatePicker(Activity activity, String date, DatePickerDialog.OnDateSetListener listener) {
        Calendar cal = DateHelper.stringToDate(date, DateHelper.TIME_YYYYMMDD);
        DatePickerDialog picker = cal != null ?
                DatePickerDialog.newInstance(listener, cal) :
                DatePickerDialog.newInstance(listener);
        picker.show(activity.getFragmentManager(), activity.getLocalClassName());
    }

}
