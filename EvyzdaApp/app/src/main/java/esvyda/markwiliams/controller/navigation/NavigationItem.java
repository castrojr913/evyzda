package esvyda.markwiliams.controller.navigation;

/**
 * Created: 3/5/18.
 * Author: jesus.castro
 */

public enum NavigationItem {

    APPOINTMENT {
        @Override
        public int getId() {
            return 1;
        }
    },
    PATIENT {
        @Override
        public int getId() {
            return 2;
        }
    };

    public abstract int getId();

}
