package com.example.dam_m13_proyecto.carrier;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dam_m13_proyecto.R;

public class ReviewAcceptedRequestsActivity extends AppCompatActivity {
    private String userId;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviewopenloads);
        SharedPreferences preferences = getSharedPreferences("user_preferences", Context.MODE_PRIVATE);
        // Retrieving the user ID, defaultValue can be an empty string or any default value you prefer
        String userId = preferences.getString("user_id", "");

    }
}
