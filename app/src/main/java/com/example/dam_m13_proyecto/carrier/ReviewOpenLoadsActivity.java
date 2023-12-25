package com.example.dam_m13_proyecto.carrier;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dam_m13_proyecto.R;
import com.example.dam_m13_proyecto.adapter.ListAdapter;
import com.example.dam_m13_proyecto.adapter.ListElement;
import com.example.dam_m13_proyecto.adapter.ListLoadAdapter;
import com.example.dam_m13_proyecto.adapter.ListLoadElement;
import com.example.dam_m13_proyecto.session.SignInActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ReviewOpenLoadsActivity extends AppCompatActivity {

    private DrawerLayout drawer;

    private Button buttonCreateLoad,buttonReviewCarrierRequests,buttonReviewOpenLoads;

    List<ListLoadElement> elements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviewopenloadsactivity);

        SharedPreferences preferences = getSharedPreferences("user_preferences", Context.MODE_PRIVATE);
        // Retrieving the user ID, defaultValue can be an empty string or any default value you prefer
        String userId = preferences.getString("user_id", null);
        new GetDataFromDatabase().execute();
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

                SharedPreferences preferences = getSharedPreferences("user_preferences", Context.MODE_PRIVATE);
                // Retrieving the user ID, defaultValue can be an empty string or any default value you prefer
                String userId = preferences.getString("user_id", null);

                // Crea una declaración SQL y ejecuta la consulta
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT " +
                        "origin_address, origin_city, origin_city, destination_address, destination_city, status " +
                        "FROM new_loads where " + "id_user ='" + userId +"'");

                // Procesa los resultados
                while (resultSet.next()) {
                    // Obtén los datos del resultado
                    String origin_address = resultSet.getString("origin_address");
                    String origin_city = resultSet.getString("origin_city");
                    String destination_address = resultSet.getString("destination_address");
                    String destination_city = resultSet.getString("destination_city");
                    String status = resultSet.getString("status");

                    result += origin_address + ",";
                    result += origin_city + ",";
                    result += destination_address + ",";
                    result += destination_city + ",";
                    result += status + ",";


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
            System.out.println("Llega");
            String[] values = result.split(",");

            if (values.length > 0) {
                //String origin_address,origin_city,destination_address,destination_city,status,color;

                elements = new ArrayList<>();
                for (int i = 0; i < values.length - 4; i += 5) {
                    // Assuming the values array has at least 5 elements for each iteration
                    String value0 = values[i];
                    String value1 = values[i + 1];
                    String value2 = values[i + 2];
                    String value3 = values[i + 3];
                    String value4 = values[i + 4];

                    // Create a new ListLoadElement and add it to the list
                    elements.add(new ListLoadElement(value0, value1, value2, value3, value4, "#607d8b"));
                }
                //elements.add(new ListLoadElement(values[0],values[1],values[2],values[3],values[4],"#607d8b"));


                ListLoadAdapter listAdapter = new ListLoadAdapter(elements,ReviewOpenLoadsActivity.this);
                RecyclerView recyclerView = findViewById(R.id.listOpenLoadsRecyclerView);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(ReviewOpenLoadsActivity.this));
                recyclerView.setAdapter(listAdapter);
            } else {
                Toast.makeText(getApplicationContext(), "User account not found", Toast.LENGTH_SHORT).show();

            }
        }
    }
   /* public void init(){
        elements = new ArrayList<>();
        elements.add(new ListElement("Pedro","#607d8b","Barcelona","Activo"));
        elements.add(new ListElement("Juan","#607d8b","Barcelona","Activo"));
        elements.add(new ListElement("Pedro","#607d8b","Barcelona","Activo"));
        elements.add(new ListElement("Juan","#607d8b","Barcelona","Activo"));
        elements.add(new ListElement("Pedro","#607d8b","Barcelona","Activo"));
        elements.add(new ListElement("Juan","#607d8b","Barcelona","Activo"));
        elements.add(new ListElement("Pedro","#607d8b","Barcelona","Activo"));
        elements.add(new ListElement("Juan","#607d8b","Barcelona","Activo"));
        elements.add(new ListElement("Pedro","#607d8b","Barcelona","Activo"));
        elements.add(new ListElement("Juan","#607d8b","Barcelona","Activo"));
        elements.add(new ListElement("Pedro","#607d8b","Barcelona","Activo"));
        elements.add(new ListElement("Juan","#607d8b","Barcelona","Activo"));

        ListAdapter listAdapter = new ListAdapter(elements,this);
        RecyclerView recyclerView = findViewById(R.id.listRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);

    }*/

}
