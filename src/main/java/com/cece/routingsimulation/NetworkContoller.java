package com.cece.routingsimulation;

import java.util.*;
public class NetworkContoller {
    private List<Router> routers;
    
    public static void main(String[] args){
        new NetworkContoller();
    }

    public NetworkContoller(){
        this.routers = new ArrayList<>();
        routers.add(new Router(1, 2, 3));
        routers.add(new Router(2, 1, 3));
        routers.add(new Router(3, 1, 2));
        routers.add(new Router(4, 3));
        for(Router router : routers){
            router.start();
        }

    }
}
