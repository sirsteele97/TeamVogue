package com.packagename.myapp.NNDataGenerator;

import com.packagename.myapp.Services.IBMClothesClassifier;
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
    static List<String> Shirts;
    static List<String> Pants;
    static List<String> Dress;
    static List<String> Shoes;
    static List<List> OutfitMatrix;

    File Backup;

    public OutfitTestGenerator() {
        Backup = new File("src/main/java/com/packagename/myapp/NNDataGenerator/BackupClothes");
        cc = new IBMClothesClassifier();
        attributes = new HashMap<String , String>();
        Shirts = new ArrayList<String>();
        Pants = new ArrayList<String>();
        Dress = new ArrayList<String>();
        Shoes = new ArrayList<String>();
        OutfitMatrix = new ArrayList<List>();

    }

    //given a folder path, will run through all images and populate Clothes file
    //will overwrite existing document
    public void RunGenerator(String Path, int StartingIndex)  {
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

    public Map<String, String> Convert(String str) {
        String s = str.substring(1, str.length()-1);
        String[] tokens = s.split(", |=");
        Map<String, String> map = new HashMap<>();
        for (int i=0; i<tokens.length-1; ) map.put(tokens[i++], tokens[i++]);
        return map;
    }

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

}
