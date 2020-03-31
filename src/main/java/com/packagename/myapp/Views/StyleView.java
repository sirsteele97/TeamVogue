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
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Map;

@Route(value="style")
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class StyleView extends VerticalLayout {

    private IClothesStorage clothesStorageService;
    private IImageStorage imageStorageService;

    public StyleView(@Autowired IClothesStorage clothesStorageService, @Autowired IImageStorage imageStorageService) {
        this.clothesStorageService = clothesStorageService;
        this.imageStorageService = imageStorageService;

        //PICTURE
        FlexLayout pictureArea = new FlexLayout();
        pictureArea.setWrapMode(FlexLayout.WrapMode.WRAP);//turn byte[] into Image
        //pictureArea.add(new Image(new StreamResource("", () -> new ByteArrayInputStream(clothesStorageService.getRandomPic())), ""));
        pictureArea.addClassName("centered-content");

        //USER REACTIONS
        HorizontalLayout reactionButtons = new HorizontalLayout();
        reactionButtons.addClassName("horizontal-spacing");
        Label a = new Label("I like it");
        a.addClassName("green");
        a.addAttachListener(e ->
            {
                updateNN(1);
                pictureArea.removeAll();
                //pictureArea.add(new Image(new StreamResource("", () -> new ByteArrayInputStream(clothesStorageService.getRandomPic())), ""));
            });
        Label b = new Label("I don't care");
        b.addClassName("yellow");
        b.addAttachListener(e ->
        {
            pictureArea.removeAll();
            //pictureArea.add(new Image(new StreamResource("", () -> new ByteArrayInputStream(clothesStorageService.getRandomPic())), ""));
        });
        Label c = new Label("I don't like it");
        c.addClassName("red");
        c.addAttachListener(e ->
        {
            updateNN(-1);
            pictureArea.removeAll();
            //pictureArea.add(new Image(new StreamResource("", () -> new ByteArrayInputStream(clothesStorageService.getRandomPic())), ""));
        });
        Div space1 = new Div();
        Div space2 = new Div();
        space1.setWidth("100px");
        space2.setWidth("100px");
        reactionButtons.add(a, space1, b, space2, c);
        reactionButtons.addClassName("centered-content");

        add(new TopBar(),pictureArea,reactionButtons);
    }

    private void updateNN(int x) {
        //TODO - call method to update NN
    }
}
