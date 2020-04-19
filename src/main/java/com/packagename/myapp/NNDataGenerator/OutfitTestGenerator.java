package com.packagename.myapp.NNDataGenerator;

import com.packagename.myapp.Services.DiscoveryClothesStorage;
import com.packagename.myapp.Services.IBMClothesClassifier;
import com.packagename.myapp.Services.Interfaces.IClothesStorage;
import com.packagename.myapp.Services.Interfaces.IWeatherService;
import com.packagename.myapp.Services.OpenWeatherService;
import com.packagename.myapp.Utils.SessionData;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.URL;
import java.util.*;

public class OutfitTestGenerator {

    enum colors{Red, Blue, Green, Yellow, Black, White}

    IBMClothesClassifier cc;
    Map<String,String> attributes;
    static List<Map<String,String>> Shirts;
    static List<Map<String,String>> Pants;
    static List<Map<String,String>> Dress;
    static List<Map<String,String>> Shoes;
    static List<List> OutfitMatrix;

    File Backup;

    public OutfitTestGenerator() {
        Backup = new File("src/main/java/com/packagename/myapp/NNDataGenerator/BackupClothes");
        cc = new IBMClothesClassifier();
        attributes = new HashMap<String , String>();
        Shirts = new ArrayList<Map<String,String>>();
        Pants = new ArrayList<Map<String,String>>();
        Dress = new ArrayList<Map<String,String>>();
        Shoes = new ArrayList<Map<String,String>>();
        OutfitMatrix = new ArrayList<List>();

    }

    //given a folder path, will run through all images and populate Clothes file
    //will overwrite existing document
 /*   public void RunGenerator(String Path, int StartingIndex)  {
        File path = new File(Path);

        try{
            File [] files = path.listFiles();

            for (int i = StartingIndex; i < files.length; i++){
                if (files[i].isFile()){
                    AddTestImages(new FileInputStream(files[i]));
                    System.out.println("Processed image:" + i);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
*/
    public List<Map<String,String>> RunGenerator(IClothesStorage dcs)  {
        Map<String, Map<String, String>> items = dcs.getClothes("", "", "", SessionData.getAttribute("Username"));

        for (Map<String,String> clothingItem: items.values()) {
            if(clothingItem.get("ClothModel").equals("short sleeve shirt")){
                Shirts.add(clothingItem);
            }else if(clothingItem.get("ClothModel").equals("long sleeve shirt")){
                Shirts.add(clothingItem);
            }else if(clothingItem.get("ClothModel").equals("shorts")){
                Pants.add(clothingItem);
            }else if(clothingItem.get("ClothModel").equals("skirt")){
                Pants.add(clothingItem);
            }else if(clothingItem.get("ClothModel").equals("pants")){
                Pants.add(clothingItem);
            }else if(clothingItem.get("ClothModel").equals("dress")){
                Dress.add(clothingItem);
            }else if(clothingItem.get("ClothModel").equals("shoes")){
                Shoes.add(clothingItem);
            }
        }

        Random rand = new Random();
        Random rand2 = new Random();
        List<Map<String,String>> clothingItems = new ArrayList<Map<String,String>>();

        if(rand2.nextInt(2) > 1 || Dress.isEmpty()){
            if(!Shirts.isEmpty() && !Shoes.isEmpty() && !Pants.isEmpty()){
                clothingItems.add(Shirts.get(rand.nextInt(Shirts.size())));
                clothingItems.add(Pants.get(rand.nextInt(Pants.size())));
                clothingItems.add(Shoes.get(rand.nextInt(Shoes.size())));
            }
        }else{
            clothingItems.add(Dress.get(rand.nextInt(Shirts.size())));
            clothingItems.add(Shoes.get(rand.nextInt(Shoes.size())));
        }

        return clothingItems;
    }

    public List<Map<String,String>> RunGeneratorWithWeather(IClothesStorage dcs)  {
        Map<String, Map<String, String>> items = dcs.getClothes("", "", "", SessionData.getAttribute("Username"));

        IWeatherService ws = new OpenWeatherService();
        Map<String, String> weather =  ws.getWeather(SessionData.getAttribute("ZipCode"));

        double temp = Double.valueOf(weather.get("Temp"));
        boolean isCold = temp <= 45;
        for (Map<String,String> clothingItem: items.values()) {
            if(clothingItem.get("ClothModel").equals("short sleeve shirt") && !isCold){
                Shirts.add(clothingItem);
            }else if(clothingItem.get("ClothModel").equals("long sleeve shirt")){
                Shirts.add(clothingItem);
            }else if(clothingItem.get("ClothModel").equals("shorts") && !isCold){
                Pants.add(clothingItem);
            }else if(clothingItem.get("ClothModel").equals("skirt") && !isCold){
                Pants.add(clothingItem);
            }else if(clothingItem.get("ClothModel").equals("pants")){
                Pants.add(clothingItem);
            }else if(clothingItem.get("ClothModel").equals("dress") && !isCold){
                Dress.add(clothingItem);
            }else if(clothingItem.get("ClothModel").equals("shoes")){
                Shoes.add(clothingItem);
            }
        }

        Random rand = new Random();
        Random rand2 = new Random();
        List<Map<String,String>> clothingItems = new ArrayList<Map<String,String>>();

        if(rand2.nextInt(2) > 1 || Dress.isEmpty()){
            if(!Shirts.isEmpty() && !Shoes.isEmpty() && !Pants.isEmpty()){
                clothingItems.add(Shirts.get(rand.nextInt(Shirts.size())));
                clothingItems.add(Pants.get(rand.nextInt(Pants.size())));
                clothingItems.add(Shoes.get(rand.nextInt(Shoes.size())));
            }
        }else{
            clothingItems.add(Dress.get(rand.nextInt(Shirts.size())));
            clothingItems.add(Shoes.get(rand.nextInt(Shoes.size())));
        }

        return clothingItems;
    }

    public Map<String, String> Convert(String str) {
        String s = str.substring(1, str.length()-1);
        String[] tokens = s.split(", |=");
        Map<String, String> map = new HashMap<>();
        for (int i=0; i<tokens.length-1; ) map.put(tokens[i++], tokens[i++]);
        return map;


    }
/*
    //call only to refill the test data
    public void PopulateTestArrays(){
        Map<String, String> clothingItem = new HashMap<String, String>();
        if(Backup.length() != 0) {
            //Initializes arrays if read data already exists
            try {
                Scanner sc = new Scanner(Backup);
                String s;
                while(sc.hasNext()){
                    s = sc.nextLine();
                    clothingItem = Convert(s);
                    if(clothingItem.get("ClothModel").equals("short sleeve shirt")){
                        Shirts.add(clothingItem.get("ColorModel"));
                    }else if(clothingItem.get("ClothModel").equals("long sleeve shirt")){
                        Shirts.add(clothingItem.get("ColorModel"));
                    }else if(clothingItem.get("ClothModel").equals("shorts")){
                        Pants.add(clothingItem.get("ColorModel"));
                    }else if(clothingItem.get("ClothModel").equals("skirt")){
                        Pants.add(clothingItem.get("ColorModel"));
                    }else if(clothingItem.get("ClothModel").equals("pants")){
                        Pants.add(clothingItem.get("ColorModel"));
                    }else if(clothingItem.get("ClothModel").equals("dress")){
                        Dress.add(clothingItem.get("ColorModel"));
                    }else if(clothingItem.get("ClothModel").equals("shoes")){
                        Shoes.add(clothingItem.get("ColorModel"));
                    }
                }
                sc.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void AddTestImages(InputStream image){
        cc = new IBMClothesClassifier();
        attributes = new HashMap<>();//cc.getClothingAttributes(image);

        if(!attributes.isEmpty()){
            try{
                PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(Backup, true)));
                writer.println(attributes.toString());
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //Returns a matrix of randomized outfits based on the inputted data
    public List GenerateOutfits(int num){

        String p = "";
        String s = "";
        String sh = "";
        String d = "";

        for(int i = 0; i < num; i++){
            Random rand = new Random();
            Random rand2 = new Random();
            if(rand2.nextInt(8) > 3 || Dress.isEmpty()) {
                 p = Pants.get(rand.nextInt(Pants.size()));
                 s = Shirts.get(rand.nextInt(Shirts.size()));
                 sh = Shoes.get(rand.nextInt(Shoes.size()));
            }else{
                 sh = Shoes.get(rand.nextInt(Pants.size()));
                 d = Dress.get(rand.nextInt(Shirts.size()));
            }

            List<Integer> outfit = new ArrayList<Integer>();

            if(p.equals("red") || s.equals("red") || sh.equals("red")|| d.equals("red")){
                outfit.add(1);
            }else{
                outfit.add(0);
            }

            if(p.equals("green") || s.equals("green") || sh.equals("green")|| d.equals("green")){
                outfit.add(1);
            }else{
                outfit.add(0);
            }

            if(p.equals("blue") || s.equals("blue") || sh.equals("blue")|| d.equals("blue")){
                outfit.add(1);
            }else{
                outfit.add(0);
            }

            if(p.equals("black") || s.equals("black") || sh.equals("black")|| d.equals("black")){
                outfit.add(1);
            }else{
                outfit.add(0);
            }

            if(p.equals("white") || s.equals("white") || sh.equals("white")|| d.equals("white")){
                outfit.add(1);
            }else{
                outfit.add(0);
            }

            OutfitMatrix.add(outfit);
        }
        return  OutfitMatrix;
    }
*/
}
