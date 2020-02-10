package com.packagename.myapp.services.interfaces;

import java.io.InputStream;
import java.util.List;

public interface IClothesClassifier {

    List<String> getClothingAttributes(InputStream image);

}
