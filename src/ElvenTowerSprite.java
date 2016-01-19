import java.util.ArrayList;

/**
 * Created by scc on 1/11/2016.
 */
public class ElvenTowerSprite extends ElvenSprite {
    private int type;

    public ArrayList<ElvenMissile> bullets;
    public int missileTime;



    ElvenTowerSprite (int x, int y, int type, String fileName){

        super(x, y, 0, 600, fileName);
        bullets = new ArrayList<ElvenMissile>();

        loadScaledImage(0.5, 0.5);
        this.type = type;
        

    }


    public void ShootBullet(int xTarget, int yTarget) {

        bullets.add(new ElvenMissile(x + getWidth() / 2, y + getHeight() / 2, angle, "main/resources/faearrow.png", xTarget, yTarget));

    }



}
