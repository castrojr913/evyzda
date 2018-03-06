package esvyda.markwiliams.helper;

/**
 * Created: 3/4/18.
 * Author: jesus.castro
 */

public class StringHelper {

    public static boolean isEmpty(String string) {
        return string == null || string.isEmpty();
    }

    public static String nullToEmpty(String text) {
        return isEmpty(text) ? "" : text;
    }

}
