package com.example.dam_m13_proyecto;

import android.content.Intent;
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
import java.util.Arrays;

public class UserLoginCreate extends AppCompatActivity implements View.OnClickListener{
    private TextView textViewNombreLogin;
    private TextView textViewPassLogin;
    private Spinner spinnerUserType;
    private Button buttonLogin;

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

        new GetDataForSpinner().execute();






spinnerUserType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        // Get the selected item
        selectedValue = (String) spinnerUserType.getItemAtPosition(pos);

        // You can do something with the selected value here
        Toast.makeText(UserLoginCreate.this, "Selected: " + selectedValue, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
});

    }
    @Override
    public void onClick(View view) {
        new GetDataFromDatabase().execute();

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
                Connection connection = (Connection) DriverManager.getConnection(
                        "jdbc:mysql://10.0.2.2/empresa",
                        "androidDBUser",
                        "0310");


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
            ArrayAdapter<String> adapter = new ArrayAdapter<>(UserLoginCreate.this, android.R.layout.simple_spinner_item, spinnerValues);

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
                Connection connection = (Connection) DriverManager.getConnection(
                        "jdbc:mysql://10.0.2.2/empresa",
                        "androidDBUser",
                        "0310");


                // Crea una declaración SQL y ejecuta la consulta
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM empleados where " +
                        "id_user ='" + textViewNombreLogin.getText() + "' and " +
                        "password ='" + textViewPassLogin.getText() + "'and " +
                        "user_type ='" + selectedValue + "'" +
                        "");
                    System.out.println("EEEEEEEEEEEEEEEEEE " + selectedValue);



                // Procesa los resultados
                while (resultSet.next()) {

                    // Obtén los datos del resultado
                    String dataNombre = resultSet.getString("nombre");
                    String dataApellidos = resultSet.getString("apellidos");
                    String dataDepartamento = resultSet.getString("departamento");

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
            // Actualiza la interfaz de usuario con los datos recuperados de la base de datos
            // Split the string using the delimiter (comma in this case)
            String[] values = result.split(",");



// Set the values in the respective EditText widgets
            if (values.length >= 3) {
                Toast.makeText(getApplicationContext(),"Results found",Toast.LENGTH_SHORT).show();

                textViewNombreLogin.setText("Nombre: " +values[0]); // Set the first value in editText1
                textViewPassLogin.setText("Apellido: "  + values[1]); // Set the second value in editText2
                //textViewDeparamentoResultado.setText("Departamento: " +  values[2]); // Set the third value in editText3
            } else {
                Toast.makeText(getApplicationContext(),"No results found. Going back to main page",Toast.LENGTH_SHORT).show();
                //Intent intent = new Intent(UserLoginCreate.this, MainActivity.class);
               // startActivity(intent);
            }
        }
    }
}
