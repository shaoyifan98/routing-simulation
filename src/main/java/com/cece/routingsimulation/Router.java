package com.cece.routingsimulation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.*;


public class Router extends Thread{

	public static final int UNREACHABLE = 16;

	public static final int SEND_BREAK = 5000;

	private Map<Integer, Entry> table;

	private int id;

	//store neighbors' ports
	private Set<Integer> neighbors;

	private RouterReceiver rr;


	public Router(int id, int ... neighbors){
		this.id = id;
		this.table = new HashMap<>();
		this.neighbors = new HashSet<>();
		//init neighbors ports
		for(int i : neighbors){
			this.neighbors.add(i);
		}
		table.put(this.id, new Entry(this.id, 0, this.id));
		rr = new RouterReceiver(this.id, this.table, this.neighbors);
		rr.start();
	}


	@Override
    public void run() {
		while (true) {
			printTable();
			sendTableToNeighbors();
			try {
				sleep(SEND_BREAK);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }
	
	private void sendTableToNeighbors(){
		for(int id : neighbors){
			sendTableToNeighbor(id);
		}
	}

	private void sendTableToNeighbor(int neighborId){
		try {
			DatagramSocket ds = new DatagramSocket();
			ds.setSoTimeout(1000);
			ds.connect(InetAddress.getByName("localhost"), 3000 + neighborId); // 连接指定服务器和端口
			// 发送:
			byte[] data = contructMessage(neighborId);
			DatagramPacket packet = new DatagramPacket(data, data.length);
			ds.send(packet);
			ds.close();
		} catch (Exception e) {
			//TODO: handle exception
			e.printStackTrace();
		}
		
	}

	private byte[] contructMessage(int neighborId) throws IOException{
		List<Entry> entries = new ArrayList<>();
		for(Entry entry : table.values()){
			if(entry.getNextHop() != neighborId){
				entries.add(new Entry(entry.getDestination(), entry.getDistance(), id));
			}
		}
		Message message = new Message(id, entries, Message.Type.UPDATE);
		ByteArrayOutputStream bStream = new ByteArrayOutputStream();
		ObjectOutput oo = new ObjectOutputStream(bStream); 
		oo.writeObject(message);
		oo.close();
		byte[] serializedMessage = bStream.toByteArray();
		return serializedMessage;
	}

	private void printTable(){
		System.out.println("I am router " + id);
		for(Entry entry : table.values()){
			System.out.println(entry);
		}
	}

	

	
}