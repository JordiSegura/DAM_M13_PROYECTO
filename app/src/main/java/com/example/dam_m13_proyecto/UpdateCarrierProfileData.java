package com.example.dam_m13_proyecto;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;


import com.google.android.gms.location.FusedLocationProviderClient;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class UpdateCarrierProfileData extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextNombre, editTextEmail, editTextUserId, editTextPassword, editTextUserType,editTextCompanyName;
    private Button buttonSubmit;
    private String userId;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private GestureDetectorCompat gestureDetector;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatecarrierprofiledata);
        SharedPreferences preferences = getSharedPreferences("user_preferences", Context.MODE_PRIVATE);
        userId = preferences.getString("user_id", "");


        buttonSubmit = findViewById(R.id.buttonSubmit);
        buttonSubmit.setOnClickListener(this);
        editTextNombre = findViewById(R.id.editTextNombre);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextUserId = findViewById(R.id.editTextUserId);
        editTextUserId.setEnabled(false);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextUserType = findViewById(R.id.editTextUserType);
        editTextUserType.setEnabled(false);
        editTextCompanyName = findViewById(R.id.editTextCompanyName);



        new GetDataFromDatabase().execute();

    }


    @Override
    public void onClick(View view) {

        if (areEditTextFieldsEmpty(editTextNombre,
                editTextEmail,
                editTextUserId,
                editTextPassword,
                editTextUserType,
                editTextCompanyName)) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();

        } else {
            new PostDataToDatabase().execute();


        }

    }
    private boolean areEditTextFieldsEmpty(EditText... editTexts) {
        for (EditText editText : editTexts) {
            if (editText == null || editText.getText().toString().trim().isEmpty()) {
                return true; // At least one EditText is empty
            }
        }
        return false; // None of the EditTexts is empty
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
                ResultSet resultSet = statement.executeQuery("SELECT " +
                        "id_user, password, user_type, company_name, user_full_name, user_contact_email " +
                        "FROM empleados where " + "id_user ='" + userId +"'");


                // Procesa los resultados
                while (resultSet.next()) {
                    // Obtén los datos del resultado
                    String idUser = resultSet.getString("id_user");
                    String password = resultSet.getString("password");
                    String user_type = resultSet.getString("user_type");
                    String company_name = resultSet.getString("company_name");
                    String user_full_name = resultSet.getString("user_full_name");
                    String user_contact_email = resultSet.getString("user_contact_email");

                    result += idUser + ",";
                    result += password + ",";
                    result += user_type + ",";
                    result += company_name + ",";
                    result += user_full_name + ",";
                    result += user_contact_email + ",";


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
                //"id_user, password, user_type, company_name, user_full_name, user_contact_email
                editTextUserId.setText("User ID: " + values[0]);
                editTextPassword.setText(values[1]);
                editTextUserType.setText("User type: " + values[2]);
                editTextCompanyName.setText(values[3]);
                editTextNombre.setText(values[4]);
                editTextEmail.setText(values[5]);


            } else {
                Toast.makeText(getApplicationContext(), "User account not found", Toast.LENGTH_SHORT).show();

            }
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

                String editTextNombreC = String.valueOf(editTextNombre.getText());
                String editTextEmailC = String.valueOf(editTextEmail.getText());
                String editTextCompanyNameC = String.valueOf(editTextCompanyName.getText());
                String editTextPasswordC = String.valueOf(editTextPassword.getText());



                Class.forName("com.mysql.jdbc.Driver");
                connection = (Connection) DriverManager.getConnection(
                        "jdbc:mysql://10.0.2.2/empresa",
                        "androidDBUser",
                        "0310");

                    String insertQuery = "UPDATE empleados set " +
                            "password=? , company_name=? , user_full_name=? , user_contact_email=? " +
                            "where id_user=?  ";

                    preparedStatement = connection.prepareStatement(insertQuery);
                    preparedStatement.setString(1, editTextPasswordC);
                    preparedStatement.setString(2, editTextCompanyNameC);
                    preparedStatement.setString(3, editTextNombreC);
                    preparedStatement.setString(4, editTextEmailC);
                    preparedStatement.setString(5, userId);


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
                Toast.makeText(UpdateCarrierProfileData.this, "Record has been inserted in database", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(UpdateCarrierProfileData.this, WelcomePageCarrier.class);
                startActivity(intent);


            } else if (!completadoOK) {
                Toast.makeText(UpdateCarrierProfileData.this, "Password or user is already in use", Toast.LENGTH_LONG).show();

            }

        }
    }


}
