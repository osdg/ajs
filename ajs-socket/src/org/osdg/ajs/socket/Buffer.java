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

package org.osdg.ajs.socket;

import java.nio.ByteBuffer;

/**
 * Created by plter on 9/11/15.
 */
public class Buffer {


    private ByteBuffer byteBuffer;
    private int _size = 0;

    public Buffer(int capacity) {
        byteBuffer = ByteBuffer.allocate(capacity);
    }

    public Buffer() {
        this(64);
    }

    public int getCapacity() {
        return byteBuffer.capacity();
    }

    public void put(byte[] bytes) {
        if (bytes.length > byteBuffer.remaining()) {

            ByteBuffer tmp = ByteBuffer.allocate(getCapacity() * 2);
            byteBuffer.flip();
            tmp.put(byteBuffer);
            tmp.put(bytes);
            byteBuffer = tmp;
        } else {
            byteBuffer.put(bytes);
        }

        _size += bytes.length;
    }

    public byte get(int index) {
        return byteBuffer.get(index);
    }

    public int size() {
        return _size;
    }

    public byte[] toByteArray(){
        byte[] bytes = new byte[size()];
        byteBuffer.get(bytes);
        return bytes;
    }
}
