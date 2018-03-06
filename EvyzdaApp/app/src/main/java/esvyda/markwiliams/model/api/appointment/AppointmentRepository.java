package esvyda.markwiliams.model.api.appointment;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import esvyda.markwiliams.model.api.ApiRepository;
import esvyda.markwiliams.model.api.ApiUrl;
import esvyda.markwiliams.model.api.OnApiListener;
import esvyda.markwiliams.model.api.WsError;
import esvyda.markwiliams.model.data.entity.AppointmentEntity;
import esvyda.markwiliams.model.data.form.AppointmentFormData;

import static esvyda.markwiliams.model.api.http.EHttpRequest.GET;
import static esvyda.markwiliams.model.api.http.EHttpRequest.POST;
import static esvyda.markwiliams.model.api.http.EHttpRequest.PUT;

public class AppointmentRepository extends ApiRepository {

    //<editor-fold desc="Singleton">

    private static AppointmentRepository singleton;

    private AppointmentRepository() {
        // Blank
    }

    public static AppointmentRepository getInstance() {
        if (singleton == null) {
            singleton = new AppointmentRepository();
        }
        return singleton;
    }

    //</editor-fold>

    @Override
    protected Class<?> getLogTag() {
        return AppointmentRepository.class;
    }

    //<editor-fold desc="Response">

    @Override
    protected void manageResponse(String url, byte[] response, OnApiListener listener) {
        if (url.contains(ApiUrl.APPOINTMENT_SEARCH)) {
            Type type = new TypeToken<List<AppointmentEntity>>() {}.getType();
            List<AppointmentEntity> entities = jsonParser.fromJson(new String(response), type);
            ((OnAppointmentListener) listener).onAppointments(entities);
        } else if (url.contains(ApiUrl.APPOINTMENT)) {
            listener.onSuccess();
        } else {
            listener.onError(WsError.UNKNOWN);
        }
    }

    //</editor-fold>

    //<editor-fold desc="Request">

    public void getAppointments(OnAppointmentListener listener) {
        sendRequest(GET, ApiUrl.APPOINTMENT_SEARCH, NONE, NONE, listener);
    }

    public void registerAppointment(boolean isNew, AppointmentFormData data, OnApiListener listener) {
        String url = isNew ? ApiUrl.APPOINTMENT : String.format(ApiUrl.APPOINTMENT_EDIT, data.getId());
        Map<String, String> params = new HashMap<>();
        params.put("description", data.getDescription());
        params.put("time", data.getTimeStamp());
        params.put("category", "" + data.getCategoryId().getId());
        params.put("patientId", "" + data.getPatientId());
        sendRequest(isNew ? POST : PUT, url, params, NONE, listener);
    }

    //</editor-fold>

}
