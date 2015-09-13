package org.osdg.ajs.socket;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.Future;

/**
 * Created by plter on 9/11/15.
 */
public class Channel {

    private AsynchronousSocketChannel asynchronousSocketChannel;
    private Object attachment;

    public Channel(AsynchronousSocketChannel asynchronousSocketChannel) {
        this.asynchronousSocketChannel = asynchronousSocketChannel;
    }

    public AsynchronousSocketChannel getAsynchronousSocketChannel() {
        return asynchronousSocketChannel;
    }

    public void setAttachment(Object attachment) {
        this.attachment = attachment;
    }

    public Object getAttachment() {
        return attachment;
    }

    public Future<Integer> write(ByteBuffer buffer) {
        return getAsynchronousSocketChannel().write(buffer);
    }

    public <A> void write(ByteBuffer src,A attachment,CompletionHandler<Integer,? super A> handler){
        getAsynchronousSocketChannel().write(src,attachment,handler);
    }

    public void close() throws IOException {
        getAsynchronousSocketChannel().close();
    }
}
