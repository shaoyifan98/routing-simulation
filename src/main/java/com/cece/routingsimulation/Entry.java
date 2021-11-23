package com.cece.routingsimulation;

import java.util.*;
public class Entry implements java.io.Serializable{

    private int destination;
    private int distance;
    private int nextHop;
   

    public Entry(int destination, int distance, int nextHop){
        this.destination = destination;
        this.distance = distance;
        this.nextHop = nextHop;
    }

    public void addDistance(int amount){
        this.distance += amount;
    }

    @Override
    public String toString(){
        return "" + destination + "," + distance + "," + nextHop;
    }

    @Override
    public int hashCode(){
        return this.destination;
    }

    @Override
    public boolean equals(Object o){
        Entry entry = (Entry)o;
        return this.destination == entry.destination;
    }

    public int getDestination() {
        return destination;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getNextHop() {
        return nextHop;
    }

    public void setNextHop(int nextHop) {
        this.nextHop = nextHop;
    }

    


}
