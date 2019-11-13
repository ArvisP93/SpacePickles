package Game;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class Player {
	private int x;
	private int y;

	private int health;
	private int leftAddY=0;
	private int rightAddY=0;
	private int aimAngle=0;
	private float velocityX;
	private float velocityY;
	private static int id=0;
	private Image img;
	private Color c;
	private boolean isGrounded=false;
	private boolean isGroundedJump=false;
	private boolean moveLeft=false;
	private boolean moveRight=false;
	private boolean gravityEnabled=true;
	private boolean isFacingRight=true;
	
	public Player(int x, int y, Image img) {
		setX(x);
		setY(y);
		setHealth(100);
		setImage(img);
		++id;
	}
	
	public boolean isGrounded() {
		return isGrounded;
	}
	public void checkGround(Graphics g) {
		c=g.getPixel(getX(), getY()+1);
		if(c.r==0.0f && c.g==0.0f && c.b==0.0f) {
			//System.out.println("falling");
			isGrounded=false;
		}
		else {
			isGrounded=true;
			//gravityEnabled=false;
		}
		c=g.getPixel(getX(), getY()+5);
		if(c.r==0.0f && c.g==0.0f && c.b==0.0f) {
			isGroundedJump=false;
		}
		else {
			isGroundedJump=true;
		}
	}

	public void checkRight(Graphics g) {
		c=g.getPixel(getX()+1, getY());
		if(c.r==0.0f && c.g==0.0f && c.b==0.0f) {
			moveRight=true;
			rightAddY=0;
		}
		else {
			c=g.getPixel(getX()+1, getY()-2);
			if(c.r==0.0f && c.g==0.0f && c.b==0.0f) {
				moveRight=true;
				rightAddY=-2;
			}
			else {
				c=g.getPixel(getX()+1, getY()-4);
				if(c.r==0.0f && c.g==0.0f && c.b==0.0f) {
					moveRight=true;
					rightAddY=-4;
				}
				else {
					c=g.getPixel(getX()+1, getY()-6);
					if(c.r==0.0f && c.g==0.0f && c.b==0.0f) {
						moveRight=true;
						rightAddY=-6;
					}
					else {
						moveRight=false;
					}
				}
			}
		}
	}
	public void checkLeft(Graphics g) {
		c=g.getPixel(getX()-1, getY());
		if(c.r==0.0f && c.g==0.0f && c.b==0.0f) {
			moveLeft=true;
			leftAddY=0;
		}
		else {
			c=g.getPixel(getX()-1, getY()-2);
			if(c.r==0.0f && c.g==0.0f && c.b==0.0f) {
				moveLeft=true;
				leftAddY=-2;
			}
			else {
				c=g.getPixel(getX()-1, getY()-4);
				if(c.r==0.0f && c.g==0.0f && c.b==0.0f) {
					moveLeft=true;
					leftAddY=-4;
				}
				else {
					c=g.getPixel(getX()-1, getY()-6);
					if(c.r==0.0f && c.g==0.0f && c.b==0.0f) {
						moveLeft=true;
						leftAddY=-6;
					}
					else {
						moveLeft=false;
					}
				}
			}
		}
	}
	public void angleUp() {
		System.out.println(aimAngle);
		if(aimAngle<=80)
			aimAngle+=1;
	}
	public void angleDown() {
		if(aimAngle>=-60)
			aimAngle-=1;
	}
	public double getAimX() {
		if(isFacingRight)
			return Math.cos(Math.toRadians(aimAngle));
		else
			return -Math.cos(Math.toRadians(aimAngle));
	}
	public double getAimY() {
		return -Math.sin(Math.toRadians(aimAngle));
	}
	public void applyGravity(int delta) {
		if(!isGrounded) {
			if(gravityEnabled) {
				velocityY += 9.8f * delta/100f;
				y+=velocityY * delta/100f;
			}
		}
		else
			velocityY=0.0f;
	}
	public void limitMaxSpeed() {
		if(velocityY >= 18) {
			velocityY=18;
		}
	}
	public void moveLeft(int delta) {
		isFacingRight=false;
			if(moveLeft) {
				x-=1;
				y+=leftAddY;
			}
	}
	public void moveRight(int delta) {
			isFacingRight=true;
			if(moveRight) {
				x+=1;
				y+=rightAddY;
			}
	}
	public void jump() {
		if(isGroundedJump) {
			isGrounded=false;
			isGroundedJump=false;
			velocityY=-18;
			y+=velocityY;
		}
	}
	public void draw(Graphics g){
		if(isFacingRight)
			g.drawImage(getImage(), getX()-8, getY()-32);
		else
			g.drawImage(getImage().getFlippedCopy(true, false), getX()-8, getY()-32);
	}
	public void drawHealthBar(Graphics g){
		g.setColor(Color.green);
		g.fillRect(getX()-health/4, getY()-50, health/2, 8);
	}
	public Image getImage() {
		return img;
	}
	public void setImage(Image image) {
		img=image;
	}
	public int getID() {
		return id;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public void setX(int x) {
		this.x=x;
	}
	public void setY(int y) {
		this.y=y;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
	public void checkExplosionDistance(int expX, int expY) {
		int distance=(int)Math.sqrt(Math.pow(expX-getX(), 2) + Math.pow(expY-(getY()-16), 2));
		if(distance<=25)
			explosionDamage(distance);
	}
	public void explosionDamage(int distance) {
		int damage=50-distance;
		setHealth(getHealth()-damage);
	}
}
