/**
 * Copyright [2015] [plter] http://osdg.org
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.osdg.ajs.http;


import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


//TODO This class is not right
public class HttpResponse {

    private static final byte[] CRLF = new byte[]{'\r', '\n'};
    private static final byte[] SPACE = new byte[]{' '};
    private static final byte[] HTTP_VERSION = new byte[]{'H','T','T','P','/','1','.','1'};
    private static final byte[] OK=new byte[]{'O','K'};
    private static final byte[] NOT_FOUND = new byte[]{'N','o','t',' ','F','o','u','n','d'};

    /**
     * HTTP response codes
     */
    public static final int HTTP_STATUS_SUCCESS = 200;
    public static final int HTTP_STATUS_ACCESS_DENIED = 403;
    public static final int HTTP_STATUS_NOT_FOUND = 404;
    public static final int HTTP_STATUS_SERVER_ERROR = 500;

    private long contentLength = 0;
    private final Map<String, String> headers = new HashMap<String, String>();
    private int responseCode = 200;
    private HttpRequest httpRequest = null;
    private SelectionKey selectionKey = null;
    private boolean keepAlive = false;
    private String charset = "UTF-8";


    public HttpResponse() {
    }

    private void initHeaders() {
        header("Server", "ajs(Async Java Server)");
        header("Cache-Control", "private");
        header("Connection", "keep-alive");
        header("Keep-Alive", "200");
    }

    private HttpResponse header(String name, String value) {
        getHeaders().put(name, value);
        return this;
    }


    private final ByteBuffer headerBuf = ByteBuffer.allocateDirect(2048);

    private final void makeHeaderBuf() {

        headerBuf.clear();
        headerBuf.put(HTTP_VERSION);
        headerBuf.put(SPACE);
        headerBuf.put(String.valueOf(getResponseCode()).getBytes());
        headerBuf.put(SPACE);
        switch (getResponseCode()) {
            case HttpResponse.HTTP_STATUS_SUCCESS:
                headerBuf.put(OK);
                break;
            case HttpResponse.HTTP_STATUS_NOT_FOUND:
                headerBuf.put(NOT_FOUND);
                break;
        }
        headerBuf.put(CRLF);
        for (Entry<String, String> entry : getHeaders().entrySet()) {
            headerBuf.put(entry.getKey().getBytes());
            headerBuf.put(": ".getBytes());
            headerBuf.put(entry.getValue().getBytes());
            headerBuf.put(CRLF);
        }
        // now the content length is the body length
        if (getContentLength() > 0) {
            headerBuf.put("Content-Length: ".getBytes());
            headerBuf.put(String.valueOf(getContentLength()).getBytes());
            headerBuf.put(CRLF);
        }
        headerBuf.put(CRLF);

        headerBuf.flip();

    }

    public void setContentType(String contentType) {
        header("Content-Type", String.format("%s; charset=%s", contentType, getCharset()));
        this.contentType = contentType;
    }

    public String getContentType() {
        return contentType;
    }

    private String contentType = null;

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }


    public Map<String, String> getHeaders() {
        return headers;
    }

    public long getContentLength() {
        return contentLength;
    }

    public void setContentLength(long value) {
        contentLength = value;
    }

    public ByteBuffer getHeaderBuf() {
        makeHeaderBuf();
        return headerBuf;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getCharset() {
        return charset;
    }
}