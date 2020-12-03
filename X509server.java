import java.io.*;
import java.net.*;
import java.security.*;

import javax.crypto.*;

public class X509server {

	public static void main(String[] args) throws Exception 
	{   
		//aliasname and password (Note - I did not change it from the default keystore password.)
		String aliasname="localhost";
        char[] pwd="changeit".toCharArray();
		
        int port = 7999;
		ServerSocket server = new ServerSocket(port);
		Socket s = server.accept();
		ObjectInputStream is = new ObjectInputStream(s.getInputStream());
		//initalizing and loading key store
        KeyStore k = KeyStore.getInstance("jks");
        k.load(new FileInputStream("keystore.jks"), pwd);
		//fetching private key from keystore
        PrivateKey serverPrivKey = (PrivateKey)k.getKey(aliasname, pwd);
		//creating RSA cipher object
        Cipher c = Cipher.getInstance("RSA");
        byte[] input = (byte[]) is.readObject();
		//decrypting
		c.init(Cipher.DECRYPT_MODE, serverPrivKey);
		byte[] b = c.doFinal(input);
		//printing plaintext
		System.out.println("The plaintext is: " + new String(b));
		server.close();
	}

}