// A Java program for a Server 

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
 
public class Server
{
 
    private static Socket socket;
 
    public static void main(String[] args)
    {
        try
        {
 
            int port = 5000;
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server Started and listening to the port 5000");
            
            System.out.println("Waiting for a client...");
 
            //Server is running always. This is done using this while(true) loop
            while(true)
            {
                //Reading the message from the client, Connection
                socket = serverSocket.accept();
                System.out.println("Client connected.");
                
                //Read from keyboard
                BufferedReader keyRead = new BufferedReader(new InputStreamReader(System.in));
                
                //Send to client, and logic to send to dataNode
                OutputStream ostream = socket.getOutputStream();
                PrintWriter pwrite = new PrintWriter(ostream, true);
                
                //Receive from server, and logic to receive from dataNode
                InputStream istream = socket.getInputStream();
                BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));
                
                String receiveMessage, sendMessage;
                
 
                while(true)
                {
                	receiveMessage = receiveRead.readLine();
                	if((receiveMessage.equals("APPEND")))
					{
						// For debugging purposes, to be removed in final edition
						System.out.println("1, Client wishes to APPEND.");
						// This is where we call the logic for APPEND
					}
					else if((receiveMessage.equals("READ")))
					{
						// For debugging purposes, to be removed in final edition
						System.out.println("2, Client wishes to READ.");
						// This is where we call the logic for READ
					}
					else
					{
						System.out.println("3, Client entered unacepptable input.");
					}
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                socket.close();
            }
            catch(Exception e){}
        }
    }
}


/*
 *  This has been an attempt to work with multi-threading, but we couldn't get it to work this early.
To be returned to once we have more time to work on it
 * 
 * import java.net.*;
import java.io.*; 
import java.text.*;
import java.util.*;

public class Server  
{ 
public static void main(String[] args) throws IOException  
{ 
  // server is listening on port 5000
  ServerSocket ss = new ServerSocket(5000); 
    
  // running infinite loop for getting 
  // client request 
  while (true)  
  { 
      Socket s = null; 
        
      try 
      { 
      	System.out.println("Waiting for a client ..."); 
      	
          // socket object to receive incoming client requests 
          s = ss.accept(); 
            
          System.out.println("A new client is connected : " + s); 
            
          // obtaining input and out streams 
          DataInputStream dis = new DataInputStream(s.getInputStream()); 
          DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 
            
          System.out.println("Assigning new thread for this client"); 

          // create a new thread object 
          Thread t = new ClientHandler(s, dis, dos); 

          // Invoking the start() method 
          t.start(); 
            
      } 
      catch (Exception e){ 
          s.close(); 
          e.printStackTrace(); 
      } 
  } 
} 
} 

*/
/* 
 * 
 *  This has been an attempt to work with multi-threading, but we couldn't get it to work this early.
To be returned to once we have more time to work on it

//ClientHandler class 
class ClientHandler extends Thread  
{
	DateFormat fordate = new SimpleDateFormat("yyyy/MM/dd"); 
	DateFormat fortime = new SimpleDateFormat("hh:mm:ss"); 
	final DataInputStream dis; 
	final DataOutputStream dos; 
	final Socket s; 
		
	// Constructor 
	public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos)  
	{ 
		this.s = s; 
		this.dis = dis; 
		this.dos = dos; 
	} 
		
	@Override
	public void run()
	{ 
		String received; 
		String toreturn; 
		while (true)
		{
			try
			{
	        	 // Ask user what he wants 
	             dos.writeUTF("What do you want?[Append | Read]..\n"+ 
	                         "Type Exit to terminate connection."); 
             
	             // receive the answer from client 
	             received = dis.readUTF(); 
	               
	             if(received.equals("Exit")) 
	             {  
	                 System.out.println("Client " + this.s + " sends exit..."); 
	                 System.out.println("Closing this connection."); 
	                 this.s.close(); 
	                 System.out.println("Connection closed"); 
	                 break; 
	             } 
	               
	             // creating Date object 
	             Date date = new Date(); 
	               
	             // write on output stream based on the 
	             // answer from the client 
	             switch (received) 
	             { 
	                 case "Append" : 
	                     toreturn = fordate.format(date); 
	                     dos.writeUTF(toreturn); 
	                     break; 
	                       
	                 case "Read" : 
	                     toreturn = fortime.format(date); 
	                     dos.writeUTF(toreturn); 
	                     break; 
	                       
	                 default: 
	                     dos.writeUTF("Invalid input"); 
	                     break;
	             }
			}
			catch (IOException e) { e.printStackTrace(); }
		}
     
   try
   { 
       // closing resources 
       this.dis.close(); 
       this.dos.close(); 
         
   }
   catch(IOException e) { e.printStackTrace(); } 
	}
}
	*/
