package esvyda.markwiliams.model.api.http;

import android.util.Log;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * OkHttp Callback in order to cancel pending requests
 *
 * http://stackoverflow.com/questions/23269806/perform-requests-with-retrofit-inside-custom-runnable/23271559#23271559
 *
 * Created: 3/4/18.
 * Author: jesus.castro
 */
public abstract class OkHttpCancelableCallback implements Callback {

    private static final String LOG_TAG = OkHttpCancelableCallback.class.getSimpleName();

    private static List<OkHttpCancelableCallback> mList = new ArrayList<>();
    private boolean isCanceled = false;
    private Object mTag = null;

    //<editor-fold desc="Constructors">

    public OkHttpCancelableCallback() {
        mList.add(this);
    }

    public OkHttpCancelableCallback(Object tag) {
        mTag = tag;
        mList.add(this);
    }

    //</editor-fold>

    //<editor-fold desc="Abstract Methods">

    public abstract void onSuccess(Response response) throws IOException;

    public abstract void onError(Request request, IOException e);

    // </editor-fold>

    // <editor-fold desc="Overrides">

    @Override
    public void onFailure(Request request, IOException e) {
        if (!isCanceled) onError(request, e);
        mList.remove(this);
    }

    @Override
    public void onResponse(Response response) throws IOException {
        if (!isCanceled) onSuccess(response);
        mList.remove(this);
    }

    // </editor-fold>

    public static void cancelAll() {
        Log.d(LOG_TAG, "Cancelling all Http requests ... ");
        for (OkHttpCancelableCallback callback : mList) {
            callback.cancel();
        }
    }

    public static void cancel(Object tag) {
        if (tag != null)
            for (OkHttpCancelableCallback callback : mList) {
                if (tag.equals(callback.mTag)) {
                    Log.d(LOG_TAG, "Cancelling Http request ... " + tag);
                    callback.cancel();
                }
            }
    }

    public void cancel() {
        isCanceled = true;
        mList.remove(this);
    }

}