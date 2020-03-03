package com.packagename.myapp.NNDataGenerator;

import com.packagename.myapp.Services.IBMClothesClassifier;

import java.io.*;
import java.util.*;

public class OutfitGenerator {

    List<Map<String,String>> ClothingItems;
    double[] Outfit;

    private int OutfitSize = 5;

    public OutfitGenerator() {
        ClothingItems = new ArrayList<Map<String,String>>();
        Outfit = new double[6];
    }

    public void InitializeOutfitArray(){
        Outfit = new double[6];
        for(int i = 0; i < OutfitSize; i++){
            Outfit[i] =  0.;
        }
    }

    public void AddImages(Map<String,String> ClothingItem) {
        ClothingItems.add(ClothingItem);
    }

    public void ClearImages() {
        ClothingItems.clear();
    }

    //Returns a matrix of randomized outfits based on the inputted data
    public double[] GetOutfit() {
        InitializeOutfitArray();
        for(int i = 0; i < ClothingItems.size()-1; i++){
            for(int j = 0; j < OutfitSize; j++){
                if(ClothingItems.get(i).get("ColorModel").equals("red")){
                    Outfit[j] = 1.;
                }
                if(ClothingItems.get(i).get("ColorModel").equals("green")){
                    Outfit[j] = 1.;
                }
                if(ClothingItems.get(i).get("ColorModel").equals("blue")){
                    Outfit[j] = 1.;
                }
                if(ClothingItems.get(i).get("ColorModel").equals("black")){
                    Outfit[j] = 1.;
                }
                if(ClothingItems.get(i).get("ColorModel").equals("white")){
                    Outfit[j] = 1.;
                }
            }

        }
        return this.Outfit;
    }

}
