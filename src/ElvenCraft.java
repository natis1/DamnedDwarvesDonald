import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class ElvenCraft extends ElvenSprite {

	private ElvenKeyManager keySetter = new ElvenKeyManager();


	private float dx;
	private float dy;
	public float accelx;
	public float accely;

	private int shieldRegenTimer;
	public int craftShields;
	private int maxCraftShields;


	private double speedMultiplier = ElvenBoard.speedMultiplier;
	
	//More speed = more friction to make it easier to control.
	private double friction = ( (int) ElvenMain.ElvenUpgradeMovement * ElvenBoard.speedMultiplier) * 0.01;
	
	ElvenPixAlly pixieHelper;

	private int delayShot = 15 - ElvenMain.ElvenUpgradeFirerate;
	
	//Makes pix more important
	private int delayGhostShot = 15;

	public float realx;
	public float realy;

	private double universalScaler;

	private Random craftRNG;

	private int generateParticles = 30;
	private int maxGenerateParticles = (int) ((30 - ElvenMain.ElvenUpgradeMovement)/ ElvenBoard.speedMultiplier);
	private float particleSpeed = (float) (( (1 + (float) ElvenMain.ElvenUpgradeMovement / 5)) * ElvenBoard.speedMultiplier);


	private ArrayList<ElvenMissile> Elvenmissiles;
	private ArrayList<ElvenSpriteParticle> ElvenSpriteParticles;	
	public ElvenSpriteParticle craftShieldsDrawing;



	public ElvenCraft(int x, int y, double angle, int cs) {


		super(x, y, 0, 20, "main/resources/Ghost.gif");

		maxCraftShields = cs;

		universalScaler = (1 + .1 * ElvenMain.ElvenUpgradeMovement) * speedMultiplier;
		if (universalScaler > (3.0 * speedMultiplier)){
			universalScaler = 3.0 * speedMultiplier;
		}

		initCraft();
	}




	private void initCraft() {
		
		if (delayGhostShot > 15){
			delayGhostShot = 15;
		}
		

		pixieHelper = new ElvenPixAlly(x - 30, y + 80, angle, "main/resources/elvenFae.png");


		//Init arrays
		craftShieldsDrawing = new ElvenSpriteParticle(x, y, 4, 0, "main/resources/elvenForceField.png");
		ElvenSpriteParticles = new ArrayList<ElvenSpriteParticle>();
		Elvenmissiles = new ArrayList<ElvenMissile>();
		loadImage(); 

		//Init RNG. Doesn't matter what the input is
		craftRNG = new Random(System.currentTimeMillis());

		realx = x;
		realy = y;
	}

	public void move() {

		delayShot--;

		shieldRegenTimer++;
		if (shieldRegenTimer > ((30 - (maxCraftShields/50)))/(speedMultiplier) && craftShields < maxCraftShields){
			craftShields++;
			shieldRegenTimer = 0;
		}
		
		if (accelx > friction){
			accelx -= friction;
		} else if (accelx < -friction){
			accelx += friction;
		} else {
			accelx = 0;
		}
		if (accely > friction){
			accely -= friction;
		} else if (accely < -friction){
			accely += friction;
		} else {
			accely = 0;
		}




		if (accelx > 3.0 * universalScaler){
			accelx = (float) (3.0 * universalScaler);
		} else if (accelx < -3.0 * universalScaler){
			accelx = (float) (-3.0 * universalScaler);
		}
		if (accely > 3.0 * universalScaler){
			accely = (float) (3.0 * universalScaler);
		} else if (accely < -3.0 * universalScaler){
			accely = (float) (-3.0 * universalScaler);
		}
		dx = accelx;
		dy = accely;

		realx += dx;
		realy += dy;

		x = (int) realx;
		y = (int) realy;


		if (x < 0.0){
			accelx = -accelx;
			realx++;
			x++;
			realx++;
			x++;
			realx++;
			x++;
		} else if (x > (int) (1000)){
			accelx = -accelx;
			realx--;
			x--;
			realx--;
			x--;
			realx--;
			x--;
		}

		if (y < 0.0){
			accely = -accely;
			realy++;
			y++;
			realy++;
			y++;
			realy++;
			y++;
		} else if (y > (int) (1080 - getWidth())){
			accely = -accely;
			realy--;
			y--;
			realy--;
			y--;
			realy--;
			y--;
		}
		
		craftShieldsDrawing.x = x;
		craftShieldsDrawing.y = y;

		pixieHelper.move(x - 30, y + 80, this.angle);


	}

	public Rectangle getBounds() {
		getImageDimensions();
		return new Rectangle(x, y, width, height);
	}


	//In my new game this will be null :)
	public ArrayList<ElvenMissile> getMissiles() {
		return Elvenmissiles;
	}

	public ArrayList<ElvenSpriteParticle> getSpriteParticles() {
		return ElvenSpriteParticles;
	}


	public void keyPressed(KeyEvent e) {

		int key = e.getKeyCode();

		if (key == KeyEvent.VK_SPACE) {
			keySetter.elvenAsciiInput[0] = true;
		}

		if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
			keySetter.elvenAsciiInput[2] = true;
		}

		if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
			keySetter.elvenAsciiInput[3] = true;
		}

		if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
			keySetter.elvenAsciiInput[1] = true;
		}

		if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
			keySetter.elvenAsciiInput[4] = true;
		}
		if (key == KeyEvent.VK_Q) {
			keySetter.elvenAsciiInput[5] = true;
		}
		if (key == KeyEvent.VK_E) {
			keySetter.elvenAsciiInput[6] = true;
		}


	}

	public void isPressed(){
		if (keySetter.elvenAsciiInput[0]){ //VK SPACE
			fire();
		}
		if (keySetter.elvenAsciiInput[1]){ //VK UP
			accely += (float) ((float) (- 0.25 - (float) (ElvenMain.ElvenUpgradeMovement) *.1) * (speedMultiplier));
			generateParticles--;

			if (generateParticles < 0){
				int spriteLoc = craftRNG.nextInt(35);
				ElvenSpriteParticles.add(new ElvenSpriteParticle(x + spriteLoc, y + getHeight() - 4, particleSpeed, Math.PI/2, "main/resources/elvenPixieDustYellow.png"));
				generateParticles = maxGenerateParticles;
			}
		}
		if (keySetter.elvenAsciiInput[2]){ //VK LEFT
			accelx += (float) ((float) (- 0.25 - (float) (ElvenMain.ElvenUpgradeMovement) *.1) * (speedMultiplier));
			generateParticles--;

			if (generateParticles < 0){
				int spriteLoc = craftRNG.nextInt(35);
				ElvenSpriteParticles.add(new ElvenSpriteParticle(x + getWidth() - 4, y + spriteLoc, particleSpeed, 0, "main/resources/elvenPixieDustYellow.png"));
				generateParticles = maxGenerateParticles;
			}
		}
		if (keySetter.elvenAsciiInput[3]){ //VK RIGHT
			accelx += (float) ((float) (0.25 + (float) (ElvenMain.ElvenUpgradeMovement) *.1) * (speedMultiplier));
			generateParticles--;

			if (generateParticles < 0){
				int spriteLoc = craftRNG.nextInt(35);
				ElvenSpriteParticles.add(new ElvenSpriteParticle(x, y + spriteLoc, particleSpeed, Math.PI, "main/resources/elvenPixieDustYellow.png"));
				generateParticles = maxGenerateParticles;
			}
		}
		if (keySetter.elvenAsciiInput[4]){ //VK DOWN
			accely += (float) ((float) (0.25 + (float) (ElvenMain.ElvenUpgradeMovement) *.1) * (speedMultiplier));
			generateParticles--;
			if (generateParticles < 0){
				int spriteLoc = craftRNG.nextInt(35);
				ElvenSpriteParticles.add(new ElvenSpriteParticle(x + spriteLoc, y + 4, particleSpeed, 3 * Math.PI/2, "main/resources/elvenPixieDustYellow.png"));
				generateParticles = maxGenerateParticles;
			}
		}
		if (keySetter.elvenAsciiInput[5]){
			this.changeAngle(Math.PI/128.0);
			realx = x;
			realy = y;
		}
		if (keySetter.elvenAsciiInput[6]){
			this.changeAngle(-Math.PI/128.0);
			realx = x;
			realy = y;
		}


	}

	public void fire() {

		delayGhostShot--;
		
		if (delayGhostShot < 1){
			Elvenmissiles.add(new ElvenMissile( x + width/2 + (int)(30 * Math.cos(this.angle)), 
					y + height/2 + (int)(30 * Math.sin(this.angle)), 
					this.angle, "main/resources/elvenarrow.gif"));
			delayGhostShot = (int) ((double) (15) / (speedMultiplier));
			//No upgrading this.
			
		}
		
		if (delayShot < 0){// I am so good at casting they call me a wizard
			delayShot = (int) ((double) (30 - ElvenMain.ElvenUpgradeFirerate) / (speedMultiplier));
			
			

			pixieHelper.fire();

		}

	}

	public void keyReleased(KeyEvent e) {

		int key = e.getKeyCode();

		if (key == KeyEvent.VK_SPACE) {
			keySetter.elvenAsciiInput[0] = false;
		}

		if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
			keySetter.elvenAsciiInput[2] = false;


		}

		if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
			keySetter.elvenAsciiInput[3] = false;
			//dx = dx + accelx;
		}

		if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
			keySetter.elvenAsciiInput[1] = false; 
		}

		if (key == KeyEvent.VK_Q) {
			keySetter.elvenAsciiInput[5] = false;
		}
		if (key == KeyEvent.VK_E) {
			keySetter.elvenAsciiInput[6] = false;
		}

		if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {


			keySetter.elvenAsciiInput[4] = false;
		}
	}



}




