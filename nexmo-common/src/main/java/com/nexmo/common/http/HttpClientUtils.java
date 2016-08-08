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
package com.nexmo.common.http;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
/**
 * HttpClientUtils.java<br><br>
 *
 * A Helper factory for instanciating HttpClient instances<br><br>
 *
 * Created on 5 January 2011, 17:34
 *
 * @author  Paul Cook
 * @version 1.0
 */
public class HttpClientUtils {

    private final static Map<String, HttpClientUtils> instances = new HashMap<>();

    private final SocketConfig socketConfig;
    private final ConnectionConfig connectionConfig;
    private final RequestConfig requestConfig;
    private final PoolingHttpClientConnectionManager connectionManager;
    private final CloseableHttpClient httpclient;

    private final int connectionTimeout;
    private final int soTimeout;
    private final String appVersion;

    private HttpClientUtils(int connectionTimeout, int soTimeout) {
        this.connectionTimeout = connectionTimeout;
        this.soTimeout = soTimeout;
        this.appVersion = "1.7.0";

        this.socketConfig = SocketConfig.custom()
                .setTcpNoDelay(true)
                .build();
        this.connectionConfig = ConnectionConfig.custom()
                .setCharset(Charset.forName("UTF-8"))
                .build();
        this.requestConfig = RequestConfig.custom()
                .setSocketTimeout(this.soTimeout)
                .setConnectTimeout(this.connectionTimeout)
                .setConnectionRequestTimeout(this.soTimeout)
                .build();
        this.connectionManager = new PoolingHttpClientConnectionManager();
        this.connectionManager.setDefaultMaxPerRoute(200);
        this.connectionManager.setMaxTotal(200);
        this.connectionManager.setValidateAfterInactivity(this.connectionTimeout);

        this.httpclient = HttpClients.custom()
                .useSystemProperties()
                .setUserAgent("Nexmo Java SDK " + appVersion)
                .setDefaultSocketConfig(this.socketConfig)
                .setDefaultConnectionConfig(this.connectionConfig)
                .setDefaultRequestConfig(this.requestConfig)
                .setConnectionManager(this.connectionManager)
                .build();
    }

    /**
     * Return an existing or instanciate a new HttpClient factory instance with explicitely specified connection and read timeout values
     *
     * @param connectionTimeout the timeout value in milliseconds to use when establishing a new http socket
     * @param soTimeout the timeout value in milliseconds to wait for a http response before closing the socket
     *
     * @return HttpClientUtils an instance of the HttpClient factory primed with the requested timeout values
     */
    public static HttpClientUtils getInstance(int connectionTimeout, int soTimeout) {
        return new HttpClientUtils(connectionTimeout, soTimeout);
    }

    /**
     * Instanciate a new HttpClient instance that uses the timeout values associated with this factory instance
     *
     * @return HttpClient a new HttpClient instance
     */
    public CloseableHttpClient getNewHttpClient() {
        return this.httpclient;
    }
}
