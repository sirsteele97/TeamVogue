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

    File Clothes;
    File Backup;

    public OutfitTestGenerator() {
        Clothes = new File("src/main/java/com/packagename/myapp/NNDataGenerator/Clothes");
        Backup = new File("src/main/java/com/packagename/myapp/NNDataGenerator/BackupClothes");
        cc = new IBMClothesClassifier();
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
    public void RunGenerator(String Path)  {
        File path = new File(Path);

        try{
            File [] files = path.listFiles();

            for (int i = 0; i < files.length; i++){
                if (files[i].isFile()){
                    AddTestImages(new FileInputStream(files[i]));
                    System.out.println("Processed image:" + i);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
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
                if (s.equals("Pants")) {
                    s = sc.nextLine();
                    while (!s.equals("Shirt")) {
                        Pants.add(s);
                        s = sc.nextLine();
                    }
                    //populates shirts
                    while (!s.equals("Shoes")) {
                        Shirts.add(s);
                        s = sc.nextLine();
                    }
                    //populates shoes
                    while (!s.equals("Dress")) {
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
        cc = new IBMClothesClassifier();
        attributes = cc.getClothingAttributes(image);

        if(!attributes.isEmpty()){

            try{
                PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(Backup, true)));
                writer.println(attributes.toString());
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(attributes.get("ClothModel").toLowerCase().equals("pants") ||
                    attributes.get("ClothModel").toLowerCase().equals("shorts") ||
                    attributes.get("ClothModel").toLowerCase().equals("skirt")){

                Pants.add(attributes.get("ColorModel"));

            }else if(attributes.get("ClothModel").toLowerCase().equals("short sleeve shirt") ||
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
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(Clothes, false)));
            writer.println("Pants");
            for (Iterator it = Pants.iterator(); it.hasNext(); ) {
                writer.println((String) it.next());
            }

            writer.println("Shirt");
            for (Iterator it = Shirts.iterator(); it.hasNext(); ) {
                writer.println((String) it.next());
            }
            writer.println("Shoes");
            for (Iterator it = Shoes.iterator(); it.hasNext(); ) {
                writer.println((String) it.next());
            }
            writer.println("Dress");
            for (Iterator it = Dress.iterator(); it.hasNext(); ) {
                writer.println((String) it.next());
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
            if(rand2.nextInt(8) > 3 || Dress.isEmpty()) {
                 p = Pants.get(rand.nextInt(Pants.size()));
                 s = Shirts.get(rand.nextInt(Shirts.size()));
                 sh = Shoes.get(rand.nextInt(Shoes.size()));
            }else{
                 sh = Shoes.get(rand.nextInt(Pants.size()));
                 d = Dress.get(rand.nextInt(Shirts.size()));
            }

            List<Integer> outfit = new ArrayList<Integer>();

            if(p.equals("white") || s.equals("white") || sh.equals("white")|| d.equals("white")){
                outfit.add(1);
            }else{
                outfit.add(0);
            }

            if(p.equals("black") || s.equals("black") || sh.equals("black")|| d.equals("black")){
                outfit.add(1);
            }else{
                outfit.add(0);
            }

            if(p.equals("red") || s.equals("red") || sh.equals("red")|| d.equals("red")){
                outfit.add(1);
            }else{
                outfit.add(0);
            }

            if(p.equals("blue") || s.equals("blue") || sh.equals("blue")|| d.equals("blue")){
                outfit.add(1);
            }else{
                outfit.add(0);
            }

            if(p.equals("green") || s.equals("green") || sh.equals("green")|| d.equals("green")){
                outfit.add(1);
            }else{
                outfit.add(0);
            }

            OutfitMatrix.add(outfit);
        }
        return  OutfitMatrix;
    }

}
