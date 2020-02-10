package com.packagename.myapp.views;

import com.packagename.myapp.services.interfaces.IClothesClassifier;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@Route
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class UploadView extends VerticalLayout {

    public UploadView(@Autowired IClothesClassifier clothesClassifier) {

        // Add basic upload button.
        MemoryBuffer buffer = new MemoryBuffer();
        Upload upload = new Upload(buffer);
        upload.addFinishedListener(e -> {
            List<String> attributes = clothesClassifier.getClothingAttributes(buffer.getInputStream());
            for(String s : attributes){
                System.out.println(s);
            }
        });

        // Add the upload button and import the css class to center it.
        addClassName("centered-content");
        add(upload);
    }

}