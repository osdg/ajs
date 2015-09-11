package org.osdg.ajs.socket;

/**
 * Created by plter on 9/11/15.
 */
public abstract class Filter {

    private Filter previous=null,next=null;
    private String name = "Noname";

    public Filter(String name) {
        this.name = name;
    }

    public Filter() {
    }

    public Filter getPrevious() {
        return previous;
    }

    public Filter getNext() {
        return next;
    }

    void setNext(Filter next) {
        this.next = next;
    }

    void setPrevious(Filter previous) {
        this.previous = previous;
    }

    public String getName() {
        return name;
    }

    abstract protected void onAccept(Channel channel);
    abstract protected void onClose(Channel channel);
    abstract protected void onReceive(Channel channel,Object msg);
}
