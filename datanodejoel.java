package datanode.joel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.json.JSONObject;

public class DataNode {
	// Capacity in MB	
	final int Capacity = 1000;
	// BlockSize is the max size of one file in MB
	final int BlockSize = 4;
	// StoragePath is where the files will get stored	
	final String StoragePath = "C:\\Users\\rahaf\\Desktop\\Operating-Systems\\Dfs\\Storage";
	
	// ServerPort is the port that the data node server will operate on	
	final int ServerPort = 6789;
	
	// blocksCount reports how many blocks we have available in relative to the Capacity and BlockSize   
	int blocksCount = (int) Math.floor(this.Capacity / this.BlockSize);
	
	// Blocks is a hashmap to store the blockID as key and boolean flag as value to determine whether
	// the block was TAKEN (true) or FREE (false).	
	HashMap<Integer,Boolean> Blocks = new HashMap<Integer,Boolean>();
	
	DataNode() {
		for (int i = 0; i < blocksCount; i++) {
			this.Blocks.put(i, false);
		}
	}
	
	// ListenAndServe method launches a server for the data node which is responsible for
	// receiving commands and executing them.
	// 
	// The server expects command requests in the JSON format as following:
	//	ALLOC: {"command ": "ALLOC" } 								RETURNS: {"blockId": $blockId}
	//	WRITE: {"command": "WRITE", "blocked":1, "content":"...."}	RETURNS: none
	//  READ:  {"command" : "READ", "blocked": 1}					RETURNS: {"content": "...."}
	public void ListenAndServe() {
	  try {
		  ServerSocket serverSocket = new ServerSocket(this.ServerPort);
	
		  while (true) {
			  	Socket conn = serverSocket.accept();
			  	
			  	DataNode _this = this;
			  	
			  	Runnable runnable = new Runnable() {
					public void run() {
						if(lock.isWriteLocked()) {
           				 System.out.println("Write Lock Present.");
        					 }
         	lock.readLock().lock();
						
						try {
							BufferedReader inFromClient = new BufferedReader(new InputStreamReader(conn.getInputStream()));
							DataOutputStream outToClient = new DataOutputStream(conn.getOutputStream());
							
												
							
							String clientCommandJSON  = "";
							String line = "";
							while((line = inFromClient.readLine()) != null) {
								clientCommandJSON += line;
							}
							
							JSONObject obj = new JSONObject(clientCommandJSON);
							
							String cmd = obj.getString("command");
					
							switch (cmd) {
							case "ALLOC":
								int id = _this.Alloc();
								outToClient.writeUTF(SerializeAlloc(id));
								break;
							
							case "WRITE":
								_this.Write(obj.getInt("blockId"), obj.getString("content"));
								lock.writeLock().lock();
								break;
							
							case "READ":
								String content = _this.Read(obj.getInt("blockId"));
								outToClient.writeUTF(SerializeRead(content));
								break;
								default:
									System.out.println("Received unknown command " + cmd);
							}
						}catch(Exception ex) {}
					}
				};
								runnable.run();
			  }
		  } catch(IOException ex) {
			  System.out.println(ex);
		  }
	  
	}
	
	
	private int Alloc() {
		for (int i = 0; i < blocksCount; i++) {
			boolean taken = this.Blocks.get(i).booleanValue();
			if (!taken) {
				this.Blocks.put(i, true);
				return i;
			}
		} 
		return -1;
	}

		
	private void Write(int blockId, String contents) {
		Writer writer = null;
		try {
			writer = new BufferedWriter(new OutputStreamWriter( new FileOutputStream(this.StoragePath + "/" + blockId), "utf-8"));
			writer.write(contents);
		} catch(IOException ex) {
			
		}finally {
			try {
				writer.close();
			} catch(IOException ex) {}
		}
	}
	
	
	private String Read(int blockId) {
		String content = "";
		try {
			Scanner in = new Scanner(new FileReader(this.StoragePath + "/" + blockId));
			while (in.hasNextLine()) { 
				content += in.nextLine() + "\n";
				
			} 
		} catch(IOException ex) {}
		return content;
	}
	
	private String SerializeAlloc(int id) {
		JSONObject obj = new JSONObject();
		obj.put("blockId", id);
		return obj.toString();
	}
	
	private String SerializeRead(String content) {
		JSONObject obj = new JSONObject();
		obj.put("content", content);
		return obj.toString();
	}
	
	
}
