package com.packagename.myapp.Services;

import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.visual_recognition.v3.model.ClassResult;
import com.ibm.watson.visual_recognition.v3.model.ClassifiedImages;
import com.ibm.watson.visual_recognition.v3.model.ClassifierResult;
import com.ibm.watson.visual_recognition.v3.model.ClassifyOptions;
import com.packagename.myapp.Services.Interfaces.IClothesClassifier;
import com.packagename.myapp.Utils.KeyHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IBMClothesClassifier implements IClothesClassifier {

    private final String[] keys = {KeyHolder.getKey("ibm.visualrecognition1.apiKey"),KeyHolder.getKey("ibm.visualrecognition2.apiKey")};
    private final String[] urls = {"https://api.us-south.visual-recognition.watson.cloud.ibm.com/instances/e8a1af7f-a932-462f-94e6-addcbd5a69da",
            "https://api.us-south.visual-recognition.watson.cloud.ibm.com/instances/2310e2f1-02b7-4dcb-83ce-22be74fe3414"};
    private final String[] models = {"ClothModel_1452037569","ColorModel_1451624520"};

    @Override
    public Map<String, String> getClothingAttributes(String imageUrl) {
        Map<String,String> attributes = classify(imageUrl,0);
        //attributes.putAll(classify(imageUrl,1));
        return attributes;
    }

    private Map<String,String> classify(String imageUrl, int recognizer){
        IamAuthenticator authenticator = new IamAuthenticator(keys[recognizer]);
        VisualRecognition visualRecognition = new VisualRecognition("2018-03-19", authenticator);
        visualRecognition.setServiceUrl(urls[recognizer]);

        ClassifyOptions classifyOptions = new ClassifyOptions.Builder()
                .url(imageUrl)
                .addClassifierId(models[recognizer*2])
                .addClassifierId(models[recognizer*2+1])
                .threshold(0f)
                .build();
        ClassifiedImages results = visualRecognition.classify(classifyOptions).execute().getResult();

        Map<String, String> attributes = new HashMap<String,String>();

        List<ClassifierResult> classifiers = results.getImages().get(0).getClassifiers();
        for(ClassifierResult cr : classifiers){
            String topClass = "";
            float topScore = 0f;

            for(ClassResult c : cr.getClasses()){
                if(c.getScore() > topScore){
                    topClass = c.getXClass();
                    topScore = c.getScore();
                }
            }

            attributes.put(cr.getName().replace(" ",""),topClass);
        }

        return attributes;
    }
}
