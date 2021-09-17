package com.example.wheatherapiapp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public class WeatherReportModel {
    private long id;

    @ToString.Include(name = "State: ")
    private String weather_state_name;
    private String weather_state_abbr;
    private String wind_direction_compass;
    private String created;
    @ToString.Include(name = "Date: ")
    private String applicable_date;

    @ToString.Include(name = "Min temp: ")
    private long min_temp;
    @ToString.Include(name = "Max temp: ")
    private long max_temp;
    @ToString.Include(name = "Temp: ")
    private long the_temp;
    private long wind_speed;
    private long wind_direction;

    private int air_pressure;
    private int humidity;

    private long visibility;
    private int predictability;





}
