/**
 * The MIT License
 * Copyright (c) 2011 - 2016, Nexmo Inc
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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
