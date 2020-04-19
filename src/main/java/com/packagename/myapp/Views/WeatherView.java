package com.packagename.myapp.Views;

import com.packagename.myapp.Components.TopBar;
import com.packagename.myapp.NNDataGenerator.OutfitGenerator;
import com.packagename.myapp.NNDataGenerator.OutfitTestGenerator;
import com.packagename.myapp.Services.Interfaces.IClothesStorage;
import com.packagename.myapp.Services.Interfaces.IImageStorage;
import com.packagename.myapp.neuralNet.ClassifierNet;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Route(value="weather")
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class WeatherView extends VerticalLayout {

    private IClothesStorage clothesStorageService;
    private IImageStorage imageStorageService;
    private double[][] OutfitMatrix;
    private ClassifierNet neuralNetwork;
    private List<Map<String, String>> generatedOutfit;
    private String zipcode;

    public WeatherView(@Autowired IClothesStorage clothesStorageService, @Autowired IImageStorage imageStorageService) {
        this.clothesStorageService = clothesStorageService;
        this.imageStorageService = imageStorageService;
        neuralNetwork = new ClassifierNet();
        setup();
    }

    private FlexLayout getGeneratedOutfit(){
        FlexLayout pictureArea = new FlexLayout();
        OutfitGenerator OG = new OutfitGenerator();

            OG.AddImages(generatedOutfit.get(0));
            OG.AddImages(generatedOutfit.get(1));
            OG.AddImages(generatedOutfit.get(2));

            //USE THIS TO GIVE TO NN TO EVALUATE CURRENT RATING
            OutfitMatrix = OG.GetOutfit();

            //PICTURE
            pictureArea.setWrapMode(FlexLayout.WrapMode.WRAP);//turn byte[] into Image
            Image img1 = new Image(generatedOutfit.get(0).get("ImageLink"), "");
            img1.setHeight("150px");
            img1.setWidth("150px");
            pictureArea.add(img1);
            Image img2 = new Image(generatedOutfit.get(1).get("ImageLink"), "");
            img2.setHeight("150px");
            img2.setWidth("150px");
            pictureArea.add(img2);
            Image img3 = new Image();
            if (generatedOutfit.size() > 2) {
                img3 = new Image(generatedOutfit.get(2).get("ImageLink"), "");
                img3.setHeight("150px");
                img3.setWidth("150px");
                pictureArea.add(img3);
            }
            pictureArea.addClassName("centered-content");

        return pictureArea;
    }

    public void setup(){
        super.removeAll();

        OutfitTestGenerator OTG = new OutfitTestGenerator();
        generatedOutfit = OTG.RunGenerator(clothesStorageService);
        TopBar topbar = new TopBar();

        //WEATHER, ZIPCODE STUFF
        TextField textZipCode = new TextField("Zip Code");
        /*
         *
         * todo: get zip code from user account to populate String zipcode.
         *
         * */
        zipcode = "";
        textZipCode.setValue(zipcode);
        Button updateZipCode = new Button("Update");
        updateZipCode.addAttachListener(e -> {
            setZipCode(textZipCode.getValue());
        });
        FlexLayout zipcodeStuff = new FlexLayout();
        zipcodeStuff.add(textZipCode);
        zipcodeStuff.add(updateZipCode);

        if(generatedOutfit.size() > 1) {
            FlexLayout pictureArea = new FlexLayout();
            pictureArea.add(getGeneratedOutfit());

            //USER REACTIONS
            HorizontalLayout reactionButtons = new HorizontalLayout();
            reactionButtons.setWidth("1700px");
            //reactionButtons.addClassName("horizontal-spacing");
            Button a = new Button("I like");
            a.addClassName("green");
            a.setHeight("30px");
            a.setWidth("500px");
            a.addClickListener(e ->
            {
                neuralNetwork.backpropagation("good", OutfitMatrix, neuralNetwork.LEARNING_RATE);
                generatedOutfit = OTG.RunGenerator(clothesStorageService);
                setup();
            });
            Button b = new Button("Neutral");
            b.addClassName("yellow");
            b.setHeight("30px");
            b.setWidth("500px");
            b.addClickListener(e ->
            {
                generatedOutfit = OTG.RunGenerator(clothesStorageService);
                setup();
            });
            Button c = new Button("I dislike");
            c.addClassName("red");
            c.setHeight("30px");
            c.setWidth("500px");
            c.addClickListener(e ->
            {
                neuralNetwork.backpropagation("bad", OutfitMatrix, neuralNetwork.LEARNING_RATE);
                generatedOutfit = OTG.RunGenerator(clothesStorageService);
                setup();
            });
            Div space1 = new Div();
            Div space2 = new Div();
            space1.setWidth("100px");
            space2.setWidth("100px");
            reactionButtons.add(a, space1, b, space2, c);

            Component outfit = pictureArea.getComponentAt(0);

            add(topbar, zipcodeStuff, outfit, reactionButtons);
        }else{
            HorizontalLayout horizontalLayout = new HorizontalLayout();
            Text text = new Text("Please add more clothes to closet to use this feature!");
            horizontalLayout.add(text);
            add(topbar, horizontalLayout);
        }
    }

    private void setZipCode(String s) {
        this.zipcode = s;
    }
}
