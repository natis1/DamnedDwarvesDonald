/**
 * Created by scc on 1/11/2016.
 */
public class ElvenTowerSprite extends ElvenSprite {
    private int type;


    ElvenTowerSprite (int x, int y, int type, String fileName){

        super(x, y, 0, 0, fileName);
        loadImage();

        this.x = x;
        this.y = y;
        this.type = type;





    }



}
