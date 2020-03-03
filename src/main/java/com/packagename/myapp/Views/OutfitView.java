package com.packagename.myapp.Views;

import com.google.gson.Gson;
import com.packagename.myapp.Components.TopBar;
import com.packagename.myapp.Services.Interfaces.IClothesStorage;
import com.packagename.myapp.Services.Interfaces.IImageStorage;
import com.vaadin.flow.component.Component;
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
import com.vaadin.flow.component.upload.Receiver;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.FileData;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import org.apache.catalina.connector.OutputBuffer;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Route(value="outfit")
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class OutfitView extends VerticalLayout {

    public OutfitView() {
        // Add basic upload button.
        MemoryBuffer buffer = new MemoryBuffer();
        Upload upload = new Upload(buffer);
        upload.setAcceptedFileTypes("image/jpeg","image/png");
        upload.setMaxFiles(1);
        upload.addClassName("centered-content");

        //PICTURE AREA
        FlexLayout pictureArea = new FlexLayout();
        pictureArea.setWrapMode(FlexLayout.WrapMode.WRAP);
        pictureArea.addClassName("centered-content");

        //USER REACTIONS
        HorizontalLayout reactionButtons = new HorizontalLayout();
        reactionButtons.setWidthFull();

        Label a = new Label("I agree");
        a.addClassName("green");
        a.addAttachListener(e -> {
            updateNN(1);
        });
        Label b = new Label("I don't care");
        b.addClassName("yellow");
        Label c = new Label("I disagree");
        c.addClassName("red");
        c.addAttachListener(e -> {
            updateNN(-1);
        });
        Div space1 = new Div();
        Div space2 = new Div();
        space1.setWidth("100px");
        space2.setWidth("100px");
        reactionButtons.add(a, space1, b, space2, c);
        reactionButtons.addClassName("centered-content");

        //REACTION FROM NN
        Div NNDiv = new Div();
        NNDiv.addClassName("centered-content");

        // On a file successfully uploading, show the output and get reaction.
        upload.addSucceededListener(event -> {
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                IOUtils.copy(buffer.getInputStream(), baos);
                byte[] bytes = baos.toByteArray();

                //turn byte[] into Image
                StreamResource resource = new StreamResource("", () -> new ByteArrayInputStream(bytes));
                pictureArea.add(new Image(resource, ""));

                //get reaction and update
                String reaction = getNNReaction(bytes);
                NNDiv.removeAll();
                NNDiv.add(reaction);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        add(new TopBar(), upload, pictureArea, NNDiv, reactionButtons);
    }

    private void updateNN(int x) {
        //TODO - call method to update NN
    }

    private String getNNReaction(byte[] bytes) {
        //TODO - get NN reaction to pic
        return "getNNReaction";
    }
}
