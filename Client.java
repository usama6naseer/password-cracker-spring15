import java.util.*;
import java.io.*;
import java.net.*;
import java.nio.*;

public class Client {
        public static char[] makeMyArray(char[] magic, char[] client_id, char[] command) {
                StringBuilder sb = new StringBuilder(64);
                sb.append(magic);
                sb.append(client_id);
                sb.append(command);
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
        public static void main(String args[]) {
                int count = 0;
                try {
                InetAddress address = InetAddress.getLocalHost();
                System.out.println("Request Client Sending");
                final DatagramSocket dg = new DatagramSocket();
               // Telling about port
                // String str = "port".concat(Integer.toString(dg.getLocalPort()));
                // byte[] buf = str.getBytes();
                // DatagramPacket pkt = new DatagramPacket(buf, buf.length, address.getLocalHost(), 13300);
                // dg.send(pkt);

                // Start protocol
                char[] magic = new char[5];
                char[] client_id = new char[2];
                char[] command = new char[1];
                char[] hash = new char[33];
                String temp = "15440";
                magic = temp.toCharArray();
                temp = "0";
                client_id = convertChar(temp);
                temp = "8";
                command = temp.toCharArray();

                char[] result = makeMyArray(magic, client_id, command);
                String tempstr = (new String(result)).concat(args[0]);
                tempstr = tempstr.trim();
                final String temp_inner = tempstr;
                byte[] buf = tempstr.getBytes();
                DatagramPacket pkt = new DatagramPacket(buf, buf.length, address.getLocalHost(), 13300);
                dg.send(pkt);

                while (true) {
                    byte[] bufff = new byte[1000];
                    System.out.println("listening");
                    DatagramPacket pktt = new DatagramPacket(bufff, bufff.length);
                    dg.receive(pktt);
                    String rcv_str = new String(bufff);
                    rcv_str = rcv_str.substring(7,8);
                    int com = Integer.parseInt(rcv_str);

                    if (com == 0) {
                        System.out.println("Sending ping response");
                        DatagramPacket new_pkt = new DatagramPacket(bufff, bufff.length, address.getLocalHost(), 13300);
                        dg.send(new_pkt);                        
                    }
                    else if (com == 3) {
                        System.out.println("Ackjob" + new String(bufff));    
                    }
                    else if (com == 9) {
                        System.out.println("password found!");
                        System.out.println(new String(bufff));
                        break;
                    }
                    else {
                        System.out.println(new String(bufff));
                           
                    }
                    
     
                }
                // byte[] bufff = new byte[1000];
                // System.out.println("listening");
                // DatagramPacket pktt = new DatagramPacket(bufff, bufff.length);
                // dg.receive(pktt);
                // System.out.println("Acjob" + new String(bufff));
                // // char[] com11  = (new String(bufff)).toCharArray();
                // final char com1 = com11[5];
                // final char com2 = com11[6];

                // Timer ping = new Timer();
                // ping.scheduleAtFixedRate(new TimerTask() {
                // @Override
                // public void run() {
                //     try {
                //         System.out.println("ping");
                //         char[] arr = temp_inner.toCharArray();
                //         arr[5] = com1;
                //         arr[6] = com2;
                //         arr[7] = '0';
                //         String temp1 = new String(arr);
                //         byte[] buff = temp1.getBytes();
                //         DatagramPacket pkt1 = new DatagramPacket(buff, buff.length, address.getLocalHost(), 13300);
                //         dg.send(pkt1);
                //     }
                //     catch(Exception e) {e.printStackTrace();}
                //     }
                //     }, 5000, 5000);


                // bufff = new byte[1000];
                // DatagramPacket pkttt = new DatagramPacket(bufff, bufff.length);
                // dg.receive(pkttt);
                // System.out.println("password found!");
                // System.out.println(new String(bufff));
                // ping.cancel();   
                             
                // Timer ping = new Timer();
                // ping.scheduleAtFixedRate(new TimerTask() {
                // @Override
                // public void run() {
                //     try {
                //         System.out.println("ping");
                //         char[] arr = temp_inner.toCharArray();
                //         arr[5] = com1;
                //         arr[6] = com2;
                //         arr[7] = '0';
                //         String temp1 = new String(arr);
                //         byte[] buff = temp1.getBytes();
                //         DatagramPacket pkt1 = new DatagramPacket(buff, buff.length, address.getLocalHost(), 13300);
                //         dg.send(pkt1);
                //     }
                //     catch(Exception e) {e.printStackTrace();}
                //     }
                //     }, 5000, 5000);

                // buf = new byte[1000];
                // pkt = new DatagramPacket(buf, buf.length);
                // dg.receive(pkt);
                // System.out.println(new String(buf));
                
                // System.out.println(new String(magic));
                // System.out.println(new String(client_id));
                // System.out.println(new String(command));
                // System.out.println(new String(result));
                // 15540

                } catch (Exception e) {
                        e.printStackTrace();
                }
        }
}




