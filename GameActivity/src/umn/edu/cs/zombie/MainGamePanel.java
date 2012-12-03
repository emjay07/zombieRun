/**
 * 
 */
package umn.edu.cs.zombie;

import java.util.ArrayList;
import java.util.Random;

import umn.edu.cs.zombie.model.Health;
import umn.edu.cs.zombie.model.Obstacle;
import umn.edu.cs.zombie.model.Player;
import umn.edu.cs.zombie.model.Zombie;
import umn.edu.cs.zombie.model.components.Speed;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Vibrator;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;

/**
 * @author emily
 * This is the main surface that handles the UI
 */
@SuppressLint("NewApi")
public class MainGamePanel extends SurfaceView implements
		SurfaceHolder.Callback {

	private static final String TAG = MainGamePanel.class.getSimpleName();
	
	private MainThread thread;
	private Context baseContext;
	//private Zombie zombie;
	//private Health health;
	private ArrayList<Zombie> zombies;
	private ArrayList<Health> heals;
	private ArrayList<Obstacle> obstacles;
	private Player player;
	private Random generator = new Random();
	private int width;
	private int height;
	private float leftbound;
	private float rightbound;
	private float margin;
	private float progressMin;
	private float progressMax;
	private float progressCurr;
	private float speedBarMin;
	private float speedBarMax;
	private float speedCurr;
	private int objectSpeed;
	private float barHeight = 20;
	private float ySpeed = 0;
	private int num = 1;
	private boolean speedBar = false;
	private boolean pause;
	private boolean endLevel = false;
	private boolean dead = false;
	private Vibrator vibe;
	private Bitmap _zombie = BitmapFactory.decodeResource(getResources(), R.drawable.zombie);
	private Bitmap _player = BitmapFactory.decodeResource(getResources(), R.drawable.player);
	private Bitmap _smallOrb = BitmapFactory.decodeResource(getResources(), R.drawable.small_orbs);
	private Bitmap _bigOrb = BitmapFactory.decodeResource(getResources(), R.drawable.big_orb);
	private Bitmap _antidote = BitmapFactory.decodeResource(getResources(), R.drawable.shot);
	private Bitmap _progress = BitmapFactory.decodeResource(getResources(), R.drawable.player_head);
	private Bitmap _boulder = BitmapFactory.decodeResource(getResources(), R.drawable.boulder);
	private Bitmap _car = BitmapFactory.decodeResource(getResources(), R.drawable.car_red_damaged_second);
	private Bitmap _streetLamp = BitmapFactory.decodeResource(getResources(), R.drawable.tilted_fallen_street_light_2);
	private Bitmap _tree = BitmapFactory.decodeResource(getResources(), R.drawable.second_fallen_tree);
	private Bitmap _death = BitmapFactory.decodeResource(getResources(), R.drawable.death_screen);
	private Bitmap _red = BitmapFactory.decodeResource(getResources(), R.drawable.player_red);
	private Bitmap _building = BitmapFactory.decodeResource(getResources(), R.drawable.building);
	private Bitmap _grass1 = BitmapFactory.decodeResource(getResources(), R.drawable.grass);
	private Bitmap _grass2 = BitmapFactory.decodeResource(getResources(), R.drawable.grass2);
	private Bitmap _oneup = BitmapFactory.decodeResource(getResources(), R.drawable.oneup);

	public MainGamePanel(Context context) {
		super(context);
		baseContext = context;
		// adding the callback (this) to the surface holder to intercept events
		getHolder().addCallback(this);
		
		//get screen size
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		width = size.x;
		height = size.y;
		
		//set left and right bounds
		leftbound = (float)((1.0/6.0) * width);
		rightbound = (float)((5.0/6.0) * width);
		margin = (float)(leftbound/10.0);
		progressMin = leftbound+margin-10;
		progressMax = margin+10;
		progressCurr = progressMin;
		speedBarMax = margin+barHeight+70;
		speedBarMin = height-margin-barHeight-40;
		speedCurr = (speedBarMax + speedBarMin)/2;
		objectSpeed = 2;
		
		//initialize pause
		pause = false;
		
		//create player
		player = new Player(_player, height - 50, width/2);
		
		//tilt sensor
		((SensorManager)context.getSystemService(Context.SENSOR_SERVICE)).registerListener(
	    		new SensorEventListener() {    
	    			//@Override  
	    			public void onSensorChanged(SensorEvent event) {  
	    			    //set speed based on phone tilt (ignore X and Z axes)
	    				ySpeed = event.values[1];
	    			}
	        		//@Override  
	        		public void onAccuracyChanged(Sensor sensor, int accuracy) {} //ignore this event
	        	},
	        	((SensorManager)context.getSystemService(Context.SENSOR_SERVICE))
	        	.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0), SensorManager.SENSOR_DELAY_NORMAL);
		
		vibe = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE) ;
		
		//create zombies
		zombies = new ArrayList<Zombie>();
		for(int i = 0; i < num; i++) {
			int y;
			int xDirection;
			int rand = generator.nextInt()%2;
			if(rand == 0) {
				y = 0;
				xDirection = -1;
			} else {
				y = width;
				xDirection = 1;
			}
			int x = generator.nextInt(height/2);
			Zombie zombie = new Zombie(_zombie, x, y);
			zombie.getSpeed().setyDirection(xDirection);
			zombies.add(zombie);
			//zombie = new Zombie(BitmapFactory.decodeResource(getResources(), R.drawable.droid_1), generator.nextInt()%(height-size) + size, generator.nextInt()%(width-size) + size);
		}
		
		//create health orbs
		heals = new ArrayList<Health>();
		for(int i = 0; i < num; i++) {
			Bitmap heal;
			int score;
			int rand = generator.nextInt()%3;
			if(rand < 2) {
				heal = _smallOrb;
				score = 2;
			} else {
				heal = _bigOrb;
				score = 5;
			}
			heals.add(new Health(heal, 0, generator.nextInt((int)(rightbound-leftbound))+(int)leftbound, objectSpeed, score));
		}
		
		obstacles = new ArrayList<Obstacle>();
		for(int i = 0; i < num; i++) {
			Bitmap obs;
			int rand = generator.nextInt()%4;
			if(rand == 0)
				obs = _boulder;
			else if(rand == 1)
				obs = _tree;
			else if(rand == 2)
				obs = _streetLamp;
			else
				obs = _car;
			obstacles.add(new Obstacle(obs, 0, generator.nextInt((int)(rightbound-leftbound))+(int)leftbound, objectSpeed));
		}
		
		// create the game loop thread
		thread = new MainThread(getHolder(), this);
		
		// make the GamePanel focusable so it can handle events
		setFocusable(true);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
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
		if(!pause && !endLevel && !dead) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				int eventX = (int)event.getX();
				int eventY = (int)event.getY();
				//margin, speedCurr, margin+barHeight, speedCurr+(barHeight-margin)
				if(eventX >= margin && eventX <= margin+barHeight &&
						eventY >= speedCurr && eventY <= speedCurr+(barHeight-margin)) {
					speedBar = true;
				}
			}
			if(event.getAction() == MotionEvent.ACTION_MOVE) {
				//click and drag - speed bar
				if(speedBar) {
					int eventY = (int)event.getY();
				
					if(eventY <= speedBarMin && eventY >= speedBarMax) {
						speedCurr = eventY;
						double slow = (speedBarMin + speedBarMax)*(2.0/3.0);
						double medium = (speedBarMin + speedBarMax)*(1.0/3.0);
						if(speedCurr >= slow) {
							objectSpeed = 1;
						} else if(speedCurr >= medium) {
							objectSpeed = 2;
						} else {
							objectSpeed = 3;
						}
					}
				}
			}
			if (event.getAction() == MotionEvent.ACTION_UP) {
				if(speedBar) {
					speedBar = false;
				}
				int eventX = (int)event.getX();
				int eventY = (int)event.getY();
				if(eventX >= (margin) && eventX <= ((2*margin)+((float)(margin/2.0))) &&
						eventY >= (height-margin-barHeight) && eventY <=(height-margin)) {
						pause = !pause;
				}
				if(eventX >= (player.getY()-2*leftbound) && eventX <= (player.getY()+2*leftbound)
						&& eventY <= (player.getX()+2*leftbound) && eventY >= (player.getX()-2*leftbound)) {
					for(int i = 0; i < zombies.size(); i++) {
						Zombie zombie = zombies.get(i);
						if (eventX >= (zombie.getY() - zombie.getBitmap().getWidth() / 2) && (eventX <= (zombie.getY() + zombie.getBitmap().getWidth()/2))
							&& eventY >= (zombie.getX() - zombie.getBitmap().getHeight() / 2) && (eventY <= (zombie.getX() + zombie.getBitmap().getHeight() / 2))) {
								// touch was released
								zombies.remove(i);
								int y;
								int xDirection;
								int rand = generator.nextInt()%2;
								if(rand == 0) {
									y = 0;
									xDirection = -1;
								} else {
									y = width;
									xDirection = 1;
								}
								int x = generator.nextInt(height/2);
								Zombie z = new Zombie(_zombie, x, y);
								zombies.add(z);
								player.incrementKillCount();
						}
					}
				}
			}
		} else {
			//width/3 + width/30, height/2 + height/30, 2*width/3 - width/30, 3*height/4 - height/30
			if (event.getAction() == MotionEvent.ACTION_UP) {
				int eventX = (int)event.getX();
				int eventY = (int)event.getY();
				if(eventX > (width/3 + width/30) && eventY > (height/4 + height/30) && 
				   eventX < (2*width/3 - width/30) && eventY < (height/2 - height/30)) {
					pause = !pause;
				}
				if(eventX > (width/3 + width/30) && eventY > (height/2 + height/30) && 
				   eventX < (2*width/3 - width/30) && eventY < (3*height/4 - height/30)) {
					this.destroy();
				}
			}
		}
		
		return true;
	}

	public void render(Canvas canvas) {
		//draw background color
		canvas.drawRGB(34, 144, 76);
		
		//paint swatch
		Paint paint = new Paint();
		
		//draw road
		paint.setColor(Color.rgb(185, 122, 87));
		paint.setStrokeWidth(3);
		canvas.drawRect(leftbound, 0, rightbound, height, paint);
		
		for(Zombie zombie : zombies) {
			zombie.draw(canvas);
		}
		for(Health health : heals) {
			health.draw(canvas);
		}
		for(Obstacle obs : obstacles) {
			obs.draw(canvas);
		}
		
		int alpha_o = 200;
		int alpha_t = 100;
		
		/** Draw Player **/
		paint.setARGB(alpha_t, 255, 0, 0);
		paint.setStyle(Paint.Style.FILL);
		canvas.drawCircle(player.getY(), player.getX(), 2*leftbound, paint);
		paint.setARGB(alpha_t, 255, 128, 0);
		canvas.drawCircle(player.getY(), player.getX(), leftbound, paint);
		player.draw(canvas);
		/** Draw Player **/
		
		/** HEALTH **/
		//draw health meter
		paint.setARGB(alpha_o, 237, 28, 36);
		canvas.drawRect(margin, margin, (float)((player.getLife()/100.0)*leftbound)+margin, margin+barHeight, paint);
		//draw outline
		paint.setARGB(alpha_o, 138, 11, 17);
		paint.setStrokeWidth(2);
		paint.setStyle(Paint.Style.STROKE);
		canvas.drawRect(margin, margin, (leftbound)+margin, margin+barHeight, paint);
		//life count
		canvas.drawBitmap(_antidote, margin, margin+barHeight+10, paint);
		paint.setARGB(alpha_o, 0, 0, 0);
		canvas.drawText(""+player.getLifeCount(), margin+30, margin+barHeight+30, paint);
		//orb count
		canvas.drawBitmap(_smallOrb, (leftbound)+margin-40, margin+barHeight+10, paint);
		canvas.drawText(""+player.getHealthPieces(), (leftbound)+margin-10, margin+barHeight+30, paint);
		/** HEALTH **/
		
		/** MINI MAP **/
		//draw background
		paint.setARGB(alpha_t, 128, 128, 128);
		paint.setStyle(Paint.Style.FILL);
		canvas.drawRect(width-(leftbound+margin), margin, width-margin, leftbound+margin, paint);
		//draw outline
		paint.setARGB(alpha_o, 0, 0, 0);
		paint.setStyle(Paint.Style.STROKE);
		canvas.drawRect(width-(leftbound+margin), margin, width-margin, leftbound+margin, paint);
		//draw progress bar
		float midpoint = (float)(width-(((1.0/2.0)*leftbound)+margin));
		
		canvas.drawLine(midpoint, progressMin, midpoint, progressMax, paint);
		canvas.drawBitmap(_progress, midpoint-15, progressCurr-20, paint);
		/** MINI MAP **/
		
		/** KILL COUNT **/
		//draw background
		paint.setARGB(alpha_t, 128, 128, 128);
		paint.setStyle(Paint.Style.FILL);
		canvas.drawRect(width-(barHeight+margin), height-margin-barHeight, width-margin, height-margin, paint);
		//draw outline
		paint.setARGB(alpha_o, 0, 0, 0);
		paint.setStyle(Paint.Style.STROKE);
		canvas.drawRect(width-(barHeight+margin), height-margin-barHeight, width-margin, height-margin, paint);
		canvas.drawText(""+player.getKillCount(), width-(barHeight+margin-5), height-margin-5, paint);
		/** KILL COUNT **/
		
		/** PAUSE BUTTON **/
		float pause_w = (float)(margin/2.0);
		//draw background
		if(!pause) {
			paint.setARGB(alpha_t, 255, 255, 255);
		} else {
			paint.setARGB(alpha_o, 0, 0, 0);
		}
		paint.setStrokeWidth(1);
		paint.setStyle(Paint.Style.FILL);
		//left
		canvas.drawRect(margin, height-margin-barHeight, margin+pause_w, height-margin, paint);
		//right
		canvas.drawRect(2*margin, height-margin-barHeight, (2*margin)+(pause_w), height-margin, paint);
		//draw outline
		paint.setARGB(alpha_o, 0, 0, 0);
		paint.setStyle(Paint.Style.STROKE);
		//left
		canvas.drawRect(margin, height-margin-barHeight, (margin)+(float)(margin/2.0), height-margin, paint);
		//right
		canvas.drawRect(2*margin, height-margin-barHeight, (2*margin)+(pause_w), height-margin, paint);
		/** PAUSE BUTTON **/
		
		/** SPEED BAR **/
		paint.setARGB(alpha_o, 0, 0, 0);
		paint.setStyle(Paint.Style.STROKE);
		canvas.drawLine(barHeight, speedBarMin, barHeight, speedBarMax, paint);
		paint.setStyle(Paint.Style.FILL);
		canvas.drawRect(margin, speedCurr, margin+barHeight, speedCurr+(barHeight-margin), paint);
		/** SPEED BAR **/
		
		
		//if paused...
		if(pause) {
			paint.setARGB(alpha_o, 128, 128, 128);
			paint.setStyle(Paint.Style.FILL);
			canvas.drawRect(0, 0, width, height, paint);
			//code for the "pause menu"
			paint.setColor(Color.BLACK);
			canvas.drawRect(width/3, height/4, 2*width/3, 3*height/4, paint);
			paint.setColor(Color.RED);
			//these values are fairly arbitrary...
			canvas.drawRect(width/3 + width/30, height/4 + height/30, 2*width/3 - width/30, height/2 - height/30, paint);
			canvas.drawRect(width/3 + width/30, height/2 + height/30, 2*width/3 - width/30, 3*height/4 - height/30, paint);
			paint.setColor(Color.BLACK);
			canvas.drawText("Resume Game", width/3 + width/10, 3*height/8, paint);
			canvas.drawText("Exit Game", width/3 + width/8, 5*height/8, paint);
		} 
		
		//end of level
		if(endLevel) {
			paint.setARGB(alpha_o, 128, 128, 128);
			paint.setStyle(Paint.Style.FILL);
			canvas.drawRect(0, 0, width, height, paint);
			//code for the "pause menu"
			paint.setColor(Color.BLACK);
			canvas.drawRect(width/3, height/4, 2*width/3, 3*height/4, paint);
			paint.setColor(Color.RED);
			//these values are fairly arbitrary...
			canvas.drawRect(width/3 + width/30, height/4 + height/30, 2*width/3 - width/30, height/2 - height/30, paint);
			canvas.drawRect(width/3 + width/30, height/2 + height/30, 2*width/3 - width/30, 3*height/4 - height/30, paint);
			paint.setColor(Color.BLACK);
			canvas.drawText("Next Level", width/3 + width/10, 3*height/8, paint);
			canvas.drawText("Upgrades", width/3 + width/8, 5*height/8, paint);
		} 
		
		//end of level
		if(dead) {
			paint.setARGB(alpha_o, 128, 128, 128);
			paint.setStyle(Paint.Style.FILL);
			canvas.drawRect(0, 0, width, height, paint);
			//death picture
			canvas.drawBitmap(_death, width/4, height/4, paint);
		}
	}

	/**
	 * This is the game update method. It iterates through all the objects
	 * and calls their update method if they have one or calls specific
	 * engine's update method.
	 */
	public void update() {
		// check collision with right wall if heading right
		if(!pause && !endLevel && !dead) {
			
			dead = player.getIsDead();
			player.setBitmap(_player);
			//update progress
			float inc = (float) 0.01;
			if(progressCurr <= progressMax+10) {
				endLevel = true;
			} else {
				progressCurr -= inc;
			}
			
			for(Zombie zombie : zombies) {
				// check collision with right wall if heading right
				if (zombie.getSpeed().getxDirection() == Speed.DIRECTION_RIGHT
						&& zombie.getX() + zombie.getBitmap().getWidth() / 2 >= getWidth()) {
					zombie.getSpeed().toggleXDirection();
				}
				// check collision with left wall if heading left
				if (zombie.getSpeed().getxDirection() == Speed.DIRECTION_LEFT
						&& zombie.getX() - zombie.getBitmap().getWidth() / 2 <= 0) {
					zombie.getSpeed().toggleXDirection();
				}
				// check collision with bottom wall if heading down
				if (zombie.getSpeed().getyDirection() == Speed.DIRECTION_DOWN
						&& zombie.getY() + zombie.getBitmap().getHeight() / 2 >= getHeight()) {
					zombie.getSpeed().toggleYDirection();
				}
				// check collision with top wall if heading up
				if (zombie.getSpeed().getyDirection() == Speed.DIRECTION_UP
						&& zombie.getY() - zombie.getBitmap().getHeight() / 2 <= 0) {
					zombie.getSpeed().toggleYDirection();
				}

				if( isCollision(player, zombie) ) {
					player.decrementLife();
					vibe.vibrate(500);
					player.setBitmap(_red);
//					zombies.remove(zombie);
//					Zombie z = new Zombie(_zombie, generator.nextInt()%height + 10, generator.nextInt()%width + 10);
//					zombies.add(z);
					//take away health
					//maybe do something else
				}


				zombie.update();
			}
			for(Health health : heals) {
				health.update();
				if(health.getX() >= height) {
					heals.remove(health);
					Bitmap heal;
					int score;
					int rand = generator.nextInt()%3;
					if(rand < 2) {
						heal = _smallOrb;
						score = 2;
					} else {
						heal = _bigOrb;
						score = 5;
					}
					heals.add(new Health(heal, 0, generator.nextInt((int)(rightbound-leftbound))+(int)leftbound, objectSpeed, score));
				}
				if(isCollision(player, health)) {
					player.addHealthPieces(health.getScore());
					heals.remove(health);
					Bitmap heal;
					int score;
					int rand = generator.nextInt()%3;
					if(rand < 2) {
						heal = _smallOrb;
						score = 2;
					} else {
						heal = _bigOrb;
						score = 5;
					}
					heals.add(new Health(heal, 0, generator.nextInt((int)(rightbound-leftbound))+(int)leftbound, objectSpeed, score));
					//increase health of player
					//make health orb go away
				}
			}
			
			for(Obstacle obstacle : obstacles) {
				obstacle.update();
				if(obstacle.getX() >= height) {
					obstacle.setX(0);
					Bitmap obs;
					int rand = generator.nextInt()%4;
					if(rand == 0)
						obs = _boulder;
					else if(rand == 1)
						obs = _tree;
					else if(rand == 2)
						obs = _streetLamp;
					else
						obs = _car;
					obstacle.setBitmap(obs);
					obstacle.setSpeed(objectSpeed);
					obstacle.setY(generator.nextInt((int)(rightbound-leftbound))+(int)leftbound);
				}
				
				if( isCollision(player, obstacle) ) {
					player.decrementLife();
					vibe.vibrate(500);
					player.setBitmap(_red);
//					zombies.remove(zombie);
//					Zombie z = new Zombie(_zombie, generator.nextInt()%height + 10, generator.nextInt()%width + 10);
//					zombies.add(z);
					//take away health
					//maybe do something else
				}
			}

			//update player
			player.update(ySpeed, leftbound, rightbound);
		}
	}
	
	public boolean isCollision(Player player, Zombie zombie) {
		float playerLeft = player.getY() - player.getBitmap().getWidth()/2;
		float playerRight = player.getY() + player.getBitmap().getWidth()/2;
		float playerTop = player.getX() - player.getBitmap().getHeight()/2;
		float playerBottom = player.getX() + player.getBitmap().getHeight()/2;
		float zombieLeft = zombie.getY() - zombie.getBitmap().getWidth()/2;
		float zombieRight = zombie.getY() + zombie.getBitmap().getWidth()/2;
		float zombieTop = zombie.getX() + zombie.getBitmap().getHeight()/2;
		float zombieBottom = zombie.getX() - zombie.getBitmap().getHeight()/2;		

		if( (zombieRight > playerLeft && zombieBottom < playerTop && zombieTop > playerTop && zombieLeft < playerLeft) ||
				(zombieRight > playerRight && zombieBottom < playerTop && zombieTop > playerTop && zombieLeft < playerRight) ||
				(zombieRight > playerLeft && zombieBottom < playerBottom && zombieTop > playerBottom && zombieLeft < playerLeft) ||
				(zombieRight > playerRight && zombieBottom < playerBottom && zombieTop > playerBottom && zombieLeft < playerRight) ||
				(zombieRight < playerRight && zombieBottom < playerBottom && zombieTop > playerTop && zombieLeft > playerLeft)) 
					return true;

		return false;
	}

	public boolean isCollision(Player player, Health health) {
		float playerLeft = player.getY() - player.getBitmap().getWidth()/2;
		float playerRight = player.getY() + player.getBitmap().getWidth()/2;
		float playerTop = player.getX() - player.getBitmap().getHeight()/2;
		float playerBottom = player.getX() + player.getBitmap().getHeight()/2;
		float healthLeft = health.getY() - health.getBitmap().getWidth()/2;
		float healthRight = health.getY() + health.getBitmap().getWidth()/2;
		float healthTop = health.getX() - health.getBitmap().getWidth()/2;
		float healthBottom = health.getX() + health.getBitmap().getWidth()/2;		

		if( (healthRight > playerLeft && healthBottom < playerTop && healthTop > playerTop && healthLeft < playerLeft) ||
			(healthRight > playerRight && healthBottom < playerTop && healthTop > playerTop && healthLeft < playerRight) ||
			(healthRight > playerLeft && healthBottom < playerBottom && healthTop > playerBottom && healthLeft < playerLeft) ||
			(healthRight > playerRight && healthBottom < playerBottom && healthTop > playerBottom && healthLeft < playerRight) ||
			(healthRight < playerRight && healthBottom < playerBottom && healthTop > playerTop && healthLeft > playerLeft)) 
				return true;

		return false;
	}

	 public boolean isCollision(Player player, Obstacle obstacle) {
		 float playerLeft = player.getY() - player.getBitmap().getWidth()/2;
		 float playerRight = player.getY() + player.getBitmap().getWidth();
		 float playerTop = player.getX() - player.getBitmap().getHeight()/2;
		 float playerBottom = player.getX() - player.getBitmap().getHeight()/2;
		 float obstacleLeft = obstacle.getY() - obstacle.getBitmap().getWidth()/2;
		 float obstacleRight = obstacle.getY() + obstacle.getBitmap().getWidth()/2;
		 float obstacleTop = obstacle.getX() + obstacle.getBitmap().getHeight()/2;
		 float obstacleBottom = obstacle.getX() - obstacle.getBitmap().getHeight()/2;		


		 if( (obstacleRight > playerLeft && obstacleBottom < playerTop && obstacleTop > playerTop && obstacleLeft < playerLeft) ||
			 (obstacleRight > playerRight && obstacleBottom < playerTop && obstacleTop > playerTop && obstacleLeft < playerRight) ||
			 (obstacleRight > playerLeft && obstacleBottom < playerBottom && obstacleTop > playerBottom && obstacleLeft < playerLeft) ||
			 (obstacleRight > playerRight && obstacleBottom < playerBottom && obstacleTop > playerBottom && obstacleLeft < playerRight) ) 
				 return true;

		 return false;
	 }

	public void destroy() {
		
//		boolean retry = true;
//		while (retry) {
//			try {
//				thread.join();
//				retry = false;
//			} catch (InterruptedException e) {
//				// try again shutting down the thread
//			}
//		}
		getHolder().removeCallback(this);
		for(Zombie zombie : zombies) {
			zombie.destroy();
		}
		for(Health health : heals) {
			health.destroy();
		}
		for(Obstacle obstacle : obstacles) {
			obstacle.destroy();
		}
		player.destroy();
		zombies.clear();
		heals.clear();
		obstacles.clear();
		player = null;
		generator = null;
	}
	
	private void mission(Canvas canvas) {
		//canvas.drawBitmap(bitmap, left, top, paint);
	}
}
