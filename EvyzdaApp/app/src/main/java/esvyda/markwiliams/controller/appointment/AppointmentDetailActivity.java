package esvyda.markwiliams.controller.appointment;

import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Spinner;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.Touch;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;

import java.util.ArrayList;
import java.util.List;

import esvyda.markwiliams.R;
import esvyda.markwiliams.helper.DateHelper;
import esvyda.markwiliams.helper.ViewHelper;
import esvyda.markwiliams.model.api.WsError;
import esvyda.markwiliams.model.api.appointment.AppointmentRepository;
import esvyda.markwiliams.model.api.patient.OnPatientListener;
import esvyda.markwiliams.model.api.patient.PatientRepository;
import esvyda.markwiliams.model.data.entity.AppointmentEntity;
import esvyda.markwiliams.model.data.entity.PatientEntity;
import esvyda.markwiliams.model.data.enums.EAppointmentCategory;
import esvyda.markwiliams.model.data.form.AppointmentFormData;
import esvyda.markwiliams.ui.SpinnerItem;

@EActivity(R.layout.view_appointment_detail)
public class AppointmentDetailActivity extends AppCompatActivity implements
        OnPatientListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    //<editor-fold desc="Views">

    @ViewById(R.id.view_appointment_edit_toolbar)
    Toolbar toolbar;

    @ViewById(R.id.view_appointment_edit_text_date)
    TextInputEditText dateEditText;

    @ViewById(R.id.view_appointment_edit_text_time)
    TextInputEditText timeEditText;

    @ViewById(R.id.view_appointment_edit_text_description)
    TextInputEditText descriptionEditText;

    @ViewById(R.id.view_appointment_edit_spinner_patient)
    Spinner patientSpinner;

    @ViewById(R.id.view_appointment_edit_spinner_category)
    Spinner categorySpinner;

    @ViewById(R.id.view_appointment_layout_content)
    View contentView;

    @ViewById(R.id.view_appointment_edit_loading)
    View loadingView;

    //</editor-fold>

    //<editor-fold desc="String Res">

    @StringRes(R.string.default_title_edit)
    String toolbarTitle;

    @StringRes(R.string.appointment_edit_category_first)
    String categoryFirstText;

    @StringRes(R.string.appointment_edit_category_check)
    String categoryCheckText;

    @StringRes(R.string.default_date_placeholder)
    String defaultDateText;

    @StringRes(R.string.default_time_placeholder)
    String defaultTimeText;

    @StringRes(R.string.error_connectivity)
    String errorConnectivity;

    @StringRes(R.string.error_timeout)
    String errorTimeout;

    @StringRes(R.string.error_webservice)
    String errorWebservice;

    @StringRes(R.string.appointment_error_already_exist)
    String errorBadRequest;

    //</editor-fold>

    //<editor-fold desc="Extras">

    @Extra("formData")
    AppointmentEntity data;

    //</editor-fold>

    @AfterViews
    public void onInit() {
        // Toolbar
        toolbar.setTitle(toolbarTitle);
        setSupportActionBar(toolbar);
        ActionBar bar = getSupportActionBar();
        if (bar != null) bar.setDisplayHomeAsUpEnabled(true);
        // Load patients
        showLoading(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        PatientRepository.getInstance().getPatients(this);
    }

    //<editor-fold desc="Events">

    @Touch(R.id.view_appointment_edit_text_date)
    void OnDateClick(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            String text =  dateEditText.getText().toString();
            ViewHelper.showDatePicker(this, text.equals(defaultDateText) ? "" : text, this);
        }
    }

    @Touch(R.id.view_appointment_edit_text_time)
    void OnTimeClick(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            String text =  timeEditText.getText().toString();
            ViewHelper.showTimePicker(this, text.equals(defaultTimeText) ? "" : text, this);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        dateEditText.setText(DateHelper.buildDateText(year, monthOfYear, dayOfMonth));
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        boolean isAM = view.getSelectedTime().isAM();
        timeEditText.setText(DateHelper.buildTimeText(hourOfDay, minute, isAM));
    }

    @Click(R.id.view_appointment_edit_button_save)
    public void onSubmit() {
        showLoading(true);
        AppointmentFormData formData = new AppointmentFormData();
        Object category = ((SpinnerItem) categorySpinner.getSelectedItem()).getId();
        Object patientId = ((SpinnerItem) patientSpinner.getSelectedItem()).getId();
        formData.setId(data != null ? data.getId() : 0);
        formData.setCategoryId((EAppointmentCategory) category);
        formData.setPatientId((int) patientId);
        formData.setDescription(descriptionEditText.getText().toString());
        formData.setDate(dateEditText.getText().toString());
        formData.setTime(timeEditText.getText().toString());
        AppointmentRepository.getInstance().registerAppointment(data == null, formData, this);
    }

    //</editor-fold>

    //<editor-fold desc="Api">

    @Override
    @UiThread
    public void onPatients(List<PatientEntity> patients) {
        showLoading(false);
        EAppointmentCategory[] categories = {EAppointmentCategory.FIRST, EAppointmentCategory.CHECK};
        setupCategoryItems(categories);
        setupPatientItems(patients);
        if (data != null) {
            descriptionEditText.setText(data.getDescription());
            dateEditText.setText(DateHelper.calendarToString(data.getTime(), DateHelper.TIME_YYYYMMDD));
            timeEditText.setText(DateHelper.calendarToString(data.getTime(), DateHelper.TIME_HHMMZ));
        }
    }

    @Override
    @UiThread
    public void onSuccess() {
        finish();
    }

    @Override
    @UiThread
    public void onError(WsError error) {
        showLoading(false);
        String message = error == WsError.CONNECTIVITY_FAILURE ? errorConnectivity :
                         error == WsError.TIMEOUT_FAILURE ? errorTimeout :
                         error == WsError.BAD_REQUEST_FAILURE ? errorBadRequest : errorWebservice;
        Snackbar.make(contentView, message, Snackbar.LENGTH_SHORT).show();
    }

    //</editor-fold>

    //<editor-fold desc="Spinner">

    private List<SpinnerItem> setupCategoryItems(EAppointmentCategory[] categories) {
        int position = 0;
        for (int i = 0; i < categories.length; i++) {
            if (data == null) break;
            if (categories[i].getId() == data.getCategory().getId()) {
                position = i;
                break;
            }
        }
        List<SpinnerItem> items = new ArrayList<>();
        items.add(new SpinnerItem(EAppointmentCategory.FIRST, categoryFirstText));
        items.add(new SpinnerItem(EAppointmentCategory.CHECK, categoryCheckText));
        categorySpinner.setAdapter(ViewHelper.buildSpinnerAdapter(this, items));
        categorySpinner.setSelection(position);
        return items;
    }

    private List<SpinnerItem> setupPatientItems(List<PatientEntity> patients) {
        List<SpinnerItem> items = new ArrayList<>();
        int position = 0;
        for (int i = 0; i < patients.size(); i++) {
            if (data == null) break;
            if (patients.get(i).getId() == data.getPatientId()) {
                position = i;
                break;
            }
        }
        for (PatientEntity item : patients) {
            items.add(new SpinnerItem(item.getId(), item.getName()));
        }
        patientSpinner.setAdapter(ViewHelper.buildSpinnerAdapter(this, items));
        patientSpinner.setSelection(position);
        return items;
    }

    //</editor-fold>

    @UiThread
    public void showLoading(boolean isVisible) {
        loadingView.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        contentView.setVisibility(isVisible ? View.GONE : View.VISIBLE);
    }

}
