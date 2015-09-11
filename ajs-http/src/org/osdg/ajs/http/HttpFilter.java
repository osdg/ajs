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
import org.osdg.ajs.socket.Channel;
import org.osdg.ajs.socket.FilterAdapter;

import java.io.IOException;

/**
 * Created by plter on 9/11/15.
 */
public class HttpFilter extends FilterAdapter {


    @Override
    protected void onAccept(Channel channel) {
        channel.setAttachment(new HttpFilterChannelAttachment());
        super.onAccept(channel);
    }

    @Override
    protected void onReceive(Channel channel, Object msg) {

        HttpFilterChannelAttachment attachment = (HttpFilterChannelAttachment) channel.getAttachment();


        Buffer buffer = attachment.getBuffer();
        buffer.put((byte[]) msg);

        if (buffer.size() >= 8) {
            if (buffer.get(0) == 'G' && buffer.get(1) == 'E' && buffer.get(2) == 'T') {
                if (getHeaderCompleted(buffer)) {
                    attachment.setMethod("GET");
                    decodeGetHeader(channel);

                    super.onReceive(channel,attachment.toHttpRequest());
                }
            } else {
                //TODO unsupport
                try {
                    channel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean getHeaderCompleted(Buffer buffer) {
        int size = buffer.size();
        return buffer.get(size - 4) == '\r'
                && buffer.get(size - 3) == '\n'
                && buffer.get(size - 2) == '\r'
                && buffer.get(size - 1) == '\n';
    }

    private void decodeGetHeader(Channel channel) {
        HttpFilterChannelAttachment attachment = (HttpFilterChannelAttachment) channel.getAttachment();
        Buffer buffer = attachment.getBuffer();

        StringBuilder line = new StringBuilder();
        char ch;
        boolean firstLine = true;
        for (int i =0;i<buffer.size();i++){
            ch = (char) buffer.get(i);

            if (ch!='\n') {
                line.append((char) buffer.get(i));
            }else {
                //find line end
                String lineContent = line.toString().trim();
                if (!firstLine){
                    String[] tokens = lineContent.split(": ");
                    if (tokens.length==2){
                        attachment.getHeaders().put(tokens[0],tokens[1]);
                    }
                }else {
                    firstLine = false;
                    //read first line
                    String[] strings = lineContent.split(" ");
                    if (strings.length==3) {
                        attachment.setContext(strings[1]);
                        attachment.setHttpVersion(strings[2]);
                    }else {
                        try {
                            channel.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                line.delete(0,line.length());
            }
        }
    }
}
