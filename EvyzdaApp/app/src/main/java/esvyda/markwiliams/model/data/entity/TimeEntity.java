package esvyda.markwiliams.model.data.entity;

import java.io.Serializable;

import esvyda.markwiliams.helper.StringHelper;

/**
 * Created: 3/6/18.
 * Author: jesus.castro
 */

class TimeEntity implements Serializable {

    private String date;

    //<editor-fold desc="Getter / Setter">

    public String getDate()
    {
        return StringHelper.nullToEmpty(date);
    }

    //</editor-fold>

}
