package com.example.wheatherapiapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    /*--Declare variables------*/

    Button btn_getCityId;
    Button btn_getWeatherByName;
    Button btn_getWeatherByID;

    EditText et_dataInput;

    ListView lv_weatherReports;

    WeatherDataService weatherDataService = new WeatherDataService(MainActivity.this);
    /*-----------------------*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // assign values on each control on the layout.

        btn_getCityId = findViewById(R.id.btn_getCityID);
        btn_getWeatherByID = findViewById(R.id.btn_getWeatherByID);
        btn_getWeatherByName = findViewById(R.id.btn_getWeatherByName);

        et_dataInput = findViewById(R.id.et_dataInput);
        lv_weatherReports = findViewById(R.id.lv_weatherReports);

        // set on listener for each button
        /* Button getCityID*/

        btn_getCityId.setOnClickListener(View -> {
            // Instantiate the RequestQueue.
            //RequestQueue queue = Volley.newRequestQueue(this);

            String cityID = weatherDataService.getCityID(et_dataInput.getText().toString(), new WeatherDataService.volleyResponseListener() {
                @Override
                public void onError(String message) {
                    Toast.makeText(MainActivity.this, "something wrong ", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(String cityID) {
                    Toast.makeText(MainActivity.this, "City ID =" + cityID, Toast.LENGTH_SHORT).show();
                }
            });
        });


        btn_getWeatherByID.setOnClickListener(View -> {

            weatherDataService.getCityForecastByID(et_dataInput.getText().toString(), new WeatherDataService.forecastByIDResponse() {
                @Override
                public void onError(String message) {
                    Toast.makeText(MainActivity.this, "something wrong", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(List<WeatherReportModel> weatherReportModelList) {
                   // put the entire list into listview control
                    ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, weatherReportModelList);
                    lv_weatherReports.setAdapter(arrayAdapter);
                }
            });
        });

        btn_getWeatherByName.setOnClickListener(View -> {


            Toast.makeText(MainActivity.this, "you type " + et_dataInput.getText().toString(), Toast.LENGTH_SHORT).show();
        });


    }
}