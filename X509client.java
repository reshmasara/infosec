
import java.io.*;
import java.net.*;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.util.*;
import javax.crypto.*;

public class X509client {

	public static void main(String[] args) throws Exception 
	{
		String host = "127.0.0.1"; //localhost
		int port = 7999;
		//creatig socket obejct
		Socket s = new Socket(host, port);
	    
		ObjectOutputStream os = new ObjectOutputStream(s.getOutputStream());
		//reading created certificate
        InputStream inStream = new FileInputStream("reshmasara.cer");
		//creating certificate factory and X509 objects
        CertificateFactory cfact = CertificateFactory.getInstance("X.509");
        X509Certificate xcert = (X509Certificate)cfact.generateCertificate(inStream);
        inStream.close();
        //printing X509 certificate
        System.out.println("X509 Certificate Details:");
        System.out.println(xcert.toString());
        Date d = xcert.getNotAfter();
        //checking if certificate is current 
		if(d.after(new Date()))
        {
        	System.out.println("This certificate is current.");
        	System.out.println("It is valid from "+ xcert.getNotBefore()+ " to "+xcert.getNotAfter()) ;
        }
        else
        {     
        	System.out.println("This certificate is expired.");
        }
		//checking if certificate is valid
        try
        {
    	   xcert.checkValidity();
    	   System.out.println("This certificate is valid.");
        } 
		catch(Exception e)
		{
			//error handling
			e.printStackTrace();
		}
		//user input
        System.out.println("Enter a string: ");
        Scanner scan = new Scanner(System.in);
        String msg= scan.nextLine();
        //fetching RSA public key from certificate
        RSAPublicKey rsaPK = (RSAPublicKey) xcert.getPublicKey();
        Cipher c1 = Cipher.getInstance("RSA");
		//encrypting message with RSA key
        c1.init(Cipher.ENCRYPT_MODE, rsaPK);
        byte[] b = c1.doFinal(msg.getBytes());
        System.out.println("The ciphertext is: " + b);
		//writing to stream
        os.writeObject(b);
		os.flush();
		os.close();
	    s.close();
	    scan.close();
	}



}
