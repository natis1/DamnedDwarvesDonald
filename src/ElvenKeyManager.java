

public class ElvenKeyManager {
	
	//public boolean[] elvenAsciiInput;
	//ascii?
	
	public boolean[] elvenAsciiInput;
	
	public ElvenKeyManager() {
		elvenAsciiInput = new boolean[52];
		
    }
	
	
	
	
	
	
	void keyReleased(int keyYouLetGoOf){
		
		elvenAsciiInput[keyYouLetGoOf] = false;
		
		
		
		
	}
	

}
