package com.packagename.myapp;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.FileBuffer;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.InputStream;

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
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class UploadView extends VerticalLayout {

    /**
     * Construct a Vaadin view for the upload page.
     * <p>
     * Build the initial UI state for the user uploading an image.
     *
     * @param service The message service. Automatically injected Spring managed bean.
     */
    public UploadView(@Autowired GreetService service) {
        // Add basic upload button.
        FileBuffer fileBuffer = new FileBuffer();
        Upload upload = new Upload(fileBuffer);
        upload.addFinishedListener(e -> {
            InputStream inputStream =
                    fileBuffer.getInputStream();
        });

        // Add the upload button and import the css class to center it.
        addClassName("centered-content");
        add(upload);
    }


}
