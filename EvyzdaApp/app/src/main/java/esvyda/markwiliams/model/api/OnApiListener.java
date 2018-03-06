package esvyda.markwiliams.model.api;

/**
 * Created: 3/4/18.
 * Author: jesus.castro
 */

public interface OnApiListener {

    void onSuccess();

    void onError(WsError error);

}
