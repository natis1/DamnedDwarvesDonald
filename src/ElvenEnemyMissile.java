//Never cower from difficulty; difficult soil harbors strong roots.


import java.awt.*;

//package com.zetcode;

public class ElvenEnemyMissile extends ElvenSprite {

    private final int BOARD_WIDTH = -50;
    private final double MISSILE_SPEED = (6 + ElvenMain.ElvenGameDifficulty) * ElvenBoard.speedMultiplier;
    //private double angle;
    
    private float realy;
    private float realx;
    

    public ElvenEnemyMissile(int x, int y, double angle, String image_file) {
    	
    	
    	
        super(x, y, angle, 10, image_file);
        realy= y;
        realx= x;
        
        
        //x += (int)10*Math.cos(angle);
        //y += (int)10*Math.sin(angle);
        
        
        this.angle = angle;
        
        loadImage();
    }
    
    
    
    
    
    
    public void moveToLocation(int xLoc, int yLoc) {
        
    	//this.angle = angle
    	
    	
    	//Uses math and magic IDK
    	int xTheTriangle = xLoc - x;
    	int yTheTriangle = yLoc - y;
    	if ( xTheTriangle < -30 && (yTheTriangle > 15 || yTheTriangle < -15)){
    		
		//Go where life can exist
		//With the exception of areas where arctan is not useful
        	double angle = Math.atan((double) yTheTriangle/((double) xTheTriangle));
		
        	this.angle = angle + Math.PI;
        	
            realx += Math.cos(this.angle) * MISSILE_SPEED;
            realy += Math.sin(this.angle) * MISSILE_SPEED /2;
            x = (int) realx;
            y = (int) realy;
            
            
            //x -= 4;

    	} else {
    		move();
    	}
    	
    }
    

    public void move() {
        
    	realx -= MISSILE_SPEED;
    	x = (int) realx;
        if (x < BOARD_WIDTH) {
            vis = false;
        }
    }
    
    public Rectangle getBounds() {
    	
    	getImageDimensions();
        return new Rectangle(x, y, width, height);
        
        
    }

    
    
    
}