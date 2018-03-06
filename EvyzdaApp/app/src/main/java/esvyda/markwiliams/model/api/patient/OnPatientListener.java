package esvyda.markwiliams.model.api.patient;

import java.util.List;

import esvyda.markwiliams.model.api.OnApiListener;
import esvyda.markwiliams.model.data.entity.PatientEntity;

/**
 * Created: 3/4/18.
 * Author: jesus.castro
 */

public interface OnPatientListener extends OnApiListener {

    void onPatients(List<PatientEntity> patients);

}
