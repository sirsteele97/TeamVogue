package com.packagename.myapp.Services.Interfaces;

import java.util.Map;

public interface IWeatherService {

    Map<String,String> getWeather(String zipCode);

}
