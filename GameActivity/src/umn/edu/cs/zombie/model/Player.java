package umn.edu.cs.zombie.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Player {
	
	private float life;
	private int lifeCount;
	private int healthPieces;
	private int killCount;
	private boolean isDead;
	private Bitmap image;
	private float x;
	private float y;
	
	public Player(Bitmap image, float x, float y) {
		this.life = 100;
		this.lifeCount = 3;
		this.healthPieces = 0;
		this.killCount = 0;
		this.isDead = false;
		this.image = image;
		this.x = x;
		this.y = y;
	}
	
	public Bitmap getBitmap() {
		return image;
	}
	public void setBitmap(Bitmap bitmap) {
		this.image = bitmap;
	}
	
	public float getX() {
		return this.x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return this.y;
	}
	public void setY(float y) {
		this.y = y;
	}
	
	public float getLife() {
		return this.life;
	}
	
	public void decrementLife() {
		this.life -= 0.5;
		if(this.life <= 0) {
			if(this.lifeCount <= 0) {
				isDead = true;
				return;
			} else {
				this.life = 100;
				this.lifeCount--;
			}
		}
	}
	
	public int getLifeCount() {
		return this.lifeCount;
	}
	
	public void decrementLifeCount() {
		this.lifeCount--;
	}

	public int getHealthPieces() {
		return this.healthPieces;
	}
	
	public void addHealthPieces(int add) {
		if(add > 0) {
			this.healthPieces += add;
			if(this.healthPieces >= 50) {
				this.healthPieces -= 50;
				this.lifeCount++;
			}
		}
	}
	
	public int getKillCount() {
		return this.killCount;
	}
	
	public void incrementKillCount() {
		this.killCount++;
	}
	
	public boolean getIsDead() {
		return this.isDead;
	}
	
	public void draw(Canvas canvas) {
		canvas.drawBitmap(image, y - (image.getWidth() / 2), x - (image.getHeight() / 2), null);
	}
	
	public void update(float speed, float left, float right) {
		if(this.y + speed > left && this.y + speed < right) {
			this.y += speed;
		}
	}
	
	public void destroy() {
		this.image.recycle();
	}
	
}
