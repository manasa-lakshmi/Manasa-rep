package com.ceix.ehs.model.loader;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.Properties;

import oracle.adf.share.logging.ADFLogger;

public class ReadProperties {
    private static ADFLogger _logger = ADFLogger.createADFLogger(ReadProperties.class);
    private static String EXTERNAL_PROPERTY_FILE = "WebService.properties";
    Properties mModelProperties = null;

    public ReadProperties() {
        super();
    }

    public String getResourceFromProperties(String key) {
        // read property file only once
        if (mModelProperties == null) {
            initProperties();
        }

        return mModelProperties.getProperty(key);
    }

    /**
     * load the properties from the resource file
     */
    private void initProperties() {
        //InputStream inStream = this.getClass().getClassLoader().getResourceAsStream(EXTERNAL_PROPERTY_FILE);
        InputStream inStream = null;
        try {
            inStream = new FileInputStream(EXTERNAL_PROPERTY_FILE);
            if (inStream == null) {
                System.out.println(" ===> Could not load property file: ==> '" +EXTERNAL_PROPERTY_FILE + "'");
                mModelProperties = new Properties();
                return;
            }
            mModelProperties = new Properties();
            mModelProperties.load(inStream);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(" ===>> Can't load properties: " +e.getMessage());
        }
        finally {
            if(inStream!=null)
                try {
                    inStream.close();    
                    inStream=null;
                }
            catch(Exception e) {
                    e.printStackTrace();
                }
                


        }
        //System.out.println("  ===>> Model properties loaded!");
    }


    }
