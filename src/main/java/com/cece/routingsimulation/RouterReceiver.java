package com.cece.routingsimulation;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.*;

public class RouterReceiver extends Thread {
    private int myId;
    private Map<Integer, Entry> table;
    private Set<Integer> neighbors;

    public RouterReceiver(int myId, Map<Integer, Entry> table, Set<Integer> neighbors){
        this.myId = myId;
        this.table = table;
        this.neighbors = neighbors;
    }

    @Override
    public void run() {
        System.out.println("Router " + this.myId + " start listening!");
        while(true){
            try {
                DatagramSocket ds = new DatagramSocket(3000 + myId);
                byte[] buffer = new byte[2048];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                ds.receive(packet); 
                ObjectInputStream iStream = new ObjectInputStream(new ByteArrayInputStream(buffer));
                Message message = (Message) iStream.readObject();
                
                iStream.close();
                ds.close();
                handle(message);
            } catch (Exception e) {
                //TODO: handle exception
                e.printStackTrace();
            }
        }             
    }

    private void handle(Message message){
        if(!neighbors.contains(message.getSenderId())){
            neighbors.add(message.getSenderId());
        }
        switch (message.getType()) {
            case UPDATE:
                update(message.getEntries());
                break;
            default:
                break;
        }
    }

    private void update(List<Entry> entries){
        for(Entry entry : entries){
            int destination = entry.getDestination();
            if(isBetterPath(entry)){
                entry.addDistance(1);
                table.put(destination, entry);
            }
        }
    }

    private boolean isBetterPath(Entry entry){
        int destination = entry.getDestination();
        return !table.containsKey(entry.getDestination()) || table.get(destination).getDistance() > 1 + entry.getDistance();
    }


}
