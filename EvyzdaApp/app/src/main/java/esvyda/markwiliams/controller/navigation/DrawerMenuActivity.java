package esvyda.markwiliams.controller.navigation;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.ColorRes;
import org.androidannotations.annotations.res.DrawableRes;
import org.androidannotations.annotations.res.StringRes;

import java.util.ArrayList;

import esvyda.markwiliams.R;
import esvyda.markwiliams.controller.appointment.AppointmentSearchFragment_;
import esvyda.markwiliams.controller.patient.PatientSearchFragment_;

/**
 * Created: 3/5/18.
 * Author: jesus.castro
 */

@EActivity(R.layout.view_main_navigation)
public class DrawerMenuActivity extends AppCompatActivity implements Drawer.OnDrawerItemClickListener {

    private static final int RES_LAYOUT_CHILDREN = R.id.main_drawer_view_fragment;

    //<editor-fold desc="View Instances">

    @ViewById(R.id.main_drawer_toolbar)
    Toolbar toolbar;

    //</editor-fold>

    //<editor-fold desc="String Resources">

    @StringRes(R.string.appointment_title_search)
    String appointmentMenuText;

    @StringRes(R.string.patient_title_search)
    String patientMenuText;

    //</editor-fold>

    //<editor-fold desc="Drawable & Color Resources">

    @DrawableRes(R.drawable.drawer_header_background)
    Drawable headerBackground;

    @ColorRes(R.color.app_drawer_text_selected)
    int itemTextColor;

    //</editor-fold>

    @AfterViews
    void init() {
        setSupportActionBar(toolbar);
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setDisplayHomeAsUpEnabled(true);
        }
        setupDrawer();
        loadNavigationView(NavigationItem.APPOINTMENT);
    }

    //<editor-fold desc="Drawer Panel">

    private void setupDrawer() {
        // Drawer Header
        AccountHeader header = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(headerBackground)
                .withSelectionListEnabled(false)
                .build();
        // Creating Items for Drawer
        ArrayList<IDrawerItem> drawerItems = new ArrayList<>();
        drawerItems.add(new PrimaryDrawerItem()
                .withIdentifier(NavigationItem.APPOINTMENT.getId())
                .withName(appointmentMenuText)
                .withTag(appointmentMenuText)
                .withSelectedTextColor(itemTextColor));
        drawerItems.add(new PrimaryDrawerItem()
                .withIdentifier(NavigationItem.PATIENT.getId())
                .withName(patientMenuText)
                .withTag(patientMenuText));
        // Building Drawer
        new DrawerBuilder()
                .withActivity(this)
                .withActionBarDrawerToggle(true)
                .withToolbar(toolbar)
                .withAccountHeader(header)
                .withDrawerItems(drawerItems)
                .withOnDrawerItemClickListener(this).build();
    }

    private NavigationItem getItem(long itemId) {
        for (NavigationItem item : NavigationItem.values()) {
            if (item.getId() == itemId) return item;
        }
        return null;
    }

    private String getItemName(NavigationItem item) {
        String name = patientMenuText;
        if (item.equals(NavigationItem.APPOINTMENT)) {
            name = appointmentMenuText;
        }
        return name;
    }

    private Fragment getItemFragment(NavigationItem item) {
        Fragment fragment;
        if (item.equals(NavigationItem.APPOINTMENT)) {
            fragment = new AppointmentSearchFragment_();
        } else {
            fragment = new PatientSearchFragment_();
        }
        return fragment;
    }

    //</editor-fold>

    // <editor-fold desc="Events">

    @Override
    public boolean onItemClick(View view, int i, IDrawerItem drawerItem) {
        loadNavigationView(getItem(drawerItem.getIdentifier()));
        return false;
    }


    // </editor-fold>

    private void loadNavigationView(NavigationItem item) {
        setTitle(getItemName(item)); // Add Toolbar title
        getSupportFragmentManager().beginTransaction()
                .replace(RES_LAYOUT_CHILDREN, getItemFragment(item))
                .commit();
    }

}
