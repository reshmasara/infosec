import java.io.*;
import java.net.*;
import java.security.*;
import java.math.BigInteger;

public class ELGamalAlice 
{
	//impl
	private static BigInteger computeY(BigInteger p, BigInteger g, BigInteger d)
	{
		//returns a BigInteger whose value is (this ^ exponent mod p ).
		BigInteger y = g.modPow(d, p);
		return y;
	}
	//impl
	private static BigInteger computeK(BigInteger p)
	{
		SecureRandom rand = new SecureRandom();
		int numBits = 1024;
		//creating new BigInteger K
		BigInteger k = new BigInteger(numBits, rand);
		//subtracting one from p
		BigInteger pSubOne = p.subtract(BigInteger.ONE); 
		
		//gcd check
		while(!k.gcd(pSubOne).equals(BigInteger.ONE))
		{
			//checking where k and p - 1 are relatively prime
			k = new BigInteger(numBits, rand); 
		}
		
		return k;
	}
	//impl
	private static BigInteger computeA(BigInteger p, BigInteger g, BigInteger k)
	{
		//returns a BigInteger whose value is (this ^ exponent mod p ).
		BigInteger a = g.modPow(k, p);
		return a;
	}
	//impl
	private static BigInteger computeB(	String message, BigInteger d, BigInteger a, BigInteger k, BigInteger p)
	{	
		//creating new BigInteger objects
		//using ElGamal encryption logic
		BigInteger msg = new BigInteger(message.getBytes()); 
		BigInteger pSubOne = p.subtract(BigInteger.ONE); 
		
		BigInteger p1 = pSubOne;
		BigInteger a0 = BigInteger.ZERO;
		BigInteger a1 = BigInteger.ONE;
		BigInteger a2 = k;
		BigInteger b, b2, b3;
		//while a2 != 0
		while(!a2.equals(BigInteger.ZERO))
		{
			b = p1.divide(a2);
			b2 = p1.subtract(a2.multiply(b));
			p1 = a2;
			a2 = b2;
			b3 = a0.subtract(a1.multiply(b));
			a0 = a1;
			a1 = b3;
		}
		
		BigInteger b4 = a0.multiply(msg.subtract(d.multiply(a))).mod(pSubOne);
		
		return b4;
	}

	public static void main(String[] args) throws Exception 
	{
		String message = "The quick brown fox jumps over the lazy dog.";
		
		String host = "127.0.0.1";//localhost
		int port = 7999;
		Socket s = new Socket(host, port);
		ObjectOutputStream os = new ObjectOutputStream(s.getOutputStream());

		// You should consult BigInteger class in Java API documentation to find out what it is.
		BigInteger y, g, p; // public key
		BigInteger d; // private key

		int numBits = 1024; // key bit length
		SecureRandom mSecureRandom = new SecureRandom(); // a cryptographically strong pseudo-random number

		// Create a BigInterger with numBits bit length that is highly likely to be prime.
		// (The '16' determines the probability that p is prime. Refer to BigInteger documentation.)
		p = new BigInteger(numBits, 16, mSecureRandom);
		
		// Create a randomly generated BigInteger of length numBits-1
		g = new BigInteger(numBits-1, mSecureRandom);
		d = new BigInteger(numBits-1, mSecureRandom);

		y = computeY(p, g, d);

		// At this point, you have both the public key and the private key. Now compute the signature.

		BigInteger k = computeK(p);
		BigInteger a = computeA(p, g, k);
		BigInteger b = computeB(message, d, a, k, p);

		// send public key
		os.writeObject(y);
		os.writeObject(g);
		os.writeObject(p);

		// send message
		os.writeObject(message);
		
		// send signature
		os.writeObject(a);
		os.writeObject(b);
		
		s.close();
	}
}
