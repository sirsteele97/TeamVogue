package com.packagename.myapp;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.router.Route;
import elemental.json.Json;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * A Vaadin view class for the upload page
 *
 * A new instance of this class is created for every new user and every
 * browser tab/window.
 */
@Route
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class ImageUploadView extends VerticalLayout {

    /**
     * Construct a Vaadin view for the upload page.
     * <p>
     * Build the initial UI state for the user uploading an image.
     *
     * @param service The service for image uploading.
     */
    public ImageUploadView(@Autowired ImageUploaderServiceInterface service) {
        Div output = new Div();
        Button button = new Button("Upload");
        button.setEnabled(false);
        button.setDisableOnClick(true);
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        // Add basic upload button.
        MultiFileMemoryBuffer buffer = new MultiFileMemoryBuffer();
        Upload upload = new Upload(buffer);
        upload.setAcceptedFileTypes("image/jpeg","image/png");
        upload.setMaxFileSize(8388608);
        upload.setMaxFiles(3);

        // On a file successfully uploading, show the output.
        upload.addSucceededListener(event -> {
            Component component = service.createComponent(event.getMIMEType(),
                    event.getFileName(),
                    buffer.getInputStream(event.getFileName()));
            service.showOutput(event.getFileName(), component, output);
            button.setEnabled(true);
        });

        // On a button click, clear out the displayed images and reset the upload component.
        button.addClickListener(e -> {
            service.uploadImages(buffer);
            buffer.getFiles().clear();
            output.removeAll();
            upload.getElement().setPropertyJson("files", Json.createArray());
            button.setEnabled(false);
        });

        // Add the upload button and import the css class to center it.
        addClassName("centered-content");
        add(upload, output, button);
    }
}
