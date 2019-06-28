// A Java program for a Server 
import java.net.*;
import java.io.*; 
import java.text.*;
import java.util.*;

/*
public class Server 
{ 
	//initialize socket and input stream 
	private Socket		  socket = null; 
	private ServerSocket  server = null; 
	private DataInputStream in	 = null; 

	// constructor with port 
	public Server(int port) 
	{ 
		// starts server and waits for a connection 
		try
		{ 
			server = new ServerSocket(port); 
			System.out.println("Server started"); 

			System.out.println("Waiting for a client ..."); 

			socket = server.accept(); 
			System.out.println("Client accepted"); 

			// takes input from the client socket 
			in = new DataInputStream( 
				new BufferedInputStream(socket.getInputStream())); 

			String line = ""; 

			// reads message from client until "Over" is sent 
			while (!line.equals("Over")) 
			{ 
				try
				{ 
					line = in.readUTF(); 
					System.out.println(line); 
				} 
				catch(IOException i) 
				{ 
					System.out.println(i); 
				} 
			} 
			System.out.println("Closing connection"); 

			// close connection 
			socket.close(); 
			in.close(); 
		} 
		catch(IOException i) 
		{ 
			System.out.println(i); 
		} 
	} 

	public static void main(String args[]) 
	{ 
		Server server = new Server(5000); 
	} 
} 

	/*

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
/*

*/

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
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
                //Reading the message from the client
                socket = serverSocket.accept();
                InputStream is = socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String number = br.readLine();
                System.out.println("Message received from client is "+number);
 
                //Multiplying the number by 2 and forming the return message
                String returnMessage;
                try
                {
                    int numberInIntFormat = Integer.parseInt(number);
                    int returnValue = numberInIntFormat*2;
                    returnMessage = String.valueOf(returnValue) + "\n";
                }
                catch(NumberFormatException e)
                {
                    //Input was not a number. Sending proper message back to client.
                    returnMessage = "Please send a proper number\n";
                }
 
                //Sending the response back to the client.
                OutputStream os = socket.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(os);
                BufferedWriter bw = new BufferedWriter(osw);
                bw.write(returnMessage);
                System.out.println("Message sent to the client is "+returnMessage);
                bw.flush();
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
