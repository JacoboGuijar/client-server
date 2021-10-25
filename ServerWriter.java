public class ServerWriter extends Thread{
	OutputStreamWriter osw;
	public Queue<String> queue;
	public int queueSize;
	
	public ServerWriter(OutputStreamWriter osw, Queue<String> queueIn,int queueSizeIn) {
		this.osw = osw;
		this.queue = queueIn;
        this.queueSize = queueSizeIn;

	}
	static void print_queue(Queue<String> queue){
		synchronized(queue) {
			while(queue.isEmpty() == false) {
				System.out.println(queue.remove());
			}
		}
	}
	@Override
	public void run() {
		int n = 1;
		boolean active = true;
		BufferedWriter bw = new BufferedWriter(osw);
		while(active) {
			try {
				print_queue(queue);
				bw.write("Servidor: " + Integer.toString(n));
				bw.newLine();
				bw.flush();
				if(n==50)
					n = 0;
				n++;
				
				try{Thread.sleep(500);}catch(InterruptedException ex){ex.printStackTrace();}
			}catch(IOException e) {
				try {
					active = false;
					if(bw != null) 
						bw.close();
					
				}catch(IOException f) {e.printStackTrace();f.printStackTrace();}
			}			
			
		}
	}
	
}
