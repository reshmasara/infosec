
import java.io.*;
import java.net.*;
import java.security.*;
import javax.crypto.*;

public class CipherClient {
	public static void main(String[] args) throws Exception 
	{
		//plaintext and socket object
		String message = "The quick brown fox jumps over the lazy dog.";
		String host = "127.0.0.1"; //localhost
		int port = 7999;
		Socket s = new Socket(host, port);
		
		KeyGenerator k1 = KeyGenerator.getInstance("DES");
		SecureRandom rand = new SecureRandom();
		k1.init(rand);
		//generating a DES key
		Key key = k1.generateKey();
		//System.out.println(key);
		//creating DES output file Reshma_DesKey.txt
		File f1 = new File("Reshma_DesKey.txt");
		ObjectOutputStream out1 = new ObjectOutputStream(new FileOutputStream(f1));
		//store DES key in Reshma_DesKey.txt
		out1.writeObject(key);		
		 
		ObjectOutputStream outSocket = new ObjectOutputStream(s.getOutputStream());
	    outSocket.writeObject(key);
	    outSocket.flush();
		//creating cipher object
	    Cipher c1 = Cipher.getInstance("DES");
	    CipherOutputStream c2 = new CipherOutputStream(s.getOutputStream(), c1);
		//encrypt cipher with 'key' stored in file
		
		c1.init(Cipher.ENCRYPT_MODE, key);
	    byte b[] = message.getBytes();
	    System.out.println("The plaintext is:" + message);
	    System.out.println("The ciphertext is:" + b);
	    c2.write(b);
	    c2.close(); 
	    s.close();
	    out1.close();
		
	}

}
