package esvyda.markwiliams.model.data.form;

import esvyda.markwiliams.model.data.enums.EAppointmentCategory;

/**
 * Created: 3/6/18.
 * Author: jesus.castro
 */

public class PatientFormData {

    private int id;
    private String name;
    private String birthday;
    private String phone;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
