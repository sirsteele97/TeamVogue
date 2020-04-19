package com.packagename.myapp.NNDataGenerator;

import com.packagename.myapp.Services.DiscoveryClothesStorage;
import com.packagename.myapp.Services.IBMClothesClassifier;

import java.io.*;
import java.util.*;

public class OutfitGenerator {

    List<Map<String,String>> ClothingItems;
    double[][] Outfit;

    private int OutfitSize = 6;
    private int ParameterSize = 2;

    public OutfitGenerator() {
        ClothingItems = new ArrayList<Map<String,String>>();
        Outfit = new double[ParameterSize][OutfitSize];
    }

    public void AddOutfit(List<Map<String,String>> outfit){
        ClothingItems = outfit;
    }

    public void InitializeOutfitArray(){
        Outfit = new double[ParameterSize][OutfitSize];
        for(int i = 0; i < ParameterSize; i++){
            for(int j=0;j < OutfitSize; j++){
                Outfit[i][j] =  0.;
            }

        }
    }

    public void AddImages(Map<String,String> ClothingItem) {
        ClothingItems.add(ClothingItem);
    }

    public void ClearImages() {
        ClothingItems.clear();
    }

    //Returns a matrix of randomized outfits based on the inputted data
    public double[][] GetOutfit() {
        InitializeOutfitArray();
        for(int i = 0; i < ClothingItems.size()-1; i++){
                if(ClothingItems.get(i).get("ColorModel").equals("red")){
                    Outfit[0][0] =+ 1.;
                }
                if(ClothingItems.get(i).get("ColorModel").equals("green")){
                    Outfit[0][1] =+ 1.;
                }
                if(ClothingItems.get(i).get("ColorModel").equals("blue")){
                    Outfit[0][2] =+ 1.;
                }
                if(ClothingItems.get(i).get("ColorModel").equals("black")){
                    Outfit[0][3] =+ 1.;
                }
                if(ClothingItems.get(i).get("ColorModel").equals("white")){
                    Outfit[0][4] =+ 1.;
                }
            }

        for(int i = 0; i < ClothingItems.size()-1; i++){
            if(ClothingItems.get(i).get("PatternModel").equals("solid")){
                Outfit[1][0] =+ 1.;
            }
            if(ClothingItems.get(i).get("PatternModel").equals("stripe")){
                Outfit[1][1] =+ 1.;
            }
            if(ClothingItems.get(i).get("PatternModel").equals("dot")){
                Outfit[1][2] =+ 1.;
            }
            if(ClothingItems.get(i).get("PatternModel").equals("plaid")){
                Outfit[1][3] =+ 1.;
            }
            if(ClothingItems.get(i).get("PatternModel").equals("icon")){
                Outfit[1][4] =+ 1.;
            }
        }

        ClothingItems = new ArrayList<Map<String,String>>();
        return this.Outfit;
    }


}
