//package com.zetcode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

//import java.awt.Image;


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
    private ArrayList<ElvenEnemy> Elvenenemies;
    public int spawnTimer = 0;
    
    private String bgImageString;


    public Random universalRandomNumberGenerator;

                
    private ElvenBackgroundSprite backgroundSprite1;
    private ElvenBackgroundSprite backgroundSprite2;
    
    
    //3- Draw all, 2- No useless sprites, 1- No moving background, 0- TBD when we need more GPU capabilities.
    private int graphicsQuality = 3;
    
    
    private int frameCatchup = 0;
    
    
    
    public ElvenBoard(double scaler, int monitorHZ) {
        universalScaler = scaler;
        computerHZ = monitorHZ;
        //computerHZ = 300;
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


        //Draw stuff here







        
        
        
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

            if (graphicsQuality > 2){
                updateParticles();
            }

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


        //TODO ADD ACTUAL PARTICLES


    }
    
    
    
    

    private class TAdapter extends KeyAdapter {

        //TODO KEYBOARD SUPPORT

    }
}