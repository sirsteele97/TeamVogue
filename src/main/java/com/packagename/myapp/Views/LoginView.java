package com.packagename.myapp.Views;

import com.packagename.myapp.Services.Interfaces.IClothesStorage;
import com.packagename.myapp.Services.Interfaces.IUserStorage;
import com.packagename.myapp.Utils.SessionData;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value="")
@PWA(name = "Vaadin Application",
        shortName = "Vaadin App",
        description = "This is an example Vaadin application.",
        enableInstallPrompt = true)
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class LoginView extends VerticalLayout {

    public LoginView(@Autowired IUserStorage userStorage) {
        TextField textFieldName = new TextField("Username");
        PasswordField passwordField = new PasswordField("Password");

        Button button = new Button("Log In");
        button.addClickListener(
                e -> {
                    String username = textFieldName.getValue();
                    String password = passwordField.getValue();
                    if(userStorage.validCredentials(username,password)) {
                        //Successful Login
                        SessionData.setAttribute("Username",username);
                        SessionData.setAttribute("ZipCode",userStorage.getUserZipCode(username));
                        button.getUI().ifPresent(ui -> ui.navigate("closet"));
                    }
                    else{
                        //Unsuccessful Login
                        Notification notification = new Notification(
                                "Invalid credentials!", 3000, Notification.Position.MIDDLE);
                        notification.open();
                    }
                });

        Button newUserButton = new Button("Create New User");
        newUserButton.addClickListener(e -> newUserButton.getUI().ifPresent(ui -> ui.navigate("newuser")));
        newUserButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        //cosmetics
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addClassName("centered-content");

        add(textFieldName, passwordField, button, newUserButton);
    }
}
