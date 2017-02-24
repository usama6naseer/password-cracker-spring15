import java.util.*;
import java.io.*;
import java.net.*;
import java.nio.*;

public class WorkerClientThread extends Thread {
    public static DatagramSocket dg;
    // public static Boolean work_finished = true;
    // public static Boolean work_flag = true;
    public WorkerClientThread(DatagramSocket d) {
        dg =d;
    }
    public static char[] makeMyArray(char[] magic, char[] client_id, char[] command, char[] range_start, char[] range_end, char[] hash) {
        StringBuilder sb = new StringBuilder(64);
        sb.append(magic);
        sb.append(client_id);
        sb.append(command);
        sb.append(range_start);
        sb.append(range_end);
        sb.append(hash);
        char result[] = sb.toString().toCharArray();
        return result;
    }
    public static char[] convertChar(String n) {
        char[] ch = new char[2];
        int count = 0;
        for (int i=0; i<2; i++) {
            if (i > 1-n.length()) {
                ch[i] = n.charAt(count);
                count++;
            }
            else {
                ch[i] = '0';
            }
        }
        return ch;
    } 
    
    public void run() {
        try {
            char[] magic = new char[5];
            char[] client_id = new char[2];
            char[] command = new char[1];
            char[] range_start = new char[5];
            char[] range_end = new char[5];
            char[] hash = new char[33];
            String temp = "15440";
            magic = temp.toCharArray();
            temp = "0";
            client_id = convertChar(temp);
            temp = "1";
            command = temp.toCharArray();
            char[] result = makeMyArray(magic, client_id, command, range_start, range_end, hash);
            byte[] buf = (new String(result)).getBytes();
            DatagramPacket pkt = new DatagramPacket(buf, buf.length, InetAddress.getLocalHost(), 13300);
            dg.send(pkt);

            buf = new byte[1000];
            pkt = new DatagramPacket(buf, buf.length);
            dg.receive(pkt);
            String view = new String(buf);
            view = view.trim();
            System.out.println(view);
            // WorkerClient.start_cracking = true;
            while(true) {
                buf = new byte[1000];
                pkt = new DatagramPacket(buf, buf.length);
                dg.receive(pkt);
                System.out.println("getting responses");
                String view1 = new String(buf);
                view1 = view1.trim();
                System.out.println(view1);

                // ACK JOB
                temp = new String(buf); 
                String action = temp.substring(7,8);
                if (Integer.parseInt(action) == 0) {
                    System.out.println("Ping received");
                    if (WorkerClient.start_cracking == true && WorkerClient.pass_found == false) {
                        System.out.println("work not finished");
                        char[] temp2 = temp.toCharArray();
                        temp2[7] = '6';
                        buf = (new String(temp2)).getBytes();
                        pkt = new DatagramPacket(buf, buf.length, InetAddress.getLocalHost(), 13300);
                        dg.send(pkt);
                    }
                    else {
                        System.out.println("Not doing any work");
                        char[] temp2 = temp.toCharArray();
                        temp2[7] = '0';
                        buf = (new String(temp2)).getBytes();
                        pkt = new DatagramPacket(buf, buf.length, InetAddress.getLocalHost(), 13300);
                        dg.send(pkt);                    
                    }
                }
                if (Integer.parseInt(action) == 2) {
                    WorkerClient.start_cracking = true;

                    System.out.println("Job received");
                    char[] temp2 = temp.toCharArray();
                    temp2[7] = '3';
                    buf = (new String(temp2)).getBytes();
                    pkt = new DatagramPacket(buf, buf.length, InetAddress.getLocalHost(), 13300);
                    dg.send(pkt);

                    CrackerThread t2 = new CrackerThread(temp);
                    t2.start();
                }   
                if (Integer.parseInt(action) == 7) {
                    System.out.println("canceling worker client");
                    WorkerClient.pass_found = true;
                    WorkerClient.start_cracking = false;
                }
            }    
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}




