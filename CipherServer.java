

import java.io.*; 
import java.net.*;
import java.security.*;

import javax.crypto.*;

public class CipherServer 
{
	public static void main(String[] args) throws Exception 
	{
		//creating socket connection
		int port = 7999;
		ServerSocket server = new ServerSocket(port);
		Socket s = server.accept();
		
		ObjectInputStream in = new ObjectInputStream(s.getInputStream());
		//reading the key from file generated by client
		Key key = (Key) in.readObject(); 
		//creating cipher object
		Cipher c1 = Cipher.getInstance("DES");
		//initializing cipher object for Decryption
		c1.init(Cipher.DECRYPT_MODE, key);
		CipherInputStream cIn= new CipherInputStream(s.getInputStream(),c1);
		//reading cipher input stream
		int i = cIn.read();

		StringBuilder actualText = new StringBuilder();
		//append cipher stream to string
		while( i != -1)
	     {	
			actualText.append((char) i);
	    	i=cIn.read();
	     }
		//print plaintext
		System.out.println("The Plaintext is: "+ actualText.toString());
		in.close();
		cIn.close();
		server.close();
	}
}
