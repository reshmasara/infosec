import java.io.*;
import java.net.*;
import java.security.*;
import java.math.BigInteger;

public class ELGamalBob 
{
	//impl
	private static boolean verifySignature(	BigInteger y, BigInteger g, BigInteger p, BigInteger a, BigInteger b, String message)
	{
		//creating result object for comparison using message and public and private keys
		BigInteger res1 = (y.modPow(a, p).multiply(a.modPow(b, p))).mod(p);
		BigInteger r = new BigInteger(message.getBytes());
		//returns a BigInteger whose value is (this ^ exponent mod p ).
		BigInteger res2 = g.modPow(r, p);
		//compares both BigIntegers
		return res1.equals(res2);
	}

	public static void main(String[] args) throws Exception 
	{
		int port = 7999;
		ServerSocket s = new ServerSocket(port);
		Socket client = s.accept();
		ObjectInputStream is = new ObjectInputStream(client.getInputStream());

		// read public key
		BigInteger y = (BigInteger)is.readObject();
		BigInteger g = (BigInteger)is.readObject();
		BigInteger p = (BigInteger)is.readObject();

		// read message
		String message = (String)is.readObject();

		// read signature
		BigInteger a = (BigInteger)is.readObject();
		BigInteger b = (BigInteger)is.readObject();
		//boolean flag
		boolean flag = verifySignature(y, g, p, a, b, message);

		System.out.println(message);

		if (flag == true)
			System.out.println("Signature verified.");
		else
			System.out.println("Signature verification failed.");

		s.close();
	}
}
