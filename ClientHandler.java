
public class ClientHandler extends Thread{
		
	public Socket s;
	private OutputStreamWriter osw;
	public InputStreamReader isr;
	//Consumer-producer example to share information between Threads
	Queue<String> queue = new LinkedList<>();
			
	public ClientHandler(Socket s) {
		try {
			this.s = s;
			this.osw = new OutputStreamWriter(s.getOutputStream());
			this.isr = new InputStreamReader(s.getInputStream());
			
		}catch(IOException e) {
			try {
				if(s != null)s.close();
				
			}catch(IOException f) {e.printStackTrace();f.printStackTrace();}
		}
	}
	
	@Override
	public void run() {
		
		int buffer = 5;
		Thread tWriter = new ServerWriter(osw, queue, buffer);
		Thread tReader = new ServerReader(isr, queue, buffer);
		tReader.start();
		tWriter.start();
	}
}
