package com.packagename.myapp.NNDataGenerator;

import com.packagename.myapp.Services.IBMClothesClassifier;

import java.io.*;
import java.util.*;

public class OutfitGenerator {

    enum colors {Red, Blue, Green, Yellow, Black, White}

    IBMClothesClassifier cc;
    Map<String, String> attributes;
    static List<String> Shirts;
    static List<String> Pants;
    static List<String> Dress;
    static List<String> Shoes;
    static List<Integer> Outfit;

    public OutfitGenerator() {

        attributes = new HashMap<String, String>();
        Shirts = new ArrayList<String>();
        Pants = new ArrayList<String>();
        Dress = new ArrayList<String>();
        Shoes = new ArrayList<String>();
        Outfit = new ArrayList<Integer>();
    }

    private void AddImages(InputStream image) {

        attributes = cc.getClothingAttributes(image);
        if (attributes.containsKey("Type")) {
            if (attributes.get("ClothModel").toLowerCase().equals("pants") ||
                    attributes.get("ClothModel").toLowerCase().equals("shorts") ||
                    attributes.get("ClothModel").toLowerCase().equals("skirt")) {

                Pants.add(attributes.get("ColorModel"));

            } else if (attributes.get("ClothModel").toLowerCase().equals("shirt") ||
                    attributes.get("ClothModel").toLowerCase().equals("long sleeve shirt")) {

                Shirts.add(attributes.get("ColorModel"));
            } else if (attributes.get("ClothModel").toLowerCase().equals("shoes")) {

                Shoes.add(attributes.get("ColorModel"));
            } else if (attributes.get("ClothModel").toLowerCase().equals("dress")) {

                Dress.add(attributes.get("ColorModel"));
            }
        }
    }

    //Returns a matrix of randomized outfits based on the inputted data
    public void GetOutfit() {

        String p = "";
        String s = "";
        String sh = "";
        String d = "";

        List<Integer> outfit = new ArrayList<Integer>();

        if (p == "White" || s == "White" || sh == "White" || d == "White") {
            outfit.add(1);
        } else {
            outfit.add(0);
        }

        if (p == "Black" || s == "Black" || sh == "Black" || d == "Black") {
            outfit.add(1);
        } else {
            outfit.add(0);
        }

        if (p == "Red" || s == "Red" || sh == "Red" || d == "Red") {
            outfit.add(1);
        } else {
            outfit.add(0);
        }

        if (p == "Blue" || s == "Blue" || sh == "Blue" || d == "Blue") {
            outfit.add(1);
        } else {
            outfit.add(0);
        }

        if (p == "Green" || s == "Green" || sh == "Green" || d == "Green") {
            outfit.add(1);
        } else {
            outfit.add(0);
        }

    }

}
