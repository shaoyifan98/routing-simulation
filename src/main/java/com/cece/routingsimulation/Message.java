package com.cece.routingsimulation;

import java.util.*;

public class Message implements java.io.Serializable{
    private int senderId;
    private List<Entry> entries;
    private Type type;

    public Message(int senderId, List<Entry> entry, Type type) {
        this.entries = entry;
        this.type = type;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public void setEntries(List<Entry> entry) {
        this.entries = entry;
    }

    

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    
    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    enum Type {
        UPDATE,
        STOP
    }


}
