import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.security.*;
import java.util.Scanner;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import javax.crypto.Cipher;

public class RSAAlice 
{	
	public static void main(String[] args) throws Exception 
	{
        String host = "127.0.0.1";//localhost
		int port = 7999;
		//creating socket
		Socket s = new Socket(host, port);
		Scanner scan = new Scanner(System.in);
		//menu
		System.out.println("RSA Public Key System Demonstration");
		System.out.println("To demonstrate confidentiality           : Press 1");
	    System.out.println("To demonstrate integrity and authentication: Press 2");
		System.out.println("To Exit                                  : Press 0");
	    
		ObjectOutputStream BobPublicKey = new ObjectOutputStream(s.getOutputStream());
		ObjectInputStream AlicePublicKey = new ObjectInputStream(s.getInputStream());
		
		/* Generate Alice's private key and public key*/
		KeyPairGenerator genKeyPair = KeyPairGenerator.getInstance("RSA");
		genKeyPair.initialize(1024 ,new SecureRandom());
	    KeyPair keyPair = genKeyPair.genKeyPair();
	    RSAPublicKey PublicKey_Alice = (RSAPublicKey) keyPair.getPublic();
	    RSAPrivateKey PrivateKey_Alice = (RSAPrivateKey)keyPair.getPrivate();
	    
	    /* Send Alice's just generated public key to Bob */
	    BobPublicKey.writeObject(PublicKey_Alice);
	    
	    /* Obtain Bob's Public key */
	    RSAPublicKey keyBob = (RSAPublicKey) AlicePublicKey.readObject();
	    Cipher c1 = Cipher.getInstance("RSA");
	    
	    int c= scan.nextInt();
		//user choice
		if(c==1)
		{
				  System.out.println("\nConfidentiality is achieved by encrypting the plaintext with by receiver's public key (Bob). \nIn that way, in the receiver (Bob) can decrypt it. ");
				  Scanner i1 = new Scanner(System.in);
				  //user's plaintext
				  System.out.println("\nEnter plaintext: ");
				  String s1= i1.nextLine();
				  //encrypting with Bob's public key
				  c1.init(Cipher.ENCRYPT_MODE, keyBob);
   			      byte[] cText1 = c1.doFinal(s1.getBytes());
				  //printing cipher text
				  System.out.println("\nThe ciphertext is: " + cText1);
				  BobPublicKey.writeInt(1);
				  //writing cipher text to object
				  BobPublicKey.writeObject(cText1);
				  BobPublicKey.flush();
				  BobPublicKey.close();
		}	
		else if(c==2)
		{
				  System.out.println("\nIntegrity/Authentication is achieved by encrypting the message using the sender's private key (Alice). \nThis means that only Alice could have sent that message as she is the only one that has it and the message can be decrypted by the sender's (Alice) public key.");
				  Scanner i2 = new Scanner(System.in);
				  //user's plaintext
				  System.out.println("\nEnter plaintext: ");
				  String s2= i2.nextLine();
				  //encrypting with Alice's Private Key
				  c1.init(Cipher.ENCRYPT_MODE, PrivateKey_Alice);
   			      byte[] cText2 = c1.doFinal(s2.getBytes());
				  //printing cipher text
				  System.out.println("\nThe ciphertext is: " + cText2);
				  BobPublicKey.writeInt(2);
				  //writing cipher text to object
				  BobPublicKey.writeObject(cText2);
				  BobPublicKey.flush();
				  BobPublicKey.close();
		}		  
		else
		{
			//exit
			System.out.println("Exit.");
		}	
	    s.close();
	    scan.close();
	}	    
}

