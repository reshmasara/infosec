
import java.io.*;
import java.net.*;
import java.security.*;

public class ProtectedServer {
	//implement this
	public boolean authenticate(InputStream inStream) throws IOException, NoSuchAlgorithmException
	{
		DataInputStream in = new DataInputStream(inStream);
		//reading data from buffer stream
		String user = in.readUTF();
		String password = lookupPassword(user);
		//System.out.println(password);
		//creating date and timestamp variables for new data from buffer
		long ts1 = in.readLong();
		long ts2 = in.readLong();
		double rand1 = in.readDouble();
		double rand2 = in.readDouble();
		//reading complete message digest
		int length = in.readInt();
		byte[] receivedDigest = new byte [length];
		in.readFully(receivedDigest);
		
		boolean flag = true;
		//implementing the double strength password
		byte[] Result1 = Protection.makeDigest(user, password, ts1, rand1);
		byte[] Result2 = Protection.makeDigest(Result1, ts2, rand2);
		
		flag = MessageDigest.isEqual(receivedDigest, Result2);
		//System.out.println(flag);
		//ture or false
		return flag;
		
	}
	
	protected String lookupPassword(String user)
	{
		//returning user password
		return "abc123";
	}
	
	public static void main(String[] args) throws Exception
	{
		int port = 7999;
		ServerSocket s = new ServerSocket(port);
		Socket client = s.accept();//accept the socket of client
		
		ProtectedServer server = new ProtectedServer();
		
		if (server.authenticate(client.getInputStream()))
			System.out.println("The Client has been logged in.");
		else
			System.out.println("The Client failed to log in.");
		
		s.close();
	}
}
