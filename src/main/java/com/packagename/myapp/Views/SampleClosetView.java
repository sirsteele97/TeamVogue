package com.packagename.myapp.Views;

import com.packagename.myapp.Components.TopBar;
import com.packagename.myapp.Services.Interfaces.IClothesStorage;
import com.packagename.myapp.Services.Interfaces.IImageStorage;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.HtmlComponent;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Map;

@Route(value="closet")
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class SampleClosetView extends VerticalLayout {

    private IClothesStorage clothesStorageService;
    private IImageStorage imageStorageService;

    public SampleClosetView(@Autowired IClothesStorage clothesStorageService, @Autowired IImageStorage imageStorageService) {
        this.clothesStorageService = clothesStorageService;
        this.imageStorageService = imageStorageService;

        ArrayList<String> clothes = new ArrayList<String>();
        clothes.add("");
        clothes.add("long sleeve shirt");
        clothes.add("short sleeve shirt");
        clothes.add("shorts");
        clothes.add("skirts");
        clothes.add("pants");
        clothes.add("shoes");
        ComboBox clothesSelect = new ComboBox("Clothes",clothes);
        clothesSelect.setAllowCustomValue(false);

        ArrayList<String> colors = new ArrayList<String>();
        colors.add("");
        colors.add("red");
        colors.add("green");
        colors.add("blue");
        colors.add("black");
        colors.add("white");
        ComboBox colorsSelect = new ComboBox("Color",colors);
        colorsSelect.setAllowCustomValue(false);

        FlexLayout pictureArea = new FlexLayout();
        pictureArea.setWrapMode(FlexLayout.WrapMode.WRAP);
        addImagesToDiv("","",pictureArea);

        Button filterButton = new Button("Filter",e -> {
            pictureArea.removeAll();
            String clothesParam = (clothesSelect.getValue() != null) ? clothesSelect.getValue().toString() : "";
            String colorParam = (colorsSelect.getValue() != null) ? colorsSelect.getValue().toString() : "";
            addImagesToDiv(clothesParam,colorParam,pictureArea);
        });

        Button uploadButton = new Button("Upload",e -> filterButton.getUI().ifPresent(ui->ui.navigate("imageupload")));

        Div mainStuff = new Div();
        mainStuff.add(clothesSelect, colorsSelect, filterButton,uploadButton);


        add(new TopBar(),mainStuff,pictureArea);
    }

    public void addImagesToDiv(String clothesParam, String colorParam, FlexLayout pictureArea){
        Map<String,Map<String,String>> images = clothesStorageService.getClothes(clothesParam,colorParam);
        for(String imageId : images.keySet()){
            pictureArea.add(createClothingItem(imageId,images.get(imageId)));
        }
    }

    public Div createClothingItem(String imageId, Map<String,String> imageAttributes){
        Div closetItem = new Div();

        Image image = new Image(imageAttributes.get("ImageLink"),"");
        image.setWidth("300px");
        image.setHeight("300px");
        closetItem.add(image);
        closetItem.add(new HtmlComponent("br"));
        closetItem.add(new Text(imageAttributes.get("ColorModel")+" , "+imageAttributes.get("ClothModel")));
        closetItem.add(new HtmlComponent("br"));
        closetItem.add(new Button("Delete",e->{
            clothesStorageService.deleteClothing(imageId);
            imageStorageService.deleteImage(imageAttributes.get("DeleteKey"));
        }));

        return closetItem;
    }

}
