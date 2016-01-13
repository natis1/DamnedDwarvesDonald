import java.awt.*;
//Act with wisdom, but act.






//Hel yes, this code looks awesome. I cannot even explain why.

public class ElvenEnemy extends ElvenSprite {




    //This will be determined by the RNG to allow for multiple kinds of enemies
    //All enemies controlled by this class so that they can be in one array though.
    protected int elvenEnemyType;

    private double elvenEnemySpeed = ElvenMenuPanel.speedMultiplier;
    
    private double elvenEnemyYSpeed = 0;
    
    
    private final double speedMultiplier = ElvenMenuPanel.speedMultiplier;

    private int movementPhase = 0;
    


    
    private float realx;
    
    //to be used when enemies move better
    private float realy;


    public ElvenEnemy(long elvenActionToTake) {


        super(20, 1080, -Math.PI / 2, 50, "main/resources/elvenelf1.png");

        initEnemy(elvenActionToTake);
    }

        //Eh good enough
	private final int BOARD_WIDTH = -200;
    
	private int EnemyHP;
    
    
    private void initEnemy(long elvenActionToTake) {

    	
    	
        //So many possibilites. We need one for every y co-ord and every elvenEnemyType
        //One day this will take advantage of the size of the screen, but I am a bad programmer
    	
    	int difficulty = ElvenMain.ElvenGameDifficulty;
    	if (difficulty > 9) difficulty = 9;
    	int realElvenAction = (int) elvenActionToTake % (1080 * (difficulty + 1));//no divide by zero here officer

        //0 - ? possible
        elvenEnemyType = realElvenAction / 1080;
        elvenEnemySpeed = ElvenMenuPanel.speedMultiplier * (10  + (ElvenMain.ElvenGameDifficulty / 4));
        
        if (elvenEnemyType < 0){
        	elvenEnemyType = 0 - elvenEnemyType;
        }
        switch (elvenEnemyType) {
        case 0: this.image_file = "main/resources/e1.png";
    	elvenEnemySpeed = ElvenMenuPanel.speedMultiplier * (8  + (ElvenMain.ElvenGameDifficulty / 4));
    	break;
    	
        case 1: this.image_file = "main/resources/e2.png";
    	elvenEnemySpeed = ElvenMenuPanel.speedMultiplier * (5  + (ElvenMain.ElvenGameDifficulty / 4));
    	break;
    	
        case 2:
        break;
    	
        case 3: this.image_file = "main/resources/asteroid.png";
    	EnemyHP = 50000;
    	elvenEnemySpeed = ElvenMenuPanel.speedMultiplier * (5  + (ElvenMain.ElvenGameDifficulty / 4));
    	break;
        
        case 4: this.image_file = "main/resources/e1.png";
    	elvenEnemyYSpeed = ElvenMenuPanel.speedMultiplier * (3  + (ElvenMain.ElvenGameDifficulty / 4));
    	elvenEnemySpeed = ElvenMenuPanel.speedMultiplier * 4;
    	break;
    	
        case 5: this.image_file = "main/resources/e2.png";
    	elvenEnemyYSpeed = ElvenMenuPanel.speedMultiplier * (1  + (ElvenMain.ElvenGameDifficulty / 4));
    	elvenEnemySpeed = ElvenMenuPanel.speedMultiplier * 4;
    	break;
    	
        case 6:
        elvenEnemySpeed = ElvenMenuPanel.speedMultiplier * 5;
        break;
    	
        case 7: this.image_file = "main/resources/e1.png";
    	elvenEnemyYSpeed = ElvenMenuPanel.speedMultiplier * (3  + (ElvenMain.ElvenGameDifficulty / 4));
    	elvenEnemySpeed = ElvenMenuPanel.speedMultiplier * 5;
    	break;
    	
        case 8: this.image_file = "main/resources/e2.png";
    	elvenEnemyYSpeed = ElvenMenuPanel.speedMultiplier * (1  + (ElvenMain.ElvenGameDifficulty / 4));
    	elvenEnemySpeed = ElvenMenuPanel.speedMultiplier * 5;
    	break;
        
        
        
        }


        realx = x;
        realy = y;

    	
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
            switch (movementPhase) {
                case 0:
                    realy -= elvenEnemySpeed;
                    if (realy < 32.0) {
                        realy = 32;
                        movementPhase++;
                    }
                    break;
                case 1:
                    realx += elvenEnemySpeed;
                    if (realx > 714) {
                        realx = 714;
                        movementPhase++;
                    }
                    break;


            }

        	
            /*
            realx -= elvenEnemySpeed;
            if (realx < BOARD_WIDTH) {
                vis = false;
            }
            */
            
            //approximation of real location at any frame
            x = (int) realx;
            y = (int) realy;
            
    	}
        
    	



    	
    	
    	
    	

    }
    
    public Rectangle getBounds() {
    	
    	getImageDimensions();
        return new Rectangle(x, y, width, height);
    }
    
    
    
}




