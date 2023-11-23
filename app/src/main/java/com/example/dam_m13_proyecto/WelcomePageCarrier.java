package com.example.dam_m13_proyecto;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomePageCarrier extends AppCompatActivity implements View.OnClickListener {
    private TextView textViewTest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcomepage);
        //Buscar
        textViewTest = findViewById(R.id.textViewTest);
        textViewTest.setText("Welcome Page Carrier!");

    }


    @Override
    public void onClick(View view) {

    }
}
