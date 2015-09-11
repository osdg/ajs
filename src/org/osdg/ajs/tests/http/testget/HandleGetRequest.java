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

package org.osdg.ajs.tests.http.testget;

import org.osdg.ajs.http.HttpFilter;
import org.osdg.ajs.socket.AjsServer;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Created by plter on 9/11/15.
 */
public class HandleGetRequest {

    public static void main(String[] args){
        AjsServer server = new AjsServer(8000);
        server.getFilterChain().addFilter(new HttpFilter());
        server.getFilterChain().addFilter(new Handler());
        try {
            System.out.println("Server will start at port 8000");
            server.start();
        } catch (IOException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
