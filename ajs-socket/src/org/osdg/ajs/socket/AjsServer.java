package org.osdg.ajs.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;

/**
 * Created by plter on 9/11/15.
 */
public class AjsServer {


    private int port = 8000;
    private final ByteBuffer buffer = ByteBuffer.allocateDirect(2048);
    private final FilterChain filterChain = new FilterChain();

    public AjsServer(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public FilterChain getFilterChain() {
        return filterChain;
    }

    public void start() throws IOException, ExecutionException, InterruptedException {
        AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open();
        server.bind(new InetSocketAddress(getPort()));

        while (true){
            new ChannelHandler(server.accept().get()).handle();
        }
    }

    private class ChannelHandler{

        private AsynchronousSocketChannel channel = null;
        private Channel channelWrapper = null;
        private ByteBuffer buffer = ByteBuffer.allocateDirect(2048);

        public ChannelHandler(AsynchronousSocketChannel channel) {
            this.channel = channel;

            channelWrapper = new Channel(channel);
            getFilterChain().doAccept(channelWrapper);
        }

        public void handle(){
            channel.read(buffer, channelWrapper, new CompletionHandler<Integer, Channel>() {
                @Override
                public void completed(Integer result, Channel attachment) {

                    if (result!=-1){
                        buffer.flip();
                        byte[] bytes = new byte[buffer.remaining()];
                        buffer.get(bytes);
                        getFilterChain().doReceive(attachment, bytes);
                        buffer.clear();

                        channel.read(buffer,attachment,this);
                    }else {
                        getFilterChain().doClose(attachment);
                    }
                }

                @Override
                public void failed(Throwable exc, Channel attachment) {
                    getFilterChain().doClose(attachment);
                }
            });
        }
    }
}
