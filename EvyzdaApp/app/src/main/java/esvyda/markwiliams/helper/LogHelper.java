package esvyda.markwiliams.helper;

import android.annotation.SuppressLint;
import android.util.Log;

/**
 * Created: 3/4/18.
 * Author: jesus.castro
 */

public class LogHelper {

    private String LOG_LINE = "_____________________";

    //<editor-fold desc="Singleton">

    private static LogHelper singleton;

    private LogHelper() {
        // blank
    }

    public static LogHelper getInstance() {
        if (singleton == null) {
            singleton = new LogHelper();
        }
        return singleton;
    }

    //</editor-fold">

    //<editor-fold desc="Console">

    public void debug(Class<?> sourceClass, String message) {
        Log.d(sourceClass.getSimpleName(), message);
    }

    public void exception(Class<?> sourceClass, Throwable exception) {
        Log.e(sourceClass.getSimpleName(), exception.toString());
    }

    //</editor-fold>

    //<editor-fold desc="Http Requests">

    @SuppressLint("DefaultLocale")
    private String buildResponseMessage(String url, String message, int statusCode, byte[] responseBody) {
        String body = responseBody != null ? new String(responseBody) : "";
        return String.format("\n%s " +
                "\n- URL:%s " +
                "\n- STATUS_CODE:%d " +
                "\n- BODY:'%s' \n" + LOG_LINE, message, url, statusCode, body);
    }

    private String buildRequestMessage(String url, String message) {
        return String.format("\n%s " + "\n- URL:%s \n" + LOG_LINE, message, url);
    }

    public void debugRequest(Class<?> sourceClass, String url, String message) {
        Log.d(sourceClass.getSimpleName(), buildRequestMessage(url, message));
    }

    public void debugResponse(Class<?> sourceClass, String url, String message, int statusCode, byte[] responseBody) {
        Log.d(sourceClass.getSimpleName(), buildResponseMessage(url, message, statusCode, responseBody));
    }

    public void errorResponse(Class<?> sourceClass, String url, String message, int statusCode, byte[] responseBody) {
        Log.e(sourceClass.getSimpleName(), buildResponseMessage(url, message, statusCode, responseBody));
    }

    //</editor-fold>

}
