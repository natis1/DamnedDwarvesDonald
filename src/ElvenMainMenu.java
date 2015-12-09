
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.stream.Stream;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class ElvenMainMenu extends JPanel implements ActionListener {
	
	public Stream<String> saveData;
	private int score;
	private JButton startGameButton;
	private JFrame f;
	
	private JMenu e;
	
	private JMenuItem b;
	private JMenuItem c;
	private JMenuItem d;
	private JMenuItem downb;
	private JMenuItem downc;
	private JMenuItem downd;
	
	
	
	private JMenu downG;
	private JMenu a;
	
	private JMenuItem jmiAbout;
	
	private int nextSoulPrice;
	private int lastSoulPrice;
	
	
	private int souls;
	private int difficulty = ElvenMain.ElvenGameDifficulty;
	
	private int resX;
	private int resY;
	private int framerate;
	
	private char speed, faeBullets, faeTracking;
	
	
	ElvenMainMenu(int myScore) {
		
		
		
		readSaveFile(myScore);
		
		
		lastSoulPrice = (int) Math.pow((double)(speed + faeBullets + faeTracking - 2), 2);
		if ((speed + faeBullets + faeTracking - 2) < 0){
			lastSoulPrice = 0;
		}
		
		nextSoulPrice = (int) Math.pow((double)(speed + faeBullets + faeTracking), 2);
		
		f = new JFrame("Main Menu");
		f.setSize(400, 400);

		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JMenuBar jmb = new JMenuBar();

		JMenu jmFile = new JMenu("File");
		JMenuItem jmiOpen = new JMenuItem("Open");
		JMenuItem jmiClose = new JMenuItem("Close");
		JMenuItem jmiSave = new JMenuItem("Save");
		JMenuItem jmiExit = new JMenuItem("Exit");
		jmFile.add(jmiOpen);
		jmFile.add(jmiClose);
		jmFile.add(jmiSave);
		jmFile.addSeparator();
		jmFile.add(jmiExit);
		jmb.add(jmFile);

		JMenu jmOptions = new JMenu("Options");
		
		
		
		
		JMenu GraphicsSettings = new JMenu("Display");
		
		jmb.add(GraphicsSettings);
		
		JMenu Resolution = new JMenu("Resolution");
		JMenu Framerate = new JMenu("FPS");
		
		jmOptions.add(GraphicsSettings);
		GraphicsSettings.add(Framerate);
		
		JMenuItem CapToMonitor = new JMenuItem("Autodetect framerate");
		JMenuItem suprememasterrace = new JMenuItem("240");
		JMenuItem onesixtyfive = new JMenuItem("165");
		JMenuItem onefortyfour = new JMenuItem("144");
		JMenuItem onetwenty = new JMenuItem("120");
		JMenuItem seventyfive = new JMenuItem("75");
		JMenuItem sixty = new JMenuItem("60");
		JMenuItem cinematic = new JMenuItem("30");
		JMenuItem ubisoft = new JMenuItem("24");
		
		Framerate.add(CapToMonitor);
		Framerate.add(suprememasterrace);
		Framerate.add(onesixtyfive);
		Framerate.add(onefortyfour);
		Framerate.add(onetwenty);
		Framerate.add(seventyfive);
		Framerate.add(sixty);
		Framerate.add(cinematic);
		Framerate.add(ubisoft);
		
		CapToMonitor.addActionListener(this);
		suprememasterrace.addActionListener(this);
		onesixtyfive.addActionListener(this);
		onefortyfour.addActionListener(this);
		onetwenty.addActionListener(this);
		seventyfive.addActionListener(this);
		sixty.addActionListener(this);
		cinematic.addActionListener(this);
		ubisoft.addActionListener(this);
		
		GraphicsSettings.add(Resolution);
		
		JMenuItem detectMonitor = new JMenuItem("Fullscreen");
		JMenuItem res2880 = new JMenuItem("2880p");
		JMenuItem res2160 = new JMenuItem("2160p");
		JMenuItem res1600 = new JMenuItem("1600p");
		JMenuItem res1440 = new JMenuItem("1440p");
		JMenuItem res1200 = new JMenuItem("1200p");
		JMenuItem res1080 = new JMenuItem("1080p");
		JMenuItem res1050 = new JMenuItem("1050p");
		JMenuItem res1024 = new JMenuItem("1024p");
		JMenuItem res900 = new JMenuItem("900p");
		JMenuItem res768 = new JMenuItem("768p");
		JMenuItem res720 = new JMenuItem("720p");
		JMenuItem res480 = new JMenuItem("480p");
		JMenuItem res360 = new JMenuItem("360p");
		JMenuItem res240 = new JMenuItem("240p");
		
		Resolution.add(detectMonitor);
		Resolution.add(res2880);
		Resolution.add(res2160);
		Resolution.add(res1600);
		Resolution.add(res1440);
		Resolution.add(res1200);
		Resolution.add(res1080);
		Resolution.add(res1050);
		Resolution.add(res1024);
		Resolution.add(res900);
		Resolution.add(res768);
		Resolution.add(res720);
		Resolution.add(res480);
		Resolution.add(res360);
		Resolution.add(res240);
		
		detectMonitor.addActionListener(this);
		res2880.addActionListener(this);
		res2160.addActionListener(this);
		res1600.addActionListener(this);
		res1440.addActionListener(this);
		res1200.addActionListener(this);
		res1080.addActionListener(this);
		res1050.addActionListener(this);
		res1024.addActionListener(this);
		res900.addActionListener(this);
		res768.addActionListener(this);
		res720.addActionListener(this);
		res480.addActionListener(this);
		res360.addActionListener(this);
		res240.addActionListener(this);
		
		
		
		a = new JMenu("Upgrades " + nextSoulPrice);
		b = new JMenuItem("Increase Speed " + (int) speed);
		c = new JMenuItem("More Fae Bullets " + (int) faeBullets);
		d = new JMenuItem("Better Fae Tracking " + (int) faeTracking);
		a.add(b);
		a.add(c);
		a.add(d);
		jmOptions.add(a);
		
		
		downG = new JMenu("Downgrades -" + lastSoulPrice);
		downb = new JMenuItem("Slower Speed");
		downc = new JMenuItem("Less Fae Bullets");
		downd = new JMenuItem("Worse Fae Tracking");
		downG.add(downb);
		downG.add(downc);
		downG.add(downd);
		jmOptions.add(downG);
		
		
		
		e = new JMenu("Difficulty: " + Integer.toString(difficulty));
		JMenuItem difficultyUp = new JMenuItem("Difficulty Up");
		JMenuItem difficultyDown = new JMenuItem("Difficulty Down");
		difficultyUp.addActionListener(this);
		difficultyDown.addActionListener(this);
		e.add(difficultyUp);
		e.add(difficultyDown);
		jmOptions.add(e);

		jmb.add(jmOptions);

		JMenu jmHelp = new JMenu("Help");
		jmiAbout = new JMenuItem("souls: " + Integer.toString(souls));
		JMenuItem jmiScore = new JMenuItem("score: " + Integer.toString(score));
		jmHelp.add(jmiAbout);
		jmHelp.add(jmiScore);
		jmb.add(jmHelp);

		jmiOpen.addActionListener(this);
		jmiClose.addActionListener(this);
		jmiSave.addActionListener(this);
		jmiExit.addActionListener(this);
		downb.addActionListener(this);
		downc.addActionListener(this);
		downd.addActionListener(this);
		b.addActionListener(this);
		c.addActionListener(this);
		d.addActionListener(this);
		e.addActionListener(this);
		jmiAbout.addActionListener(this);

		f.setJMenuBar(jmb);
		f.setVisible(true);
		
		
    	//when compiling for real, remove Images/
    	//TODO: ADD Images/ WHEN COMPILING FOR ECLIPSE
		ImageIcon startGameIcon = new ImageIcon("Images/ghost.png", "the ghost icon");
		
		
		startGameButton = new JButton("Play", startGameIcon);
		startGameButton.setVerticalTextPosition(AbstractButton.BOTTOM);
		startGameButton.setHorizontalTextPosition(AbstractButton.CENTER);
		startGameButton.addActionListener(this);
		startGameButton.setSize(100, 100);
		startGameButton.setLocation(150, 150);
		startGameButton.setVisible(true);
		f.add(startGameButton);
		


	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLACK);
        g.drawLine(0, 0, getWidth(), getHeight());
        g.dispose();
        
        Font font = new Font("Courier", Font.BOLD, 15);
        g.setFont(font);
        g.drawString((Integer.toString(score)), 25, 25);
        
        
        
        
    }
	
	
	
	public void readSaveFile(int myScore){
		 // The name of the file to open.
        String fileName = "elvenShooter.txt";
        
        souls = ElvenMain.ElvenSoulsStolen;

        // This will reference one line at a time
        //String line = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = 
                new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);

            
            if (bufferedReader.readLine() != null){
            	score = Integer.parseInt(bufferedReader.readLine());
            	difficulty = Integer.parseInt(bufferedReader.readLine());
            	souls += Integer.parseInt(bufferedReader.readLine());
            	if (score > 5000 * difficulty){
            		difficulty++; //Level up
            	}
            	speed = (bufferedReader.readLine().charAt(0));
            	faeBullets = (bufferedReader.readLine().charAt(0));
            	faeTracking = (bufferedReader.readLine().charAt(0));
            	resX = Integer.parseInt(bufferedReader.readLine());
            	System.out.println(bufferedReader.readLine());
            	resY = Integer.parseInt(bufferedReader.readLine());
            	framerate = Integer.parseInt(bufferedReader.readLine());
            	
            	
            }
            score += myScore;
            
            writeNewScore();
            
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                fileName + "' Because the file was not found. Recreating... this may lead to lossed data");
            
            PrintWriter writer;
			try {
				writer = new PrintWriter("elvenShooter.txt", "UTF-8");
				
				
				writer.println("Save File. Do not edit you cheater");
	            writer.println(myScore);
	            writer.println(difficulty);
	            writer.println(souls);
	            writer.println(speed);
	            writer.println(faeBullets);
	            writer.println(faeTracking);
	            writer.println(resX);
	            writer.println("x");
	            writer.println(resY);
	            writer.println(framerate);
	            writer.close();
	            
	            readSaveFile(myScore);
				
			} catch (FileNotFoundException e) {
				System.out.println(
		                "No idea why I can't make '" + 
		                fileName + "' Something is seriously wrong with your system");
				
			} catch (UnsupportedEncodingException e) {
				System.out.println(
		                "You do not have UTF-8? I am seriously amazed");
			}
            
            
            
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + fileName + "' Because you do not have permissions. Run as Admin to fix.");                  
            // Or we could just do this: 
            // ex.printStackTrace();
        }

		
        
        
	}
	
	private void writeNewScore() {
		String fileName = "elvenShooter.txt";
		
		PrintWriter writer;
		try {
			writer = new PrintWriter("elvenShooter.txt", "UTF-8");
			
			
			//building new score
			writer.println("Save File. Do not edit you cheater");
            writer.println(score);
            writer.println(difficulty);
            writer.println(souls);
            writer.println(speed);
            writer.println(faeBullets);
            writer.println(faeTracking);
            writer.println(resX);
            writer.println("x");
            writer.println(resY);
            writer.println(framerate);
            writer.close();
            
            
			
		} catch (FileNotFoundException e) {
			System.out.println(
	                "No idea why I can't make '" + 
	                fileName + "' Something is seriously wrong with your system");
			
		} catch (UnsupportedEncodingException e) {
			System.out.println(
	                "You do not have UTF-8? I am seriously amazed");
		}
		
		
	}
	
	
	
	public void actionPerformed(ActionEvent ae) {
		String comStr = ae.getActionCommand();
		System.out.println(comStr + " Selected");
		if (comStr == "Play"){
			// 0 - 255 will select a difficulty
			
			writeNewScore();
			f.setVisible(false);
			f.dispose();
			ElvenMain.ElvenGameState = 10;
			ElvenMain.ElvenGameDifficulty = difficulty;
        
		} else if (comStr == "Difficulty Up"){
			difficulty++;
			e.setText("Difficulty: " + Integer.toString(difficulty));
			
			
			
			writeNewScore();
			
		} else if (comStr == "Difficulty Down"){
			if (difficulty > 0) {
				difficulty--;
			}
			e.setText("Difficulty: " + Integer.toString(difficulty));
			writeNewScore();
		}
		
		if (comStr.contains("Increase Speed")){
			if (souls >= nextSoulPrice && speed < 20){
				speed++;
				b.setText("Increase Speed " + (int) speed);
				souls -= nextSoulPrice;
				lastSoulPrice = (int) Math.pow((double)(speed + faeBullets + faeTracking - 2), 2);
				nextSoulPrice = (int) Math.pow((double)(speed + faeBullets + faeTracking), 2);
				downG.setText("Downgrades -" + lastSoulPrice);
				a.setText("Upgrades " + nextSoulPrice);
				
				jmiAbout.setText("souls: " + Integer.toString(souls));
			}
			
		} else if (comStr.contains("More Fae Bullets")){
			if (souls >= nextSoulPrice && faeBullets < 25){
				faeBullets++;
				c.setText("More Fae Bullets " + (int) faeBullets);
				souls -= nextSoulPrice;
				lastSoulPrice = (int) Math.pow((double)(speed + faeBullets + faeTracking - 2), 2);
				nextSoulPrice = (int) Math.pow((double)(speed + faeBullets + faeTracking), 2);
				downG.setText("Downgrades -" + lastSoulPrice);
				a.setText("Upgrades " + nextSoulPrice);
				
				jmiAbout.setText("souls: " + Integer.toString(souls));
				writeNewScore();
			}
			
		} else if (comStr.contains("Better Fae Tracking")){
			if (souls >= nextSoulPrice && faeTracking < 30){
				faeTracking++;
				d.setText("Better Fae Tracking" + (int) faeTracking);
				souls -= nextSoulPrice;
				lastSoulPrice = (int) Math.pow((double)(speed + faeBullets + faeTracking - 2), 2);
				nextSoulPrice = (int) Math.pow((double)(speed + faeBullets + faeTracking), 2);
				downG.setText("Downgrades -" + lastSoulPrice);
				a.setText("Upgrades " + nextSoulPrice);
				
				jmiAbout.setText("souls: " + Integer.toString(souls));
				writeNewScore();
			}
			
		}
		
		if (comStr.contains("Slower Speed")){
			if (speed > 0){
				speed--;
				b.setText("Increase Speed " + (int) speed);
				souls += lastSoulPrice;
				lastSoulPrice = (int) Math.pow((double)(speed + faeBullets + faeTracking - 2), 2);
				nextSoulPrice = (int) Math.pow((double)(speed + faeBullets + faeTracking), 2);
				jmiAbout.setText("souls: " + Integer.toString(souls));
				downG.setText("Downgrades -" + lastSoulPrice);
				a.setText("Upgrades " + nextSoulPrice);
				
				writeNewScore();
			}
			
			
		} else if (comStr.contains("Less Fae Bullets")){
			if (faeBullets > 0){
				faeBullets--;
				c.setText("More Fae Bullets " + (int) faeBullets);
				souls += lastSoulPrice;
				lastSoulPrice = (int) Math.pow((double)(speed + faeBullets + faeTracking - 2), 2);
				nextSoulPrice = (int) Math.pow((double)(speed + faeBullets + faeTracking), 2);
				jmiAbout.setText("souls: " + Integer.toString(souls));
				downG.setText("Downgrades -" + lastSoulPrice);
				a.setText("Upgrades " + nextSoulPrice);
				
				writeNewScore();
			}
			
			
			
		} else if (comStr.contains("Worse Fae Tracking")){
			if (faeTracking > 0){
				faeTracking--;
				d.setText("Better Fae Tracking" + (int) faeTracking);
				souls += lastSoulPrice;
				lastSoulPrice = (int) Math.pow((double)(speed + faeBullets + faeTracking - 2), 2);
				nextSoulPrice = (int) Math.pow((double)(speed + faeBullets + faeTracking), 2);
				jmiAbout.setText("souls: " + Integer.toString(souls));
				downG.setText("Downgrades -" + lastSoulPrice);
				a.setText("Upgrades " + nextSoulPrice);
				
				writeNewScore();
			}
			
			
			
		}
		
		switch (comStr){
		case "Fullscreen": resX = 0; //0 = fullscreen
		resY = 0; break;
		case "2880p": resY = 2880;
		resX = 1; break;
		case "2160p": resY = 2160;
		resX = 1; break;
		case "1600p": resY = 1600;
		resX = 1; break;
		case "1440p": resY = 1440;
		resX = 1; break;
		case "1200p": resY = 1200;
		resX = 1; break;
		case "1080p": resY = 1080;
		resX = 1; break;
		case "1050p": resY = 1050;
		resX = 1; break;
		case "1024p": resY = 1024;
		resX = 1; break;
		case "900p": resY = 900;
		resX = 1; break;
		case "768p": resY = 768;
		resX = 1; break;
		case "720p": resY = 720;
		resX = 1; break;
		case "480p": resY = 480;
		resX = 1; break;
		case "360p": resY = 360;
		resX = 1; break;
		case "240p": resY = 240;
		resX = 1; break;
		
		case "Autodetect framerate":
			framerate = 0; break;
		case "240": framerate = 240;break;
		case "165": framerate = 165; break;
		case "144": framerate = 144; break;
		case "120": framerate = 120; break;
		case "75": framerate = 75; break;
		case "60": framerate = 60; break;
		case "30": framerate = 30; break;
		case "24": framerate = 24; break; // have fun idiot

		}
		
		
		
		writeNewScore();
		repaint();
		
	}
	/*private class TAdapter extends KeyAdapter {


        @Override
        public void keyPressed(KeyEvent e) {
        	int key = e.getKeyCode();
            if (key == KeyEvent.VK_R){
            	PrintWriter writer;
        		try {
        			writer = new PrintWriter("elvenshooterTEMP", "UTF-8");
        			writer.println("Return To Game");
                    writer.close();
        		} catch (FileNotFoundException f) {
        			System.out.println(
        	                "Something is seriously wrong with your system");
        			
        		} catch (UnsupportedEncodingException f) {
        			System.out.println(
        	                "You do not have UTF-8? I am seriously amazed");
        		}
            }
        }
    }*/
}