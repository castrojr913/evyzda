package esvyda.markwiliams.ui;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created: 3/5/18.
 * Author: jesus.castro
 */

public class SpinnerAdapter<T extends SpinnerItem> extends ArrayAdapter<T> {

    private static final int RES_LAYOUT = android.R.layout.simple_spinner_dropdown_item;

    private Activity context;
    private T[] data;

    public SpinnerAdapter(Activity context, T[] data)
    {
        super(context, RES_LAYOUT, data);
        this.context = context;
        this.data = data;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent)
    {
        View row = convertView;
        if(row == null)
        {
            LayoutInflater inflater = context.getLayoutInflater();
            row = inflater.inflate(RES_LAYOUT, parent, false);
        }
        T item = data[position];
        if(item != null)
        {
            TextView textView = row.findViewById(android.R.id.text1);
            textView.setText(item.getDescription());
        }
        return row;
    }

}
