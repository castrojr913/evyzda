package esvyda.markwiliams.model.data.entity;

import java.io.Serializable;
import java.util.Calendar;

import esvyda.markwiliams.helper.DateHelper;
import esvyda.markwiliams.helper.StringHelper;

/**
 * Created: 3/4/18.
 * Author: jesus.castro
 */

public class PatientEntity implements Serializable {

    private int id;
    private String phone;
    private String name;
    private TimeEntity birthday;

    //<editor-fold desc="Getter / Setter">

    public int getId() {
        return id;
    }

    public Calendar getBirthday() {
        return DateHelper.stringToDate(birthday.getDate(), DateHelper.TIME_YYYMMDD_HHMM);
    }

    public String getPhone() {
        return StringHelper.nullToEmpty(phone);
    }

    public String getName() {
        return StringHelper.nullToEmpty(name);
    }

    //</editor-fold>

}