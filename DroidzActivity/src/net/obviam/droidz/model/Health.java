package net.obviam.droidz.model;

import net.obviam.droidz.model.components.Speed;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Health {

	private Bitmap bitmap;	// the actual bitmap
	private int x;			// the X coordinate
	private int y;			// the Y coordinate
	private Speed speed;	// the speed with its directions
	
	public Health(Bitmap bitmap, int x, int y) {
		this.bitmap = bitmap;
		this.x = x;
		this.y = y;
		this.speed = new Speed(0,1);
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

	public void setSpeed(Speed speed) {
		this.speed = speed;
	}

	public void draw(Canvas canvas) {
		canvas.drawBitmap(bitmap, x - (bitmap.getWidth() / 2), y - (bitmap.getHeight() / 2), null);
	}
	
	/**
	 * Method which updates the droid's internal state every tick
	 */
	public void update() {
		x += (speed.getXv() * speed.getxDirection()); 
		y += (speed.getYv() * speed.getyDirection());
	}
	
}
