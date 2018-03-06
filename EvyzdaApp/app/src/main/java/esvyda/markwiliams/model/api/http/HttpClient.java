package esvyda.markwiliams.model.api.http;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created: 3/4/18.
 * Author: jesus.castro
 */

public class HttpClient {

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private final OkHttpClient client;
    private Gson gson = new Gson();

    //<editor-fold desc="Singleton">

    private static HttpClient singleton;

    private HttpClient() {
        client = new OkHttpClient();
    }

    public static HttpClient getInstance() {
        if (singleton == null) {
            singleton = new HttpClient();
        }
        return singleton;
    }

    //</editor-fold>

    //<editor-fold desc="Header">

    private Headers buildHeaders(@NonNull Map<String, String> headers) {
        Headers.Builder builder = new Headers.Builder();
        Set<String> keys = headers.keySet();
        for (String key : keys) {
            builder.add(key, headers.get(key));
        }
        return builder.build();
    }

    private Map<String, String> createDefaultHeader() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        return headers;
    }

    //</editor-fold>

    private String buildUrlParameters(Map<String, String> params) {
        String string = "?";
        Set<String> keys = params.keySet();
        for (String key : keys) {
            string += String.format("%s=%s&", key, params.get(key));
        }
        return string.substring(0, string.lastIndexOf("&"));
    }

    //<editor-fold desc="Requests">

    public void get(@NonNull String url, Map<String, String> parameters,
                    @NonNull Map<String, String> headers, @NonNull Callback callback) {
        Request.Builder builder = new Request.Builder();
        builder.url(url + (parameters != null && parameters.size() != 0 ? buildUrlParameters(parameters) : ""));
        builder.headers(buildHeaders(headers));
        Request request = builder.build();
        client.newCall(request).enqueue(callback);
    }

    public void post(@NonNull String url, Map<String, String> parameters, @NonNull Callback callback) {
        RequestBody body = RequestBody.create(JSON, gson.toJson(parameters));
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
         client.newCall(request).enqueue(callback);
    }

    public void put(@NonNull String url, Map<String, String> parameters, @NonNull Callback callback) {
        RequestBody body = RequestBody.create(JSON, gson.toJson(parameters));
        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .build();
        client.newCall(request).enqueue(callback);
    }

    //</editor-fold>

}
