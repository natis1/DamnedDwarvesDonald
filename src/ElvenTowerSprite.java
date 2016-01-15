import java.util.ArrayList;

/**
 * Created by scc on 1/11/2016.
 */
public class ElvenTowerSprite extends ElvenSprite {
    private int type;

    private ArrayList<ElvenMissile> bullets;


    ElvenTowerSprite (int x, int y, int type, String fileName){

        super(x, y, 0, 0, fileName);
        loadScaledImage(0.5, 0.5);
        this.type = type;





    }

    public ShootBullet() {

        bullets.add(new ElvenMissile(x + getWidth() / 2, y + getHeight() / 2, angle, "main/resources/faearrow.png"));

    }



}
