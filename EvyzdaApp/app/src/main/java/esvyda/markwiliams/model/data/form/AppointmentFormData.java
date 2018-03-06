package esvyda.markwiliams.model.data.form;

import esvyda.markwiliams.model.data.enums.EAppointmentCategory;

/**
 * Created: 3/6/18.
 * Author: jesus.castro
 */

public class AppointmentFormData {

    private int id;
    private String description;
    private String time;
    private String date;
    private int patientId;
    private EAppointmentCategory categoryId;

    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public void setCategoryId(EAppointmentCategory categoryId) {
        this.categoryId = categoryId;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public int getPatientId() {
        return patientId;
    }

    public EAppointmentCategory getCategoryId() {
        return categoryId;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getTimeStamp() {
        return String.format("%s %s", date, time);
    }

}
