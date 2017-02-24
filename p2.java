import java.util.*;
import java.io.*;
import java.net.*;
import java.security.*;
import java.math.*;

public class p2 {
    public static void main(String args[]) {
    	try{
       	String s = "aBcdd";
       	MessageDigest m=MessageDigest.getInstance("MD5");
        m.update(s.getBytes(),0,s.length());
        s = new BigInteger(1,m.digest()).toString(16);
        System.out.println(s);
    } catch (Exception e) {}
    }
}