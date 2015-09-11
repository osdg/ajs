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

import org.osdg.ajs.socket.Buffer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by plter on 9/11/15.
 */
public class HttpFilterChannelAttachment {

    private Buffer buffer = new Buffer(2048);
    private String method,context,httpVersion;
    private Map<String,String> headers = new HashMap<>();

    public Buffer getBuffer() {
        return buffer;
    }

    public String getContext() {
        return context;
    }

    public String getMethod() {
        return method;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    void setContext(String context) {
        this.context = context;
    }

    void setHttpVersion(String httpVersion) {
        this.httpVersion = httpVersion;
    }

    void setMethod(String method) {
        this.method = method;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public HttpRequest toHttpRequest(){
        return new HttpRequest(getContext(),getMethod(),getHttpVersion(),getHeaders());
    }
}
