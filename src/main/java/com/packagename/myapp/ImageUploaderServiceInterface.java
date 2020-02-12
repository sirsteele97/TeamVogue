package com.packagename.myapp;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;

import java.io.InputStream;
import java.io.Serializable;

interface ImageUploaderServiceInterface extends Serializable {
    void uploadImages(MultiFileMemoryBuffer buffer);

    void showOutput(String fileName, Component component, HasComponents outputContainer);

    Component createComponent(String mimeType, String fileName, InputStream inputStream);
}
