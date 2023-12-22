package com.example.dam_m13_proyecto;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class WelcomePageShipper2 extends AppCompatActivity {

    private DrawerLayout drawer;

    private Button buttonCreateLoad,buttonReviewCarrierRequests,buttonReviewOpenLoads;

    List<ListElement> elements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_card_view);

        SharedPreferences preferences = getSharedPreferences("user_preferences", Context.MODE_PRIVATE);
        // Retrieving the user ID, defaultValue can be an empty string or any default value you prefer
        String userId = preferences.getString("user_id", null);
            init();
    }
    public void init(){
        elements = new ArrayList<>();
        elements.add(new ListElement("Pedro","#607d8b","Barcelona","Activo"));
        elements.add(new ListElement("Juan","#FFFFFF","Barcelona","Activo"));
        elements.add(new ListElement("Pedro","#607d8b","Barcelona","Activo"));
        elements.add(new ListElement("Juan","#FFFFFF","Barcelona","Activo"));
        elements.add(new ListElement("Pedro","#607d8b","Barcelona","Activo"));
        elements.add(new ListElement("Juan","#FFFFFF","Barcelona","Activo"));
        elements.add(new ListElement("Pedro","#607d8b","Barcelona","Activo"));
        elements.add(new ListElement("Juan","#FFFFFF","Barcelona","Activo"));
        elements.add(new ListElement("Pedro","#607d8b","Barcelona","Activo"));
        elements.add(new ListElement("Juan","#FFFFFF","Barcelona","Activo"));
        elements.add(new ListElement("Pedro","#607d8b","Barcelona","Activo"));
        elements.add(new ListElement("Juan","#FFFFFF","Barcelona","Activo"));

        ListAdapter listAdapter = new ListAdapter(elements,this);
        RecyclerView recyclerView = findViewById(R.id.listRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);

    }




}
