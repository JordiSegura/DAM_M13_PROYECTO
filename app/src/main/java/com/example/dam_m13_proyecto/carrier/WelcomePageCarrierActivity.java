package com.example.dam_m13_proyecto.carrier;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.dam_m13_proyecto.R;
import com.example.dam_m13_proyecto.shipper.ReviewCarrierRequestsActivity;
import com.example.dam_m13_proyecto.CRUD.UpdateCarrierProfileDataActivity;
import com.google.android.material.navigation.NavigationView;

public class WelcomePageCarrierActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private DrawerLayout drawer;

    private Button buttonCheckNewLoads,buttonReviewLoadRequests,buttonAcceptedLoads;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcomepagecarrier);

        //DrawerLayout
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        drawer.open();
        toggle.syncState();

        //Buttons
        buttonCheckNewLoads = findViewById(R.id.buttonCheckNewLoads);
        buttonCheckNewLoads.setOnClickListener(this);

        buttonReviewLoadRequests = findViewById(R.id.buttonReviewLoadRequests);
        buttonReviewLoadRequests.setOnClickListener(this);

        buttonAcceptedLoads = findViewById(R.id.buttonAcceptedLoads);
        buttonAcceptedLoads.setOnClickListener(this);
        SharedPreferences preferences = getSharedPreferences("user_preferences", Context.MODE_PRIVATE);
        // Retrieving the user ID, defaultValue can be an empty string or any default value you prefer
        String userId = preferences.getString("user_id", null);

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        if (item.getTitle().equals("Manage company data")){
            // Handle your menu item clicks here
            Intent intent = new Intent(WelcomePageCarrierActivity.this, UpdateCarrierProfileDataActivity.class);
            startActivity(intent);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }
        return true;

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();

        if (viewId == R.id.buttonCheckNewLoads) {
            Intent intent = new Intent(WelcomePageCarrierActivity.this, ReviewOpenLoadsActivity.class);
            startActivity(intent);
        } else if (viewId == R.id.buttonReviewLoadRequests) {
            Intent intent = new Intent(WelcomePageCarrierActivity.this, ReviewAcceptedRequestsActivity.class);
            startActivity(intent);
        } else if (viewId == R.id.buttonAcceptedLoads) {
            Intent intent = new Intent(WelcomePageCarrierActivity.this, ReviewAcceptedLoadsActivity.class);
            startActivity(intent);
        }
    }

}
