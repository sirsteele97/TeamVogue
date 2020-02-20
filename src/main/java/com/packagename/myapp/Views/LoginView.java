package com.packagename.myapp.Views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

@Route(value="")
@PWA(name = "Vaadin Application",
        shortName = "Vaadin App",
        description = "This is an example Vaadin application.",
        enableInstallPrompt = true)
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class LoginView extends VerticalLayout {

    public LoginView() {
        TextField textFieldName = new TextField("Username");
        PasswordField passwordField = new PasswordField("Password");

        Button button = new Button("Log In");
        button.addClickListener(
                e -> {
                    if(textFieldName.getValue().equalsIgnoreCase("TeamVogue") && passwordField.getValue().equalsIgnoreCase("teamvogue1")) {
                        //Successful Login
                        button.getUI().ifPresent(ui -> ui.navigate("closet"));
                    }

                    else{
                        //Unsuccessful Login
                        Notification notification = new Notification(
                                "Invalid credentials.", 3000, Notification.Position.MIDDLE);
                        notification.open();
                    }
                });

        //cosmetics
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addClassName("centered-content");

        add(textFieldName, passwordField, button);
    }
}
