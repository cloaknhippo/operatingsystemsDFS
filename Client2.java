// A Java program for a Client 
import java.net.*; 
import java.io.*; 

public class Client 
{
	
		// initialize socket and input output streams 
		private Socket socket		 = null; 
		private DataInputStream input = null; 
		private DataOutputStream out	 = null; 

		// constructor to put ip address and port 
		public Client(String address, int port) 
		{ 
			// establish a connection 
			try
			{ 
				socket = new Socket(address, port); 
				System.out.println("Connected"); 

				//Read from keyboard
				BufferedReader keyRead = new BufferedReader(new InputStreamReader(System.in));
				
				//Send to client
				OutputStream ostream = socket.getOutputStream();
				PrintWriter pwrite = new PrintWriter(ostream, true);
				
				//Receive from server
				InputStream istream = socket.getInputStream();
				BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));
				
				System.out.println("Would you like to APPEND, or would you like to READ?");
				
				String receiveMessage, sendMessage;
				while(true)
				{
					sendMessage = keyRead.readLine();
					pwrite.println(sendMessage);
					pwrite.flush();
					
					
					if((sendMessage.equals("APPEND")))
					{
						// This is where we call the logic for APPEND
						// For debugging purposes, to be removed in final edition
						System.out.println("1, what would you like to append?");
					}
					else if((sendMessage.equals("READ")))
					{
						// This is where we call the logic for READ
						// For debugging purposes, to be removed in final edition
						System.out.println("2, what would you like to read?");
					}
					else
					{
							System.out.println("Please choose to READ or APPEND");
					}	
				}
					
					// Old logic, holding onto as we aren't nearly done with it.
					/*if((receiveMessage = receiveRead.readLine()) != null)
					{
						System.out.println(receiveMessage);
						if(receiveMessage.equals("APPEND"))
						{
							System.out.println("Which file to append?");
						}
						else if(receiveMessage.equals("READ"))
						{
							System.out.println("Which file to read?");
						}
						else
						{
							System.out.println("Please choose to APPEND or to READ");
						}
					}*/
				}
			catch(IOException i) 
			{ 
				System.out.println(i); 
			} 
		} 

		public static void main(String args[]) 
		{ 
			Client client = new Client("25.60.29.53", 5000); 
		} 
	} 
