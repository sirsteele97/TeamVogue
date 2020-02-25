package com.packagename.myapp.Services.Interfaces;

import java.io.InputStream;
import java.util.Map;

public interface IImageStorage {

    Map<String,String> uploadImage(InputStream inputStream, String fileName);

    void deleteImage(String deleteKey);

}
