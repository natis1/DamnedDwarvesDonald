import java.awt.*;

//package com.zetcode;

public class ElvenMissile extends ElvenSprite {

	
	
	//Should be 2000* resolution scale
    private final int BOARD_WIDTH = 2000;
    private final double MISSILE_SPEED = (7.0 * ElvenMenuPanel.speedMultiplier);
    private double angle;
    
    private float realy;
    private float realx;
    
    
    //private int elvenUpgrade = ElvenMain.ElvenUpgradeTracking;
    

    public ElvenMissile(int x, int y, double angle, String image_file) {
    	
    	
    	
        super(x, y, angle, 10, image_file);
        x += (int)10*Math.cos(angle);
        y += (int)10*Math.sin(angle);
        realx = x;
        realy = y;
        
        this.angle = angle;
        
        loadImage();
    }
    
    /*private void initMissile() {
        
        loadImage("elvenarrow.gif");  
        getImageDimensions();
    }*/

    
    public void moveToLocation(int xLoc, int yLoc) {
        
    	//this.angle = angle
    	
    	
    	//Uses math and magic IDK
    	int xTheTriangle = xLoc - (int) realx;
    	int yTheTriangle = yLoc - (int) realy;
    	
    	
    	if ( xTheTriangle > 5 && (yTheTriangle > 8 || yTheTriangle < -8)){
    		
        	double angle = Math.atan((double) yTheTriangle/((double) xTheTriangle));
        	this.angle = angle;
        	
        	realx += (Math.cos(this.angle) * (((double) ElvenMain.ElvenUpgradeTracking + 5) / 5)) * MISSILE_SPEED;
        	realy += (Math.sin(this.angle) * (((double) ElvenMain.ElvenUpgradeTracking + 5) / 5)) * MISSILE_SPEED;
            
            x = (int) realx;
            y = (int) realy;
            
    	} else {
    		move();
    	}
    	
    	
    	
    }
    

    public void move() {
    	
    	//this.angle = angle
        realx += Math.cos(this.angle) * MISSILE_SPEED;
        realy += Math.sin(this.angle) * MISSILE_SPEED;
        x = (int) realx;
        y = (int) realy;
        
        if (x > BOARD_WIDTH) {
            vis = false;
        }
    }
    
    public Rectangle getBounds() {
    	
    	getImageDimensions();
        return new Rectangle(x, y, width, height);
        
        
    }

    
    
    
}


