import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import javax.crypto.Cipher;

public class RSABob 
{

	public static void main(String[] args) throws Exception 
	{
	    int port = 7999;
		//creating socket object
		ServerSocket s = new ServerSocket(port);
		Socket client = s.accept();
		//creating Input and Output stream objects for Alice and Bob
		ObjectInputStream AlicePublicKey = new ObjectInputStream(client.getInputStream());
		ObjectOutputStream PublicKey = new ObjectOutputStream(client.getOutputStream());
		
		// Generate Bob's public key 
		KeyPairGenerator genKeyPair = KeyPairGenerator.getInstance("RSA");
	    genKeyPair.initialize(1024, new SecureRandom()); 
	    KeyPair kp = genKeyPair.genKeyPair();
	    RSAPublicKey BobPublicKey = (RSAPublicKey) kp.getPublic();
	    RSAPrivateKey AlicePrivateKey = (RSAPrivateKey)kp.getPrivate();
	    
	    // Sending Bob's public key to Alice
	    PublicKey.writeObject(BobPublicKey);
	    
	    // obtaining Alice's public key
	    RSAPublicKey keyAlice = (RSAPublicKey) AlicePublicKey.readObject();
	    Cipher c1 = Cipher.getInstance("RSA");
	    
	    int c = AlicePublicKey.readInt();
			//user choice
		    if(c==1)
			{
				  //reading Alice's public key and using it to decrypt message encrypted with her private key
				  byte[] b1 = (byte[]) AlicePublicKey.readObject();
				  c1.init(Cipher.DECRYPT_MODE, AlicePrivateKey);
   			      byte[] pt1 = c1.doFinal(b1);
				  System.out.println("\nThe plaintext is: " + new String(pt1));
			}	    
			
			if(c==2)
			{
			 	  //encrypting message with Alice's private key - which can only be decrypted with her public key, proving Alice sent the message
				  byte[] b2 = (byte[]) AlicePublicKey.readObject();
				  c1.init(Cipher.DECRYPT_MODE, keyAlice);
   			      byte[] pt2 = c1.doFinal(b2);
				  System.out.println("\nThe plaintext is: " + new String(pt2));
			}	 		 
	}
		
}
 


