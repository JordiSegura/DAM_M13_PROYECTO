package com.example.dam_m13_proyecto;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class UserLoginRegister extends AppCompatActivity implements View.OnClickListener {
    private TextView textViewNombreLogin;
    private TextView textViewPassLogin;
    private Spinner spinnerUserType;
    private Button buttonLogin, buttonRegister;

    private String selectedValue;

    private String[] spinnerValues = {};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlogincreate);
        //Buscar
        textViewNombreLogin = findViewById(R.id.editTextName);
        textViewPassLogin = findViewById(R.id.editTextPassword);
        spinnerUserType = findViewById(R.id.spinnerUserType);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(this);
        buttonRegister = findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener(this);
        new GetDataForSpinner().execute();


        spinnerUserType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                // Get the selected item
                selectedValue = (String) spinnerUserType.getItemAtPosition(pos);

                // You can do something with the selected value here
                Toast.makeText(UserLoginRegister.this, "Selected: " + selectedValue, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    public void onClick(View view) {

        int viewId = view.getId();

        if (viewId == R.id.buttonLogin) {
            new GetDataFromDatabase().execute();

        } else if (viewId == R.id.buttonRegister) {
            Intent intent = new Intent(UserLoginRegister.this, UserRegister.class);
            startActivity(intent);
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    private class GetDataForSpinner extends AsyncTask<Void, Void, String> {

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
                ResultSet resultSet = statement.executeQuery("SELECT user_type FROM user_type  ");


                // Procesa los resultados
                while (resultSet.next()) {

                    // Obtén los datos del resultado
                    String dataUserType = resultSet.getString("user_type");

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
            int newSize = spinnerValues.length + newValues.length;

            // Create a new array with the updated size
            String[] combinedArray = new String[newSize];

            // Copy existing values to the new array
            System.arraycopy(spinnerValues, 0, combinedArray, 0, spinnerValues.length);

            // Copy new values to the new array
            System.arraycopy(newValues, 0, combinedArray, spinnerValues.length, newValues.length);

            // Update spinnerValues with the combined array
            spinnerValues = combinedArray;


            // Create an ArrayAdapter using the string array and a default spinner layout
            ArrayAdapter<String> adapter = new ArrayAdapter<>(UserLoginRegister.this, android.R.layout.simple_spinner_item, spinnerValues);

            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // Apply the adapter to the spinner
            spinnerUserType.setAdapter(adapter);


        }
    }

    private class GetDataFromDatabase extends AsyncTask<Void, Void, String> {

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
                ResultSet resultSet = statement.executeQuery("SELECT * FROM empleados where " + "id_user ='" + textViewNombreLogin.getText() + "' and " + "password ='" + textViewPassLogin.getText() + "'and " + "user_type ='" + selectedValue + "'" + "");


                // Procesa los resultados
                while (resultSet.next()) {

                    // Obtén los datos del resultado
                    String dataNombre = resultSet.getString("id_user");
                    String dataApellidos = resultSet.getString("password");
                    String dataDepartamento = resultSet.getString("user_type");

                    result += dataNombre + ",";
                    result += dataApellidos + ",";
                    result += dataDepartamento + ",";


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

            String[] values = result.split(",");

            if (values.length > 0) {
               if (values[2].equals("carrier")) {
                   // In your LoginActivity or wherever you handle the login process

                   SharedPreferences preferences = getSharedPreferences("user_preferences", Context.MODE_PRIVATE);
                   SharedPreferences.Editor editor = preferences.edit();

// Assuming userId is the variable where you store the user ID
                   editor.putString("user_id", values[0]);
                   editor.apply();
                   Intent intent = new Intent(UserLoginRegister.this, WelcomePageCarrier.class);
                   startActivity(intent);
               } else if (values[2].equals("shipper")) {
                   // In your LoginActivity or wherever you handle the login process

                   SharedPreferences preferences = getSharedPreferences("user_preferences", Context.MODE_PRIVATE);
                   SharedPreferences.Editor editor = preferences.edit();

// Assuming userId is the variable where you store the user ID
                   editor.putString("user_id", values[0]);
                   editor.apply();
                    Intent intent = new Intent(UserLoginRegister.this, WelcomePageCarrier.class);
                    startActivity(intent);
                }
            } else {
                Toast.makeText(getApplicationContext(), "User account not found", Toast.LENGTH_SHORT).show();

            }
        }
    }
}
