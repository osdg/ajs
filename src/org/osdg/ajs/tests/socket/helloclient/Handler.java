package org.osdg.ajs.tests.socket.helloclient;

import org.osdg.ajs.socket.Channel;
import org.osdg.ajs.socket.FilterAdapter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.concurrent.ExecutionException;

/**
 * Created by plter on 9/11/15.
 */
public class Handler extends FilterAdapter {


    @Override
    protected void onAccept(Channel channel) {

        try {
            channel.write(ByteBuffer.wrap("Hello Client\n".getBytes("UTF-8"))).get();

            channel.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        super.onAccept(channel);
    }

    @Override
    protected void onClose(Channel channel) {
        super.onClose(channel);

        System.out.println("channel close");
    }
}
