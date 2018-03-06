package esvyda.markwiliams.model.api;

/**
 * Created: 3/5/18.
 * Author: jesus.castro
 */

public final class ApiUrl
{

    // Para probar Timeouts: cambiar la URL rai por IPs No Ruteables:
    // 10.0.0.0 , 10.255.255.255, 172.16.0.0, 172.31.255.255, 192.168.0.0 ,192.168.255.255
    // Ver contenido de una Request:  "https://httpbin.org/{metodo}" (ej: post, get, put, delete)

    private static final String BASE = "http://www.ingjesuscastro.com/EvyzdaRestApi/app.php/api";

    public static final String APPOINTMENT = BASE + "/appointment";
    public static final String APPOINTMENT_SEARCH = APPOINTMENT + "/search";
    public static final String APPOINTMENT_EDIT = APPOINTMENT + "/%s";

    public static final String PATIENT = BASE + "/patient";
    public static final String PATIENT_SEARCH = PATIENT + "/search";
    public static final String PATIENT_EDIT = PATIENT + "/%s";

}