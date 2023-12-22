package com.example.dam_m13_proyecto;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class CreateLoadCarrier extends AppCompatActivity implements View.OnClickListener {

    private Spinner spinnerLinearUom, spinnerWeightUom;
    private EditText editTextOriginAddress, editTextDestinationAddress, editTextWeight, editTextHeight, editTextWidth, editTextOriginCity, editTextOriginZip, editTextDestinationCity, editTextDestinationZip, editTextLatitude, editLongitude;
    private String selectedValueLinearUom, selectedValueWeightUom;
    private double  latitude, longitude;
    private String[] spinnerLinearUOMValues = {};
    private String[] spinnerWeightUOMValues = {};
    private Button buttonSubmit;
    private String userId;

    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createloadcarrier);
        SharedPreferences preferences = getSharedPreferences("user_preferences", Context.MODE_PRIVATE);
        // Retrieving the user ID, defaultValue can be an empty string or any default value you prefer
        String userId = preferences.getString("user_id", "");
System.out.println("USUARIO activo: " + userId);
        spinnerLinearUom = findViewById(R.id.spinnerLinearUOM);
        spinnerWeightUom = findViewById(R.id.spinnerWeightUOM);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        buttonSubmit.setOnClickListener(this);

        editTextOriginAddress = findViewById(R.id.editTextOriginAddress);
        editTextDestinationAddress = findViewById(R.id.editTextDestinationAddress);
        editTextWeight = findViewById(R.id.editTextWeight);
        editTextHeight = findViewById(R.id.editTextHeight);
        editTextWidth = findViewById(R.id.editTextWidth);
        editTextOriginCity = findViewById(R.id.editTextOriginCity);
        editTextOriginZip = findViewById(R.id.editTextOriginZip);
        editTextDestinationCity = findViewById(R.id.editTextDestinationCity);
        editTextDestinationZip = findViewById(R.id.editTextDestinationZip);
        editTextLatitude = findViewById(R.id.editTextLatitude);
        editLongitude = findViewById(R.id.editLongitude);

        new GetDataForSpinnerLinearUom().execute();
        new GetDataForSpinnerWeightUom().execute();




        spinnerWeightUom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                // Get the selected item
                selectedValueWeightUom = (String) spinnerWeightUom.getItemAtPosition(pos);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerLinearUom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                // Get the selected item
                selectedValueLinearUom = (String) spinnerLinearUom.getItemAtPosition(pos);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }



    private class GetDataForSpinnerWeightUom extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            String result = "";

            try {


                // Carga el controlador JDBC para MySQL
                Class.forName("com.mysql.jdbc.Driver");

                // Establece la conexión con tu base de datos en PhpMyAdmin
                Connection connection = (Connection) DriverManager.getConnection("jdbc:mysql://10.0.2.2/empresa", "androidDBUser", "0310");


                // Crea una declaración SQL y ejecuta la consulta
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT uom_id FROM weight_uom  ");


                // Procesa los resultados
                while (resultSet.next()) {

                    // Obtén los datos del resultado
                    String dataUserType = resultSet.getString("uom_id");

                    result += dataUserType + ",";
                }


                // Cierra la conexión
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();

                result = "Error: " + e.getMessage();

            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            // Split the string using the comma as the delimiter
            String[] newValues = result.split(",");

            // Calculate the new size of the array
            int newSize = spinnerWeightUOMValues.length + newValues.length;

            // Create a new array with the updated size
            String[] combinedArray = new String[newSize];

            // Copy existing values to the new array
            System.arraycopy(spinnerWeightUOMValues, 0, combinedArray, 0, spinnerWeightUOMValues.length);

            // Copy new values to the new array
            System.arraycopy(newValues, 0, combinedArray, spinnerWeightUOMValues.length, newValues.length);

            // Update spinnerValues with the combined array
            spinnerWeightUOMValues = combinedArray;


            // Create an ArrayAdapter using the string array and a default spinner layout
            ArrayAdapter<String> adapter = new ArrayAdapter<>(CreateLoadCarrier.this, android.R.layout.simple_spinner_item, spinnerWeightUOMValues);

            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // Apply the adapter to the spinner
            spinnerWeightUom.setAdapter(adapter);


        }
    }

    private class GetDataForSpinnerLinearUom extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            String result = "";

            try {

                // Carga el controlador JDBC para MySQL
                Class.forName("com.mysql.jdbc.Driver");

                // Establece la conexión con tu base de datos en PhpMyAdmin
                Connection connection = (Connection) DriverManager.getConnection("jdbc:mysql://10.0.2.2/empresa", "androidDBUser", "0310");


                // Crea una declaración SQL y ejecuta la consulta
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT uom_id FROM linear_uom  ");


                // Procesa los resultados
                while (resultSet.next()) {

                    // Obtén los datos del resultado
                    String dataUserType = resultSet.getString("uom_id");

                    result += dataUserType + ",";
                }


                // Cierra la conexión
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();

                result = "Error: " + e.getMessage();

            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {

            // Split the string using the comma as the delimiter
            String[] newValues = result.split(",");

            // Calculate the new size of the array
            int newSize = spinnerLinearUOMValues.length + newValues.length;

            // Create a new array with the updated size
            String[] combinedArray = new String[newSize];

            // Copy existing values to the new array
            System.arraycopy(spinnerLinearUOMValues, 0, combinedArray, 0, spinnerLinearUOMValues.length);

            // Copy new values to the new array
            System.arraycopy(newValues, 0, combinedArray, spinnerLinearUOMValues.length, newValues.length);

            // Update spinnerValues with the combined array
            spinnerLinearUOMValues = combinedArray;


            // Create an ArrayAdapter using the string array and a default spinner layout
            ArrayAdapter<String> adapter = new ArrayAdapter<>(CreateLoadCarrier.this, android.R.layout.simple_spinner_item, spinnerLinearUOMValues);

            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // Apply the adapter to the spinner
            spinnerLinearUom.setAdapter(adapter);


        }
    }

    @Override
    public void onClick(View view) {

        if (null != editTextOriginAddress.getText() && null != editTextDestinationAddress.getText()
                && null != editTextWeight.getText() && null != editTextHeight.getText()
                && null != editTextWidth.getText()) {
            new PostDataToDatabase().execute();
        } else {
            Toast.makeText(CreateLoadCarrier.this, "Input data in all available fields", Toast.LENGTH_LONG).show();

        }

    }

    private class PostDataToDatabase extends AsyncTask<Void, Void, String> {
        boolean completadoOK = false;

        @Override
        protected String doInBackground(Void... voids) {
            String result = "";
            PreparedStatement preparedStatement = null;
            Connection connection = null;
            PreparedStatement checkStatement = null;
            try {
                String editTextOriginAddressC = String.valueOf(editTextOriginAddress.getText());
                String editTextDestinationAddressC = String.valueOf(editTextDestinationAddress.getText());
                String editTextWeightC = String.valueOf(editTextWeight.getText());
                String editTextHeightC = String.valueOf(editTextHeight.getText());
                String editTextWidthC = String.valueOf(editTextWidth.getText());
                String editTextOriginCityC = String.valueOf(editTextOriginCity.getText());
                String editTextOriginZipC = String.valueOf(editTextOriginZip.getText());
                String editTextDestinationCityC = String.valueOf(editTextDestinationCity.getText());
                String editTextDestinationZipC = String.valueOf(editTextDestinationZip.getText());
                String latitudeC = String.valueOf(editTextLatitude.getText());
                String longitudeC = String.valueOf(editLongitude.getText());
                SharedPreferences preferences = getSharedPreferences("user_preferences", Context.MODE_PRIVATE);
                // Retrieving the user ID, defaultValue can be an empty string or any default value you prefer
                String userId = preferences.getString("user_id", "");

                Class.forName("com.mysql.jdbc.Driver");
                connection = (Connection) DriverManager.getConnection(
                        "jdbc:mysql://10.0.2.2/empresa",
                        "androidDBUser",
                        "0310");

                    String insertQuery = "INSERT INTO new_loads " +
                            "( origin_address, origin_city,origin_zip_code, destination_address,destination_city,destination_zip_code, status, weight, height, width, " +
                            "linear_uom, weight_uom, tariff_id, load_rate_cost, currency_uom, latitude, longitude, id_user) " +
                            "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

                    preparedStatement = connection.prepareStatement(insertQuery);
                    preparedStatement.setString(1, editTextOriginAddressC);
                    preparedStatement.setString(2, editTextOriginCityC);
                    preparedStatement.setString(3, editTextOriginZipC);
                    preparedStatement.setString(4, editTextDestinationAddressC);
                    preparedStatement.setString(5, editTextDestinationCityC);
                    preparedStatement.setString(6, editTextDestinationZipC);
                    preparedStatement.setString(7, "00");
                    preparedStatement.setString(8, editTextWeightC);
                    preparedStatement.setString(9, editTextHeightC);
                    preparedStatement.setString(10, editTextWidthC);
                    preparedStatement.setString(11, selectedValueLinearUom);
                    preparedStatement.setString(12, selectedValueWeightUom);
                    preparedStatement.setString(13, "00");
                    preparedStatement.setString(14, "50");
                    preparedStatement.setString(15, "Yen");
                    preparedStatement.setString(16, latitudeC);
                    preparedStatement.setString(17, longitudeC);
                    preparedStatement.setString(18, userId);


                preparedStatement.executeUpdate();
                    completadoOK = true;

            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (preparedStatement != null) {
                        preparedStatement.close();
                    }
                    if (connection != null) {
                        connection.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            System.out.println("valor del boolean");
            System.out.println(completadoOK);
            if (completadoOK) {
                Toast.makeText(CreateLoadCarrier.this, "Record has been inserted in database", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(CreateLoadCarrier.this, WelcomePageCarrier.class);
                startActivity(intent);


            } else if (!completadoOK) {
                Toast.makeText(CreateLoadCarrier.this, "Password or user is already in use", Toast.LENGTH_LONG).show();

            }

        }
    }


}
