package com.packagename.myapp;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Route(value="closet")
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class SampleClosetView extends VerticalLayout {

    public SampleClosetView(@Autowired DiscoveryServiceMock service) {
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

        Div pictureArea = new Div();
        List<String> allImages = service.imageUrls("","");
        for(String imageUrl : allImages){
            Image image = new Image(imageUrl,"");
            image.setWidth("300px");
            image.setHeight("300px");
            pictureArea.add(image);
        }
        //add all pictures to area here (pictureArea.add())

        Button filterButton = new Button("Filter",e -> {
            pictureArea.removeAll();
            String clothesParam = clothesSelect.getValue().toString();
            String colorParam = colorsSelect.getValue().toString();
            List<String> images = service.imageUrls(clothesParam,colorParam);
            for(String imageUrl : images){
                Image image = new Image(imageUrl,"");
                image.setWidth("300px");
                image.setHeight("300px");
                pictureArea.add(image);
            }
            //add pictures based on filter parameters here (pictureArea.add())
        });

        Div mainStuff = new Div();
        mainStuff.add(clothesSelect, colorsSelect, filterButton);

        add(new TopBar(),mainStuff,pictureArea);
    }

}
