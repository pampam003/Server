package myServer;

import java.io.IOException;
import java.net.*;


public class MultiThreadedTCPServer {
	
	private ServerSocket serverSocket;
	private int port;
	
	public MultiThreadedTCPServer(int port){
		this.port = port;
	}
	
	public void start(){
		try{
			serverSocket = new ServerSocket(port);
		}catch (IOException e){
			e.printStackTrace();
			System.out.println("Could not listen on port " + port);
		}
		System.out.println("Server started!");
		while (true){
			try{
				Socket socket = serverSocket.accept();
				new Thread(new TCPServerSideThread(socket)).start();
			}catch (Exception e){
				e.printStackTrace();
				System.out.println("Accept failed!");
			}
		}
		
	}
	
}
