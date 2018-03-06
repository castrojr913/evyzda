package esvyda.markwiliams.model.data.enums;

/**
 * Created: 3/5/18.
 * Author: jesus.castro
 */

public enum EAppointmentCategory {

    FIRST {
        @Override
        public int getId() {
            return 1;
        }
    },
    CHECK {
        @Override
        public int getId() {
            return 2;
        }
    };

    public abstract int getId();

}
