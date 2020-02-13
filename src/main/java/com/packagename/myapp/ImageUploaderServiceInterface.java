package com.packagename.myapp;

import com.google.gson.Gson;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Map;

interface ImageUploaderServiceInterface extends Serializable {
    void uploadImages(MultiFileMemoryBuffer buffer) throws IOException;

    void showOutput(String fileName, Component component, HasComponents outputContainer);

    Component createComponent(String mimeType, String fileName, InputStream inputStream);

    String runVisualRecognition(InputStream file, Gson g);
}
