import java.awt.*;
//Act with wisdom, but act.






//Hel yes, this code looks awesome. I cannot even explain why.

public class ElvenTowerPlaceable extends ElvenSprite {






    public ElvenTowerPlaceable(int x, int y) {


        super(x, y, 0, 0, "main/resources/towers/TowerPlaceable.png");
        loadImage();



        //Like normal sprite but with getBounds
    }





    public Rectangle getBounds() {

        getImageDimensions();
        return new Rectangle(x, y, width, height);
    }



}




