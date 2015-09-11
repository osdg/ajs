package org.osdg.ajs.tests.socket.rcvmsg;

import org.osdg.ajs.socket.AjsServer;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Created by plter on 9/11/15.
 */
public class RecMsg {

    public static void main(String[] args){

        AjsServer server = new AjsServer(8000);
        server.getFilterChain().addFilter(new Handler());
        try {
            System.out.println("Server will start at port 8000");
            server.start();
        } catch (IOException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
