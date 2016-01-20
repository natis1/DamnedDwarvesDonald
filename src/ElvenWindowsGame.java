//package com.zetcode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

//import java.awt.Image;


public class ElvenWindowsGame extends JPanel implements MouseListener {

    //private KeyboardAnimation animation2


    private double universalScaler; // This determines the resolution relative to 1080p

    private double now; // Used in drawing the game
    private int gameHZ; // Used to determine game framerate
    private boolean is4K; // Possibly used later to determine to load high or low quality images
    private double lastUpdateTime;

    private int waveSize = 30; // Testing number of enemies
    private int spawnEnemy = 50; // When to respawn


    private ArrayList<ElvenTowerPlaceable> TowerLocations;
    private ArrayList<ElvenEnemy> ElvenEnemies;

    private ArrayList<ElvenTowerSprite> Towers;

    public static double speedMultiplier;
    double TIME_BETWEEN_UPDATES;


    private ElvenSprite backgroundSprite;


    private int towerSelected = -1;


    private ElvenSprite tower0;


    //3- Draw all, 2- No useless sprites, 1- No moving background, 0- TBD when we need more GPU capabilities.


    //I Think this feature is kind of pointless in a 2D game but I included it anyway. If your game stutters
    //for too long it will automatically reduce the graphics until the stuttering stops.
    private int graphicsQuality = 3;

    private int frameCatchup = 0;


    /**

     By default this class is simply used to record the framerate and the relative resolution
     compared to 60 and 1080p respectively. It





     */

    public ElvenWindowsGame(double scaler, int monitorHZ) {
        universalScaler = scaler;


        gameHZ = monitorHZ;

        //gameHZ = 10;


        TIME_BETWEEN_UPDATES = 1000000000 / gameHZ;



        //designed for 60, compensates for everything else.

        //100% working on every multiple of 60, everything else should work on other numbers as ints are rarely used in favor of floats.
        speedMultiplier = (double) (60) / (double) gameHZ;


        initBoard();


    }

    private void initBoard() {

        addMouseListener(this);

        String bgImageString;
        if (universalScaler <= 1.0001){
            bgImageString = "main/resources/WindowsTD.png";
            is4K = false;
        } else {
            bgImageString = "main/resources/WindowsTD.png";
            is4K = true; //maybe if I ever use 4k images for other stuff,
        }

        //Draw the background sprite. This is used as an example of how to use g2d and sprite

        backgroundSprite = new ElvenSprite(0, 0, 0, 0, bgImageString);
        backgroundSprite.loadImage();
        tower0 = new ElvenSprite(1445, 0, 0, 0, "main/resources/towers/torrenter5.png");
        tower0.loadImage();


        ElvenEnemies = new ArrayList<ElvenEnemy>();
        Towers = new ArrayList<ElvenTowerSprite>();
        TowerLocations = new ArrayList<ElvenTowerPlaceable>();
        //Reds
        TowerLocations.add(new ElvenTowerPlaceable(252, 340));
        TowerLocations.add(new ElvenTowerPlaceable(539, 302));
        TowerLocations.add(new ElvenTowerPlaceable(393, 327));
        TowerLocations.add(new ElvenTowerPlaceable(549, 560));
        TowerLocations.add(new ElvenTowerPlaceable(540, 428));
        TowerLocations.add(new ElvenTowerPlaceable(394, 326));
        TowerLocations.add(new ElvenTowerPlaceable(253, 461));

        //Greens
        TowerLocations.add(new ElvenTowerPlaceable(888, 286));


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



     In a fork of this project you actually CAN do this, but it is so interdependent that you remove any
     one class and you need to edit over 50 lines.


     In these versions: Donald, Deux, and Codename: Hitler Trampoline Hero the graphics function looks like below:



     */
    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        g2d.scale(universalScaler, universalScaler);


        //Draw stuff here
        g2d.drawImage(backgroundSprite.getImage(), backgroundSprite.getX(),
                backgroundSprite.getY(), this);

        g2d.drawImage(tower0.getImage(), tower0.getX(), tower0.getY(), this);

        for (ElvenTowerPlaceable PotentialTowerLocation : TowerLocations) {

            g2d.drawImage(PotentialTowerLocation.getImage(),
                    PotentialTowerLocation.getX(), PotentialTowerLocation.getY(), this);
        }

        for (ElvenTowerSprite TowerLocation : Towers) {

            g2d.drawImage(TowerLocation.getImage(), TowerLocation.getX(), TowerLocation.getY(), this);

            for (ElvenMissile Missile : TowerLocation.bullets) {
                g2d.drawImage(Missile.getImage(), Missile.getX(), Missile.getY(), this);
            }

        }


        for (ElvenEnemy e : ElvenEnemies) {
            g2d.drawImage(e.getImage(), e.getX(), e.getY(), this);
        }






        if (towerSelected > 0){


            Point towerHere = MouseInfo.getPointerInfo().getLocation();

            towerHere.x -= 29;
            towerHere.y -= 73;
            towerHere.x /= universalScaler; towerHere.y /= universalScaler;



            switch (towerSelected) {
                case 1:


                    ElvenSprite towerUsed = new ElvenSprite((int)towerHere.getX(), (int)towerHere.getY(),
                            0, 0, "main/resources/towers/torrenter5.png");
                    towerUsed.loadScaledImage(0.3);

                    g2d.drawImage(towerUsed.getImage(), towerUsed.getX(), towerUsed.getY(), this);
                    break;



            }

        }








    }

    //Starts a new thread and runs the game loop in it.

    //This thread is separate so I can do my best to keep the game running at a stable framerate
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

        //The lower this is the more the game will use slowdown instead of stuttering.
        final int MAX_UPDATES_BEFORE_RENDER = 100;

        //Get the current render and update time to the nanosecond. IDK why last update time is previously initialized.
        lastUpdateTime = System.nanoTime();
        double lastRenderTime = System.nanoTime();

        //If we are able to get as high as this FPS, don't render again.
        final double TARGET_FPS = gameHZ;
        final double TARGET_TIME_BETWEEN_RENDERS = 1000000000 / TARGET_FPS;

        //Simple way of finding FPS.
        int lastSecondTime = (int) (lastUpdateTime / 1000000000);

        while (true)
        {
            now = System.nanoTime();
            int updateCount = 0;


            //Do as many game updates as we need to, potentially playing catchup.
            while(now - lastUpdateTime > TIME_BETWEEN_UPDATES && updateCount < MAX_UPDATES_BEFORE_RENDER)
            {
                Update();
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
                //You can remove this line and it will still work (better), your CPU just climbs on certain OSes. Or something, IDK.
                //This is what the stack overflow comment said but I am not sure it is entirely accurate. It seems to run fine on Manjaro.
                try {Thread.sleep(1);} catch(Exception e) {}

                now = System.nanoTime();
            }
        }
    }


    private void DrawGame(float interpolation)
    {

        repaint();


    }


    private void UpdateTowers() {
        for (ElvenTowerSprite Tower : Towers) {
            Tower.bullets.forEach(ElvenMissile::moveToTarget);

            if (Tower.missileTime < 1) {
                for (int i = 0; i < ElvenEnemies.size(); i++) {

                    ElvenEnemy Enemy = ElvenEnemies.get(i);

                    if (Tower.GetCircularRectangle().intersects((Enemy.GetCircularRectangle()))) {
                        Tower.ShootBullet(Enemy.getX(), Enemy.getY(), i);
                        Tower.setAngle(Tower.bullets.get((Tower.bullets.size() - 1)).angle);

                        break;
                    }
                }
                Tower.missileTime = 50;
            } else {

                for (int i = 0; i < Tower.bullets.size(); i++) {
                    ElvenMissile Bullet = Tower.bullets.get(i);

                    if (Bullet.GetCircularRectangle().contains(Bullet.GetTarget())) {

                        if (ElvenEnemies.size() > 0) {
                            ElvenEnemies.get(Bullet.enemyTarget).hit();
                        }
                        Tower.bullets.remove(i);

                    }


                }

                Tower.missileTime--;
            }


        }


    }

    public void UpdateEnemies() {

        ElvenEnemies.forEach(ElvenEnemy::move);


        if (spawnEnemy == 0 && waveSize > 0) {
            ElvenEnemies.add(new ElvenEnemy(1000));
            //TODO Randomly generate enemies


            waveSize--;
            spawnEnemy = 100;
        } else {
            spawnEnemy--;
        }

        for (int i = 0; i < ElvenEnemies.size(); i++) {

            ElvenEnemy Enemy = ElvenEnemies.get(i);

            if (!Enemy.isVisible()) {
                ElvenEnemies.remove(i);
            }
        }




    }

    public void Update() {

        UpdateEnemies();
        UpdateTowers();



        if (graphicsQuality > 2){
            updateParticles();
        }

        float interpolation = Math.min(1.0f, (float) ((now - lastUpdateTime) / TIME_BETWEEN_UPDATES) );
        DrawGame(interpolation);


        //NOT DONE HERE ANYMORE
        //repaint();
    }

    private void updateParticles() {


        //TODO ADD ACTUAL PARTICLES


    }



    @Override
    public void mouseClicked(MouseEvent me) {





    }




    //no need for all this
    @Override
    public void mousePressed(MouseEvent me) {

        System.out.println(me.getX() / universalScaler + ", " + me.getY() / universalScaler);

        if ((me.getX() / universalScaler) > tower0.getX() && (me.getX() / universalScaler) < (tower0.getWidth() + tower0.getX())
                && (me.getY() / universalScaler > tower0.getY()) && (me.getY() / universalScaler) < (tower0.getY() + tower0.getHeight()) ){
            towerSelected = 1;

            System.out.println("clicked tower " + towerSelected);


        }


    }

    @Override
    public void mouseReleased(MouseEvent me) {

        if (me.getX() / universalScaler < 1440){


            //TODO place tower.


            Point myLoc = me.getPoint();
            myLoc.x /= universalScaler;
            myLoc.y /= universalScaler;


            switch (towerSelected) {
                case 1:
                    for (int i = 0; i < TowerLocations.size(); i++) {

                        ElvenTowerPlaceable PotentialTowerLocation = TowerLocations.get(i);
                        if (PotentialTowerLocation.getBounds().contains(myLoc)) {

                            //TODO call the tower sprite in the elventower sprite


                            Towers.add(new ElvenTowerSprite(PotentialTowerLocation.getX(),
                                    PotentialTowerLocation.getY(), towerSelected, "main/resources/towers/torrenter.png"));

                            TowerLocations.remove(i);
                            break;

                        }
                    }
                    break;


            }

            towerSelected = 0;


        }





    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }




}