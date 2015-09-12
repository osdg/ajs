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

import org.osdg.ajs.http.HttpRequest;
import org.osdg.ajs.socket.Channel;
import org.osdg.ajs.socket.FilterAdapter;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.ExecutionException;

/**
 * Created by plter on 9/11/15.
 */
public class Handler extends FilterAdapter {

    @Override
    protected void onReceive(Channel channel, Object msg) {

        HttpRequest request = (HttpRequest) msg;

//        System.out.println(request.getContext());

        ByteBuffer buffer = ByteBuffer.allocate(2048);
        buffer.put("HTTP/1.1 200 OK\r\n".getBytes());
        buffer.put("Content-Type: text/html\r\n".getBytes());
        buffer.put("\r\n".getBytes());
        buffer.put("<html><head><title>Hello AJS</title></head><body><h1>Hello AJS</h1></body></html>".getBytes());
        buffer.flip();

        try {
            channel.write(buffer).get();
            channel.close();
        } catch (InterruptedException | ExecutionException | IOException e) {
            e.printStackTrace();
        }
    }
}
