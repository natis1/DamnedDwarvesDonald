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

    private String cutsceneString = "America, I am so happy that you\nfinally elected me to lead this nation.\nAs president my first executive action\nis to kill Bernie Sanders.";

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
        drawString(g, cutsceneString, 28, 440);




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
                    cutsceneString = "My second is to become\n" +
                            "America's dictator. I know\n" +
                            "you all want me to lead you\n" +
                            "sheep.";
                    break;
                case 2:
                    trumpFace = "main/resources/TrumpThumbs.jpg";
                    cutsceneString = "If you are not three quarters\n" +
                            "white, and one quarter bald eagle,\n" +
                            "you shall be deported or taken\n" +
                            "as a slave.";
                    break;
                case 3:
                    trumpFace = "main/resources/TrumpPoint.jpg";
                    cutsceneString = "Also I am banning encryption\n" +
                            "and the internet, and fun.\n" +
                            "If we have fun, the\n" +
                            "terrorists will win.";
                    break;
                case 4:
                    trumpFace = "main/resources/TrumpMad.jpg";
                    cutsceneString = "And anyone who does not\n" +
                            "commit at least 5 shootings\n" +
                            "a day will find themselves\n" +
                            "a victim of one.";
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
