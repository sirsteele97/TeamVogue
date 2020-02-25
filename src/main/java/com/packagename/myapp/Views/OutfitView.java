package com.packagename.myapp;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Route(value="outfit")
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class OutfitView extends VerticalLayout {

    private DiscoveryService discoveryService;
    private S3Service s3Service;

    public OutfitView(@Autowired DiscoveryService discoveryService, @Autowired S3Service s3Service) {
        this.discoveryService = discoveryService;
        this.s3Service = s3Service;

        Button button = new Button("OUTFIT");

        add(new TopBar(), button);
    }
}
