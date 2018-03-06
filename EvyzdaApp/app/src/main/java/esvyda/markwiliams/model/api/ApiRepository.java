package esvyda.markwiliams.model.api;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import esvyda.markwiliams.helper.LogHelper;
import esvyda.markwiliams.model.api.http.OkHttpCancelableCallback;
import esvyda.markwiliams.model.api.http.EHttpRequest;
import esvyda.markwiliams.model.api.http.HttpClient;

/**
 * Created: 3/4/18.
 * Author: jesus.castro
 */
public abstract class ApiRepository {

    protected Map NONE = Collections.EMPTY_MAP;
    protected Gson jsonParser = new Gson();

    //<editor-fold desc="Abstract Methods">

    protected abstract Class<?> getLogTag();

    protected abstract void manageResponse(String url, byte[] response, OnApiListener listener);

    //</editor-fold>

    //<editor-fold desc="Request">

    protected void sendRequest(EHttpRequest request, String url,
                               @NonNull Map<String, String> parameters,
                               @NonNull Map<String, String> headers,
                               @NonNull OnApiListener listener) {
        String message = String.format("Headers: %s\n, Params:%s", headers.toString(), parameters.toString());
        LogHelper.getInstance().debugRequest(getLogTag(), url, message);
        if (request == EHttpRequest.GET)
            HttpClient.getInstance().get(url, parameters, headers, createHttpCallback(url, listener));
        if (request == EHttpRequest.POST)
            HttpClient.getInstance().post(url, parameters, createHttpCallback(url, listener));
        if (request == EHttpRequest.PUT)
            HttpClient.getInstance().put(url, parameters, createHttpCallback(url, listener));
    }

    //</editor-fold>

    //<editor-fold desc="Response Handling">

    private Callback createHttpCallback(final String url, final OnApiListener listener) {
        final Class<?> logTag = getLogTag();
        return new OkHttpCancelableCallback(ApiRepository.class.getName()) {

            @Override
            public void onError(Request request, IOException e) {
                WsError error;
                String exception = e.toString();
                if (exception.contains("ConnectException") || exception.contains("UnknownHostException")) {
                    error = WsError.CONNECTIVITY_FAILURE;
                } else if (exception.contains("SocketTimeoutException")) {
                    error = WsError.TIMEOUT_FAILURE;
                } else {
                    error = WsError.WEBSERVICE_FAILURE;
                }
                LogHelper.getInstance().exception(logTag, e);
                listener.onError(error);
            }

            @Override
            public void onSuccess(Response response) throws IOException {
                String requestType = response.request().method();
                byte[] responseStream = response.body().bytes();
                if (response.isSuccessful()) { // status code: [200, 300)
                    // Delegate the concrete manager how it should manage this successful response
                    LogHelper.getInstance().debugResponse(logTag, url, requestType, response.code(), responseStream);
                    manageResponse(url, responseStream, listener);
                } else {
                    LogHelper.getInstance().errorResponse(logTag, url, requestType, response.code(), responseStream);
                    listener.onError(response.code() == 400 ? WsError.BAD_REQUEST_FAILURE : WsError.WEBSERVICE_FAILURE);
                }
            }

        };
    }

    //</editor-fold>

}