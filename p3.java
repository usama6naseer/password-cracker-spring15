import java.util.*;
import java.io.*;
import java.net.*;
import java.security.*;
import java.math.*;

public class p3 {
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
        // System.out.println(s);
            char[] alph = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
            if (s.equals("aaaaa")) {
                getRange("aaa99");
            }
            char a = s.charAt(2);
            int n = getStringIndex(a);
            if (a == '9') {
                a = s.charAt(1);
                n = getStringIndex(a);
                if (a == '9') {
                    a = s.charAt(0);
                    n = getStringIndex(a);
                    if (a == '9') {
                        System.out.println("STRING FINISHED");
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
                    c[2] = 'a';
                    return (new String(c));            
                }    
            }
            else {
                char b = alph[n+1];
                char[] c = s.toCharArray();
                c[2] = b;
                return (new String(c));            
            }
            return null;
        }

        public static void main(String args[]) {
            String s = getRange("aaaaa");
            while(s != null) {
                s = getRange(s);
                System.out.println(s);
            }

    }
}