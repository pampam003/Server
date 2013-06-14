package myServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class TCPServerSideThread implements Runnable {

	private Socket socket;
	private BufferedReader in = null;
	private BufferedWriter out = null;
	
	public TCPServerSideThread(Socket socket){
		this.socket = socket;
		try {
			//buffer r/w initialization
			this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		
		try{
			
			//receive requestID
			String messageType = in.readLine();
			System.out.println("message from android   " + Integer.parseInt(messageType));
			
			if (messageType.equals("1")){
				
				saveBitmap();
			}else if (messageType.equals("2")){
				
				String name = in.readLine();
				String description = in.readLine();
				String longitude = in.readLine();
				String latitude = in.readLine();
				String time = in.readLine();
				System.out.println(name + "  " + time);
				
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager
						.getConnection("jdbc:mysql://localhost:3307/gmapserver?"
								+ "user=root&password=root");
				PreparedStatement prepSt = con.prepareStatement("INSERT INTO infopoints"
						+ " (clientId, name, description, latitude, longitude, time) " +
						"values (?, ?, ?, ?, ?, ?)");
				prepSt.setInt(1, 1);
				prepSt.setString(2, name);
				prepSt.setString(3, description);
				prepSt.setString(4, longitude);
				prepSt.setString(5, latitude);
				prepSt.setString(6, time);
				prepSt.execute();
				
				con.close();
			}
			
            
		}catch (Exception e){
			System.out.println("Exception while handling client");
			e.printStackTrace();
		}
		
	}
	
	private void saveBitmap(){
		
		//receive bitmap
		InputStream in_bmp;
		OutputStream out_bmp;
		try {
			in_bmp = socket.getInputStream();
			out_bmp = new FileOutputStream("filename.bmp");
			
			final byte[] buffer = new byte[1024];
			int read = -1;
			while ((read = in_bmp.read(buffer)) != -1) {
				out_bmp.write(buffer, 0, read); 
			}
			in_bmp.close();
			out_bmp.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Done.");
	}

}
