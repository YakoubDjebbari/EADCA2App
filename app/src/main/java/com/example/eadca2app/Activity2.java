package com.example.eadca2app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Activity2 extends AppCompatActivity {

    public static final String QUERY_FOR_Book_ID = "https://ca2service20220424232144.azurewebsites.net/api";
    TextView libraryList;
    EditText userInput;
    Button button1;
    Button button2;

    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        libraryList = (TextView) findViewById(R.id.libraryList);
        userInput = (EditText) findViewById(R.id.userInput);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);

        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                getLibraryInfo();
            }
        });



        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openMainActivity();
            }
        });



    }


    public void getLibraryInfo(){
        RequestQueue queue = Volley.newRequestQueue(Activity2.this);
        String url = QUERY_FOR_Book_ID + "/Books/" + userInput.getText().toString();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String libraryId = "";

                try {
                    libraryId = response.getString("libraryID");
                    String url2 = QUERY_FOR_Book_ID + "/Libraries/" + libraryId;

                    JsonObjectRequest request2 = getLibraryTableInfo(url2);
                    queue.add(request2);


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Activity2.this, "Error", Toast.LENGTH_SHORT).show();
            }
        }
        );

        queue.add(request);
    };


    public JsonObjectRequest getLibraryTableInfo(String url){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String libraryInfo = "";
                try {
                    libraryInfo = response.getString("libraryName") + "\t\t\t\t\t\t " + response.getString("libraryAddress");
                    libraryList.setText(libraryInfo);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Activity2.this, "Error", Toast.LENGTH_SHORT).show();
            }
        }
        );
        return request;
    };

}