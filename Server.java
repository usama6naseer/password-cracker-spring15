import java.util.*;
import java.io.*;
import java.net.*;

public class Server {
        // public static int[] jobs;
        public static int[] w_client_id;
        public static int w_count;
        public static int[] r_client_id;
        public static int r_count;
        public static int[] w_client_port;
        public static int[] r_client_port;
        public static Boolean[] free;
        public static Boolean[] ack_job;
        public static int[] job_assigned;
        public static Boolean[] job_finish;
        public static int[] job_count;
        public static int[] ping_count;
        public static int[] ping_count_worker;
        
        // public static Boolean jobfinish = false;

        public static int[] removeFromArray(int[] arr, int a) {
                int flag = 0;
                for (int i=0; i<Server.w_count; i++) {
                        if (arr[i] == a) {
                                flag = 1;
                        }
                        if (flag == 1) {
                                arr[i] = arr[i+1];
                        }
                }
                if (flag == 0) {
                        arr[Server.w_count] = 0;
                }
                return arr;
        }
        // public static void removeFrom_Wport(int a) {
        //         int flag = 0;
        //         for (int i=0; i<Server.r_count; i++) {
        //                 if (Server.w_client_port == a) {
        //                         flag = 1;
        //                 }
        //                 if (flag == 1) {
        //                         Server.w_client_port[i] = Server.w_client_port[i+1];
        //                 }
        //         }
        //         if (flag == 0) {
        //                 Server.w_client_port[Server.r_count] = 0;
        //         }
        // }
        // public static void removeFrom_Wid(int a) {
        //         int flag = 0;
        //         for (int i=0; i<Server.r_count; i++) {
        //                 if (Server.w_client_id == a) {
        //                         flag = 1;
        //                 }
        //                 if (flag == 1) {
        //                         Server.w_client_id[i] = Server.w_client_id[i+1];
        //                 }
        //         }
        //         if (flag == 0) {
        //                 Server.w_client_id[Server.r_count] = 0;
        //         }
        // }

        public static DatagramSocket connectToPort(int port) {
                try {
                        DatagramSocket datagramSocket = new DatagramSocket(port);
                        System.out.println("Server Started");
                        return datagramSocket;
                } catch (SocketException e){
                        System.out.println("trying next port");
                        connectToPort(port + 1);
                } catch (Exception e) {
                        e.printStackTrace();
                }
                return null;
        }

        public static void main(String args[]) {
                w_count = 0;
                r_count = 0;
                // jobs = new int[20];
                w_client_id = new int[20];
                r_client_id = new int[20];
                w_client_port = new int[20];
                r_client_port = new int[20];
                ping_count = new int[20];
                ping_count_worker = new int[20];
                
                free = new Boolean[20];
                ack_job = new Boolean[20];
                job_finish = new Boolean[20];
                job_assigned = new int[20];
                job_count = new int[20];
                for (int i=0;i<20;i++) {
                        free[i] = false;
                        ack_job[i] = false;
                        // job_assigned[i] = false;
                        job_finish[i] = false;
                }

                int portNum = Integer.parseInt(args[0]);
                byte[] buf = new byte[100];
                try {
                        InetAddress address = InetAddress.getLocalHost();
                        DatagramSocket datagramSocket = connectToPort(portNum);

                        // Ping
                        // if (w_count > 0) {
                                final int[] temp_port = Server.w_client_port;
                                final int[] temp_id = Server.w_client_id;
                                final String temp_inner = "154400000000000000";
                                Timer ping = new Timer();
                                ping.scheduleAtFixedRate(new TimerTask() {
                                @Override
                                public void run() {
                                try {
                                        if (w_count > 0) {
                                                for (int m = 0; m < Server.w_count; m++) {
                                                        int tport = Server.w_client_port[m];
                                                        int tid = Server.w_client_id[m];
                                                        char[] com = (Integer.toString(tid)).toCharArray();
                                                        System.out.println("ping worker");
                                                        // System.out.println(Server.w_count + " : " + tid);
                                                        // for (int h=0;h<Server.w_count;h++) {
                                                        //         System.out.println(Server.w_client_port[h]);
                                                        //         System.out.println(Server.w_client_id[h]);

                                                        // }
                                                        char[] arr = temp_inner.toCharArray();
                                                        arr[5] = com[0];
                                                        arr[6] = com[1];
                                                        arr[7] = '0';
                                                        String temp1 = new String(arr);
                                                        byte[] buff = temp1.getBytes();
                                                        DatagramPacket pkt1 = new DatagramPacket(buff, buff.length, address.getLocalHost(), tport);
                                                        datagramSocket.send(pkt1);
                                                        try {
                                                                Server.ping_count_worker[m] = Server.ping_count_worker[m] + 1;
                                                        } 
                                                        catch (Exception e) {
                                                                e.printStackTrace();
                                                        }
                                                        // Server.ping_count_worker[m] = Server.ping_count_worker[m] + 1;

                                                        System.out.println(Server.w_count);
                                                        for (int h=0;h<Server.w_count;h++) {
                                                                System.out.print(Server.w_client_port[h] + " ");
                                                        }
                                                        System.out.println(" ");
                                                        for (int h=0;h<Server.w_count;h++) {
                                                                System.out.print(Server.w_client_id[h] + " ");
                                                        }
                                                        if (Server.ping_count_worker[m] > 3) {
                                                                 System.out.println("******** Worker Client Timed out ********");   
                                                                System.out.print("Having ID: ");   
                                                                System.out.print(tid);
                                                                System.out.print(" On port: ");
                                                                System.out.println(tport);
                                                                // for (int h=0;h<Server.w_count;h++) {
                                                                //         System.out.println(Server.w_client_port[h]);
                                                                //         System.out.println(Server.w_client_id[h]);

                                                                // }
                                                                // while (true) {
                                                                        try{
                                                                                Server.w_count = Server.w_count - 1;
                                                                                Server.w_client_port = removeFromArray(Server.w_client_port, tport);
                                                                                Server.w_client_id = removeFromArray(Server.w_client_id, tid);
                                                                                Server.ping_count_worker[m] = 0;
                                                                                Server.free[m] = false;
                                                                                Server.job_assigned[m] = 0;
                                                                                // break;
                                                                        } catch (Exception e) {
                                                                                e.printStackTrace();
                                                                        }
                                                                // }

                                                                // for (int h=0;h<Server.w_count;h++) {
                                                                //         System.out.println(Server.w_client_port[h]);
                                                                //         System.out.println(Server.w_client_id[h]);

                                                                // }
                                                        }
                                                }
                                        }
                                        else {
                                                System.out.println("No worker client present to ping");
                                        }
                                }
                                catch(Exception e) {e.printStackTrace();}
                                }
                                }, 5000, 5000);
                                // Request client Ping
                                // final int[] temp1_port = Server.r_client_port;
                                // final int[] temp1_id = Server.r_client_id;
                                // final String temp1_inner = "154400000000000000";
                                // Timer ping1 = new Timer();
                                // ping1.scheduleAtFixedRate(new TimerTask() {
                                // @Override
                                // public void run() {
                                // try {
                                //         if (r_count > 0) {
                                //                 for (int m = 0; m < Server.r_count; m++) {
                                //                         int tport = Server.r_client_port[m];
                                //                         int tid = Server.r_client_id[m];
                                //                         char[] com = (Integer.toString(tid)).toCharArray();
                                //                         System.out.println("Ping Request Client");
                                //                         char[] arr = temp1_inner.toCharArray();
                                //                         arr[5] = com[0];
                                //                         arr[6] = com[1];
                                //                         arr[7] = '0';
                                //                         String temp1 = new String(arr);
                                //                         byte[] buff = temp1.getBytes();
                                //                         DatagramPacket pkt1 = new DatagramPacket(buff, buff.length, address.getLocalHost(), tport);
                                //                         datagramSocket.send(pkt1);
                                //                         Server.ping_count[m] = Server.ping_count[m] + 1;
                                //                         if (Server.ping_count[m] >= 3) {
                                //                                 System.out.println("******** Request Client Timed out ********");   
                                //                                 System.out.print("Having ID: ");   
                                //                                 System.out.print(tid);
                                //                                 System.out.print(" On port: ");
                                //                                 System.out.println(tport);
                                //                                 Server.r_count = Server.r_count - 1;
                                //                                 Server.r_client_port = removeFromArray(Server.r_client_port, tport);
                                //                                 Server.r_client_id = removeFromArray(Server.r_client_id, tid);
                                //                                 //********************************************************
                                //                                 arr[7] = '5';
                                //                                 temp1 = new String(arr);
                                //                                 buff = temp1.getBytes();
                                //                                 DatagramPacket pkt81 = new DatagramPacket(buff, buff.length, address.getLocalHost(), 13300);
                                //                                 datagramSocket.send(pkt81);
                                //                                 //********************************************************
                                //                         } 
                                //                 }
                                //         }
                                //         else {
                                //                 System.out.println("No request client present to ping");
                                //         }
                                // }
                                // catch(Exception e) {e.printStackTrace();}
                                // }
                                // }, 5000, 5000);
                        // Ping

                        while(true) {
                                System.out.println("listening");
                                buf = new byte[1000];
                                DatagramPacket pkt = new DatagramPacket(buf, buf.length);
                                datagramSocket.receive(pkt);
                                ServerThread t1 = new ServerThread(datagramSocket, new String(buf), address, pkt);
                                //new Thread(t1).start();
                                t1.start();
                        }
                } catch (Exception e) {
                        e.printStackTrace();
                }
        }

}

