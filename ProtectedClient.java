
import java.io.*;
import java.net.*;
import java.security.*;
import java.util.Date;

public class ProtectedClient {
	//implement this
	public void sendAuthentication(String user, String password, OutputStream outStream) throws IOException, NoSuchAlgorithmException
	{
		//creating date object
		Date date = new Date();
		
		DataOutputStream out = new DataOutputStream(outStream);
		//creating double random number objects
		double rand1 = Math.random();
		double rand2 = Math.random();
		//creating long timestamps
		long ts1 = date.getTime();
		long ts2 = date.getTime();
		//creating byte arrays for the protection class
		byte[] digVal1 = Protection.makeDigest(user, password, ts1, rand1);
		byte[] digVal2 = Protection.makeDigest(digVal1, ts2, rand2);
		
		//writing data to buffer stream
		out.writeUTF(user); 
		out.writeLong(ts1);
		out.writeLong(ts2);
		out.writeDouble(rand1);
		out.writeDouble(rand2);
		out.writeInt(digVal1.length); 
		out.write(digVal2);
		
		
		out.flush();
	}
	
	public static void main(String[] args) throws Exception
	{
		String host = "127.0.0.1";
		int port = 7999;
		String user = "George";
		String password = "abc123";
		Socket s = new Socket(host, port);
		
		ProtectedClient client = new ProtectedClient();
		client.sendAuthentication(user, password, s.getOutputStream());
		
		s.close();
	}

}
