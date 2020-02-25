package com.packagename.myapp.NNDataGenerator;

import com.packagename.myapp.Services.IBMClothesClassifier;

import java.io.*;
import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.*;

public class Generator {

    enum colors{Red, Blue, Green, Yellow, Black, White}

    IBMClothesClassifier cc;
    Map<String,String> attributes;
    static List<String> Shirts;
    static List<String> Pants;
    static List<List> OutfitMatrix;

    File Clothes;

    public Generator() {
        Clothes = new File("Clothes.txt");

        attributes = new HashMap<String , String>();
        Shirts = new ArrayList<String>();
        Pants = new ArrayList<String>();
        OutfitMatrix = new ArrayList<List>();
        PopulateArrays();
    }

    //given a folder path, will run through all images and populate Clothes file
    //will overwrite existing document
    public void RunGenerator(String Path) {
        File path = new File(Path);
        FileInputStream fileInputStream;
        File [] files = path.listFiles();

        for (int i = 0; i < files.length; i++){
            if (files[i].isFile()){
                try{
                    fileInputStream = new FileInputStream(files[i]);
                    AddImages(fileInputStream);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        }
        //uploads the new arrays to Clothes.txt
        UploadToDoc();
    }

    public void PopulateArrays(){
        if(Clothes.length() != 0) {

            //Initializes arrays if read data already exists
            try {
                Scanner sc = new Scanner(Clothes);
                String s;
                s = sc.nextLine();
                if (s == "Pants") {
                    s = sc.nextLine();
                    while (s != "Shirt") {
                        Pants.add(s);
                        s = sc.nextLine();
                    }
                    while (sc.hasNext()) {
                        s = sc.nextLine();
                        Shirts.add(s);
                    }
                }
                sc.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void AddImages(InputStream image){

        attributes = cc.getClothingAttributes(image);
        if(attributes.containsKey("Type")){
            if(attributes.get("ClothModel").equals("Pants") || attributes.get("ClothModel").equals("Shorts")){
                Pants.add(attributes.get("ColorModel"));
            }else if(attributes.get("ClothModel").equals("Shirt") || attributes.get("ClothModel").equals("LongSleeveShirt")){
                Shirts.add(attributes.get("ColorModel"));

            }
        }

    }

    public void UploadToDoc() {
        try{
            FileWriter writer = new FileWriter(Clothes);
            writer.write("Pants");
            for (Iterator it = Pants.iterator(); it.hasNext(); ) {
                writer.write((String) it.next());
            }

            writer.write("Shirt");
            for (Iterator it = Shirts.iterator(); it.hasNext(); ) {
                writer.write((String) it.next());
            }

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    //Returns a matrix of randomized outfits based on the inputted data
    public List GenerateOutfits(){

        Random rand = new Random();

        while(!Pants.isEmpty() && !Shirts.isEmpty()){
            String p = Pants.get(rand.nextInt(Pants.size()));
            String s = Shirts.get(rand.nextInt(Shirts.size()));
            List<Integer> outfit = new ArrayList<Integer>();

            if(p == "White" | s=="White"){
                outfit.add(1);
            }else{
                outfit.add(0);
            }

            if(p == "Black" | s=="Black"){
                outfit.add(1);
            }else{
                outfit.add(0);
            }

            if(p == "Red" | s=="Red"){
                outfit.add(1);
            }else{
                outfit.add(0);
            }

            if(p == "Blue" | s=="Blue"){
                outfit.add(1);
            }else{
                outfit.add(0);
            }

            if(p == "Green" | s=="Green"){
                outfit.add(1);
            }else{
                outfit.add(0);
            }

            if(p == "Yellow" | s=="Yellow"){
                outfit.add(1);
            }else {
                outfit.add(0);
            }

            OutfitMatrix.add(outfit);
        }
        return  OutfitMatrix;
    }

}
