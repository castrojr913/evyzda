package esvyda.markwiliams.controller;

import android.os.Handler;
import android.support.annotation.UiThread;
import android.support.v7.app.AppCompatActivity;

import org.androidannotations.annotations.EActivity;

import esvyda.markwiliams.R;
import esvyda.markwiliams.controller.navigation.DrawerMenuActivity_;

/**
 * Created: 3/4/18.
 * Author: jesus.castro
 */
@EActivity(R.layout.view_splash)
public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_TIME = 2000; //ms

    @Override
    protected void onStart() {
        super.onStart();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                showNextView();
            }

        }, SPLASH_TIME);
    }

    @UiThread
    private void showNextView() {
        DrawerMenuActivity_.intent(this).start();
        finish();
    }

}
