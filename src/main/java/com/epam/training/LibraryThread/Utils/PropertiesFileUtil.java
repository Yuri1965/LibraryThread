package com.epam.training.LibraryThread.Utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by URA on 26.08.2015.
 */
public class PropertiesFileUtil {
    public static Properties myProperties = new Properties();

    public static void loadPropertiesFromFile() throws IOException {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream("AppProperty.xml");

            myProperties.loadFromXML(fis);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fis.close();
        }
    }

    public static void savePropertiesToFile() throws IOException {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream("AppProperty.xml");

            myProperties.storeToXML(fos, "", "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fos.close();
        }
    }

    public static void saveProperty(String propName, String valueProperty) {
        myProperties.setProperty(propName, valueProperty);
    }

    public static void getProperty(String propName) {
        myProperties.getProperty(propName, "10");
    }
}
