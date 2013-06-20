package myServer;

public class StartTCPServer {

	public static void main(String[] args) {
		MultiThreadedTCPServer server = new MultiThreadedTCPServer(79);
		server.start();
	}

}
