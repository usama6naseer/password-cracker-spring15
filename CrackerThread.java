import java.util.*;
import java.io.*;
import java.net.*;
import java.nio.*;
import java.security.*;
import java.math.*;

public class CrackerThread extends Thread {
    public static DatagramSocket dg;
    public static String start;
    public static String end;
    public static String hash;
    public static String msg;
    // public CrackerThread(DatagramSocket d, String s1) {
    //     dg = d;
    //     start = s1.substring(8,13);
    //     end = s1.substring(13,18);
    //     hash = s1.substring(18);
    //     msg = s1;
    // }
    public CrackerThread(String s) {
        dg = WorkerClientThread.dg;
        msg = s;
        // start = s.substring(8,13);
        // end = s.substring(13,18);
        // hash = s.substring(18);
    }
    public static int getIndex(char c) {
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
    public static Boolean isArrayEqual(char[] a, char[]b) {
                for (int i=0; i<5; i++) {
                        if (a[i] != b[i]) {
                                return false;
                        }
                }
                return true;
    }
    public static String getMDA(String s) {
        try {
            // String s="hello";
            MessageDigest m=MessageDigest.getInstance("MD5");
            m.update(s.getBytes(),0,s.length());
            s = new BigInteger(1,m.digest()).toString(16);
            // System.out.println("MD5: "+new BigInteger(1,m.digest()).toString(16));
            // // System.out.println("finding");
            // MessageDigest md = MessageDigest.getInstance("MD5");
            // md.update(original.getBytes());
            // byte[] digest = md.digest();
            // StringBuffer sb = new StringBuffer();
            // for (byte b : digest) {
            //     sb.append(String.format("%02x", b & 0xff));
            // }
            // System.out.println("original:" + original);
            // System.out.println("digested(hex):" + sb.toString());
            return s;
        } catch (Exception e) {e.printStackTrace();}
        return null;
    }

    public void run() {
        try {

            start = msg.substring(8,13);
            end = msg.substring(13,18);
            hash = msg.substring(18);
            hash = hash.trim();

            char[] alph = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
            char[] pass = new char[5];
            // char[] original = "ME15z".toCharArray();
            String passFound = new String("");
            int flag = 0;
            int flag1 = 0;
            char[] s1 = start.toCharArray();
            char[] s2 = end.toCharArray();
            int a1 = getIndex(s1[0]);
            int a2 = getIndex(s1[1]);
            int a3 = getIndex(s1[2]);
            int a4 = getIndex(s1[3]);
            int a5 = getIndex(s1[4]);
            for (int i = a1; i<62; i++) {
                pass[0] = alph[i];
                // if (pass[0] == s4) {
                //     break;
                // }
                if (i == a1 + 1) {
                    a2=0;
                    a3=0;
                    a4=0;
                    a5=0;
                }
                for (int j = a2; j<62; j++) {
                    pass[1] = alph[j];
                    if (j == a2 + 1) {
                        a3=0;
                        a4=0;
                        a5=0;
                    }
                    for (int k = a3; k<62; k++) {
                        pass[2] = alph[k];
                        if (k == a3 + 1) {
                            a4=0;
                            a5=0;
                        }
                        for (int m = a4; m<62; m++) {
                            pass[3] = alph[m];
                            if (m == a4 + 1) {
                                a5=0;
                            }
                            for (int n = a5; n<62; n++) {
                                pass[4] = alph[n];
                                // if ("hello".equals(new String(pass))) {
                                String mda = getMDA(new String(pass));
                                // System.out.println(flag);

                                if (mda.equals(hash)) {
                               System.out.println("*********************************************************");
                                System.out.println(hash);
                                System.out.println(mda);
                                System.out.println("*********************************************************");


                                    System.out.println("*********found********** " + (new String(pass)));
                                    passFound = new String(pass);
                                    flag = 1;
                                    break;
                                }
                                if (isArrayEqual(pass, s2)) {
                                    // flag = 1;
                                    flag1 = 1;
                                    break;
                                }
                            } 
                            if (flag == 1 || flag1 == 1) {break;}                    
                        }
                        if (flag == 1 || flag1 == 1) {break;}                     
                    }                     
                    if (flag == 1 || flag1 == 1) {break;}
                }                              
                if (flag == 1 || flag1 == 1) {break;}        
            }
                                // for (int h=0;h<500000;h++) {
                                //     String b = getMDA("hash");
                                // }
               
            if (flag == 1) {
                System.out.println("Password found");
                char[] temp = msg.toCharArray();
                temp[7] = '5';
                String temp1 = new String(temp);
                temp1 = temp1.substring(0,8);
                // temp1 = temp1 + passFound + msg.substring(18);
                temp1 = temp1 + passFound;
                temp1 = temp1.trim();
                System.out.println("Password found msg: " + temp1);
                
                byte[] buf = (new String(temp1)).getBytes();
                DatagramPacket pkt = new DatagramPacket(buf, buf.length, InetAddress.getLocalHost(), 13300);
                dg.send(pkt); 
            }
            else {
                System.out.println("Password not found");
                char[] temp = msg.toCharArray();
                temp[7] = '4';
                String temp1 = new String(temp);
                temp1 = temp1.trim();
                System.out.println("Password NOT found msg: " + temp1);
                byte[] buf = temp1.getBytes();
                DatagramPacket pkt = new DatagramPacket(buf, buf.length, InetAddress.getLocalHost(), 13300);
                dg.send(pkt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}




