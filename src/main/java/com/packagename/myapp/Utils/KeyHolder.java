package com.packagename.myapp.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class KeyHolder {

    private static Properties keys = new Properties();

    public static void loadKeys() {
        try {
            InputStream stream = KeyHolder.class.getClassLoader().getResourceAsStream("credentials.properties");
            keys.load(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getKey(String keyName) {
        return keys.getProperty(keyName);
    }

}
