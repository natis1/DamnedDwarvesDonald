//import java.awt.Rectangle;
//import java.awt.event.KeyEvent;
import java.util.ArrayList;
//Act with wisdom, but act.

public class ElvenPixAlly extends ElvenSprite {
	
    private int fireDelay;
    private int fireStop = (int) (ElvenMain.ElvenUpgradeFirerate);
    
    private float realx;
    private float realy;
    
    
    private ArrayList<ElvenMissile> Elvenmissiles;
    
    

    public ElvenPixAlly(int x, int y, double angle, String image_file) {
    	
    	
        super(x, y, Math.PI/2, 20, "main/resources/elvenFae.png");
        
        
        initAlly();
        
    }
    
    
    private void initAlly() {
    	
    	loadImage();
        Elvenmissiles = new ArrayList<ElvenMissile>();
        realx = x;
        realy = y;
        
    }
    
    
    public void move(int ex, int ey, double angleUsed) {
    
    	
    	float tempx = realx;
    	float tempy = realy;
    	float tempEX = ex;
    	float tempEY = ey;
    	
    	
    	realx = (float) (tempx * .97 + tempEX * 0.03);
    	realy = (float) (tempy * .97 + tempEY * 0.03);
    	
    	x = (int) realx;
    	y = (int) realy;
    	setAngle(Math.PI/2);
    	loadImage();
    	
    }
    


    public void fire() {
    	if (fireDelay < 0){
    		
    		Elvenmissiles.add(new ElvenMissile( (x + width * 3/4 ), 
    				y + height/2 , 
    				0, "main/resources/elvenarrow.gif"));
    		fireDelay = 4 - fireStop / 5;
    	}
    	fireDelay--;
        
    }
    
    
    public ArrayList<ElvenMissile> getMissiles() {
        return Elvenmissiles;
    }
    

    
}