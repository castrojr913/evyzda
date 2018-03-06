package esvyda.markwiliams.controller.patient;

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
import esvyda.markwiliams.model.api.patient.OnPatientListener;
import esvyda.markwiliams.model.api.patient.PatientRepository;
import esvyda.markwiliams.model.data.entity.PatientEntity;
import esvyda.markwiliams.ui.OnRecyclerItemClickListener;
import esvyda.markwiliams.ui.OnRecyclerItemListener;

@EFragment(R.layout.view_patient_search)
public class PatientSearchFragment extends Fragment
        implements OnRecyclerItemListener, OnPatientListener {

    //<editor-fold desc="Views">

    @ViewById(R.id.fab)
    FloatingActionButton fab;

    @ViewById(R.id.patient_recycler_view)
    RecyclerView listView;

    @ViewById(R.id.patient_view_loading)
    View loadingView;

    @ViewById(R.id.patient_view_empty)
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
        // List
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
        PatientRepository.getInstance().getPatients(this);
    }

    //<editor-fold desc="Events">

    @Click(R.id.fab)
    public void OnFloatActionButton() {
        PatientDetailActivity_.intent(this).start();
    }

    @Override
    public void onItemClick(View view, int position) {
        PatientEntity entity = ((PatientSearchAdapter) listView.getAdapter()).getItem(position);
        PatientDetailActivity_.intent(this).extra("formData", entity).start();
    }

    //</editor-fold>

    //<editor-fold desc="Api">

    @Override
    @UiThread
    public void onPatients(List<PatientEntity> patients) {
        showLoading(false);
        if (patients.isEmpty()) {
            showList(false);
        } else {
            listView.setAdapter(new PatientSearchAdapter(patients));
            showList(true);
        }
    }

    @Override
    public void onSuccess() {
        // Blank
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

    //<editor-fold desc="Visibility">

    @UiThread
    public void showLoading(boolean isVisible) {
        loadingView.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        listView.setVisibility(isVisible ? View.GONE : View.VISIBLE);
        emptyView.setVisibility(isVisible ? View.GONE : View.VISIBLE);
    }

    @UiThread
    public void showList(boolean isVisible) {
        listView.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        emptyView.setVisibility(isVisible ? View.GONE : View.VISIBLE);
    }

    //</editor-fold>

}
