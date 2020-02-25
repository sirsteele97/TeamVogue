package com.packagename.myapp;

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

        Button outfitButton = new Button("Outfit Evaluator");
        outfitButton.addClickListener(e -> closetButton.getUI().ifPresent(ui->ui.navigate("outfit")));
        outfitButton.setClassName("topBarButton");

        Button styleButton = new Button("Find Your Style");
        styleButtonButton.addClickListener(e -> closetButton.getUI().ifPresent(ui->ui.navigate("style")));
        styleButton.setClassName("topBarButton");

        Button signOutButton = new Button("Sign Out");
        signOutButton.addClickListener(e -> signOutButton.getUI().ifPresent(ui->ui.navigate("")));
        signOutButton.setClassName("topBarButton");

        add(closetButton, outfitButton, styleButton, signOutButton);
    }

}
