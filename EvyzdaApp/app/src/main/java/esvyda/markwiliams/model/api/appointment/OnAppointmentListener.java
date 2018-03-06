package esvyda.markwiliams.model.api.appointment;

import java.util.List;

import esvyda.markwiliams.model.api.OnApiListener;
import esvyda.markwiliams.model.data.entity.AppointmentEntity;

/**
 * Created: 3/4/18.
 * Author: jesus.castro
 */

public interface OnAppointmentListener extends OnApiListener {

    void onAppointments(List<AppointmentEntity> appointments);

}
