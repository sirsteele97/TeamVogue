package com.packagename.myapp.Utils;

import com.vaadin.flow.server.VaadinSession;

public class SessionData {

    public static void setAttribute(String key, String value){
        VaadinSession.getCurrent().setAttribute(key,value);
    }

    public static String getAttribute(String key){
        Object valueObject = VaadinSession.getCurrent().getAttribute(key);
        if(valueObject == null){
            return "";
        }
        return valueObject.toString();
    }
}
