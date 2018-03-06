package esvyda.markwiliams.controller.appointment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import esvyda.markwiliams.R;
import esvyda.markwiliams.helper.DateHelper;
import esvyda.markwiliams.model.data.entity.AppointmentEntity;

/**
 * Created: 3/4/18.
 * Author: jesus.castro
 */

public class AppointmentSearchAdapter extends RecyclerView.Adapter<AppointmentSearchAdapter.ViewHolder> {

    private static final int RES_LAYOUT = R.layout.view_items_data;
    private static final int RES_ICON_DATE = android.R.drawable.ic_lock_idle_alarm;

    //<editor-fold desc="Variables">

    private List<AppointmentEntity> items;

    //</editor-fold>

    AppointmentSearchAdapter(List<AppointmentEntity> items) {
        this.items = items;
    }

    AppointmentEntity getItem(int position) {
        return items.get(position);
    }

    //<editor-fold desc="Adapter Overrides">

    // Create new views (invoked by the layout manager)
    @Override
    public AppointmentSearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(RES_LAYOUT, parent, false);
        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AppointmentEntity dto = items.get(position);
        holder.patientTextView.setText(dto.getPatientName());
        holder.dateTextView.setText(String.format("%s   %s",
                DateHelper.calendarToString(dto.getTime(), DateHelper.TIME_HHMMZ),
                DateHelper.calendarToString(dto.getTime(), DateHelper.TIME_YYYYMMDD)));
        holder.dateTextView.setCompoundDrawablesWithIntrinsicBounds(RES_ICON_DATE, 0, 0, 0);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    //</editor-fold>

    // <editor-fold desc="View Holder">

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView patientTextView;
        TextView dateTextView;

        ViewHolder(View view) {
            super(view);
            patientTextView = view.findViewById(R.id.view_item_data_title);
            dateTextView = view.findViewById(R.id.view_item_data_subtitle);
        }

    }

    //</editor-fold>

}
