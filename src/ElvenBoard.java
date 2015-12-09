//package com.zetcode;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
//import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;


public class ElvenBoard extends JPanel {
	
	//private KeyboardAnimation animation2

    private final int ICRAFT_X = 200;
    private final int ICRAFT_Y = 500;
    
    private double universalScaler;
    
    private int health = 100;
    
    private int frameCount = 0;
    private double now;
    private int computerHZ;
    private boolean is4K;
    private int lastSecondTime;
    private double lastUpdateTime;
    
    public static double speedMultiplier;
    
    private boolean didCreateTheMainMenu = false;
    double TIME_BETWEEN_UPDATES;
    
    private int souls = 0;
    private int totalSouls = ElvenMain.ElvenSoulsStolen;
    
    public boolean isGameOver = false;
    private int score = 0;
    
    private final int difficultyLevel = ElvenMain.ElvenGameDifficulty;

    //This caps the game framerate, which means that the game doesn't use delta-T
    //For calculating movement. I think this is fine if we set the cap at something like 144hz (~7)
    //Smooth enough for most monitors even if eyes can still see past it.


    //Hopefully all monitors support G-sync in the future :)
    //private int DELAY = 10;
    //private Timer timer;
    private ElvenCraft craft;
    private ArrayList<ElvenEnemy> Elvenenemies;
    public int spawnTimer = 0;
    
    private String bgImageString;


    public Random universalRandomNumberGenerator;

                
    private ElvenGameOverScreen myGameOverScreen;
    private ElvenBackgroundSprite backgroundSprite1;
    private ElvenBackgroundSprite backgroundSprite2;
    
    
    //3- Draw all, 2- No useless sprites, 1- No moving background, 0- TBD when we need more GPU capabilities.
    private int graphicsQuality = 3;
    
    
    private int frameCatchup = 0;
    
    
    
    public ElvenBoard(double scaler, int monitorHZ) {
    	universalScaler = scaler;
    	computerHZ = monitorHZ;
		computerHZ = 300;
    	TIME_BETWEEN_UPDATES = 1000000000 / computerHZ;
    	//testing only
    	speedMultiplier = (double) (60) / (double) computerHZ; //designed for 60, compensates for everything else.
    	
    	//100% working on every multiple of 60, everything exept background works perfectly on any other number.
        initBoard();
        
        
    }

    private void initBoard() {



    	
    	
    	if (universalScaler <= 1.0001){
    		bgImageString = "main/resources/background2.png";
    		is4K = false;
    	} else {
    		bgImageString = "main/resources/background4.png";
    		is4K = true; //maybe if I ever use 4k images for other stuff,
    	}
    	
    	
    	myGameOverScreen = new ElvenGameOverScreen();
    	
    	Elvenenemies = new ArrayList<ElvenEnemy>();
    	
    	backgroundSprite1 = new ElvenBackgroundSprite(0, bgImageString);
    	backgroundSprite2 = new ElvenBackgroundSprite(4000 , bgImageString);
    	
    	//ElvenBackgroundSprites
    	
        //Real Seed means completely deterministic. No Entropy. I hate Entropy But all difficulty levels are different
    	universalRandomNumberGenerator = new Random(2048 + difficultyLevel);
    	
        addKeyListener(new TAdapter());
        setFocusable(true);
        
        
        
        setDoubleBuffered(true);
        
        double angle = 0;
        //String picName = "Ghost.gif";
        

        craft = new ElvenCraft(ICRAFT_X, ICRAFT_Y, angle, (int)(totalSouls / 10));
        
        runGameLoop();

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);

        Toolkit.getDefaultToolkit().sync();
    }


/** This function draws all of the sprites using graphics2D libraries
All drawing must be called from the board
You cannot call doDrawing from other classes, to add a sprite to
the drawing queue, create the class inside the board.

Yes I know it is an oversite, whatever.

*/
    private void doDrawing(Graphics g) {
    	
    	Graphics2D g2d = (Graphics2D) g;
    	
    	if (!isGameOver){
    		
    		
    		g2d.scale(universalScaler, universalScaler);
    		
    		//Draw BG
        	g2d.drawImage(backgroundSprite1.getImage(), backgroundSprite1.getX(),
        			backgroundSprite1.getY(), this);
        	
        	g2d.drawImage(backgroundSprite2.getImage(), backgroundSprite2.getX(),
        			backgroundSprite2.getY(), this);
        	
        	
        	//Draw player health
        	Font font = new Font("Serif", Font.BOLD, 15);
            g.setFont(font);
            g.setColor(Color.YELLOW);
        	g.drawString((Integer.toString(health)), 25, 25);
        	
        	
        	//Font font = new Font("Serif", Font.BOLD, 15);
        	//font already set
            g.setFont(font);
            g.setColor(Color.BLUE);
        	g.drawString((Integer.toString(craft.craftShields)), 70, 25);
        	
        	
            g.setFont(font);
            g.setColor(Color.GREEN);
        	g.drawString((Integer.toString(score)), 25, 50);
        	
        	
        	g.setFont(font);
            g.setColor(Color.RED);
        	g.drawString((Integer.toString(souls)) + " / " + (Integer.toString(totalSouls)), 25, 100);
        	
            
            g2d.drawImage(craft.getImage(), craft.getX(),
                    craft.getY(), this);

            ArrayList<ElvenMissile> ms = craft.getMissiles();
            
            ArrayList<ElvenEnemy> es = Elvenenemies;
            
            
            
            g2d.drawImage(craft.pixieHelper.getImage(), craft.pixieHelper.getX(),
            		craft.pixieHelper.getY(), this);
            
            
            
            
            if (health <= 0){
            	
            	
            	isGameOver = true;
            }
            
            

            for (Object m1 : ms) {
                ElvenMissile m = (ElvenMissile) m1;
            	if (m.vis){
                    g2d.drawImage(m.getImage(), m.getX(),
                            m.getY(), this);
            	}

            }
            
            ms = craft.pixieHelper.getMissiles();
            
            
            
            for (Object m1 : ms) {
                ElvenMissile m = (ElvenMissile) m1;
                
                if (m.vis){
                    g2d.drawImage(m.getImage(), m.getX(),
                            m.getY(), this);
                }
            }
            
            for (Object e1 : es){
            	ElvenEnemy e = (ElvenEnemy) e1;
            	if (e.vis){
                	g2d.drawImage(e.getImage(), e.getX(),
                			e.getY(), this);
            	}

            	
            	ArrayList<ElvenEnemyMissile> eem = e.elvenEnemyMissiles;
            	
            	
            	//Draw all missiles
            	for (Object ee1 : eem){
            		ElvenEnemyMissile em = (ElvenEnemyMissile) ee1;
            		if (em.vis){
                		g2d.drawImage(em.getImage(), em.getX(),
                    			em.getY(), this);
            		}

            	}
            	
            	
            	
            }
            
            //Draw particles last
            
            if (graphicsQuality > 2){
            	ArrayList<ElvenSpriteParticle> elvenSprites = craft.getSpriteParticles();
                for (Object es1 : elvenSprites) {
                    ElvenSpriteParticle myElvenCraftSprite = (ElvenSpriteParticle) es1;
                    
                    if (myElvenCraftSprite.vis){
                    	int alphaTy = AlphaComposite.SRC_OVER;
                        AlphaComposite alco = AlphaComposite.getInstance(alphaTy, myElvenCraftSprite.myAlpha);
                    	
                    	g2d.setComposite(alco);
                        g2d.drawImage(myElvenCraftSprite.getImage(), myElvenCraftSprite.getX(),
                        		myElvenCraftSprite.getY(), this);
                    }
                }
                
                int alphaType = AlphaComposite.SRC_OVER;
                AlphaComposite alcom = AlphaComposite.getInstance(alphaType);
                if (craft.craftShields < 100){
                	alcom = AlphaComposite.getInstance(alphaType, (float) ( (double) (craft.craftShields) / 100));
                }
                //draw alpha for these
                g2d.setComposite(alcom);
                g2d.drawImage(craft.craftShieldsDrawing.getImage(), craft.craftShieldsDrawing.getX(),
                		craft.craftShieldsDrawing.getY(), this);
            }
            
            
            
            
            
            
            
    	} else {
    		//Draw game over screen
    		g2d.drawImage(myGameOverScreen.getImage(), myGameOverScreen.getX(),
        			myGameOverScreen.getY(), this);
    		
    		
    		Font font = new Font("Courier New", Font.BOLD, 60);
            g.setFont(font);
            g.setColor(Color.BLUE);
        	g.drawString((Integer.toString(score)), 10, 690);
        	if (!didCreateTheMainMenu){
        		
        		didCreateTheMainMenu = true;
        		
        		try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					// RIP YOUR COMPUTER
					
				}
        		
        		ElvenMain.ElvenGameState = score + 1000;
    	    	ElvenMain.ElvenSoulsStolen = souls;
    	    	
        		
        	}
        	
    	}
    	
    	
    	
        
        
        
    }

    public void checkCollisions() {

        Rectangle r3 = craft.getBounds();

        for (ElvenEnemy ee : Elvenenemies) {
        	
        	ArrayList<ElvenEnemyMissile> eem = ee.elvenEnemyMissiles;
        	for (Object ee1 : eem){
        		ElvenEnemyMissile em = (ElvenEnemyMissile) ee1;
        		Rectangle r4 = em.getBounds();
        		if (r3.intersects(r4)){
        			if (craft.craftShields > (2 + difficultyLevel / 2)){
                		craft.craftShields -= (3 + difficultyLevel / 2);
                	} else if (craft.craftShields > 0) {
                		health -= ((3 + difficultyLevel / 2)-craft.craftShields);
                		craft.craftShields = 0;
                	} else {
                		health -= (3 + difficultyLevel / 2);
                	}
        			
        			em.setVisible(false);
        		}
        		
        		
        	}
        	
        	
            Rectangle r2 = ee.getBounds();

            if (r3.intersects(r2)) {
            	
            	if (ee.vis){
            		int damage;
            		damage = 0;
            		
            		switch (ee.elvenEnemyType) {
                    case 0: damage = 15 + difficultyLevel;
                    break;
                	case 1: damage = 30 + 3 * difficultyLevel;
                	break;
                    case 2: damage = 50 + 5 * difficultyLevel;
                    break;
                    case 3: damage = 50 + 5 * difficultyLevel;
                    break;
                    case 4: damage = 15 + difficultyLevel;
                    break;
                    case 5: damage = 30 + 3 * difficultyLevel;
                    break;
                    case 6: damage = 50 + 5 * difficultyLevel;
                    break;
                    case 7: damage = 30 + 3 * difficultyLevel;
                    break;
                    case 8: damage = 50 + 5 * difficultyLevel;
                    break;
                }
            		if (craft.craftShields > (damage - 1)){
                		craft.craftShields -= damage;
                	} else if (craft.craftShields > 0) {
                		health -= (damage-craft.craftShields);
                		craft.craftShields = 0;
                	} else {
                		health -= damage;
                	}
            		
            		
            		
                    	
                	
                	
            	} else {
            		//Your revenge will continue, Kalista.
            	}
            	
            	

            	
            	
            	ee.vis = false;
            	
            }
        }


        
        
        
        
        ArrayList<ElvenMissile> ms = craft.getMissiles();

        for (ElvenMissile m : ms) {

            Rectangle r1 = m.getBounds();

            for (ElvenEnemy ee : Elvenenemies) {

            	if (ee.vis){
            		Rectangle r2 = ee.getBounds();

                    if (r1.intersects(r2)) {
                    	
                        m.setVisible(false);
                        score += ee.hit();
                        
                        
                        //score++;
                        
                    }
            	} else {
            		//You are safe dear voidling. For now at least.
            	}
                
            }
        }
        
        ms = craft.pixieHelper.getMissiles();

        for (ElvenMissile m : ms) {

            Rectangle r1 = m.getBounds();

            for (ElvenEnemy ee : Elvenenemies) {

                Rectangle r2 = ee.getBounds();

                if (r1.intersects(r2)) {
                	
                	if (ee.vis){
                        m.setVisible(false);
                        
                        int tempScore = ee.hit();
                        score += tempScore;
                        if (tempScore > 10){
                        	souls += tempScore / 50;
                        	totalSouls += tempScore / 50;
                        	health += tempScore / 50;
                        }
                	} else {
                		//The fae cannot find its target. Let's kill someone else instead.
                	}

                }
            }
        }
        
        
    }
    
	//Starts a new thread and runs the game loop in it.
	public void runGameLoop()
	{
		Thread loop = new Thread()
		{
			public void run()
			{
				gameLoop();
			}
		};
		loop.start();
	}
    
	public void gameLoop() 
	{
		//This value would probably be stored elsewhere.
		
		
		//Always get lots of hz even if it isn't possible to render this many frames
		
		//Calculate how many ns each frame should take for our target game hertz.
		
		//At the very most we will update the game this many times before a new render.
		//If you're worried about visual hitches more than perfect timing, set this to 1.
		final int MAX_UPDATES_BEFORE_RENDER = 100;
		//We will need the last update time.
		lastUpdateTime = System.nanoTime();
		//Store the last time we rendered.
		double lastRenderTime = System.nanoTime();

		//If we are able to get as high as this FPS, don't render again.
		final double TARGET_FPS = computerHZ;
		final double TARGET_TIME_BETWEEN_RENDERS = 1000000000 / TARGET_FPS;

		//Simple way of finding FPS.
		lastSecondTime = (int) (lastUpdateTime / 1000000000);

		while (true)
		{
			now = System.nanoTime();
			int updateCount = 0;

			
				//Do as many game updates as we need to, potentially playing catchup.
				while(now - lastUpdateTime > TIME_BETWEEN_UPDATES && updateCount < MAX_UPDATES_BEFORE_RENDER)
				{
					update();
					lastUpdateTime += TIME_BETWEEN_UPDATES;
					updateCount++;
					if (updateCount > 15 && graphicsQuality > 2){
						graphicsQuality--;
						//updateCount = 10;
						frameCatchup = (int) (300 / speedMultiplier);
					}
					
				}

				//If for some reason an update takes forever, we don't want to do an insane number of catchups.
				//If you were doing some sort of game that needed to keep EXACT time, you would get rid of this.
				if ( now - lastUpdateTime > TIME_BETWEEN_UPDATES)
				{
					
					
					
					
					lastUpdateTime = now - TIME_BETWEEN_UPDATES;
				}

				//Render. To do so, we need to calculate interpolation for a smooth render.
				
				lastRenderTime = now;

				//Update the frames we got.
				int thisSecond = (int) (lastUpdateTime / 1000000000);
				if (thisSecond > lastSecondTime)
				{
					//TODO System.out.println("Main: NEW SECOND " + thisSecond + " " + frame_count);
					
					
					
					
					lastSecondTime = thisSecond;
				}
				

				//Yield until it has been at least the target time between renders. This saves the CPU from hogging.
				while ( now - lastRenderTime < TARGET_TIME_BETWEEN_RENDERS)
				{
					
					if (graphicsQuality < 3){
						frameCatchup--;
					}
					if (frameCatchup < 0){
						graphicsQuality = 3; // Good job team, you did it.
					}
					
					Thread.yield();

					//This stops the app from consuming all your CPU. It makes this slightly less accurate, but is worth it.
					//You can remove this line and it will still work (better), your CPU just climbs on certain OSes.
					//FYI on some OS's this can cause pretty bad stuttering. Scroll down and have a look at different peoples' solutions to this.
					try {Thread.sleep(1);} catch(Exception e) {} 

					now = System.nanoTime();
				}
			}
		}
	

	private void drawGame(float interpolation)
	{
		
		repaint();
		frameCount++;
	}
	
	
    
    public void update() {
    	
    	if (!isGameOver){
    		
    		
    		
    		
    		
    		updateMissiles();
        	updateEnemyMissiles();
        	updateCraft();
        	if (graphicsQuality > 2){
        		updateParticles();
        	}
        	
        	checkCollisions();
        	
        	float interpolation = Math.min(1.0f, (float) ((now - lastUpdateTime) / TIME_BETWEEN_UPDATES) );
			drawGame(interpolation);
    	} else {
    		float interpolation = Math.min(1.0f, (float) ((now - lastUpdateTime) / TIME_BETWEEN_UPDATES) );
			drawGame(interpolation);
    	}
    	
    	//NOT DONE HERE ANYMORE
        //repaint();
    }
    
    private void updateParticles() {
    	
    	ArrayList<ElvenSpriteParticle> es = craft.getSpriteParticles();
        
        for (int i = 0; i < es.size(); i++) {
        	
        	//Move all the particles from the craft
        	ElvenSpriteParticle e = (ElvenSpriteParticle) es.get(i);
            e.smartMoveToLocation();  
            if (!e.isVisible()) {
            	es.remove(i);
            }
            
            
        }
    	
		
	}

	private void updateEnemyMissiles() {
    	
    	for (ElvenEnemy ee : Elvenenemies) {
    		
        	
            	ArrayList<ElvenEnemyMissile> eem = ee.elvenEnemyMissiles;
            	for (Object ee1 : eem){
            		

            		
            		ElvenEnemyMissile em = (ElvenEnemyMissile) ee1;
            		
                		if (ee.elvenEnemyType != 1){
                			em.move();
                		} else {
                			em.moveToLocation(craft.x, craft.y);
                		}
                    
            		

            		
            		
            	}
        	}

    	
    	
    }
    

    private void updateMissiles() {
    	
    	
    	//Where stuff dies
        ArrayList<ElvenEnemy> es = Elvenenemies;
        
        for (int i = 0; i < es.size(); i++) {
            ElvenEnemy e = (ElvenEnemy) es.get(i);
            e.move();
            
            
            if (!e.isVisible()) {
            	e.missileGarbageCollector--;
            	if (e.missileGarbageCollector <= 0){
            		es.remove(i);
            	}
            }
            
        }
        ArrayList<ElvenMissile> ms = craft.getMissiles();
        
        
        for (int i = 0; i < ms.size(); i++) {

            ElvenMissile m = (ElvenMissile) ms.get(i);

            if (m.isVisible()) {

                m.move();
            } else {

                ms.remove(i);
            }
        }
        ms = craft.pixieHelper.getMissiles();
        
        
        boolean enemiesOnScreen = false;
        
        for (int i = 0; i < ms.size(); i++) {
        	
            ElvenMissile m = (ElvenMissile) ms.get(i);
            if (m.isVisible()) {
            	
            	for (int x = 0; x < es.size(); x++) {
                    ElvenEnemy e = (ElvenEnemy) es.get(x);
                    
                    if (e.vis && e != null && e.x > craft.pixieHelper.x + 150 && e.elvenEnemyType != 2) {
                    	
                    	//Heat seeking missiles
                    	m.moveToLocation(e.x + e.width/2, e.y + e.height/2);
                    	enemiesOnScreen = true;
                    	break;
                    }
            	
                
            } if (!enemiesOnScreen){
            	m.move();
            }
        } else {
        		
        		ms.remove(i);
        	}
            
        }
        
    }
    
    

    private void updateCraft() {
    	
    	if (graphicsQuality > 1){
    		backgroundSprite1.move();
        	backgroundSprite2.move();
    	}
    	
    	
        	spawnTimer++;
        	if (spawnTimer > (60 - difficultyLevel)/(speedMultiplier) && spawnTimer > 10/(speedMultiplier)){
        		
        		spawnTimer = 0;
                    long elvenActionToTake = universalRandomNumberGenerator.nextLong();
                    
                    
        		
                    //All the enemy code is in the enemy class, where it BELONGS.
        		Elvenenemies.add(new ElvenEnemy(elvenActionToTake, universalScaler));
        	}
        	
        	
        	craft.isPressed();
            craft.move();
            //craft.moveEnemy();
    	

    }
    
    
    
    
    

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            craft.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            craft.keyPressed(e);
        }
    }
}