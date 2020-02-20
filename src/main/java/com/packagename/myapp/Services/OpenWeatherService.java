package com.packagename.myapp.Services;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.packagename.myapp.Services.Interfaces.IWeatherService;
import com.packagename.myapp.Utils.KeyHolder;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class OpenWeatherService implements IWeatherService {

    private final String key = KeyHolder.getKey("openweather.key");
    private final String baseUrl = "https://api.openweathermap.org/data/2.5/weather";

    @Override
    public Map<String, String> getWeather(String zipCode) {
        Map<String,String> weather = new HashMap<String,String>();

        try {
            Gson gson = new Gson();

            HttpClient client = HttpClients.createDefault();

            HttpGet request = new HttpGet(baseUrl+"?zip="+zipCode+",us&appid="+key+"&units=imperial");

            HttpResponse response = client.execute(request);
            String responseBody = IOUtils.toString(response.getEntity().getContent(), StandardCharsets.UTF_8);

            weather.put("Temp",gson.fromJson(responseBody, JsonObject.class).getAsJsonObject("main").getAsJsonPrimitive("temp").getAsString());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return weather;
    }
}
