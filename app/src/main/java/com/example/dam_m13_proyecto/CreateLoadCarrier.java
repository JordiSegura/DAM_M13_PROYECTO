package com.example.dam_m13_proyecto;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class CreateLoadCarrier extends AppCompatActivity implements View.OnClickListener {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;

    private Spinner spinnerLinearUom, spinnerWeightUom;
    private EditText editTextOriginAddress, editTextDestinationAddress, editTextWeight, editTextHeight, editTextWidth;
    private String selectedValueLinearUom, selectedValueWeightUom;
    private double  latitude, longitude;
    private String[] spinnerLinearUOMValues = {};
    private String[] spinnerWeightUOMValues = {};
    private Button buttonSubmit;

    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createloadcarrier);
        spinnerLinearUom = findViewById(R.id.spinnerLinearUOM);
        spinnerWeightUom = findViewById(R.id.spinnerWeightUOM);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        buttonSubmit.setOnClickListener(this);

        editTextOriginAddress = findViewById(R.id.editTextOriginAddress);
        editTextDestinationAddress = findViewById(R.id.editTextDestinationAddress);
        editTextWeight = findViewById(R.id.editTextWeight);
        editTextHeight = findViewById(R.id.editTextHeight);
        editTextWidth = findViewById(R.id.editTextWidth);

        new GetDataForSpinnerLinearUom().execute();
        new GetDataForSpinnerWeightUom().execute();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000); // Update interval in milliseconds

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult != null) {
                    Location location = locationResult.getLastLocation();
                    if (location != null) {
                        // Use the 'location' object to get latitude, longitude, etc.
                         latitude = location.getLatitude();
                         longitude = location.getLongitude();
                        Toast.makeText(CreateLoadCarrier.this, "Latitude: " + latitude + ", Longitude: " + longitude, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };

        // Check for location permission
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Request location permission
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            // Permission already granted
            startLocationUpdates();
        }


        spinnerWeightUom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                // Get the selected item
                selectedValueWeightUom = (String) spinnerWeightUom.getItemAtPosition(pos);

                // You can do something with the selected value here
                Toast.makeText(CreateLoadCarrier.this, "Selected: " + selectedValueWeightUom, Toast.LENGTH_SHORT).show();
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

                // You can do something with the selected value here
                Toast.makeText(CreateLoadCarrier.this, "Selected: " + selectedValueLinearUom, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, start location updates
                startLocationUpdates();
            } else {
                // Permission denied, handle accordingly
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Stop location updates when the activity is destroyed
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
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


                Class.forName("com.mysql.jdbc.Driver");
                connection = (Connection) DriverManager.getConnection(
                        "jdbc:mysql://10.0.2.2/empresa",
                        "androidDBUser",
                        "0310");


                    String insertQuery = "INSERT INTO new_loads ( origin_address, destination_address, status, weight, height, width, linear_uom, weight_uom, tariff_id, load_rate_cost, currency_uom) " +
                            "VALUES (?,?,?,?,?,?,?,?,?,?,?)";

                    preparedStatement = connection.prepareStatement(insertQuery);
                    preparedStatement.setString(1, editTextOriginAddressC);
                    preparedStatement.setString(2, editTextDestinationAddressC);
                    preparedStatement.setString(3, "00");
                    preparedStatement.setString(4, editTextWeightC);
                    preparedStatement.setString(5, editTextHeightC);
                    preparedStatement.setString(6, editTextWidthC);
                    preparedStatement.setString(7, selectedValueLinearUom);
                    preparedStatement.setString(8, selectedValueWeightUom);
                    preparedStatement.setString(9, "00");
                    preparedStatement.setString(10, "50");
                    preparedStatement.setString(11, "Yen");

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
