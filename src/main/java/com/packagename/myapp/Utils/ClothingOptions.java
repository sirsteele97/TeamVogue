package com.packagename.myapp.Utils;

import java.util.LinkedList;

public class ClothingOptions {

    public final static String[] topTypes = {"short sleeve shirt", "long sleeve shirt"};
    public final static String[] bottomTypes = {"shorts", "pants", "skirts"};
    public final static String[] shoeTypes = {"shoes"};
    public final static String[] clothingTypes = combineTypes(topTypes,bottomTypes,shoeTypes);

    public final static String[] colors = {"red", "green", "blue", "black", "white"};

    public final static String[] patterns = {"plain", "plaid", "striped", "dotted"};

    public static boolean validClothingType(String[] clothingArray, String type){
        for(String element : clothingArray){
            if(element.equalsIgnoreCase(type)){
                return true;
            }
        }
        return false;
    }

    private static String[] combineTypes(String[]... types){
        LinkedList<String> combinedTypesList = new LinkedList<String>();

        for(String[] type : types){
            for(String element : type){
                combinedTypesList.add(element);
            }
        }

        return combinedTypesList.toArray(new String[0]);
    }

}
