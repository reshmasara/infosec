import java.util.Scanner;
import java.math.BigInteger;  
import java.nio.charset.StandardCharsets; 
import java.security.NoSuchAlgorithmException;  
import java.security.*;

//main class
public class MessageDigest 
{
	//driver function
	public static void main(String[] args)
	{
		
		System.out.println("Select scheme: ");
		System.out.println("For MD5: enter 1");
		System.out.println("For SHA: enter 2"); 
		System.out.println("To exit: enter 0"); 
		
		Scanner scan = new Scanner(System.in);
		//accepting user choice
		int c = scan.nextInt();

			if(c == 1)
			{
				System.out.println("Enter plaintext:");
				Scanner scan1 = new Scanner(System.in);
				//creating MD5 object
				MD5 pt1 = new MD5();
				
				String text1 = scan1.nextLine();
				//returning MD5 encyption result
				String res1 = pt1.getMD5(text1.getBytes());
				//printing result
				System.out.println("MD5 result: " + res1);
			}
			else if (c == 2)
			{
				System.out.println("Enter plaintext:");
				Scanner scan2 = new Scanner(System.in);
				//creating SHA object
				SHA pt2 = new SHA();
				
				String text2 = scan2.nextLine();
				//returning SHA encryption result
				String res2 = pt2.getSHA(text2.getBytes());
				//printing result
				System.out.println("SHA result: " + res2);		
			}
			else
			{
				//exit
				System.out.println("Exit. ");
				scan.close();
			}
	}
}
