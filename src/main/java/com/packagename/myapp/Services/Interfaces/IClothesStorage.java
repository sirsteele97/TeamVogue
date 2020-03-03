package com.packagename.myapp.Services.Interfaces;

import java.util.Map;

public interface IClothesStorage {

    Map<String,Map<String,String>> getClothes(String clothesParam, String colorParam);

    void addClothing(String json);

    void deleteClothing(String docId);
}
