package com.packagename.myapp.services.interfaces;

import java.io.InputStream;
import java.util.Map;

public interface IClothesClassifier {

    Map<String, String> getClothingAttributes(InputStream image);

}
