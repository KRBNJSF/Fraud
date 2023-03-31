package cz.reindl.frauddetection.service.conf;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ApplicationConfiguration {

    public static Properties PROPERTIES;

    public ApplicationConfiguration() {
        setProperties();
    }

    private void setProperties() {
        PROPERTIES = new Properties();
        try {
            PROPERTIES.load(new FileInputStream(new File("src/main/resources/config.properties")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return PROPERTIES.getProperty(key);
    }

}
