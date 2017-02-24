import java.util.*;
import java.io.*;
import java.net.*;

public class ServerThread extends Thread {
    public String msg;
    public InetAddress address;
    public int port;
    public Boolean worker;
    DatagramSocket socket; 
    
    public ServerThread(DatagramSocket datagramSocket, String s, InetAddress addr) {
      	msg = s;
       	address = addr;
       	socket = datagramSocket;
    }
    public void run() {
    	if (msg.startsWith("port")) {
    		System.out.println("new request client connected");
    		String temp = msg.substring(4);
    		temp = temp.trim();
    		// System.out.println(temp);
    		port = Integer.parseInt(temp);
    		worker = false;
    	}
    	if (msg.startsWith("wport")) {
    		System.out.println("new worker client connected");
    		String temp = msg.substring(5);
    		temp = temp.trim();
    		// System.out.println(temp);
    		port = Integer.parseInt(temp);
    		worker = true;
    	}
        System.out.println("yes");
        System.out.println(msg);
        // Server.w_count = 1;
        try {
	        byte[] buf = new byte[1000];
	        DatagramPacket pkt = new DatagramPacket(buf, buf.length);
	        socket.receive(pkt);
	        System.out.println(new String(buf));
        } catch (Exception e) {
        	e.printStackTrace();
        }

    }
}
