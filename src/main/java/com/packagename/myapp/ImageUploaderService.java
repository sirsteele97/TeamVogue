package com.packagename.myapp;

import Database.DatabaseFunctions;
import com.google.gson.Gson;
import com.ibm.cloud.sdk.core.http.HttpMediaType;
import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.discovery.v1.model.AddDocumentOptions;
import com.ibm.watson.discovery.v1.Discovery;
import com.ibm.watson.discovery.v1.model.DocumentAccepted;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HtmlComponent;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.upload.receivers.FileData;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.internal.MessageDigestUtil;
import com.vaadin.flow.server.StreamResource;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

@Service
class ImageUploaderService implements ImageUploaderServiceInterface {

    // Method from Vaadin demo used to manage the uploader component and produce a preview image.
    public Component createComponent(String mimeType, String fileName,
                                     InputStream stream) {
        if (mimeType.startsWith("text")) {
            return null;
        } else if (mimeType.startsWith("image")) {
            Image image = new Image();
            image.setMaxHeight("1000px");
            image.setMaxWidth("1000px");
            try {

                byte[] bytes = IOUtils.toByteArray(stream);
                image.getElement().setAttribute("src", new StreamResource(
                        fileName, () -> new ByteArrayInputStream(bytes)));
                try (ImageInputStream in = ImageIO.createImageInputStream(
                        new ByteArrayInputStream(bytes))) {
                    final Iterator<ImageReader> readers = ImageIO
                            .getImageReaders(in);
                    if (readers.hasNext()) {
                        ImageReader reader = readers.next();
                        try {
                            reader.setInput(in);
                            image.setWidth(reader.getWidth(0) + "px");
                            image.setHeight(reader.getHeight(0) + "px");
                        } finally {
                            reader.dispose();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return image;
        }
        Div content = new Div();
        String text = String.format("Mime type: '%s'\nSHA-256 hash: '%s'",
                mimeType, Arrays.toString(MessageDigestUtil.sha256(stream.toString())));
        content.setText(text);
        return content;

    }

    /**
     * Method to display the content that was uploaded by the user.
     * @param text The file type.
     * @param content The filename.
     * @param outputContainer The OutputBuffer with the file itself.
     */
    public void showOutput(String text, Component content,
                           HasComponents outputContainer){
        HtmlComponent p = new HtmlComponent(Tag.P);
        p.getElement().setText(text);
        outputContainer.add(p);
        outputContainer.add(content);
    }

    public void uploadImages(MultiFileMemoryBuffer buffer) throws IOException {
        Gson g = new Gson();

        Set<String> files = buffer.getFiles();
        System.out.println(" Imma upload that! " + files.toString());
        // This is where the image will be uploaded to the database and then sent to visual recognition.
        for (String fileName: files) {
            BufferedImage imageData = ImageIO.read(buffer.getInputStream(fileName));
            String extension = FilenameUtils.getExtension(fileName);
            String nameNoExtension = FilenameUtils.removeExtension(fileName);

            // Upload image to the database.
            Database.Image imageToUpload = new Database.Image(1);
            imageToUpload.SetImage(imageData,extension);
            int imageID = DatabaseFunctions.DBImages.CreateImage(imageToUpload);
            if(imageID == -1){
                System.console().printf("Failed to get ID");
            } else {
                // Run visual recognition and upload to Discovery.
                Map<String, String> metadata = new HashMap<>();
                metadata.put("UserID", "1");
                metadata.put("ImageID", String.valueOf(imageID));

                String featureJSON = runVisualRecognition(buffer.getInputStream(fileName), g);
                // This ensures that the JSON file name will usually always be unique.
                String JSONFileName = metadata.get("UserID") + "-" + metadata.get("ImageID");
                String uploadResponse = uploadFeatureJSONToDiscovery(featureJSON, JSONFileName, g.toJson(metadata));
                System.out.println(uploadResponse);
            }
        }
    }

    /**
     * Sends the image file to Visual Classification and returns the JSON response after adding in extra information.
     * @param file Input stream of the image file to be sent to Visual Recognition
     * @return JSON String of the Visual Recognition response.
     */
    public String runVisualRecognition(InputStream file, Gson g){
        IBMClothesClassifier classifier = new IBMClothesClassifier();
        Map<String,String> visualRecognitionFeatures = classifier.getClothingAttributes(file);
        return g.toJson(visualRecognitionFeatures);
    }

    /**
     * Take the response JSON from the visual recognition service and upload it to discovery.
     * @param featureJson The JSON response received from the visual recognition service.
     * @param metadata A JSON string representing the metadata to include with the JSON response.
     * @return A string representing the discovery response for the upload.
     */
    private String uploadFeatureJSONToDiscovery(String featureJson, String filename, String metadata){
        IamAuthenticator authenticator = new IamAuthenticator("Hf-r0AjrwBGkJ7b1fsAjmTdFRIHOAYkBN_Gj0yR0X9tU");
        Discovery discovery = new Discovery("2019-04-30",authenticator);
        discovery.setServiceUrl("https://api.us-south.discovery.watson.cloud.ibm.com/instances/4f60488c-afaa-4e79-a7f3-849d9d7b9b35");

        String environmentID = "c258a6c8-7cdd-494a-8e82-6dc4aa539c78";
        String collectionID = "f66cd158-2332-4ca9-90d0-546831084de1";
        InputStream documentStream = new ByteArrayInputStream(featureJson.getBytes());
        AddDocumentOptions.Builder builder = new AddDocumentOptions.Builder(environmentID,collectionID);
        builder.file(documentStream);
        builder.filename(filename);
        builder.metadata(metadata);
        builder.fileContentType(HttpMediaType.APPLICATION_JSON);
        DocumentAccepted response = discovery.addDocument(builder.build()).execute().getResult();
        return response.toString();
    }
}