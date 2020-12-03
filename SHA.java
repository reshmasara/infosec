import java.math.BigInteger;  
import java.nio.charset.StandardCharsets; 
import java.security.MessageDigest;  
import java.security.NoSuchAlgorithmException;  
import java.security.*;


public class SHA {
	public String getSHA(byte[] bytes) 
	{
		String s1="";
		try {
				//create message digest object
				MessageDigest msg = MessageDigest.getInstance("SHA");
				// Passing data to the created MessageDigest Object
				msg.update(bytes);
				//compute message digest
				byte[] b = msg.digest();
				int x;
				//creating buffer object
				StringBuffer buffer = new StringBuffer("");
				for (int i = 0; i < b.length; i++) {
					x = b[i];
					if (x < 0)
						x += 256;
					if (x < 16)
						buffer.append("0");
						//converting to Hex String
					buffer.append(Integer.toHexString(x));
				}
				s1 = buffer.toString();
			
		} 
		catch(Exception e)
		{
			//error handling
			e.printStackTrace();
		}
		return s1;
	}

}
