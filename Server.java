package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
//Threading http://users.teilar.gr/~gkakaron/oose/06.pdf

public class Server {
	public static void main(String[] args) throws IOException{
		ServerSocket ss = new ServerSocket(5555);
		try {
			while(ss.isClosed() == false) {
				Socket s = ss.accept();
				System.out.println("Nueva conexion");
				ClientHandler ch = new ClientHandler(s);
				
				Thread thread = new Thread(ch);
				thread.start();
			}
		}catch (IOException e) {
			try {
				if(ss != null) {
					ss.close();
				}
			}catch (IOException f){
				e.printStackTrace();
				f.printStackTrace();
			}
		}
	}

}
