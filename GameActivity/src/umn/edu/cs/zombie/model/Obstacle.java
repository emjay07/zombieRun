package umn.edu.cs.zombie.model;

import umn.edu.cs.zombie.model.components.Speed;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Obstacle {


	private Bitmap bitmap;	// the actual bitmap
	private int x;			// the X coordinate
	private int y;			// the Y coordinate
	private Speed speed;	// the speed with its directions

	public Obstacle(Bitmap bitmap, int x, int y, int vel) {
		this.bitmap = bitmap;
		this.x = x;
		this.y = y;
		this.speed = new Speed(vel,0);
	}
	
	public Bitmap getBitmap() {
		return bitmap;
	}
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	public void destroy() {
		this.bitmap.recycle();
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	public Speed getSpeed() {
		return speed;
	}

	public void setSpeed(int vel) {
		this.speed = new Speed(vel,0);
	}

	public void draw(Canvas canvas) {
		canvas.drawBitmap(bitmap, y - (bitmap.getWidth() / 2), x - (bitmap.getHeight() / 2), null);
	}

	/**
	 * Method which updates the droid's internal state every tick
	 */
	public void update() {
		x += (speed.getXv() * speed.getxDirection()); 
		y += (speed.getYv() * speed.getyDirection());
	}
	
}
