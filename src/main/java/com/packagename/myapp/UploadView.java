package com.packagename.myapp;

import com.packagename.myapp.IClothesClassifier;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;


@Route
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class UploadView extends VerticalLayout {

    public UploadView(@Autowired IClothesClassifier clothesClassifier) {

        // Add basic upload button.
        MemoryBuffer buffer = new MemoryBuffer();
        Upload upload = new Upload(buffer);
        upload.addFinishedListener(e -> {
            Map<String, String> attributes = clothesClassifier.getClothingAttributes(buffer.getInputStream());
            System.out.println(attributes.get("Cloth Model"));
            System.out.println(attributes.get("Color Model"));
        });

        // Add the upload button and import the css class to center it.
        addClassName("centered-content");
        add(upload);
    }

}