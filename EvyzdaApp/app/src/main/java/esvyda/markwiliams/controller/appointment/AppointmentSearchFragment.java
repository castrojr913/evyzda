package esvyda.markwiliams.controller.appointment;

import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;

import java.util.List;

import esvyda.markwiliams.R;
import esvyda.markwiliams.model.api.WsError;
import esvyda.markwiliams.model.api.appointment.AppointmentRepository;
import esvyda.markwiliams.model.api.appointment.OnAppointmentListener;
import esvyda.markwiliams.model.data.entity.AppointmentEntity;
import esvyda.markwiliams.ui.OnRecyclerItemClickListener;
import esvyda.markwiliams.ui.OnRecyclerItemListener;

@EFragment(R.layout.view_appointment_search)
public class AppointmentSearchFragment extends Fragment
        implements OnRecyclerItemListener, OnAppointmentListener {

    //<editor-fold desc="Views">

    @ViewById(R.id.fab)
    FloatingActionButton fab;

    @ViewById(R.id.appointment_recycler_view)
    RecyclerView listView;

    @ViewById(R.id.appointment_view_loading)
    View loadingView;

    @ViewById(R.id.appointment_view_empty)
    View emptyView;

    //</editor-fold>

    //<editor-fold desc="String Res">

    @StringRes(R.string.error_connectivity)
    String errorConnectivity;

    @StringRes(R.string.error_timeout)
    String errorTimeout;

    @StringRes(R.string.error_webservice)
    String errorWebservice;

    //</editor-fold>

    @AfterViews
    void onInit() {
        Activity parentActivity = getActivity();
        LinearLayoutManager layoutManager = new LinearLayoutManager(parentActivity);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listView.setLayoutManager(layoutManager);
        // Listener
        listView.addOnItemTouchListener(new OnRecyclerItemClickListener(parentActivity, this));
        // Init
        showLoading(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        AppointmentRepository.getInstance().getAppointments(this);
    }

    //<editor-fold desc="Api">

    @Override
    @UiThread
    public void onAppointments(List<AppointmentEntity> appointments) {
        showLoading(false);
        if (appointments.isEmpty()) {
            showList(false);
        } else {
            listView.setAdapter(new AppointmentSearchAdapter(appointments));
            showList(true);
        }
        listView.setAdapter(new AppointmentSearchAdapter(appointments));
    }

    @Override
    public void onSuccess() {

    }

    @Override
    @UiThread
    public void onError(WsError error) {
        showLoading(false);
        String message =
                error == WsError.CONNECTIVITY_FAILURE ? errorConnectivity :
                error == WsError.TIMEOUT_FAILURE ? errorTimeout : errorWebservice;
        Snackbar.make(listView, message, Snackbar.LENGTH_SHORT).show();
    }

    //</editor-fold>

    //<editor-fold desc="Events">

    @Click(R.id.fab)
    public void OnFloatActionButton() {
        AppointmentDetailActivity_.intent(this).start();
    }

    @Override
    public void onItemClick(View view, int position) {
        AppointmentEntity entity = ((AppointmentSearchAdapter) listView.getAdapter()).getItem(position);
        AppointmentDetailActivity_.intent(this).extra("formData", entity).start();
    }

    //</editor-fold>

    //<editor-fold desc="Visibility">

    @UiThread
    public void showLoading(boolean isVisible) {
        if (loadingView != null) loadingView.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        if (listView != null) listView.setVisibility(isVisible ? View.GONE : View.VISIBLE);
        if (emptyView != null) emptyView.setVisibility(isVisible ? View.GONE : View.VISIBLE);
    }

    @UiThread
    public void showList(boolean isVisible) {
        if (listView != null) listView.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        if (emptyView != null) emptyView.setVisibility(isVisible ? View.GONE : View.VISIBLE);
    }

    //</editor-fold>

}
