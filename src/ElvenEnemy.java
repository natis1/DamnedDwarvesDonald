import java.awt.*;
import java.util.ArrayList;
//Act with wisdom, but act.






//Hel yes, this code looks awesome. I cannot even explain why.

public class ElvenEnemy extends ElvenSprite {




    //This will be determined by the RNG to allow for multiple kinds of enemies
    //All enemies controlled by this class so that they can be in one array though.
    protected int elvenEnemyType;

    private double elvenEnemySpeed = ElvenBoard.speedMultiplier;
    
    private double elvenEnemyYSpeed = 0;
    
    
    private final double speedMultiplier = ElvenBoard.speedMultiplier;
    
    
    public double universalScaler;
    
    
    //Missile data
    private int nextEnemyMissile;
    public ArrayList<ElvenEnemyMissile> elvenEnemyMissiles;
    
    //Missile garbage collector
    //Will self nuke in t-50.
    public int missileGarbageCollector = 200;
    
    private float realx;
    
    //to be used when enemies move better
    private float realy;
    
    

    public ElvenEnemy(long elvenActionToTake, double scaler) {
    	




        super(2000, 0, -Math.PI/2, 50, "main/resources/elvenelf1.png");
        universalScaler = scaler;
        
        initEnemy(elvenActionToTake);
    }

        //Eh good enough
	private final int BOARD_WIDTH = -200;
    
	private int EnemyHP;
    
    
    private void initEnemy(long elvenActionToTake) {
    	
    	elvenEnemyMissiles = new ArrayList<ElvenEnemyMissile>();
    	nextEnemyMissile = 50;
    	
    	
        //So many possibilites. We need one for every y co-ord and every elvenEnemyType
        //One day this will take advantage of the size of the screen, but I am a bad programmer
    	
    	int difficulty = ElvenMain.ElvenGameDifficulty;
    	if (difficulty > 9) difficulty = 9;
    	int realElvenAction = (int) elvenActionToTake % (1080 * (difficulty + 1));//no divide by zero here officer

        //0 - ? possible
        elvenEnemyType = realElvenAction / 1080;
        elvenEnemySpeed = ElvenBoard.speedMultiplier * (10  + (ElvenMain.ElvenGameDifficulty / 4));
        
        if (elvenEnemyType < 0){
        	elvenEnemyType = 0 - elvenEnemyType;
        }
        switch (elvenEnemyType) {
        case 0: this.image_file = "main/resources/e1.png";
    	elvenEnemySpeed = ElvenBoard.speedMultiplier * (8  + (ElvenMain.ElvenGameDifficulty / 4));
    	break;
    	
        case 1: this.image_file = "main/resources/e2.png";
    	elvenEnemySpeed = ElvenBoard.speedMultiplier * (5  + (ElvenMain.ElvenGameDifficulty / 4));
    	break;
    	
        case 2:
        break;
    	
        case 3: this.image_file = "main/resources/asteroid.png";
    	EnemyHP = 50000;
    	elvenEnemySpeed = ElvenBoard.speedMultiplier * (5  + (ElvenMain.ElvenGameDifficulty / 4));
    	break;
        
        case 4: this.image_file = "main/resources/e1.png";
    	elvenEnemyYSpeed = ElvenBoard.speedMultiplier * (3  + (ElvenMain.ElvenGameDifficulty / 4));
    	elvenEnemySpeed = ElvenBoard.speedMultiplier * 4;
    	break;
    	
        case 5: this.image_file = "main/resources/e2.png";
    	elvenEnemyYSpeed = ElvenBoard.speedMultiplier * (1  + (ElvenMain.ElvenGameDifficulty / 4));
    	elvenEnemySpeed = ElvenBoard.speedMultiplier * 4;
    	break;
    	
        case 6:
        elvenEnemySpeed = ElvenBoard.speedMultiplier * 5;
        break;
    	
        case 7: this.image_file = "main/resources/e1.png";
    	elvenEnemyYSpeed = ElvenBoard.speedMultiplier * (3  + (ElvenMain.ElvenGameDifficulty / 4));
    	elvenEnemySpeed = ElvenBoard.speedMultiplier * 5;
    	break;
    	
        case 8: this.image_file = "main/resources/e2.png";
    	elvenEnemyYSpeed = ElvenBoard.speedMultiplier * (1  + (ElvenMain.ElvenGameDifficulty / 4));
    	elvenEnemySpeed = ElvenBoard.speedMultiplier * 5;
    	break;
        
        
        
        }
        
        
        

        //Get the y or x value
        realElvenAction = realElvenAction % 1080;
        if (realElvenAction < 0){
        	realElvenAction = 0 - realElvenAction;
        }
        
        
        realElvenAction = realElvenAction - 100;
        
        if (realElvenAction < 0){
        	realElvenAction = 0;
        }
        
        
        //Y should be determined by this point
        //DONE
        
        if (elvenEnemyType < 4) {
        	y = realElvenAction;
        } else {
        	if (realElvenAction > 540){
        		y = -100;
        		x = 1860 - (realElvenAction - 540);
        		
        	} else {
        		y = 1100;
        		x = 1860 - (realElvenAction);
        		elvenEnemyYSpeed = 0 - elvenEnemyYSpeed;
        		
        	}
        }
    	
    	
    	realy = y;
    	realx = x;
    	
    	
    	EnemyHP = 5 + ElvenMain.ElvenGameDifficulty;
    	if (elvenEnemyType == 3){
    		EnemyHP = 10000;
    	}
    	
    	//when compiling for real, comment this
    	//TODO: UNCOMMENT WHEN RUNNING IN SIM
    	//this.image_file = "Images\\" + this.image_file;
    	
    	loadImage(); 
    }
    
    public int hit(){
    	EnemyHP--;
        if (EnemyHP < 1) {
        	
        	vis = false;
        	
        	switch (elvenEnemyType) {
            case 0: return 50 * (1 + (ElvenMain.ElvenGameDifficulty / 4));
        	case 1: return 100 * (1 + (ElvenMain.ElvenGameDifficulty / 2));
            case 2: return 150 * (1 + (ElvenMain.ElvenGameDifficulty / 2));
            case 3: return 0;
            case 4: return 50 * (1 + (ElvenMain.ElvenGameDifficulty / 4));
            case 5: return 100 * (1 + (ElvenMain.ElvenGameDifficulty / 2));
            case 6: return 150 * (1 + (ElvenMain.ElvenGameDifficulty / 2));
            case 7: return 50 * (1 + (ElvenMain.ElvenGameDifficulty / 4));
            case 8: return 100 * (1 + (ElvenMain.ElvenGameDifficulty / 2));
        }
        }
        	else {
        	return 1;
        }
		return 0;
    }


    public void move() {
    	
    	if (vis){
    		
    		if (nextEnemyMissile <= 0){
        		if (elvenEnemyType == 0 || elvenEnemyType == 4 || elvenEnemyType == 7){
        			
        			elvenEnemyMissiles.add(new ElvenEnemyMissile( x, 
            				y + height/2, this.angle, "main/resources/ElvenEnemyBullet0.png"));
        			
        			//Reset based on enemy type
        			nextEnemyMissile = (int) ((70 - (ElvenMain.ElvenGameDifficulty * 2)) / speedMultiplier);
        		}
        		if (elvenEnemyType == 1 || elvenEnemyType == 5 || elvenEnemyType == 8){
        			
        			elvenEnemyMissiles.add(new ElvenEnemyMissile( x, 
            				y + height/2, this.angle, "main/resources/ElvenEnemyBullet0.png"));
        			
        			//Reset based on enemy type
        			nextEnemyMissile = (int) ((30  - (ElvenMain.ElvenGameDifficulty)) / speedMultiplier);
        		}
        		if (elvenEnemyType == 6 || elvenEnemyType == 2){
        			
        			elvenEnemyMissiles.add(new ElvenEnemyMissile( x, 
            				y + height/2, this.angle, "main/resources/ElvenEnemyBullet0.png"));
        			
        			//Reset based on enemy type
        			nextEnemyMissile = (int) ((120  - (ElvenMain.ElvenGameDifficulty * 3)) / speedMultiplier);
        		}
        		
        		
        	}
    		
    		
        	nextEnemyMissile--;
        	
            
            realx -= elvenEnemySpeed;
            realy += elvenEnemyYSpeed;
            if (realx < BOARD_WIDTH) {
                vis = false;
            }
            
            
            //approximation of real location at any frame
            x = (int) realx;
            y = (int) realy;
            
    	}
        
    	
    	
    	
    	
        for (int i = 0; i < elvenEnemyMissiles.size(); i++) {
            ElvenEnemyMissile e = (ElvenEnemyMissile) elvenEnemyMissiles.get(i);
            if (!e.isVisible()) {
            	elvenEnemyMissiles.remove(i);
            }
        }
    	
    	
    	
    	

    }
    
    public Rectangle getBounds() {
    	
    	getImageDimensions();
        return new Rectangle(x, y, width, height);
    }
    
    
    
}




