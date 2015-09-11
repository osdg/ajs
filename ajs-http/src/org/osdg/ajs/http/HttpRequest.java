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

import java.util.Map;

/**
 * Created by plter on 9/11/15.
 */
public class HttpRequest {


    private String context,method,httpVersion;
    private Map<String,String> headers = null;


    public HttpRequest(String context, String method, String httpVersion, Map<String, String> headers) {
        this.context = context;
        this.method = method;
        this.httpVersion = httpVersion;
        this.headers = headers;
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

    public Map<String, String> getHeaders() {
        return headers;
    }
}
