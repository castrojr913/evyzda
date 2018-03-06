package esvyda.markwiliams.model.data.entity;

import java.io.Serializable;
import java.util.Calendar;

import esvyda.markwiliams.helper.DateHelper;
import esvyda.markwiliams.helper.StringHelper;
import esvyda.markwiliams.model.data.enums.EAppointmentCategory;

import static esvyda.markwiliams.model.data.enums.EAppointmentCategory.CHECK;
import static esvyda.markwiliams.model.data.enums.EAppointmentCategory.FIRST;

/**
 * Created: 3/4/18.
 * Author: jesus.castro
 */

public class AppointmentEntity implements Serializable {

    private int id;
    private int category;
    private String description;
    private TimeEntity time;
    private PatientEntity patient;

    //<editor-fold desc="Getter / Setter">

    public int getId() {
        return id;
    }

    public EAppointmentCategory getCategory() {
        return category == CHECK.getId() ? CHECK : FIRST;
    }

    public Calendar getTime() {
        return DateHelper.stringToDate(time.getDate(), DateHelper.TIME_YYYMMDD_HHMM);
    }

    public int getPatientId() {
        return patient != null ? patient.getId() : 0;
    }

    public String getPatientName() {
        return patient != null ? patient.getName() : "";
    }

    public String getDescription() {
        return StringHelper.nullToEmpty(description);
    }

    //</editor-fold>

}
