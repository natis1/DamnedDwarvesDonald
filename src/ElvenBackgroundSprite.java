//Never let a wrong turn into an evil
public class ElvenBackgroundSprite extends ElvenSprite {
	
	
	private int inverseSpeedMultiplier = (int) (1 /ElvenBoard.speedMultiplier);
    private float realx;
	
	

    public ElvenBackgroundSprite(int x, String BGName) {
    	
    	
    	
        super(x, 0, 0, 0, BGName);
        realx = x;
        
        loadImage();
    }
    
    public void move() {
    	
    	//f people with overclocked monitors, they get slow backgrounds.
    	if (inverseSpeedMultiplier < 1) {
    		inverseSpeedMultiplier = 1;
    	}
        
    	realx -= 5.0 / (double) inverseSpeedMultiplier;
    	
        if (realx <= -4000) {
            realx=4000;
        }
        x = (int) realx;
        
    }
    
    
}