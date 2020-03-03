package com.packagename.myapp.Views;

import com.google.gson.Gson;
import com.packagename.myapp.Components.TopBar;
import com.packagename.myapp.Services.Interfaces.IClothesClassifier;
import com.packagename.myapp.Services.Interfaces.IClothesStorage;
import com.packagename.myapp.Services.Interfaces.IImageStorage;
import com.packagename.myapp.Utils.ClothingOptions;
import com.packagename.myapp.Utils.SessionData;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.Route;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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

    private IClothesStorage clothesStorageService;
    private IImageStorage imageStorageService;
    private IClothesClassifier clothesClassifierService;

    public ImageUploadView(@Autowired IClothesStorage clothesStorageService, @Autowired IImageStorage imageStorageService, @Autowired IClothesClassifier clothesClassifierService) {
        this.clothesStorageService = clothesStorageService;
        this.imageStorageService = imageStorageService;
        this.clothesClassifierService = clothesClassifierService;

        Div mainStuff = new Div();
        mainStuff.addClassName("centered-content");

        // Add basic upload button.
        MemoryBuffer buffer = new MemoryBuffer();
        Upload upload = new Upload(buffer);
        upload.setAcceptedFileTypes("image/jpeg","image/png");
        upload.setMaxFiles(1);

        // On a file successfully uploading, show the output.
        upload.addSucceededListener(event -> {
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                IOUtils.copy(buffer.getInputStream(), baos);
                byte[] bytes = baos.toByteArray();

                Map<String,String> uploadResults = imageStorageService.uploadImage(new ByteArrayInputStream(bytes),event.getFileName());

                Map<String,String> clothingDocument = clothesClassifierService.getClothingAttributes(uploadResults.get("ImageLink"));
                clothingDocument.put("ImageLink",uploadResults.get("ImageLink"));
                clothingDocument.put("DeleteKey",uploadResults.get("DeleteKey"));
                clothingDocument.put("Username", SessionData.getAttribute("Username"));
                clothingDocument.put("Probability",".5");

                createVerification(clothingDocument,mainStuff);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Add the upload button and import the css class to center it.
        mainStuff.add(upload);

        add(new TopBar(), mainStuff);
    }

    private void createVerification(Map<String,String> clothingDocument, Div parent){
        Image image = new Image(clothingDocument.get("ImageLink"),"");
        image.setWidth("200px");
        image.setHeight("200px");
        parent.add(image);

        FlexLayout attributesEditArea = new FlexLayout();
        attributesEditArea.setWrapMode(FlexLayout.WrapMode.WRAP);

        ArrayList<String> clothes = new ArrayList<String>();
        clothes.addAll(Arrays.asList(ClothingOptions.clothingTypes));
        ComboBox clothesSelect = new ComboBox("Clothes",clothes);
        clothesSelect.setValue(clothingDocument.get("ClothModel"));
        clothesSelect.setAllowCustomValue(false);

        ArrayList<String> colors = new ArrayList<String>();
        colors.addAll(Arrays.asList(ClothingOptions.colors));
        ComboBox colorsSelect = new ComboBox("Color",colors);
        colorsSelect.setValue(clothingDocument.get("ColorModel"));
        colorsSelect.setAllowCustomValue(false);

        attributesEditArea.add(clothesSelect,colorsSelect);
        parent.add(attributesEditArea);

        Button finishButton = new Button("Finish", e -> {
            String clothesParam = (clothesSelect.getValue() != null) ? clothesSelect.getValue().toString() : "";
            String colorParam = (colorsSelect.getValue() != null) ? colorsSelect.getValue().toString() : "";

            clothingDocument.put("ClothModel",clothesParam);
            clothingDocument.put("ColorModel",colorParam);

            String json = new Gson().toJson(clothingDocument);
            clothesStorageService.addClothing(json);

            this.getUI().ifPresent(ui -> ui.navigate("closet"));
        });
        finishButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        parent.add(finishButton);
    }
}
