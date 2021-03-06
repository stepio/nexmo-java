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
package com.nexmo.insight.sdk;

/*-
 * #%L
 * nexmo-insight
 * %%
 * Copyright (C) 2011 - 2016 Nexmo Inc
 * %%
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
 * #L%
 */

import com.nexmo.test.NexmoBaseTest;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;

import java.io.IOException;

import static org.junit.Assert.*;


public class NexmoInsightClientTest extends NexmoBaseTest {

    private NexmoInsightClient client;

    public NexmoInsightClientTest() throws IOException {
        super();
    }

    @Before
    public void setUp() throws ParserConfigurationException {
        client = new NexmoInsightClient(getApiKey(), getApiSecret());
    }

    @Test
    public void testInsight() throws IOException, SAXException {
        String[] features = null;
        String _features = getProperty("insight.features", false);
        if (_features != null)
            features = _features.split(",");

        InsightResult r = client.request(
                getProperty("insight.number"),
                getProperty("insight.callback.url"),
                features,
                getLong("insight.callback.timeout"),
                getProperty("insight.callback.method", false),
                getProperty("insight.callback.clientRef", false),
                getProperty("insight.callback.ipAddress", false));
        assertEquals(InsightResult.STATUS_OK, r.getStatus());
        assertEquals(getProperty("insight.number"), r.getNumber());
        assertEquals(0.03f, r.getRequestPrice(), 0);
    }

}
