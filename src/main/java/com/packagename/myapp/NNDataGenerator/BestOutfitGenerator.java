package com.packagename.myapp.NNDataGenerator;

import com.packagename.myapp.Services.Interfaces.IClothesStorage;
import com.packagename.myapp.neuralNet.ClassifierNet;

import java.util.*;

public class BestOutfitGenerator {

    private IClothesStorage dcs;
    private OutfitTestGenerator outfitTestGenerator;
    private OutfitGenerator outfitGenerator;
    private Map<List<Map<String,String>>, Double> RankedOutfits;
    private ClassifierNet nn;
    public BestOutfitGenerator(IClothesStorage dcs){
        this.dcs = dcs;
        outfitTestGenerator = new OutfitTestGenerator();
        outfitGenerator = new OutfitGenerator();
        RankedOutfits = new HashMap<List<Map<String,String>>, Double> ();
        nn = new ClassifierNet();

        for(int i = 0; i < 20; i++){
            List<Map<String,String>> outfit = outfitTestGenerator.RunGeneratorWithWeather(dcs);
            if(!RankedOutfits.containsKey(outfit)){
                outfitGenerator.AddOutfit(outfit);
                double[][] matrix = outfitGenerator.GetOutfit();
                double rating = nn.GetJudgeValue(matrix[0], matrix[1]);
                RankedOutfits.put(outfit, rating);
            }
        }

    }

    //returns a map of the clothing items in the best outfit
    public List<Map<String,String>> GetBestOutfit(){
        Double bestScore = -10.0;
        List<Map<String,String>> bestOutfit = new ArrayList<Map<String,String>>();
        for(Map.Entry<List<Map<String,String>>, Double> outfit: RankedOutfits.entrySet()){
            if(outfit.getValue() > bestScore){
                bestScore = outfit.getValue();
                bestOutfit = outfit.getKey();
            }
        }
        RankedOutfits.remove(bestOutfit);
        return bestOutfit;

    }



}
