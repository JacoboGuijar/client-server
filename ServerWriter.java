public class ServerWriter extends Thread{
	OutputStreamWriter osw;
	public Queue<String> queue;
	public int queueSize;
	
	public ServerWriter(OutputStreamWriter osw, Queue<String> queueIn,int queueSizeIn) {
		this.osw = osw;
		this.queue = queueIn;
        this.queueSize = queueSizeIn;

	}
	static String print_queue(Queue<String> queue){
		String to_print = null;
		synchronized(queue) {
			while(queue.isEmpty() == false) {
				to_print = queue.remove();
			}
		}
		return to_print;
	}
	@Override
	public void run() {
		int n = 1;
		boolean active = true;
		BufferedWriter bw = new BufferedWriter(osw);
		while(active) {
			try {
				String to_print = print_queue(queue);
				if(to_print!=null && to_print.isEmpty()==false) {
					bw.write(to_print);
					bw.newLine();
					bw.flush();
				}
				//bw.write("Servidor: " + Integer.toString(n));
				//bw.newLine();
				//bw.flush();
				if(n==50)
					n = 0;
				n++;
				
				try{Thread.sleep(100);}catch(InterruptedException ex){ex.printStackTrace();}
			}catch(IOException e) {
				try {
					active = false;
					if(bw != null) 
						bw.close();
					
				}catch(IOException f) {
					e.printStackTrace();
					f.printStackTrace();
					break;
				}
			}					
		}
	}
}
