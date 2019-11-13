package Game;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Bullet {
	private int x;
	private int y;
	private int velocityX;
	private int velocityY;
	private int angle;
	private static Image bulletImg;
	private Color c;
	
	public Bullet(int x, int y, int velX, int velY) throws SlickException {
		if(bulletImg==null) {
			bulletImg=new Image("res/bullet.png");
		}
		setX(x);
		setY(y);
		setVelocityX(velX);
		setVelocityY(velY);
	}
	
	public void draw(Graphics g) {
		g.drawImage(bulletImg, getX()-4, getY()-4);
	}
	public void update(int delta) {
		velocityY += 9.8f * delta/100f;
		x+=velocityX*delta/100f;
		y+=velocityY*delta/100f;
	}
	public boolean checkForCollision(Graphics g) {
		c=g.getPixel(getX(), getY());
		if(c.r!=0.0f || c.g!=0.0f || c.b!=0.0f) {
			return true;
		}
		else
			return false;
	}

	public boolean checkOutOfArea() {
		if(getX()>800 || getX()<0 || getY()>600 || getY()<0)
			return true;
		else
			return false;
	}
	public int getAngle() {
		return angle;
	}
	public void setAngle(int angle) {
		this.angle = angle;
	}
	
	public void setX(int x) {
		this.x=x;
	}
	public void setY(int y) {
		this.y=y;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getVelocityX() {
		return velocityX;
	}
	public int getVelocityY() {
		return velocityY;
	}
	public void setVelocityX(int velocityX) {
		this.velocityX = velocityX;
	}
	public void setVelocityY(int velocityY) {
		this.velocityY = velocityY;
	}
}
