package Game;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;

import org.newdawn.slick.AppGameContainer;

public class Main extends BasicGame {
	private int titleX;
	private int titleY;
	private float titleSpeed;
	private int currentIndex;
	private int shots;
	private Image img;
	private Image aim;
	private Image lvl1;
	private Image lvl1m;
	private Graphics lvl1mG;
	private Bullet bullet;
	ArrayList<Player> players = new ArrayList<Player>();

	
    public Main() {
        super("Space Pickles");
    }
    
    @Override
    public void init(GameContainer container) throws SlickException {
    	currentIndex=0;
    	shots=2;
    	titleX=5;
    	titleY=35;
    	titleSpeed=0.1f;
    	img = new Image("res/player.png");
    	aim = new Image("res/aim.png");
    	
    	lvl1 = new Image("res/lvl1.png");
    	lvl1m = new Image("res/lvl1m.png");
    	lvl1mG = lvl1m.getGraphics();
    	lvl1mG.flush();

    	players.add(new Player(120,250,img));
    	players.add(new Player(550,250,img));
    	players.add(new Player(220,60,img));
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
    	Input input = container.getInput();

    	manageInput(input, delta);
    	managePhysics(delta);
    	
    	
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
    	
    	drawLevel(g);
		managePlayerCollisions(g);
		manageBulletCollisions(g);
		drawPlayers(g);
		
		drawUI(g);
    }

    public static void main(String[] args) {
        try {
            AppGameContainer app = new AppGameContainer(new Main());
            app.setTargetFrameRate(60);
            app.setDisplayMode(800, 600, false);
            app.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
    
    public void shootBullet() throws SlickException {
		if(bullet==null) {
			bullet = new Bullet(players.get(currentIndex).getX()+(int)(10*players.get(currentIndex).getAimX()), players.get(currentIndex).getY()-16+(int)(10*players.get(currentIndex).getAimY()), (int)(70*players.get(currentIndex).getAimX()),(int)(70*players.get(currentIndex).getAimY()));
			shots-=1;
			if(shots==0) {
				shots=2;
				if(currentIndex<players.size()-1) {
					currentIndex++;
				}
				else {
					currentIndex=0;
				}
			}
		}
    }
    public void drawLevel(Graphics g) {
    	g.drawImage(lvl1, 0, 0);
    	g.setDrawMode(Graphics.MODE_COLOR_MULTIPLY);
		g.drawImage(lvl1m, 0, 0);
		g.setDrawMode(Graphics.MODE_NORMAL);
    }
    public void managePlayerCollisions(Graphics g) {
    	for(int i=0; i<players.size();i++)
			players.get(i).checkGround(g);
		players.get(currentIndex).checkLeft(g);
		players.get(currentIndex).checkRight(g);
    }
    public void manageBulletCollisions(Graphics g) {
		if(bullet != null) {
			if(bullet.checkForCollision(g)) {
				lvl1mG.setColor(Color.black);
				lvl1mG.fillOval(bullet.getX()-25, bullet.getY()-25, 50, 50);
		    	lvl1mG.flush();
		    	for(int i=0; i<players.size();i++)
		    		players.get(i).checkExplosionDistance(bullet.getX(), bullet.getY());
		    	bullet=null;
		    	
			}
		}
		if(bullet != null) {
			if(bullet.checkOutOfArea()) {
				bullet=null;
			}
		}
		if(bullet != null) {
			bullet.draw(g);
		}
    }
    public void drawPlayers(Graphics g) {
    	for(int i=0; i<players.size();i++) {
			players.get(i).draw(g);
		}
    	g.drawImage(aim, players.get(currentIndex).getX()+(int)(50*players.get(currentIndex).getAimX())-8, players.get(currentIndex).getY()-16+(int)(50*players.get(currentIndex).getAimY())-8);
		g.drawGradientLine(players.get(currentIndex).getX(), players.get(currentIndex).getY()-16, Color.transparent, players.get(currentIndex).getX()+(int)(50*players.get(currentIndex).getAimX()), players.get(currentIndex).getY()-16+(int)(50*players.get(currentIndex).getAimY()), Color.red);
    }
    public void drawUI(Graphics g) {
    	g.drawString("Pickle: #"+(currentIndex+1), 700, 10);
		g.drawString("Shots: "+shots, 600, 10);
		g.drawString("Space Pickles by Arvis Pukitis", titleX, titleY);
    	for(int i=0; i<players.size();i++)
			players.get(i).drawHealthBar(g);
    }
    public void checkForDeadPlayers() {
    	for(int i=0; i<players.size();i++) {
    		if(players.get(i).getHealth()<=0) {
    			//TODO remove dead players
    		}
    	}
    }
    public void manageInput(Input input, int delta) throws SlickException {
    	if(input.isKeyDown(Input.KEY_LEFT)){
    		players.get(currentIndex).moveLeft(delta);
    	}
    	else if(input.isKeyDown(Input.KEY_RIGHT)){
    		players.get(currentIndex).moveRight(delta);
    	}
    	if(input.isKeyPressed(Input.KEY_SPACE)){
    		players.get(currentIndex).jump();
    	}
    	if(input.isKeyPressed(Input.KEY_E)){
    		shootBullet();
    	}
    	if(input.isKeyDown(Input.KEY_UP)){
    		players.get(currentIndex).angleUp();
    	}
    	else if(input.isKeyDown(Input.KEY_DOWN)){
    		players.get(currentIndex).angleDown();
    	}
    }
    public void managePhysics(int delta) {  	
    	if(titleX<800)
    		titleX+=titleSpeed * delta;
    	
    	for(int i=0; i<players.size();i++) {
    		players.get(i).applyGravity(delta);
    		players.get(i).limitMaxSpeed();
    	}
    	if(bullet !=null)
    		bullet.update(delta);
    	
    	aim.rotate(0.1f*delta);
    }

}