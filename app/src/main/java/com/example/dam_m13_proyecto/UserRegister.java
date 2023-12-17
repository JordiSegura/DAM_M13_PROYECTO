package com.example.dam_m13_proyecto;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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


public class UserRegister extends AppCompatActivity implements View.OnClickListener {

    private Spinner spinnerUserType;
    private EditText labelCompanyName,labelUserType,labelTextOriginCity,labelTextOriginZip,labelAddress,labelMail;
    private EditText editTextCompanyName,editTextOriginCity,editTextOriginZip, editTextAddress, editTextMail;
    private String[] spinnerValues = {};
    private String selectedValue;

    private Button buttonSubmit;
    private Boolean alertDlgReturn;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userregister);
        spinnerUserType = findViewById(R.id.spinnerUserType);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        buttonSubmit.setOnClickListener(this);

        labelCompanyName = findViewById(R.id.labelCompanyName);
        //labelCompanyName.setEnabled(false);

        labelUserType = findViewById(R.id.labelUserType);
        //labelUserType.setEnabled(false);

        labelTextOriginCity = findViewById(R.id.labelTextOriginCity);
        //labelTextOriginCity.setEnabled(false);

        labelTextOriginZip = findViewById(R.id.labelTextOriginZip);
        //labelTextOriginZip.setEnabled(false);

        labelAddress = findViewById(R.id.labelAddress);
        //labelAddress.setEnabled(false);

        labelMail = findViewById(R.id.labelMail);
        //labelMail.setEnabled(false);

        editTextCompanyName = findViewById(R.id.editTextCompanyName);
        editTextOriginCity = findViewById(R.id.editTextOriginCity);
        editTextOriginZip = findViewById(R.id.editTextOriginZip);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextMail = findViewById(R.id.editTextMail);

        new GetDataForSpinnerUserType().execute();




        spinnerUserType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                // Get the selected item
                selectedValue = (String) spinnerUserType.getItemAtPosition(pos);
                Toast.makeText(UserRegister.this, "Selected: " + selectedValue, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }



    private class GetDataForSpinnerUserType extends AsyncTask<Void, Void, String> {

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
            ArrayAdapter<String> adapter = new ArrayAdapter<>(UserRegister.this, android.R.layout.simple_spinner_item, spinnerValues);

            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // Apply the adapter to the spinner
            spinnerUserType.setAdapter(adapter);



        }
    }

    @Override
    public void onClick(View view) {

        if (null != editTextCompanyName.getText() && null != editTextOriginCity.getText()
                && null != editTextOriginZip.getText()) {
            new PostDataToDatabase().execute();

        } else {
            Toast.makeText(UserRegister.this, "Input data in all available fields", Toast.LENGTH_LONG).show();

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

                String editTextCompanyNameC = String.valueOf(editTextCompanyName.getText());
                String editTextOriginCityC = String.valueOf(editTextOriginCity.getText());
                String editTextOriginZipC = String.valueOf(editTextOriginZip.getText());
                String selectedValueSpinnerC = String.valueOf(selectedValue);
                String editTextAddressC = String.valueOf(editTextAddress.getText());
                String editTextMailC = String.valueOf(editTextMail.getText());


                Class.forName("com.mysql.jdbc.Driver");
                connection = (Connection) DriverManager.getConnection(
                        "jdbc:mysql://10.0.2.2/empresa",
                        "androidDBUser",
                        "0310");

                    String insertQuery = "INSERT INTO new_user_request " +
                            "( company_name, origin_city,origin_zip_code, company_type,company_address,company_mail) " +
                            "VALUES (?,?,?,?,?,?)";

                    preparedStatement = connection.prepareStatement(insertQuery);
                    preparedStatement.setString(1, editTextCompanyNameC);
                    preparedStatement.setString(2, editTextOriginCityC);
                    preparedStatement.setString(3, editTextOriginZipC);
                    preparedStatement.setString(4, selectedValueSpinnerC);
                    preparedStatement.setString(5, editTextAddressC);
                    preparedStatement.setString(6, editTextMailC);


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
                ShowAlertDialog alertObj = new ShowAlertDialog();


                alertObj.showAlertDialogOkOnly(UserRegister.this,"Request received", "Within the next hours you will receive a confirmation mail regarding your application." +
                        "\n" + "Our team might get in contact in case further information is needed." +
                        "\n" +"If your application is successful you will receive your credentials in your inbox");

            } else if (!completadoOK) {
                Toast.makeText(UserRegister.this, "Password or user is already in use", Toast.LENGTH_LONG).show();

            }

        }
    }
    private void showAlertDialog(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Positive button click
                        // Close the dialog
                        dialog.dismiss();
                        Intent intent = new Intent(UserRegister.this, UserLoginRegister.class);
                startActivity(intent);

                    }
                })
                /*.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Negative button click
                        dialog.dismiss(); // Close the dialog
                    }
                })*/
                .show();
    }


}
