package com.packagename.myapp.Services.Interfaces;

import java.io.InputStream;
import java.util.Map;

public interface IClothesClassifier {

    Map<String, String> getClothingAttributes(InputStream image);

}
