package esvyda.markwiliams.ui;

/**
 * Created: 3/5/18.
 * Author: jesus.castro
 */

public class SpinnerItem {

    private Object id;
    private String description;

    public SpinnerItem(Object id, String description) {
        this.id = id;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public Object getId() {
        return id;
    }

    @Override
    public String toString() {
        return description;
    }

}
