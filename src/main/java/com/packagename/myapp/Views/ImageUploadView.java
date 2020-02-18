package com.packagename.myapp.Views;

import com.google.gson.Gson;
import com.packagename.myapp.Components.TopBar;
import com.packagename.myapp.Services.Interfaces.IClothesClassifier;
import com.packagename.myapp.Services.Interfaces.IClothesStorage;
import com.packagename.myapp.Services.Interfaces.IImageStorage;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.Route;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

/**
 * A Vaadin view class for the upload page
 *
 * A new instance of this class is created for every new user and every
 * browser tab/window.
 */
@Route
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class ImageUploadView extends VerticalLayout {

    /**
     * Construct a Vaadin view for the upload page.
     * <p>
     * Build the initial UI state for the user uploading an image.
     *
     * @param clothesStorageService The service for image uploading.
     */
    public ImageUploadView(@Autowired IClothesStorage clothesStorageService, @Autowired IImageStorage imageStorageService, @Autowired IClothesClassifier clothesClassifierService) {
        // Add basic upload button.
        MemoryBuffer buffer = new MemoryBuffer();
        Upload upload = new Upload(buffer);
        upload.setAcceptedFileTypes("image/jpeg","image/png");
        upload.setMaxFiles(3);

        // On a file successfully uploading, show the output.
        upload.addSucceededListener(event -> {
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                IOUtils.copy(buffer.getInputStream(), baos);
                byte[] bytes = baos.toByteArray();

                Map<String,String> uploadResults = imageStorageService.uploadImage(new ByteArrayInputStream(bytes),event.getFileName());

                Map<String,String> clothingDocument = clothesClassifierService.getClothingAttributes(new ByteArrayInputStream(bytes));
                clothingDocument.put("ImageLink",uploadResults.get("ImageLink"));
                clothingDocument.put("DeleteKey",uploadResults.get("DeleteKey"));
                String json = new Gson().toJson(clothingDocument);

                clothesStorageService.addClothing(json);

                upload.getUI().ifPresent(ui -> ui.navigate("closet"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Add the upload button and import the css class to center it.
        Div mainStuff = new Div();
        mainStuff.addClassName("centered-content");
        mainStuff.add(upload);

        add(new TopBar(), mainStuff);
    }
}
