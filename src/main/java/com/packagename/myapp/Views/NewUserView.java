package com.packagename.myapp.Views;

import com.packagename.myapp.Services.Interfaces.IUserStorage;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value="newuser")
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class NewUserView extends VerticalLayout {

    public NewUserView(@Autowired IUserStorage userStorage) {
        TextField textFieldName = new TextField("Username");
        PasswordField passwordField = new PasswordField("Password");
        PasswordField confirmPasswordField = new PasswordField("Password");
        IntegerField zipCodeField = new IntegerField("Zip Code");

        Button button = new Button("Create User");
        button.addClickListener(
                e -> {
                    String username = textFieldName.getValue();
                    String password = passwordField.getValue();
                    String confirmPassword = confirmPasswordField.getValue();
                    String zipCode = (zipCodeField.getValue()+"").replace("-","");

                    if(!username.isEmpty() && !password.isEmpty() && !confirmPassword.isEmpty() && !zipCode.isEmpty()) {
                        if(password.equalsIgnoreCase(confirmPassword)){
                            if(!userStorage.userExists(username)){
                                userStorage.addUser(username,password,zipCode);
                                button.getUI().ifPresent(ui -> ui.navigate(""));
                            }
                            else{
                                Notification notification = new Notification("User already exists!", 3000, Notification.Position.MIDDLE);
                                notification.open();
                            }
                        }
                        else{
                            Notification notification = new Notification("Passwords did not match!", 3000, Notification.Position.MIDDLE);
                            notification.open();
                        }
                    }
                    else{
                        Notification notification = new Notification("Fields cannot be empty!", 3000, Notification.Position.MIDDLE);
                        notification.open();
                    }
                });

        //cosmetics
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addClassName("centered-content");

        add(textFieldName, passwordField,confirmPasswordField,zipCodeField,button);
    }
}
