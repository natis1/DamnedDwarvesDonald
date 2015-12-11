import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by scc on 12/10/2015.
 */



public class ElvenCutscene1 extends JPanel implements MouseListener {

    private double myScale;


    private ElvenBackgroundSprite bg;
    private ElvenSprite trump;

    private ElvenSprite advanceButton;

    private JFrame f;

    private int cutsceneProgress = 0;

    private String cutsceneString = "I am a test message (1). This will appear on a cutscene or something, whatever";

    private String trumpFace = "main/resources/TrumpSmug.jpg";


    public ElvenCutscene1(double scale) {

        myScale = scale;
        initScene();

    }

    private void initScene() {

        f = new JFrame("Damned Dwarves Donald");
        f.setSize(700, 700);

        ImageIcon startGameIcon = new ImageIcon("main/resources/ghost.png", "the ghost icon");

        advanceButton = new ElvenSprite(1500, 100, 0, 0, "main/resources/continueButton.png");
        advanceButton.loadImage();

        bg = new ElvenBackgroundSprite("main/resources/cutsceneBackground.png");
        bg.loadImage();


        trump = new ElvenSprite(150, 50, 0, 0, trumpFace);
        trump.loadImage();



    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);

        Toolkit.getDefaultToolkit().sync();
    }


    /**
     * This function draws all of the sprites using graphics2D libraries
     * All drawing must be called from the board
     * You cannot call doDrawing from other classes, to add a sprite to
     * the drawing queue, create the class inside the board.
     * <p>
     * Yes I know it is an oversite, whatever.
     */
    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.scale(myScale, myScale);





        g2d.drawImage(bg.getImage(), bg.getX(), bg.getY(), this);
        g2d.drawImage(trump.getImage(), trump.getX(), trump.getY(), this);
        g2d.drawImage(advanceButton.getImage(), advanceButton.getX(), advanceButton.getY(), this);


        Font font = new Font("Serif", Font.BOLD, 70);
        g.setFont(font);
        g.setColor(Color.BLACK);
        g.drawString(cutsceneString, 25, 560);




    }



    @Override
    public void mouseClicked(MouseEvent me) {




    }




    //no need for all this
    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent me) {

        System.out.println(me.getX());
        System.out.println(me.getY());

        if (me.getX()/myScale > advanceButton.getX() && me.getX()/myScale < (advanceButton.getX() + advanceButton.getWidth())
                && me.getY()/myScale > advanceButton.getY() && me.getY()/myScale < (advanceButton.getY() + advanceButton.getHeight())){

            cutsceneProgress++;

            switch (cutsceneProgress) {
                case 1:
                    trumpFace = "main/resources/TrumpMad.jpg";
                    cutsceneString = "You will die hacker";
                    break;
                //testing

            }
            repaint(); // ok redraw stuff

        }



    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
