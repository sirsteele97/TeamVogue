package com.packagename.myapp;

import java.io.InputStream;
import java.util.Map;

public interface IClothesClassifier {

    Map<String, String> getClothingAttributes(InputStream image);

}
