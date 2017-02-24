import java.util.*;
import java.io.*;
import java.net.*;
import java.security.*;
import java.math.*;

public class p1 {
        public static void main(String args[]) {
                try {

String s="hello";
MessageDigest m=MessageDigest.getInstance("MD5");
m.update(s.getBytes(),0,s.length());
System.out.println("MD5: "+new BigInteger(1,m.digest()).toString(16));

                // String original = "hello";
                // MessageDigest md = MessageDigest.getInstance("MD5");
                // md.update(original.getBytes());
                // byte[] digest = md.digest();
                // StringBuffer sb = new StringBuffer();
                // for (byte b : digest) {
                //         sb.append(String.format("%02x", b & 0xff));
                // }

                // System.out.println("original:" + original);
                // System.out.println("digested(hex):" + sb.toString());
                // System.out.println(new String(digest));
                // // try {
                // //         byte[] bytesOfMessage = "hello".getBytes("UTF-8");
                // //         MessageDigest md = MessageDigest.getInstance("MD5");
                // //         byte[] thedigest = md.digest(bytesOfMessage);

                //         System.out.println(new String(thedigest));
                } catch (Exception e) {}


                // char[] alph = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
                // char[] pass = new char[5];
                // char[] original = "ME15z".toCharArray();
                // int flag = 0;
                // for (char i : alph) {
                //         pass[0] = i;
                //         for (char j : alph) {
                //                 pass[1] = j;
                //                 for (char k : alph) {
                //                         pass[2] = k;
                //                         for (char l : alph) {
                //                                 pass[3] = l;
                //                                 for (char m : alph) {
                //                                         pass[4] = m;
                //                                         if (isArrayEqual(pass,original)) {
                //                                                 System.out.println("found");
                //                                                 flag = 1;
                //                                                 break;
                //                                         }
                //                                 } 
                //                                 if (flag == 1) {break;}                    
                //                         }
                //                         if (flag == 1) {break;}                     
                //                 }                     
                //                 if (flag == 1) {break;}
                //         }                              
                //         if (flag == 1) {break;}        
                // }
        }
}

