import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by scc on 12/10/2015.
 */



public class ElvenCutscene1 extends JPanel implements ActionListener {
    private int resolutionX;
    private int resolutionY;
    private double myScale;

    private JButton advanceButton;

    private JFrame f;

    private int cutsceneProgress = 0;

    private String cutsceneString;


    public ElvenCutscene1(int resX, int resY, double scale) {
        resolutionX = resX;
        resolutionY = resY;
        myScale = scale;
        initScene();

    }

    private void initScene() {

        f = new JFrame("Damned Dwarves Donald");
        f.setSize(resolutionX, resolutionY);

        ImageIcon startGameIcon = new ImageIcon("main/resources/ghost.png", "the ghost icon");

        advanceButton = new JButton("Next", startGameIcon);
        advanceButton.setVerticalTextPosition(AbstractButton.BOTTOM);
        advanceButton.setHorizontalTextPosition(AbstractButton.CENTER);
        advanceButton.addActionListener(this);
        advanceButton.setSize(100, 100);
        advanceButton.setMaximumSize(new Dimension(100,100));
        advanceButton.setLocation(150, 150);
        advanceButton.setVisible(true);
        f.add(advanceButton);




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


    }

    @Override
    public void actionPerformed(ActionEvent ae) {

    }
}
