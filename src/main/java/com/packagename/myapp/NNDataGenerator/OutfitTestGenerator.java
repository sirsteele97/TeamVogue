package com.packagename.myapp.NNDataGenerator;

import com.packagename.myapp.Services.IBMClothesClassifier;

import java.io.*;
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

    File Clothes;

    public OutfitTestGenerator() {
        Clothes = new File("Clothes.txt");

        attributes = new HashMap<String , String>();
        Shirts = new ArrayList<String>();
        Pants = new ArrayList<String>();
        Dress = new ArrayList<String>();
        Shoes = new ArrayList<String>();
        OutfitMatrix = new ArrayList<List>();
        PopulateTestArrays();
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
                    AddTestImages(fileInputStream);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        }
        //uploads the new arrays to Clothes.txt
        UploadToDoc();
    }

    //call only to refill the test data
    public void PopulateTestArrays(){
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
                    //populates shirts
                    while (s != "Shoes") {
                        Shirts.add(s);
                        s = sc.nextLine();
                    }
                    //populates shoes
                    while (s != "Dress") {
                        Shoes.add(s);
                        s = sc.nextLine();
                    }
                    //populates Dresses
                    while (sc.hasNext()) {
                        s = sc.nextLine();
                        Dress.add(s);
                    }
                }
                sc.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void AddTestImages(InputStream image){

        attributes = cc.getClothingAttributes(image);
        if(attributes.containsKey("Type")){
            if(attributes.get("ClothModel").toLowerCase().equals("pants") ||
                    attributes.get("ClothModel").toLowerCase().equals("shorts") ||
                    attributes.get("ClothModel").toLowerCase().equals("skirt")){

                Pants.add(attributes.get("ColorModel"));

            }else if(attributes.get("ClothModel").toLowerCase().equals("shirt") ||
                    attributes.get("ClothModel").toLowerCase().equals("long sleeve shirt")){

                Shirts.add(attributes.get("ColorModel"));
            }else if(attributes.get("ClothModel").toLowerCase().equals("shoes")){

                Shoes.add(attributes.get("ColorModel"));
            }else if(attributes.get("ClothModel").toLowerCase().equals("dress")){

                Dress.add(attributes.get("ColorModel"));
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
            writer.write("Shoes");
            for (Iterator it = Shoes.iterator(); it.hasNext(); ) {
                writer.write((String) it.next());
            }
            writer.write("Dress");
            for (Iterator it = Dress.iterator(); it.hasNext(); ) {
                writer.write((String) it.next());
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
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
            if(rand2.nextInt(8) > 3) {
                 p = Pants.get(rand.nextInt(Pants.size()));
                 s = Shirts.get(rand.nextInt(Shirts.size()));
                 sh = Shoes.get(rand.nextInt(Pants.size()));
            }else{
                 sh = Shoes.get(rand.nextInt(Pants.size()));
                 d = Dress.get(rand.nextInt(Shirts.size()));
            }

            List<Integer> outfit = new ArrayList<Integer>();

            if(p == "White" || s=="White" || sh=="White"|| d=="White"){
                outfit.add(1);
            }else{
                outfit.add(0);
            }

            if(p == "Black" || s=="Black" || sh=="Black"|| d=="Black"){
                outfit.add(1);
            }else{
                outfit.add(0);
            }

            if(p == "Red" || s=="Red" || sh=="Red"|| d=="Red"){
                outfit.add(1);
            }else{
                outfit.add(0);
            }

            if(p == "Blue" || s=="Blue" || sh=="Blue"|| d=="Blue"){
                outfit.add(1);
            }else{
                outfit.add(0);
            }

            if(p == "Green" || s=="Green" || sh=="Green"|| d=="Green"){
                outfit.add(1);
            }else{
                outfit.add(0);
            }

            OutfitMatrix.add(outfit);
        }
        return  OutfitMatrix;
    }

}
