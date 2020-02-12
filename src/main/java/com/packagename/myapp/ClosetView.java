package com.packagename.myapp;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.Normalizer;

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
@StyleSheet("./styles/closet-text-field-styles.css")
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class ClosetView extends FormLayout {
    static Image[] loadImages(){
        return new Image[10];
    }
    /**
     * Construct a new Vaadin view.
     * <p>
     * Build the initial UI state for the user accessing the application.
     *
     * @param service The message service. Automatically injected Spring managed bean.
     */

    public ClosetView(@Autowired GreetService service) {
        H1 title=new H1("Closet");
        //title.setHeight("200px");
        //title.setWidth("100px");
        Button home= new Button(new Icon(VaadinIcon.HOME));
        Button upload = new Button(new Icon(VaadinIcon.UPLOAD));

        Image[] imgarray=new Image[10];
        add(title,home);
        for (int i=0; i<10; i++){
            imgarray[i]=new Image("https://dummyimage.com/600x400/000/fff", "DummyImage");
            add(imgarray[i]);
        }
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

