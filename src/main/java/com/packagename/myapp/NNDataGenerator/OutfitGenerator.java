package com.packagename.myapp.NNDataGenerator;

import com.packagename.myapp.Services.IBMClothesClassifier;

import java.io.*;
import java.util.*;

public class OutfitGenerator {

    static List<Map<String,String>> ClothingItems;
    List<Integer> Outfit;

    private int OutfitSize = 5;

    public OutfitGenerator() {
        ClothingItems = new ArrayList<Map<String,String>>();
        Outfit = new ArrayList<Integer>();
    }

    private void InitializeOutfitArray(){
        Outfit.clear();
        for(int i = 0; i < OutfitSize; i++){
            Outfit.add(i, 0);
        }
    }

    private void AddImages(Map<String,String> ClothingItem) {
        ClothingItems.add(ClothingItem);
    }

    private void ClearImages() {
        ClothingItems.clear();
    }

    //Returns a matrix of randomized outfits based on the inputted data
    public void GetOutfit() {
        InitializeOutfitArray();
        for(int i = 0; i < ClothingItems.size(); i++){
            for(int j = 0; j < OutfitSize; i++){
                if(ClothingItems.get(i).get("ColorModel").equals("white")){
                    Outfit.set(j, 1);
                }
                if(ClothingItems.get(i).get("ColorModel").equals("black")){
                    Outfit.set(j, 1);
                }
                if(ClothingItems.get(i).get("ColorModel").equals("red")){
                    Outfit.set(j, 1);
                }
                if(ClothingItems.get(i).get("ColorModel").equals("blue")){
                    Outfit.set(j, 1);
                }
                if(ClothingItems.get(i).get("ColorModel").equals("green")){
                    Outfit.set(j, 1);
                }
            }

        }

    }

}
