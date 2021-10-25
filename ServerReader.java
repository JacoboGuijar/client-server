public class ServerReader extends Thread{
	InputStreamReader isr;
	private Queue<String> queue;
	private int queueSize;
	
	public LecturaServidor(InputStreamReader isr, Queue<String> queueIn,int queueSizeIn) {
		this.isr = isr;
		this.queue = queueIn;
		this.queueSize = queueSizeIn;
	}
	static void add_to_queue(String msg, Queue<String> queue, int queueSize){
		synchronized(queue) {
			if(queue.size() != queueSize) 
				if(Integer.parseInt(msg.replace("Cliente: ", ""))%25 == 0) 
					queue.add(msg.replace("Cliente: ", "") + " es divisible entre 25");
		}
	}
	@Override
	public void run() {
		BufferedReader br = new BufferedReader(isr);
		while(true) {
			try {
				String msg = br.readLine();
				add_to_queue(msg, queue, queueSize);
			} catch (IOException e) {e.printStackTrace();break;}
		}	
	}
}
