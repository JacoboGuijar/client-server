public class ServerReader extends Thread{
	InputStreamReader isr;
	private Queue<String> queue;
	private int queueSize;
	
	public ServerReader(InputStreamReader isr, Queue<String> queueIn,int queueSizeIn) {
		this.isr = isr;
		this.queue = queueIn;
		this.queueSize = queueSizeIn;
	}
	static void add_to_queue(String msg, Queue<String> queue, int queueSize){
		synchronized(queue) {
			if(queue.size() != queueSize) {
					queue.add(msg);
			}
		}
	}
	@Override
	public void run() {
		BufferedReader br = new BufferedReader(isr);
		while(true) {
			try {
				String msg = Logic.msg_back(br.readLine());
				//System.out.println(msg);
				if(msg!=null&&msg.isEmpty()==false)
					add_to_queue(msg, queue, queueSize);
			} catch (IOException e) {e.printStackTrace();break;}
		}	
	}
}
