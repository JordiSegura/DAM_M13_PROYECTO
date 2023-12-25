package com.example.dam_m13_proyecto.shipper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.dam_m13_proyecto.R;
import com.example.dam_m13_proyecto.CRUD.UpdateCarrierProfileDataActivity;
import com.example.dam_m13_proyecto.carrier.CreateLoadCarrierActivity;
import com.example.dam_m13_proyecto.carrier.ReviewOpenLoadsActivity;
import com.google.android.material.navigation.NavigationView;

public class WelcomePageShipperActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private DrawerLayout drawer;

    private Button buttonCreateLoad,buttonReviewCarrierRequests,buttonReviewOpenLoads;


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
        buttonCreateLoad = findViewById(R.id.buttonCreateLoad);
        buttonCreateLoad.setOnClickListener(this);

        buttonReviewCarrierRequests = findViewById(R.id.buttonReviewRequests);
        buttonReviewCarrierRequests.setOnClickListener(this);

        buttonReviewOpenLoads = findViewById(R.id.buttonOpenLoads);
        buttonReviewOpenLoads.setOnClickListener(this);
        SharedPreferences preferences = getSharedPreferences("user_preferences", Context.MODE_PRIVATE);
        // Retrieving the user ID, defaultValue can be an empty string or any default value you prefer
        String userId = preferences.getString("user_id", null);

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        if (item.getTitle().equals("Manage company data")){
            // Handle your menu item clicks here
            Intent intent = new Intent(WelcomePageShipperActivity.this, UpdateCarrierProfileDataActivity.class);
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

        if (viewId == R.id.buttonCreateLoad) {
            Intent intent = new Intent(WelcomePageShipperActivity.this, CreateLoadCarrierActivity.class);
            startActivity(intent);
        } else if (viewId == R.id.buttonReviewRequests) {
            Intent intent = new Intent(WelcomePageShipperActivity.this, ReviewCarrierRequestsActivity.class);
            startActivity(intent);
        } else if (viewId == R.id.buttonOpenLoads) {
            Intent intent = new Intent(WelcomePageShipperActivity.this, ReviewOpenLoadsActivity.class);
            startActivity(intent);
        }
    }

}
