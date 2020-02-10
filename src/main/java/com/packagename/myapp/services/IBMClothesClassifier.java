package com.packagename.myapp.services;

import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.visual_recognition.v3.model.ClassResult;
import com.ibm.watson.visual_recognition.v3.model.ClassifiedImages;
import com.ibm.watson.visual_recognition.v3.model.ClassifyOptions;
import com.packagename.myapp.services.interfaces.IClothesClassifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class IBMClothesClassifier implements IClothesClassifier {

    @Override
    public List<String> getClothingAttributes(InputStream image) {
        IamAuthenticator authenticator = new IamAuthenticator("q9uDUDKNf8JX0yQOFlqbVhk4U-LCJzYsa43apUDvJvBb");
        VisualRecognition visualRecognition = new VisualRecognition("2018-03-19", authenticator);
        visualRecognition.setServiceUrl("https://api.us-south.visual-recognition.watson.cloud.ibm.com/instances/e8a1af7f-a932-462f-94e6-addcbd5a69da");

        ClassifyOptions classifyOptions = new ClassifyOptions.Builder()
                .imagesFile(image)
                .imagesFilename("clothing.png")
                .build();
        ClassifiedImages results = visualRecognition.classify(classifyOptions).execute().getResult();

        List<ClassResult> classes = results.getImages().get(0).getClassifiers().get(0).getClasses();

        List<String> attributes = new ArrayList<String>(classes.size());
        for(ClassResult classResult : classes) {
            attributes.add(classResult.getXClass());
        }

        return attributes;
    }
}
