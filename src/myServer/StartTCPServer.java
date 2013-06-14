package myServer;

public class StartTCPServer {

	public static void main(String[] args) {
		MultiThreadedTCPServer server = new MultiThreadedTCPServer(4567);
		server.start();
	}

}
