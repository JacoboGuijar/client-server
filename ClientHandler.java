public class CliengHandler extends Thread{
		
	public static CliengHandler[] arrayCC = new CliengHandler[0];
	private Socket s;
	private OutputStreamWriter osw;
	private InputStreamReader isr;
	//Consumer-producer example to share information between Threads: https://stackoverflow.com/a/44667033/12293801
	private Queue<String> queue;
			
	public CliengHandler(Socket s) {
		try {
			//Como declarar CliengHandler: https://docs.oracle.com/javase/tutorial/networking/sockets/clientServer.html
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
	
	public CliengHandler[] addClient(CliengHandler [] arrayCC, CliengHandler cc) {
		CliengHandler[] newArrayCC = new CliengHandler[arrayCC.length + 1];
		
		for(int i = 0; i < arrayCC.length; i++) {
			newArrayCC[i] = arrayCC[i];
		}
		newArrayCC[newArrayCC.length - 1] = cc;
		System.out.println(newArrayCC.length);
		return newArrayCC;
	}
	
	public CliengHandler[] rmvClient(CliengHandler [] arrayCC, CliengHandler cc) {
		CliengHandler[] newArrayCC = new CliengHandler[arrayCC.length - 1];
		
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
