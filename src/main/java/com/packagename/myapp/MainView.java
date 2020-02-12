package com.packagename.myapp;

import com.packagename.myapp.GreetService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import org.springframework.beans.factory.annotation.Autowired;

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
@Route
@PWA(name = "Vaadin Application",
        shortName = "Vaadin App",
        description = "This is an example Vaadin application.",
        enableInstallPrompt = true)
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class MainView extends VerticalLayout {

    /**
     * Construct a new Vaadin view.
     * <p>
     * Build the initial UI state for the user accessing the application.
     *
     * @param service The message service. Automatically injected Spring managed bean.
     */
    public MainView(@Autowired GreetService service) {

        // Use TextField for standard text input
        TextField textFieldName = new TextField("Username");
        PasswordField passwordField = new PasswordField("Password");

        //Upon click, the user attempts to log in
        Button button = new Button("Log In",
                e -> {
                    if(LogIn(textFieldName.getValue(), passwordField.getValue()) == 0) {
                        //Successful login, switch pages
                    }

                    //Unsuccessful login - alert the user
                    Notification notification = new Notification(
                            "Invalid credentials.", 3000, Notification.Position.MIDDLE);
                    notification.open();
                });

        // Theme variants give you predefined extra styles for components.
        // Example: Primary button is more prominent look.
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        // Use custom CSS classes to apply styling. This is defined in shared-styles.css.
        addClassName("centered-content");

        add(textFieldName, passwordField, button);
    }

    /**
    * Connects to the database and tests credentials.
     *
     * @return 0 if successful, -1 otherwise.
    */
    public static int LogIn (String name, String password) {
        /*
        * Connect to DB
        *
        * if(valid)
        *   return 0
        * return -1
        * */

        //to avoid error, temporary
        return 0;
    }
}
