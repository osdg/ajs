package org.osdg.ajs.socket;

/**
 * Created by plter on 9/11/15.
 */
public class FilterAdapter extends Filter {


    @Override
    protected void onAccept(Channel channel) {
        if (getNext()!=null){
            getNext().onAccept(channel);
        }
    }

    @Override
    protected void onClose(Channel channel) {
        if (getNext()!=null){
            getNext().onClose(channel);
        }
    }

    @Override
    protected void onReceive(Channel channel, Object msg) {
        if (getNext()!=null){
            getNext().onReceive(channel,msg);
        }
    }
}
