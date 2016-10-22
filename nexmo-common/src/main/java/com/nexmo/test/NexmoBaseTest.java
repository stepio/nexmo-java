package com.nexmo.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public abstract class NexmoBaseTest {
    private static final String DEFAULT_CONFIG_FILE = "test.properties";

    private final Properties config = new Properties();

    protected NexmoBaseTest() throws IOException {
        this(DEFAULT_CONFIG_FILE);
    }

    protected NexmoBaseTest(String filename) throws IOException {
        try (InputStream in = getClass().getResourceAsStream(filename)){
            config.load(in);
        }
        catch (IOException e) {
            throw new IOException("unable to load test configuration file: " + filename);
        }
    }

    protected long getLong(String key) {
        return Long.parseLong(getProperty(key, false));
    }

    protected String getProperty(String key) {
        return getProperty(key, true);
    }

    protected String getProperty(String key, boolean mandatory) {
        String value = config.getProperty(key);
        if (mandatory && (value == null || value.length() == 0))
            throw new IllegalArgumentException("Configuration value not found: " + key);
        return value;
    }

    protected String getApiKey() {
        return getProperty("api.key");
    }

    protected String getApiSecret() {
        return getProperty("api.secret");
    }

}
