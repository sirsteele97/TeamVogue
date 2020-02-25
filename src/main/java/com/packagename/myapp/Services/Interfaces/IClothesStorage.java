package com.packagename.myapp.Services.Interfaces;

import java.util.Map;

public interface IClothesStorage {

    Map<String,Map<String,String>> getClothes(String colorParam, String clothesParam);

    void addClothing(String json);

    void deleteClothing(String docId);
}
