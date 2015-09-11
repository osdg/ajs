package org.osdg.ajs.tests.socket.rcvmsg;

import org.osdg.ajs.socket.Channel;
import org.osdg.ajs.socket.FilterAdapter;

import java.io.IOException;

/**
 * Created by plter on 9/11/15.
 */
public class Handler extends FilterAdapter {

    @Override
    protected void onReceive(Channel channel, Object msg) {

        byte[] data = (byte[]) msg;

        try {
            String textMsg = new String(data,"UTF-8");

            System.out.println("Receive message : " + textMsg);

            if (textMsg.trim().equals("quit")){
                channel.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        super.onReceive(channel, msg);
    }
}
