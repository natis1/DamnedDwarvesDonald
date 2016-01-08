import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Eli on 12/10/2015.
 */



public class ElvenCutscene1 extends JPanel implements MouseListener {

    private double myScale;


    private ElvenSprite bg;
    private ElvenSprite trump;




    private ElvenSprite advanceButton;

    private JFrame f;

    private int cutsceneProgress = 0;

    private String cutsceneString = "America, I am so happy that you finally elected me to lead \nthis once great " +
            "(until we elected 3/5 of a president) nation. \nAs president my first executive action is: \nKILL Bernie Sanders.\n\n" +
            "Any Sanders supporters will be taken to the gulag.\nAs well any European immigrants, serves dem commies right.";

    private String trumpFace = "main/resources/TrumpSmug.jpg";


    public ElvenCutscene1(double scale) {

        myScale = scale;
        initScene();

    }

    private void initScene() {


        addMouseListener(this);

        f = new JFrame("Damned Dwarves Donald");
        f.setSize(700, 700);

        ImageIcon startGameIcon = new ImageIcon("main/resources/ghost.png", "the ghost icon");

        advanceButton = new ElvenSprite(1080, 50, 0, 0, "main/resources/continueButton.png");
        advanceButton.loadImage();

        bg = new ElvenSprite(0, 0, 0, 0, "main/resources/cutsceneBackground.png");
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


        Font font = new Font("Monospaced", Font.BOLD, (int) (50.0 * myScale));
        g.setFont(font);
        g.setColor(Color.BLACK);
        drawString(g, cutsceneString, 28, 470);




    }

    //multiline strings

    private void drawString(Graphics g, String text, int x, int y) {
        for (String line : text.split("\n")) {
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
        }
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
                    cutsceneString = "My second executive action is to become America's dictator.\n" +
                            "I know you all are sheep and need a leader\nand I am the perfect person to lead.\n" +
                            "\n" +
                            "So that is what I am going to do.\nLead this great nation into oblivion. I mean glory.\n" +
                            "Under my regime, nobody will be sad\nthose who are will be shot.";
                    break;
                case 2:
                    trumpFace = "main/resources/TrumpThumbs.jpg";
                    cutsceneString = "If you are not, at the very least three quarters white.\n" +
                            "(and one quarter bald eagle), then you will be deported.\n" +
                            "\n" +
                            "Or taken as a slave in America's great concentration camps\n" +
                            "\n" +
                            "Or, as mentioned before, sent to the Gulag.";
                    break;
                case 3:
                    trumpFace = "main/resources/MatrixHacker.png";
                    cutsceneString = "Hey you. Wanna escape this hell hole?\n" +
                            "\n" +
                            "Well you are in luck.\nI have devised a plan to tear down the system.\n" +
                            "\n" +
                            "You are a greyhat, so it is either me or death.\n" +
                            "Deal?";
                    break;
                case 4:
                    trumpFace = "main/resources/MatrixHacker.png";
                    cutsceneString = "Good. My plan is simple, we are going to stage a coup.\n" +
                            "We need to secretly replace Donald Trump with a computer.\n" +
                            "Then, we can order America's Army to take out Trump.\n" +
                            "\n" +
                            "This will put us in control and set America free.\n" +
                            "\n" +
                            "Let's do it. Click next to go to the main menu.";
                    break;

                case 5:
                    ElvenMain.ElvenGameState = -1;
                    break;



                //testing

            }

            trump.image_file = trumpFace;
            trump.loadImage();
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
