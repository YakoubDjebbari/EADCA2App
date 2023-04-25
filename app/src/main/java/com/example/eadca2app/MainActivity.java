package com.example.eadca2app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Button button1, button2, languageButton;
    EditText dataInput;
    ListView bookList;
    private Button button3;
    String Language;

    final WeatherDataService weatherDataService = new WeatherDataService(MainActivity.this);

    public void openActivity2() {
        Intent intent = new Intent(this, Activity2.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openActivity2();
            }
        });

        button1 = findViewById(R.id.button1);
        dataInput = findViewById(R.id.userInput);
        bookList = findViewById(R.id.bookList);
        languageButton =  findViewById(R.id.languageButton);
        Language = Locale.getDefault().getLanguage().toLowerCase(Locale.ROOT);

        languageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(Language.equals("en")) {
                    Language = "es";
                    Locale locale = new Locale(Language);
                    Locale.setDefault(locale);
                    Configuration config = new Configuration();
                    config.locale = locale;
                    getBaseContext().getResources().updateConfiguration(config,
                            getBaseContext().getResources().getDisplayMetrics());
                    recreate();
                    return;
                }
                if(Language.equals("es")) {
                    Language = "en";
                    Locale locale = new Locale(Language);
                    Locale.setDefault(locale);
                    Configuration config = new Configuration();
                    config.locale = locale;
                    getBaseContext().getResources().updateConfiguration(config,
                            getBaseContext().getResources().getDisplayMetrics());
                    recreate();
                    return;
                }
            }
        });




        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                String url = "https://ca2service20220424232144.azurewebsites.net/api/Books/";

                JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        String cityID = "";
                        JSONObject bookInfo = new JSONObject( );
                        ArrayList<String> listOfBooks = new ArrayList<>();

                        try {
                            for(int i = 0 ; i < 10 ; i++ ){
                                bookInfo = response.getJSONObject(i);
                                cityID = "";
                                cityID = bookInfo.getString("id") + " " + bookInfo.getString("bookName") + " " + bookInfo.getString("author") ;
                                listOfBooks.add(cityID);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, listOfBooks);
                        bookList.setAdapter(arrayAdapter);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });

                queue.add(request);
            }
        });

        button2 = findViewById(R.id.button2);

        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                String url = "https://ca2service20220424232144.azurewebsites.net/api/Libraries/";

                JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        String cityID = "";
                        JSONObject bookInfo = new JSONObject( );
                        ArrayList<String> listOfBooks = new ArrayList<>();

                        try {
                            for(int i = 0 ; i < 2 ; i++ ){
                                bookInfo = response.getJSONObject(i);
                                cityID = "";
                                cityID = bookInfo.getString("libraryName") + "\t\t\t " + bookInfo.getString("libraryAddress");
                                listOfBooks.add(cityID);
                            }
                            //bookInfo = response.getJSONObject(2);
                            //cityID = bookInfo.getString("bookName");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, listOfBooks);
                        bookList.setAdapter(arrayAdapter);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });

                queue.add(request);
            }
        });



    }
}