import java.util.*;
import java.io.*;
import java.net.*;
import java.nio.*;

public class WorkerClient {
        public static Boolean start_cracking = false;
        public static String range_start;
        public static String range_end;
        public static String test;
        public static int count = 0;
        public static Boolean pass_found = false;
        // public static int count_crack = 0; 

        public static void main(String args[]) {
                try {
                        // start_cracking = false;
                        // test = new String("hello");
                        InetAddress address = InetAddress.getLocalHost();
                        System.out.println("Sending");
                        DatagramSocket dg = new DatagramSocket();
                        
                        WorkerClientThread t1 = new WorkerClientThread(dg);
                        t1.start();
                        // CrackerThread t2 = new CrackerThread();
                        // t2.start();

                } catch (Exception e) {
                        e.printStackTrace();
                }
        }
}




