import java.util.*;
import java.io.*;
import java.net.*;
import java.security.*;
import java.math.*;

public class p1 {
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
        char[] original = s.toCharArray();
        char[] pass = new char[5];
        String result = new String("");
        int count = 0;
        int flag = 0;
        int a1 = getStringIndex(original[0]);
        int a2 = getStringIndex(original[1]);
        int a3 = getStringIndex(original[2]);
        int a4 = getStringIndex(original[3]);
        int a5 = getStringIndex(original[4]);
        for (int i = a1; i<62; i++) {
                pass[0] = alph[i];
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
                                count = count + 1;
                                System.out.println("string is: " + new String(pass));
                                if (count == 10000) {
                                    result = new String(pass);
                                    System.out.println("string range is: " + result);
                                    flag = 1;
                                    break;
                                }
                            } 
                            if (flag == 1) {break;}                    
                        }
                        if (flag == 1) {break;}                     
                    }                     
                    if (flag == 1) {break;}
                }                              
                if (flag == 1) {break;}        
        }
        return result;
        }
        public static void main(String args[]) {
        System.out.println(getRange("aV191"));
                // String a = getRange(getRange(getRange(getRange(getRange("aacLr")))));
        }
}

