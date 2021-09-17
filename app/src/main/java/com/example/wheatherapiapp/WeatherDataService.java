package com.example.wheatherapiapp;

import android.content.Context;

import androidx.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WeatherDataService {
    Context context;
    String cityID;


    public WeatherDataService(Context context) {
        this.context = context;
    }

    @NonNull
    private String getLocateByCityName() {
        return "https://www.metaweather.com/api/location/search/?query=";
    }

    @NonNull
    private String getWeatherByCityID() {
        return "https://www.metaweather.com/api/location/";
    }

    //call back
    public interface volleyResponseListener {

        void onError(String message);

        void onResponse(String cityID);
    }
    public interface forecastByIDResponse {

        void onError(String message);

        void onResponse(List<WeatherReportModel> weatherReportModelList);
    }



    public String getCityID(String cityName, volleyResponseListener volleyResponseListener) {
        String url = getLocateByCityName() + cityName;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                cityID = "";
                try {

                    JSONObject cityInfo = response.getJSONObject(0);
                    cityID = cityInfo.getString("woeid");

                } catch (JSONException e) {

                    e.printStackTrace();
                }
                volleyResponseListener.onResponse(cityID);
            }
        }, error -> volleyResponseListener.onError("Something wrong"));


        Singleton.getInstance(context).addToRequestQueue(request);

        return cityID;
    }



    public void getCityForecastByID(String cityID, forecastByIDResponse forecastByIDResponse) {

        String url = getWeatherByCityID() + cityID;
        List<WeatherReportModel> weatherReportModels = new ArrayList<>();

        // get the json object
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {



                try {
                    JSONArray consolidated_weather_list = response.getJSONArray("consolidated_weather");

                    //get the first day in the array

                    if(consolidated_weather_list == null){
                        System.err.println("null list");
                    }

                    int length = consolidated_weather_list.length();

                    for(int i = 0; i < length; i++) {

                        JSONObject first_day_from_api = (JSONObject) consolidated_weather_list.get(i);
                        WeatherReportModel first_day = new WeatherReportModel();

                        first_day.setId(first_day_from_api.getLong("id"));

                        first_day.setWeather_state_name(first_day_from_api.getString("weather_state_name"));
                        first_day.setWeather_state_abbr(first_day_from_api.getString("weather_state_abbr"));
                        first_day.setWind_direction_compass(first_day_from_api.getString("wind_direction_compass"));
                        first_day.setCreated(first_day_from_api.getString("created"));
                        first_day.setApplicable_date(first_day_from_api.getString("applicable_date"));
                        first_day.setApplicable_date(first_day_from_api.getString("applicable_date"));

                        first_day.setMin_temp(first_day_from_api.getLong("min_temp"));
                        first_day.setMax_temp(first_day_from_api.getLong("max_temp"));
                        first_day.setThe_temp(first_day_from_api.getLong("the_temp"));
                        first_day.setWind_speed(first_day_from_api.getLong("wind_speed"));
                        first_day.setWind_direction(first_day_from_api.getLong("wind_direction"));

                        first_day.setAir_pressure(first_day_from_api.getInt("air_pressure"));
                        first_day.setHumidity(first_day_from_api.getInt("humidity"));

                        first_day.setVisibility(first_day_from_api.getLong("visibility"));
                        first_day.setPredictability(first_day_from_api.getInt("predictability"));

                        weatherReportModels.add(first_day);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                forecastByIDResponse.onResponse(weatherReportModels);




            }


        }, error -> forecastByIDResponse.onError("Something wrong"));
        Singleton.getInstance(context).addToRequestQueue(request);
    }

//    public List<WeatherReportModel> getCityForecastByName(String cityName){
//
//    }
}
