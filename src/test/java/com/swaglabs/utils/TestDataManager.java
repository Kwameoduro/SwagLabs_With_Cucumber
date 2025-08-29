package com.swaglabs.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class TestDataManager {

    private static Properties properties;
    private static TestDataManager instance;

    private TestDataManager() {
        loadProperties();
    }

    public static TestDataManager getInstance() {
        if (instance == null) {
            instance = new TestDataManager();
        }
        return instance;
    }

    private void loadProperties() {
        properties = new Properties();
        try (InputStream inputStream = getClass().getClassLoader()
                .getResourceAsStream("testdata.properties")) {
            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                throw new RuntimeException("testdata.properties file not found in resources");
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load testdata.properties", e);
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }


    // Product Methods
    public String getBackpackName() {
        return getProperty("product.backpack");
    }

    public String getBikeLightName() {
        return getProperty("product.bikelight");
    }

    // Product Price Methods
    public String getBackpackPrice() {
        return getProperty("price.backpack");
    }

    public String getBikeLightPrice() {
        return getProperty("price.bikelight");
    }

    public String getCartHeader() {
        return getProperty("header.cart");
    }
}