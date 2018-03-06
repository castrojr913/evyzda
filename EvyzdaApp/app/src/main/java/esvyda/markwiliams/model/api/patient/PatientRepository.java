package esvyda.markwiliams.model.api.patient;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import esvyda.markwiliams.model.api.ApiRepository;
import esvyda.markwiliams.model.api.ApiUrl;
import esvyda.markwiliams.model.api.OnApiListener;
import esvyda.markwiliams.model.api.WsError;
import esvyda.markwiliams.model.data.entity.PatientEntity;
import esvyda.markwiliams.model.data.form.PatientFormData;

import static esvyda.markwiliams.model.api.http.EHttpRequest.DELETE;
import static esvyda.markwiliams.model.api.http.EHttpRequest.GET;
import static esvyda.markwiliams.model.api.http.EHttpRequest.POST;
import static esvyda.markwiliams.model.api.http.EHttpRequest.PUT;

/**
 * Created: 3/4/18.
 * Author: jesus.castro
 */

public class PatientRepository extends ApiRepository {

    //<editor-fold desc="Singleton">

    private static PatientRepository singleton;

    private PatientRepository(){
        // Blank
    }

    public static PatientRepository getInstance() {
        if (singleton == null) {
            singleton = new PatientRepository();
        }
        return singleton;
    }

    //</editor-fold>

    @Override
    protected Class<?> getLogTag() {
        return PatientRepository.class;
    }

    //<editor-fold desc="Response">

    @Override
    protected void manageResponse(String url, byte[] response, OnApiListener listener) {
        if (url.contains(ApiUrl.PATIENT_SEARCH)) {
            Type type = new TypeToken<List<PatientEntity>>() {}.getType();
            List<PatientEntity> entities = jsonParser.fromJson(new String(response), type);
            ((OnPatientListener) listener).onPatients(entities);
        } else if (url.contains(ApiUrl.PATIENT)) {
            listener.onSuccess();
        } else {
            listener.onError(WsError.UNKNOWN);
        }
    }

    //</editor-fold>

    //<editor-fold desc="Request">

    public void getPatients(OnPatientListener listener) {
        sendRequest(GET, ApiUrl.PATIENT_SEARCH, NONE, NONE, listener);
    }

    public void registerPatient(boolean isNew, PatientFormData data, OnApiListener listener) {
        String url = isNew ? ApiUrl.PATIENT : String.format(ApiUrl.PATIENT_EDIT, data.getId());
        Map<String, String> params = new HashMap<>();
        params.put("name", data.getName());
        params.put("birthdate", data.getBirthday());
        params.put("phone", data.getPhone());
        sendRequest(isNew ? POST : PUT, url, params, NONE, listener);
    }

     //</editor-fold>

}
