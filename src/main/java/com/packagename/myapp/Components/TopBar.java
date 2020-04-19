package com.packagename.myapp.Components;

import com.packagename.myapp.Utils.SessionData;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.menubar.MenuBar;

public class TopBar extends Div {

    public TopBar(){
        addClassName("topBar");

        Button closetButton = new Button("Closet");
        closetButton.addClickListener(e -> closetButton.getUI().ifPresent(ui->ui.navigate("closet")));
        closetButton.setClassName("topBarButton");

        Button outfitButton = new Button("Outfit");
        outfitButton.addClickListener(e -> outfitButton.getUI().ifPresent(ui->ui.navigate("outfit")));
        outfitButton.setClassName("topBarButton");

        Button styleButton = new Button("Style");
        styleButton.addClickListener(e -> styleButton.getUI().ifPresent(ui->ui.navigate("style")));
        styleButton.setClassName("topBarButton");

        Button weatherButton = new Button("Weather");
        weatherButton.addClickListener(e -> weatherButton.getUI().ifPresent(ui->ui.navigate("weather")));
        weatherButton.setClassName("topBarButton");

        Button signOutButton = new Button("Sign Out");
        signOutButton.addClickListener(e -> {
            SessionData.setAttribute("Username","");
            signOutButton.getUI().ifPresent(ui->ui.navigate(""));
        });
        signOutButton.setClassName("topBarButton");

        add(closetButton, outfitButton, styleButton, weatherButton, signOutButton);
    }

}
