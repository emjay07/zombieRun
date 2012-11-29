package umn.edu.cs.zombie.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Player {
	
	private int life;
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
	
	public int getLife() {
		return this.life;
	}
	
	public void addLife(int x) {
		this.life += x;
		if(this.life > 100) {
			this.life = 100;
			this.lifeCount++;
		}
	}
	
	public void decrementLife() {
		this.life -= 25;
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
	
	public void setHealthPieces(int add) {
		if(add > 0) {
			this.healthPieces += add;
			if(this.healthPieces >= 100) {
				this.healthPieces -= 100;
				this.life = 100;
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
	
	public float getX() {
		return this.x;
	}
	
	public float getY() {
		return this.y;
	}
	
	public Bitmap getBitmap() {
		return this.image;
	}
	
	public void draw(Canvas canvas) {
//		Rect destRect = null;
//		destRect.set((int) y-(image.getWidth()/2), (int) x - image.getHeight()/2,(int) y + (image.getWidth()/2),(int) x + image.getHeight()/2);
		//canvas.drawBitmap(image, null, destRect, null);
		canvas.drawBitmap(image, y - (image.getWidth() / 2), x - (image.getHeight() / 2), null);
	}
	
	public void update(float speed, float left, float right) {
		if(this.y + speed > left && this.y + speed < right) {
			this.y += speed;
		}
	}
	
}
