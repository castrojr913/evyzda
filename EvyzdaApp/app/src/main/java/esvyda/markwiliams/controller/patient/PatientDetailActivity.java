package esvyda.markwiliams.controller.patient;

import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.Touch;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;

import esvyda.markwiliams.R;
import esvyda.markwiliams.helper.DateHelper;
import esvyda.markwiliams.helper.ViewHelper;
import esvyda.markwiliams.model.api.OnApiListener;
import esvyda.markwiliams.model.api.WsError;
import esvyda.markwiliams.model.api.patient.PatientRepository;
import esvyda.markwiliams.model.data.entity.PatientEntity;
import esvyda.markwiliams.model.data.form.PatientFormData;

@EActivity(R.layout.view_patient_detail)
public class PatientDetailActivity extends AppCompatActivity implements
        DatePickerDialog.OnDateSetListener, OnApiListener {

    //<editor-fold desc="Views">

    @ViewById(R.id.view_patient_edit_toolbar)
    Toolbar toolbar;

    @ViewById(R.id.view_patient_edit_text_name)
    TextInputEditText nameEditText;

    @ViewById(R.id.view_patient_edit_text_birthday)
    TextInputEditText birthdayEditText;

    @ViewById(R.id.view_patient_edit_text_phone)
    TextInputEditText phoneEditText;

    @ViewById(R.id.view_patient_layout_content)
    View contentView;

    @ViewById(R.id.view_patient_loading)
    View loadingView;

    //</editor-fold>

    //<editor-fold desc="String Res">

    @StringRes(R.string.default_title_edit)
    String toolbarTitle;

    @StringRes(R.string.default_date_placeholder)
    String defaultDateText;

    @StringRes(R.string.error_connectivity)
    String errorConnectivity;

    @StringRes(R.string.error_timeout)
    String errorTimeout;

    @StringRes(R.string.error_webservice)
    String errorWebservice;

    //</editor-fold>

    //<editor-fold desc="Extras">

    @Extra("formData")
    PatientEntity data;

    //</editor-fold>

    @AfterViews
    public void onInit() {
        // Toolbar
        toolbar.setTitle(toolbarTitle);
        setSupportActionBar(toolbar);
        ActionBar bar = getSupportActionBar();
        if (bar != null) bar.setDisplayHomeAsUpEnabled(true);
        // Form
        if (data != null) {
            nameEditText.setText(data.getName());
            phoneEditText.setText(data.getPhone());
            birthdayEditText.setText(DateHelper.calendarToString(data.getBirthday(), DateHelper.TIME_YYYYMMDD));
        }
    }

    //<editor-fold desc="Events">

    @Touch(R.id.view_patient_edit_text_birthday)
    void OnBirthDayClick(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            String text =  birthdayEditText.getText().toString();
            ViewHelper.showDatePicker(this, text.equals(defaultDateText) ? "" : text, this);
        };
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
        birthdayEditText.setText(DateHelper.buildDateText(year, monthOfYear, dayOfMonth));
    }

    @Click(R.id.view_patient_edit_button_save)
    public void onSubmit() {
        showLoading(true);
        PatientFormData formData = new PatientFormData();
        formData.setId(data != null ? data.getId() : 0);
        formData.setBirthday(birthdayEditText.getText().toString());
        formData.setName(nameEditText.getText().toString());
        formData.setPhone(phoneEditText.getText().toString());
        PatientRepository.getInstance().registerPatient(data == null, formData, this);
    }

    //</editor-fold>

    //<editor-fold desc="Api">

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
                error == WsError.TIMEOUT_FAILURE ? errorTimeout : errorWebservice;
        Snackbar.make(contentView, message, Snackbar.LENGTH_SHORT).show();
    }

    //</editor-fold>

    @UiThread
    public void showLoading(boolean isVisible) {
        loadingView.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        contentView.setVisibility(isVisible ? View.GONE : View.VISIBLE);
    }

}
