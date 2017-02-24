import java.util.*;
import java.io.*;
import java.net.*;
import java.math.*;

        // public static Boolean[] free;
        // public static Boolean[] ack_job;
        // public static int[] job_assigned;
        // public static Boolean[] job_finish;
        // public static int[] job_count;



public class ServerThread extends Thread {
    public String msg;
    public InetAddress address;
    public int port;
    public Boolean worker;
    final public DatagramSocket socket; 
    public DatagramPacket pkt;
    
    public ServerThread(DatagramSocket datagramSocket, String s, InetAddress addr, DatagramPacket p) {
      	msg = s;
       	address = addr;
       	socket = datagramSocket;
        pkt = p;
    }

    public static Boolean arrayContains(int[] arr, int i) {
        for (int h : arr) {
            if (h == i) {
                return true;
            }
        }
        return false;
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
    public static int getStringIndex(char c) {
        char[] alph = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
        int count = 0;
        for (char i : alph) {
            if (i == c) {
                return count;
            }
            count++;
        }
        return -1;
    }
    
    public static String getRange(String s) {
        char[] alph = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
        if (s.equals("aaaaa")) {
            return "aa999";
        }
        char a = s.charAt(1);
        int n = getStringIndex(a);
        if (a == '9') {
            a = s.charAt(0);
            n = getStringIndex(a);
            if (a == '9') {
                // a = s.charAt(0);
                // n = getStringIndex(a);
                // if (a == '9') {
                    System.out.println("STRING FINISHED");
                // }
                // else {
                    // char b = alph[n+1];
                    // char[] c = s.toCharArray();
                    // c[0] = b;
                    // c[1] = 'a';
                    // return (new String(c));            
                // }    
            }
            else {
                char b = alph[n+1];
                char[] c = s.toCharArray();
                c[0] = b;
                c[1] = 'a';
                return (new String(c));            
            }    
        }
        else {
            char b = alph[n+1];
            char[] c = s.toCharArray();
            c[1] = b;
            return (new String(c));            
        }
        return null;
    }
    
    public static int countOfJob(int i) {
        int count = 0;
        for (int j : Server.job_assigned) {
            if (j == i) {
                count++;
            }
        }
        return count;
    }
    public static int getID(String s) {
        return Integer.parseInt(s.substring(5,7));
    }
    public static int getReqIndex(int n) {
        int count = 0;
        for (int i : Server.r_client_id) {
            if (i == n) {
                return count;
            }
            count++;
        }
        return -1;
    }
    public static int getWorkerIDIndex(int n) {
        int count = 0;
        for (int i : Server.w_client_id) {
            if (i == n) {
                return count;
            }
            count++;
        }
        return -1;
    }
    public static int getIndex(int n) {
        int count = 0;
        for (int i : Server.w_client_port) {
            if (i == n) {
                return count;
            }
            count++;
        }
        return -1;
    }
    public static int getFreeWorker() {
        for (int i=0; i<Server.w_count; i++) {
            if (Server.free[i] == true) {
                return i;
            }
        }
        return -1;
    } 
    public static int jobDistribution() {
        if (Server.r_count == 0) {return -1;}
        return (int)Math.ceil((Server.w_count / Server.r_count));
    }
    public static void sendWorkToWorkers(String hash, DatagramSocket soc, String fullmsg) {
        String start = "aaaaa";
        String end;
        int flag = 0;
        int count = 0;
        if (Server.w_count == 0) {
            System.out.println("No worker Client found");
        }
        else if (Server.r_count > 0) {
            while(flag == 0 && Server.job_finish[getReqIndex(getID(fullmsg))] == false) {
                if (Server.job_count[getReqIndex(getID(fullmsg))] <= jobDistribution()) {
                    int index = getFreeWorker();
                    if (index != -1) {
                        int wport = Server.w_client_port[index];
                        end = getRange(start);
                        System.out.println("range is: " + end);
                        System.out.println(Server.w_count + "  " + Server.r_count);
                        // System.out.println(jobDistribution());
                        // for (int v : Server.job_count) {
                        //     System.out.println(v);
                        // }
                        if (end == null) {
                            flag = 1;
                            break;
                        }
                        Server.job_count[getReqIndex(getID(fullmsg))] = Server.job_count[getReqIndex(getID(fullmsg))] + 1;
                        Server.job_assigned[getIndex(wport)] = getID(fullmsg);
                        System.out.println("request client id is ***** " + getID(fullmsg));
                        // getReqIndex(getID(fullmsg));
                        // Server.jobs[index] = Integer.toString(fullmsg.substring(5,7));
                        char[] yes = convertChar(Integer.toString(Server.w_client_id[index]));
                        String temp = "15440" + (new String(yes)) + "2" + start + end + hash;
                        start = end;
                        temp = temp.trim();
                        byte[] tempbuf = temp.getBytes();
                        try {
                            // command 2
                            DatagramPacket dg = new DatagramPacket(tempbuf, tempbuf.length, InetAddress.getLocalHost(), wport);
                            soc.send(dg);
                            Server.free[index] = false;
                        } catch(Exception e) {e.printStackTrace();}
                    }
                }
            }
        }
    }

    public void run() {
        int id = 0;
        String magic;
        String client_id;
        String command;
        String range_start;
        String range_end;
        String hash;

        // System.out.println("yes");
        msg = msg.trim();
        System.out.println(msg);
        port = pkt.getPort();
        System.out.println("port is:" + port);
        // Server.r_client_port[Server.r_count] = port;
        // Server.r_count++;
        magic = msg.substring(0,5);
        if (Integer.parseInt(magic) == 15440) {
            // REQUEST TO JOIN
            client_id = msg.substring(5,7);
            System.out.println("client_id is: " + client_id);
            if ((Integer.parseInt(client_id) == 0)) {
                System.out.println("New Client joined");
                Random rand = new Random();
                id = rand.nextInt(89) + 10;
                if (arrayContains(Server.w_client_port, id)) {
                    id = rand.nextInt(89) + 10;
                }
                if (arrayContains(Server.r_client_port, id)) {
                    id = rand.nextInt(89) + 10;
                }
                System.out.println("id is: " + id);
                client_id = Integer.toString(id);
            }
            command = msg.substring(7,8);
            System.out.println("command is: " + command);
            if (Integer.parseInt(command) == 1) {
                System.out.println("New client is worker client");
                Server.w_client_port[Server.w_count] = port;
                Server.w_client_id[Server.w_count] = id;
                Server.free[Server.w_count] = true;
                Server.w_count++;
                range_start = new String("");
                range_end = new String("");
                hash = new String("");
                String temp = magic + client_id + command + range_start + range_end + hash;
                temp = temp.trim();
                byte[] tempbuf = temp.getBytes();
                try {
                    DatagramPacket dg = new DatagramPacket(tempbuf, tempbuf.length, InetAddress.getLocalHost(), port);
                    socket.send(dg);
                } catch(Exception e) {}
            }
            else if(Integer.parseInt(command) == 8) {
                System.out.println("Request Client Joined");
                Server.r_client_port[Server.r_count] = port;
                Server.r_client_id[Server.r_count] = id;
                Server.r_count++;
                hash = msg.substring(8);
                hash = hash.trim();
                range_start = new String("");
                range_end = new String("");

                String temp = magic + client_id + "3" + range_start + range_end + hash;
                temp = temp.trim();
                byte[] tempbuf = temp.getBytes();
                try {
                    DatagramPacket dg = new DatagramPacket(tempbuf, tempbuf.length, InetAddress.getLocalHost(), port);
                    socket.send(dg);
                } catch(Exception e) {}
                
                System.out.println("Hash to be cracked: " + hash);
                sendWorkToWorkers(hash, socket, temp);
            }
            else if (Integer.parseInt(command) == 0) {
                System.out.println("Worker client ping received having id: " + client_id);
                int chel = getWorkerIDIndex(Integer.parseInt(client_id));
                while (true) {
                    try {
                        if (Server.ping_count_worker[chel] > 0) {
                            int hh = Server.ping_count_worker[chel];
                            Server.ping_count_worker[chel] = Server.ping_count_worker[chel] - 1;
                            if (Server.ping_count_worker[chel] == hh-1) {

                            }
                            else {
                                while (true) {
                                    System.out.print("*");
                                }
                            }
                            break;
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            else if (Integer.parseInt(command) == 3) {
                int index = getIndex(port);
                Server.ack_job[index] = true;
            }
            else if (Integer.parseInt(command) == 4) {
                int index = getIndex(port);
                Server.free[index] = true;
                int rid = Server.job_assigned[getIndex(port)];
                Server.job_count[getReqIndex(rid)] = Server.job_count[getReqIndex(rid)] - 1;
            }
            // else if (command.equals("x")) {
            //     while(true) {
            //         System.out.print("x");
            //     }
            // }
            else if (Integer.parseInt(command) == 5) {
                Server.r_count--;
                int index = getIndex(port);
                // int rid = getID(msg);
                int rid = Server.job_assigned[getIndex(port)];
                Server.job_finish[getReqIndex(rid)] = true;
                Server.free[index] = true;
                Server.job_count[getReqIndex(rid)] = Server.job_count[getReqIndex(rid)] - 1;
                // Server.jobfinish = true;
                System.out.println("******PASSWORD FOUND******");
                while (true) {
                    // System.out.println("************");
                    int count=0;
                    for (int i=0; i<Server.w_count; i++) {
                        if (Server.job_assigned[i] == rid) {
                            if (Server.free[i] == true) {
                                count++;
                            }
                        } 
                    }
                    // System.out.println(rid);
                    // System.out.println(count);
                    // System.out.println(countOfJob(rid));
                    // for (int j : Server.job_assigned) {
                    //     System.out.print(j + " ");
                    // }
                    // System.out.println("");
                    if (count == countOfJob(rid)) {
                        Server.job_finish[getReqIndex(rid)] = false;
                        break;
                    }
                }

                for (int m = 0; m < Server.w_count; m++) {
                        int tport = Server.w_client_port[m];
                        int tid = Server.w_client_id[m];
                        char[] com = (Integer.toString(tid)).toCharArray();
                        System.out.println("canceling all other worker clients");
                        char[] arr = msg.toCharArray();
                        arr[5] = com[0];
                        arr[6] = com[1];
                        arr[7] = '7';
                        String temp1 = new String(arr);
                        byte[] buff = temp1.getBytes();
                        try {
                            DatagramPacket pkt1 = new DatagramPacket(buff, buff.length, address.getLocalHost(), tport);
                            socket.send(pkt1);
                        }
                        catch (Exception e) {e.printStackTrace();}
                }
                // to request client
                try {
                    char[] endbuf = msg.toCharArray();
                    endbuf[7] = '9';
                    String endstr = new String(endbuf);
                    byte[] endbuff = endstr.getBytes();
                    DatagramPacket pkt1 = new DatagramPacket(endbuff, endbuff.length, address.getLocalHost(), Server.r_client_port[getReqIndex(rid)]);
                    socket.send(pkt1);
                }
                catch (Exception e) {e.printStackTrace();}

            }
            else if (Integer.parseInt(command) == 6) {
                System.out.println("work not finished");
            }

            // final int[] temp_port = Server.w_client_port;
            // final int[] temp_id = Server.w_client_id;
            // final String temp_inner = msg;
            // // final char[] com = (Integer.toString(id)).toCharArray();
            // // Ping to worker clients
            // Timer ping = new Timer();
            // ping.scheduleAtFixedRate(new TimerTask() {
            // @Override
            // public void run() {
            //     try {
            //         for (int m = 0; m < Server.w_count; m++) {
            //             int tport = temp_port[m];
            //             int tid = temp_id[m];
            //             char[] com = (Integer.toString(tid)).toCharArray();
            //             System.out.println("ping worker");
            //             char[] arr = temp_inner.toCharArray();
            //             arr[5] = com[0];
            //             arr[6] = com[1];
            //             arr[7] = '0';
            //             String temp1 = new String(arr);
            //             byte[] buff = temp1.getBytes();
            //             DatagramPacket pkt1 = new DatagramPacket(buff, buff.length, address.getLocalHost(), tport);
            //             socket.send(pkt1);
            //         }
            //     }
            //     catch(Exception e) {e.printStackTrace();}
            // }
            // }, 5000, 5000);
            // // Ping
        }
    }
}

