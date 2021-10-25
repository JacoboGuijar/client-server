public class ClientHandler extends Thread{
	//Added the ClientHandler Array for multiple clients	
	public static ClientHandler[] arrayCC = new ClientHandler[0];
	private Socket s;
	private OutputStreamWriter osw;
	private InputStreamReader isr;
	//Consumer-producer example to share information between Threads: https://stackoverflow.com/a/44667033/12293801
	private Queue<String> queue;
			
	public ClientHandler(Socket s) {
		try {
			//How to declarate ClientHandler: https://docs.oracle.com/javase/tutorial/networking/sockets/clientServer.html
			this.s = s;
			this.osw = new OutputStreamWriter(s.getOutputStream());
			this.isr = new InputStreamReader(s.getInputStream());
			this.queue = new LinkedList<>();
			arrayCC = addClient(arrayCC, this);
			
		}catch(IOException e) {
			try {
				//arrayCC = rmvClient(arrayCC, this);
				if(s != null)
					s.close();
				
			}catch(IOException f) {
				e.printStackTrace();
				f.printStackTrace();
			}
		}
	}
	
	public ClientHandler[] addClient(ClientHandler [] arrayCC, ClientHandler cc) {
		ClientHandler[] newArrayCC = new ClientHandler[arrayCC.length + 1];
		
		for(int i = 0; i < arrayCC.length; i++) {
			newArrayCC[i] = arrayCC[i];
		}
		newArrayCC[newArrayCC.length - 1] = cc;
		System.out.println(newArrayCC.length);
		return newArrayCC;
	}
	
	public ClientHandler[] rmvClient(ClientHandler [] arrayCC, CliengHandler cc) {
		ClientHandler[] newArrayCC = new ClientHandler[arrayCC.length - 1];
		
		for(int i = 0; i <arrayCC.length; i++) {
			if(arrayCC[i] != cc) {
				for(int j = 0; 0 < newArrayCC.length && j != -1; j++) {
					if(newArrayCC[j] == null) {
						newArrayCC[j] = arrayCC[i];
						j = -2; //j = -2 -> j + 1 = -1 -> fuera del bucle
					}
				}
			}
		}
		System.out.println(newArrayCC.length);
		return newArrayCC;
	}
	
	@Override
	public void run() {
		int buffer = 10; //buffer 10 es una cifra puesta de prueba, todavía hay que estudiar el pleno comportamiento de la variable
		Thread tWriter = new ServerWriter(osw, queue, buffer);
		Thread tReader = new ServerReader(isr, queue, buffer);
		tReader.start();
		tWriter.start();
	}
}
