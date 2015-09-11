package org.osdg.ajs.socket;

import java.nio.channels.AsynchronousSocketChannel;

/**
 * Created by plter on 9/11/15.
 */
public class FilterChain {


    private Filter first=null,last=null;

    public Filter getFirst() {
        return first;
    }

    public Filter getLast() {
        return last;
    }

    public void addFilter(Filter filter){
        if (first!=null&&last!=null){
            last.setNext(filter);
            filter.setPrevious(last);
            last = filter;
        }else {
            first = filter;
            last = filter;
        }
    }

    public void doAccept(Channel channel){
        if (getFirst()!=null){
            getFirst().onAccept(channel);
        }
    }

    public void doClose(Channel channel){
        if (getFirst()!=null){
            getFirst().onClose(channel);
        }
    }

    public void doReceive(Channel channel,Object msg){
        if (getFirst()!=null){
            getFirst().onReceive(channel,msg);
        }
    }
}
