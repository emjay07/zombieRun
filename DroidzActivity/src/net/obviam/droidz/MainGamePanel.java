/**
 * 
 */
package net.obviam.droidz;

import java.util.ArrayList;
import java.util.Random;

import net.obviam.droidz.model.Droid;
import net.obviam.droidz.model.Health;
import net.obviam.droidz.model.Player;
import net.obviam.droidz.model.components.Speed;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

/**
 * @author impaler
 * This is the main surface that handles the ontouch events and draws
 * the image to the screen.
 */
@SuppressLint("NewApi")
public class MainGamePanel extends SurfaceView implements
		SurfaceHolder.Callback {

	private static final String TAG = MainGamePanel.class.getSimpleName();
	
	private MainThread thread;
	private Droid droid;
	private Health health;
	//private ArrayList<Droid> droids;
	//private Health health[];
	private Player player;
	private Random generator = new Random();
	private int width = 600;
	private int height = 325;
	private float xSpeed = 0;
	private float ySpeed = 0;
	private int num = 1;
	private int size = 50;

	public MainGamePanel(Context context) {
		super(context);
		// adding the callback (this) to the surface holder to intercept events
		getHolder().addCallback(this);
		player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.player_1), height - 30, width/2);
		((SensorManager)context.getSystemService(Context.SENSOR_SERVICE)).registerListener(
	    		new SensorEventListener() {    
	    			//@Override  
	    			public void onSensorChanged(SensorEvent event) {  
	    			    //set ball speed based on phone tilt (ignore Z axis)
	    				xSpeed = -event.values[0];
	    				ySpeed = event.values[1];
	    			}
	        		//@Override  
	        		public void onAccuracyChanged(Sensor sensor, int accuracy) {} //ignore this event
	        	},
	        	((SensorManager)context.getSystemService(Context.SENSOR_SERVICE))
	        	.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0), SensorManager.SENSOR_DELAY_NORMAL);

		// create droid and load bitmap
		
		//TODO: loop through list
		//for(int i = 0; i < num; i++) {
			//Droid droid = new Droid(BitmapFactory.decodeResource(getResources(), R.drawable.droid_1), generator.nextInt()%height + 10, generator.nextInt()%width + 10);
			//droids.add(droid);
			droid = new Droid(BitmapFactory.decodeResource(getResources(), R.drawable.droid_1), generator.nextInt()%(height-size) + size, generator.nextInt()%(width-size) + size);
		//}
		health = new Health(BitmapFactory.decodeResource(getResources(), R.drawable.health_1), 155, 50);
		
		// create the game loop thread
		thread = new MainThread(getHolder(), this);
		
		// make the GamePanel focusable so it can handle events
		setFocusable(true);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// at this point the surface is created and
		// we can safely start the game loop
		thread.setRunning(true);
		thread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.d(TAG, "Surface is being destroyed");
		// tell the thread to shut down and wait for it to finish
		// this is a clean shutdown
		boolean retry = true;
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
				// try again shutting down the thread
			}
		}
		Log.d(TAG, "Thread was shut down cleanly");
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		//for(Droid droid : droids) {
			if (event.getAction() == MotionEvent.ACTION_UP) {
				int eventX = (int)event.getX();
				int eventY = (int)event.getY();
				if (eventX >= (droid.getX() - droid.getBitmap().getWidth() / 2) && (eventX <= (droid.getX() + droid.getBitmap().getWidth()/2))) {
					if (eventY >= (droid.getY() - droid.getBitmap().getHeight() / 2) && (eventY <= (droid.getY() + droid.getBitmap().getHeight() / 2))) {
						// touch was released
						//droid.destroy();
						//droids.remove(droid);
						//Droid d = new Droid(BitmapFactory.decodeResource(getResources(), R.drawable.droid_1), generator.nextInt()%height + 10, generator.nextInt()%width + 10);
						droid = new Droid(BitmapFactory.decodeResource(getResources(), R.drawable.droid_1), generator.nextInt()%(height-size) + size, generator.nextInt()%(width-size) + size);
						//droids.add(d);
					}
				}
			}
		//}
		return true;
	}

	public void render(Canvas canvas) {
		//canvas.drawColor(Color.BLACK);
		canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.background_1), 0, 0, null);
		//for(Droid droid : droids) {
			droid.draw(canvas);
		//}
		health.draw(canvas);
		player.draw(canvas);
	}

	/**
	 * This is the game update method. It iterates through all the objects
	 * and calls their update method if they have one or calls specific
	 * engine's update method.
	 */
	public void update() {
		// check collision with right wall if heading right
		//for(Droid droid : droids) {
			if (droid.getSpeed().getxDirection() == Speed.DIRECTION_RIGHT
					&& droid.getX() + droid.getBitmap().getWidth() / 2 >= getWidth()) {
				droid.getSpeed().toggleXDirection();
			}
			// check collision with left wall if heading left
			if (droid.getSpeed().getxDirection() == Speed.DIRECTION_LEFT
					&& droid.getX() - droid.getBitmap().getWidth() / 2 <= 0) {
				droid.getSpeed().toggleXDirection();
			}
			// check collision with bottom wall if heading down
			if (droid.getSpeed().getyDirection() == Speed.DIRECTION_DOWN
					&& droid.getY() + droid.getBitmap().getHeight() / 2 >= getHeight()) {
				droid.getSpeed().toggleYDirection();
			}
			// check collision with top wall if heading up
			if (droid.getSpeed().getyDirection() == Speed.DIRECTION_UP
					&& droid.getY() - droid.getBitmap().getHeight() / 2 <= 0) {
				droid.getSpeed().toggleYDirection();
			}
			// Update the lone droid
			droid.update();
		//}
		health.update();
		player.update(ySpeed, 110, width-150);
	}

}
