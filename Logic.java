public class Logic {
		
	public static String msg_back(String orden){
		String msg_back = "";
		int n = Integer.parseInt(orden.replace("Cliente: ", ""));
		
		if(is_prime(n)) {
			msg_back = n + " is prime";
		}
		else if(n%100 == 0) {
			msg_back = n + " is divisible by 100";
		}
		return msg_back;
	}
	public static boolean is_prime(int n) {		
		boolean bool = true;
		if(n==2)bool = false;
		if(n%2==0)bool = false;
		for(int i = 3; i<=Math.sqrt(n)&&bool == true;i++) {
			if(n%i==0) {
				bool = false;
			}
		}		
		return bool;
	}
}
