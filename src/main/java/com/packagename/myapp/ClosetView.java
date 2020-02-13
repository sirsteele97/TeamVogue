package com.packagename.myapp;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.InputStreamFactory;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.server.StreamResource;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import Database.DatabaseFunctions;

import javax.imageio.ImageIO;
import javax.xml.transform.stream.StreamSource;

import static java.lang.System.out;
/**
 * A sample Vaadin view class.
 * <p>
 * To implement a Vaadin view just extend any Vaadin component and
 * use @Route annotation to announce it in a URL as a Spring managed
 * bean.
 * Use the @PWA annotation make the application installable on phones,
 * tablets and some desktop browsers.
 * <p>
 * A new instance of this class is created for every new user and every
 * browser tab/window.
 */
@Route(value="closet")

@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class ClosetView extends FormLayout {

    /*private StreamResource createStreamResource(BufferedImage bf) {
        return new StreamResource("photo",()-> {

    }*/

    Image[] loadImages(List<Database.Image> images, int i, ImageUploaderServiceInterface service){
        Image[] toLoad=new Image[20];

        for(int j=0;j<20&&(j+i)<images.size();j++){
            Database.Image ime= images.get(i+j);
            BufferedImage bf=ime.GetBufferedImage();
            StreamResource sr=new StreamResource("img", ()->{
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                try {
                    ImageIO.write(bf, "png", bos);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return new ByteArrayInputStream(bos.toByteArray());
            });
            sr.setContentType("image/png");
            toLoad[j]= new Image(sr,"Clothing Obj");
        }

        return toLoad;
    }
    /**
     * Construct a new Vaadin view.
     * <p>
     * Build the initial UI state for the user accessing the application.
     *
     * @param service The message service. Automatically injected Spring managed bean.
     */

    public ClosetView(@Autowired GreetService guest, ImageUploaderServiceInterface service) {
        //set up filter presets
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
        //header for closer
        H1 title=new H1("Closet");
        //Gets the image database
        DatabaseFunctions.DBImages db=new DatabaseFunctions.DBImages();
        //turns all photos in database into a list
        List<Database.Image> imgs=db.SelectImages(1);
        int loc=0;

       // MultiFileMemoryBuffer buffer = new MultiFileMemoryBuffer();
        Div pictureArea = new Div();
        //add all pictures to area here (pictureArea.add())
        Image[] imgarray=loadImages(imgs,loc,service);
        for (int i=0; i<imgarray.length; i++){
            //imgarray[i]=new Image("https://dummyimage.com/600x400/000/fff", "DummyImage");
            if(imgarray[i]!=null) {
                pictureArea.add(imgarray[i]);
            }
        }
        //create filter button and functionality
        Button filterButton = new Button("Filter",e -> {
            pictureArea.removeAll();
            String clothesParam = clothesSelect.getValue().toString();
            String colorParam = colorsSelect.getValue().toString();
        });

        //title.setHeight("200px");
        //title.setWidth("100px");
        //creates hom and upload buttons
        Button home= new Button(new Icon(VaadinIcon.HOME));
        Button upload = new Button(new Icon(VaadinIcon.UPLOAD));
        home.addClickListener(e ->home.getUI().ifPresent(ui -> ui.navigate("closet")));
        upload.addClickListener(e ->home.getUI().ifPresent(ui -> ui.navigate("upload")));

        //creates seperate area for filter button
        Div mainstuff=new Div();
        mainstuff.add(clothesSelect,colorsSelect,filterButton);
        //adds everything to the view
        add(title,home,mainstuff,pictureArea);

        //to load images

        add(upload);



        // Button click listeners can be defined as lambda expressions

        //e -> Notification.show(service.greet(textField.getValue()));

        // Theme variants give you predefined extra styles for components.
        // Example: Primary button is more prominent look.
        //lo.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        // You can specify keyboard shortcuts for buttons.
        // Example: Pressing enter in this view clicks the Button.
        //button.addClickShortcut(Key.ENTER);

        // Use custom CSS classes to apply styling. This is defined in shared-styles.css.
        //addClassName("centered-content");

    }

}

