package esvyda.markwiliams.controller.patient;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import esvyda.markwiliams.R;
import esvyda.markwiliams.model.data.entity.PatientEntity;

/**
 * Created: 3/4/18.
 * Author: jesus.castro
 */

public class PatientSearchAdapter extends RecyclerView.Adapter<PatientSearchAdapter.ViewHolder> {

    private static final int RES_LAYOUT = R.layout.view_items_data;
    private static final int RES_ICON = android.R.drawable.ic_menu_view;

    //<editor-fold desc="Variables">

    private List<PatientEntity> items;

    //</editor-fold>

    PatientSearchAdapter(List<PatientEntity> items) {
        this.items = items;
    }

    PatientEntity getItem(int position) {
        return items.get(position);
    }

    //<editor-fold desc="Adapter Overrides">

    // Create new views (invoked by the layout manager)
    @Override
    public PatientSearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(RES_LAYOUT, parent, false);
        return new PatientSearchAdapter.ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(PatientSearchAdapter.ViewHolder holder, int position) {
        PatientEntity data = items.get(position);
        holder.patientTextView.setText(data.getName());
        holder.patientTextView.setCompoundDrawablesWithIntrinsicBounds(RES_ICON, 0, 0, 0);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    //</editor-fold>

    // <editor-fold desc="View Holder">

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView patientTextView;

        ViewHolder(View view) {
            super(view);
            patientTextView = view.findViewById(R.id.view_item_data_title);
            view.findViewById(R.id.view_item_data_subtitle).setVisibility(View.GONE);
        }

    }

    //</editor-fold>

}
