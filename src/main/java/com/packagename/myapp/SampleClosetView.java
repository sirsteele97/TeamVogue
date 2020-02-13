package com.packagename.myapp;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Route(value="closet")
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class SampleClosetView extends VerticalLayout {

    private DiscoveryService discoveryService;
    private S3Service s3Service;

    public SampleClosetView(@Autowired DiscoveryService discoveryService, @Autowired S3Service s3Service) {
        this.discoveryService = discoveryService;
        this.s3Service = s3Service;

        ArrayList<String> clothes = new ArrayList<String>();
        clothes.add("long sleeve shirt");
        clothes.add("short sleeve shirt");
        clothes.add("shorts");
        clothes.add("skirts");
        clothes.add("pants");
        clothes.add("shoes");
        ComboBox clothesSelect = new ComboBox("Clothes",clothes);
        clothesSelect.setAllowCustomValue(false);

        ArrayList<String> colors = new ArrayList<String>();
        colors.add("red");
        colors.add("green");
        colors.add("blue");
        colors.add("black");
        colors.add("white");
        ComboBox colorsSelect = new ComboBox("Color",colors);
        colorsSelect.setAllowCustomValue(false);

        HorizontalLayout pictureArea = new HorizontalLayout();
        addImagesToDiv("","",pictureArea);
        //add all pictures to area here (pictureArea.add())

        Button filterButton = new Button("Filter",e -> {
            pictureArea.removeAll();
            String clothesParam = (clothesSelect.getValue() != null) ? clothesSelect.getValue().toString() : "";
            String colorParam = (colorsSelect.getValue() != null) ? colorsSelect.getValue().toString() : "";
            addImagesToDiv(clothesParam,colorParam,pictureArea);
            //add pictures based on filter parameters here (pictureArea.add())
        });

        Button uploadButton = new Button("Upload",e -> filterButton.getUI().ifPresent(ui->ui.navigate("imageupload")));

        Div mainStuff = new Div();
        mainStuff.add(clothesSelect, colorsSelect, filterButton,uploadButton);

        add(new TopBar(),mainStuff,pictureArea);
    }

    public void addImagesToDiv(String clothesParam, String colorParam, HorizontalLayout pictureArea){
        Map<String,Map<String,String>> images = discoveryService.getImages(clothesParam,colorParam);
        for(String imageId : images.keySet()){
            VerticalLayout picture = new VerticalLayout();
            Image image = new Image(s3Service.getImageUrl(images.get(imageId).get("FileName")),"");
            image.setWidth("300px");
            image.setHeight("300px");
            picture.add(image);
            picture.add(new Text(images.get(imageId).get("ColorModel")+" , "+images.get(imageId).get("ClothModel")));
            picture.add(new Button("Delete",e->{
                discoveryService.deleteClothing(imageId);
                s3Service.deleteImage(images.get(imageId).get("FileName"));
            }));

            pictureArea.add(picture);
        }
    }

}
