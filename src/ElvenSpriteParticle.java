
public class ElvenSpriteParticle extends ElvenSprite {

	
	
	//Should be 2000* resolution scale
    private final int BOARD_WIDTH = 2000;
    private final int BOARD_HEIGHT = 1200;
    private float missileSpeed;
    private double angle;
    
    private float realx;
    private float realy;
    
    
    public float myAlpha = 1;

    public ElvenSpriteParticle(int x, int y, float speed, double angle, String image_file) {
    	
    	
    	
        super(x, y, angle, 10, image_file);
        
        missileSpeed = speed;
        realx = x;
        realy = y;
        
        this.angle = angle;
        
        loadImage();
    }
    
    /*private void initMissile() {
        
        loadImage("elvenarrow.gif");  
        getImageDimensions();
    }*/

    
    public void smartMoveToLocation() {
        
    	
        	
        realx += Math.cos(angle) * missileSpeed;
        realy += Math.sin(angle) * missileSpeed;
        
        
        // This is because the board also moves left. Slightly less movement then you would expect but brains are odd
        realx -= 5 * ElvenMenuPanel.speedMultiplier;

        myAlpha -= .01 * ElvenMenuPanel.speedMultiplier;
        
        x = (int) realx;
        y = (int) realy;
        
        
        if (x > BOARD_WIDTH || x < -100 || y > BOARD_HEIGHT || y < -100 || myAlpha < 0) {
            vis = false;
        }
    	
    	
    	
    }
    

}