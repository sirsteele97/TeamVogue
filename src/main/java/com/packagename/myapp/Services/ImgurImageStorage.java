package com.packagename.myapp.Services;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.packagename.myapp.Services.Interfaces.IImageStorage;
import com.packagename.myapp.Utils.KeyHolder;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class ImgurImageStorage implements IImageStorage {

    private final String baseUrl = "https://api.imgur.com/3";
    private final String clientId = KeyHolder.getKey("imgur.clientId");

    public Map<String,String> uploadImage(InputStream inputStream, String fileName) {
        Map<String,String> uploadResults = new HashMap<String,String>();

        try {
            byte[] imageBytes = IOUtils.toByteArray(inputStream);
            String base64 = Base64.encodeBase64String(imageBytes);

            Gson gson = new Gson();
            Map<String,String> bodyMap = new HashMap<String,String>();
            bodyMap.put("image",base64);
            String json = gson.toJson(bodyMap);

            HttpClient client = HttpClients.createDefault();

            HttpPost request = new HttpPost(baseUrl+"/image");
            request.addHeader("Authorization","Client-ID "+ clientId);
            StringEntity image = new StringEntity(json, ContentType.APPLICATION_JSON);
            request.setEntity(image);

            HttpResponse response = client.execute(request);

            String responseBody = IOUtils.toString(response.getEntity().getContent(), StandardCharsets.UTF_8);
            uploadResults.put("ImageLink",gson.fromJson(responseBody, JsonObject.class).getAsJsonObject("data").getAsJsonPrimitive("link").getAsString());
            uploadResults.put("DeleteKey",gson.fromJson(responseBody, JsonObject.class).getAsJsonObject("data").getAsJsonPrimitive("deletehash").getAsString());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return uploadResults;
    }

    public void deleteImage(String deleteKey) {
        try {
            HttpClient client = HttpClients.createDefault();

            HttpDelete request = new HttpDelete(baseUrl+"/image/"+deleteKey);
            request.addHeader("Authorization","Client-ID "+clientId);

            client.execute(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
