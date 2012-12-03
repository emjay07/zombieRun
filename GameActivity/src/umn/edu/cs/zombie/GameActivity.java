package umn.edu.cs.zombie;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

public class GameActivity extends Activity {
    /** Called when the activity is first created. */
	
	private static final String TAG = GameActivity.class.getSimpleName();
	private View popupView = null;
	private PopupWindow popupWindow = null;
	private boolean inGame = false;
	private MainGamePanel gamePanel = null;
	private int rangeCounter = 3;
	private TextView rangeTextView;
	private int meleeCounter = 3;
	private TextView meleeTextView;
	private boolean inUpgrades = false;
	private Bundle saved_instance_state;
	
	@Override
    public void onBackPressed() {
       Log.d("CDA", "onBackPressed Called");
       if(inGame) {
     	  inGame = false; 
    	  gamePanel.destroy();
    	  gamePanel = null;
    	  setContentView(R.layout.activity_main);
       } else if(inUpgrades) {
    	   inUpgrades = false;
    	   setContentView(R.layout.activity_main);
       } else {
	      if(popupWindow == null) {
	    	  finish();
	      } else {
	    	  popupWindow.dismiss();
	    	  popupWindow = null;
	      }
      }
    }
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        saved_instance_state = savedInstanceState;
        // requesting to turn the title OFF
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // making it full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // set our MainGamePanel as the View
        setContentView(R.layout.activity_main);
        
        final Button playButton = (Button) findViewById(R.id.button1);
        playButton.setOnClickListener(new Button.OnClickListener() {
        	public void onClick(View arg0) {
        		//change the second arguement to the intent constructor to change what it runs when 
        		//you hit play. 
        		 inGame = true;
        		 gamePanel = new MainGamePanel(GameActivity.this);
        		 if(popupWindow != null) {
        			 popupWindow.dismiss();
        			 popupWindow = null;
        		 }
        		 setContentView(gamePanel);
        	     Log.d(TAG, "View added");
        	}
        });
        
        //creating the popup screen when select level is hit
        final Button selectLevelPopup = (Button)findViewById(R.id.button2);
        selectLevelPopup.setOnClickListener(new Button.OnClickListener(){
        	@Override
    	    public void onClick(View arg0) {
    	    	
    	    	if(popupWindow != null) {
    	    		popupWindow.dismiss();
    	    		popupWindow = null;
    	    	}
	    	    LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);  
	    	    popupView = layoutInflater.inflate(R.layout.select_level_popup, null);
	    	    popupWindow = new PopupWindow(popupView, LayoutParams.WRAP_CONTENT,  LayoutParams.WRAP_CONTENT);
	    	    
	    	    
	    	    //TODO: figure out a better way to set all the on click functions for the 
	    	    //		buttons in this menu
	            Button level1btn = (Button)popupView.findViewById(R.id.level1);
	            level1btn.setOnClickListener(new Button.OnClickListener(){
				     @Override
				     public void onClick(View v) {
				    	 //TODO set level

				    	 popupWindow.dismiss();
				    	 popupWindow = null;
				     }
				});
	            Button level2btn = (Button)popupView.findViewById(R.id.level2);
	            level2btn.setOnClickListener(new Button.OnClickListener(){
				     @Override
				     public void onClick(View v) {
				    	 //TODO set level
				    	 popupWindow.dismiss();
				    	 popupWindow = null;
				     }
				});
	            
	            Button level3btn = (Button)popupView.findViewById(R.id.level3);
	            level3btn.setOnClickListener(new Button.OnClickListener(){
				     @Override
				     public void onClick(View v) {
				    	 //TODO set level
					     popupWindow.dismiss();
					     popupWindow = null;
				     }
				});
	            Display display = getWindowManager().getDefaultDisplay();
	            @SuppressWarnings("deprecation")
				int ScreenWidth = (display.getWidth()*4)/5; 
	            @SuppressWarnings("deprecation")
				int ScreenHeight = display.getHeight(); 
	    	    popupWindow.setHeight(ScreenHeight);
	    	    popupWindow.setWidth(ScreenWidth);
	    	    popupWindow.showAtLocation(selectLevelPopup, Gravity.LEFT, 0, 0);
        	}
        });
        
        //creating the upgrade menu
        final Button upgradeMenuPopup = (Button)findViewById(R.id.button3);
        upgradeMenuPopup.setOnClickListener(new Button.OnClickListener(){
        	@Override
    	    public void onClick(View arg0) {
    	    	inUpgrades = true;
    	    	if(popupWindow != null) {
    	    		popupWindow.dismiss();
    	    		popupWindow = null;
    	    	}
    	    	setContentView(R.layout.upgrades_menu_popup);
    	    	
    	    	final Button upgradesPlayButton = (Button) findViewById(R.id.upgradesButton1);
    	        upgradesPlayButton.setOnClickListener(new Button.OnClickListener() {
    	        	public void onClick(View arg0) {
    	        		//change the second arguement to the intent constructor to change what it runs when 
    	        		//you hit play. 
    	        		 inGame = true;
    	        		 gamePanel = new MainGamePanel(GameActivity.this);
    	        		 if(popupWindow != null) {
    	        			 popupWindow.dismiss();
    	        			 popupWindow = null;
    	        		 }
    	        		 setContentView(gamePanel);
    	        	     Log.d(TAG, "View added");
    	        	}
    	        });
    	        
    	        //creating the popup screen when select level is hit
    	        final Button upgradesSelectLevelPopup = (Button)findViewById(R.id.upgradesButton2);
    	        upgradesSelectLevelPopup.setOnClickListener(new Button.OnClickListener(){
    	        	@Override
    	    	    public void onClick(View arg0) {
    	    	    	
    	        		setContentView(R.layout.activity_main);
    	        		
    	                final Button hackedPlayButton = (Button) findViewById(R.id.button1);
    	                hackedPlayButton.setOnClickListener(new Button.OnClickListener() {
    	                	public void onClick(View arg0) {
    	                		//change the second arguement to the intent constructor to change what it runs when 
    	                		//you hit play. 
    	                		 inGame = true;
    	                		 gamePanel = new MainGamePanel(GameActivity.this);
    	                		 if(popupWindow != null) {
    	                			 popupWindow.dismiss();
    	                			 popupWindow = null;
    	                		 }
    	                		 setContentView(gamePanel);
    	                	     Log.d(TAG, "View added");
    	                	}
    	                });
  	                
    	              //creating the popup screen when select level is hit
    	                final Button hackedSelectLevelPopup = (Button)findViewById(R.id.button2);
    	                hackedSelectLevelPopup.setOnClickListener(new Button.OnClickListener(){
    	                	@Override
    	            	    public void onClick(View arg0) {
    	            	    	
    	            	    	if(popupWindow != null) {
    	            	    		popupWindow.dismiss();
    	            	    		popupWindow = null;
    	            	    	}
    	        	    	    LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);  
    	        	    	    popupView = layoutInflater.inflate(R.layout.select_level_popup, null);
    	        	    	    popupWindow = new PopupWindow(popupView, LayoutParams.WRAP_CONTENT,  LayoutParams.WRAP_CONTENT);
    	        	    	    
    	        	    	    
    	        	    	    //TODO: figure out a better way to set all the on click functions for the 
    	        	    	    //		buttons in this menu
    	        	            Button level1btn = (Button)popupView.findViewById(R.id.level1);
    	        	            level1btn.setOnClickListener(new Button.OnClickListener(){
    	        				     @Override
    	        				     public void onClick(View v) {
    	        				    	 //TODO set level

    	        				    	 popupWindow.dismiss();
    	        				    	 popupWindow = null;
    	        				     }
    	        				});
    	        	            Button level2btn = (Button)popupView.findViewById(R.id.level2);
    	        	            level2btn.setOnClickListener(new Button.OnClickListener(){
    	        				     @Override
    	        				     public void onClick(View v) {
    	        				    	 //TODO set level
    	        				    	 popupWindow.dismiss();
    	        				    	 popupWindow = null;
    	        				     }
    	        				});
    	        	            
    	        	            Button level3btn = (Button)popupView.findViewById(R.id.level3);
    	        	            level3btn.setOnClickListener(new Button.OnClickListener(){
    	        				     @Override
    	        				     public void onClick(View v) {
    	        				    	 //TODO set level
    	        					     popupWindow.dismiss();
    	        					     popupWindow = null;
    	        				     }
    	        				});
    	        	            Display display = getWindowManager().getDefaultDisplay();
    	        	            @SuppressWarnings("deprecation")
    	        				int ScreenWidth = (display.getWidth()*4)/5; 
    	        	            @SuppressWarnings("deprecation")
    	        				int ScreenHeight = display.getHeight(); 
    	        	    	    popupWindow.setHeight(ScreenHeight);
    	        	    	    popupWindow.setWidth(ScreenWidth);
    	        	    	    popupWindow.showAtLocation(selectLevelPopup, Gravity.LEFT, 0, 0);
    	                	}
    	                });
    	        		
    	    	    	if(popupWindow != null) {
    	    	    		popupWindow.dismiss();
    	    	    		popupWindow = null;
    	    	    	}
    		    	    LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);  
    		    	    popupView = layoutInflater.inflate(R.layout.select_level_popup, null);
    		    	    popupWindow = new PopupWindow(popupView, LayoutParams.WRAP_CONTENT,  LayoutParams.WRAP_CONTENT);
    		    	    
    		    	    
    		    	    //TODO: figure out a better way to set all the on click functions for the 
    		    	    //		buttons in this menu
    		            Button level1btn = (Button)popupView.findViewById(R.id.level1);
    		            level1btn.setOnClickListener(new Button.OnClickListener(){
    					     @Override
    					     public void onClick(View v) {
    					    	 //TODO set level

    					    	 popupWindow.dismiss();
    					    	 popupWindow = null;
    					     }
    					});
    		            Button level2btn = (Button)popupView.findViewById(R.id.level2);
    		            level2btn.setOnClickListener(new Button.OnClickListener(){
    					     @Override
    					     public void onClick(View v) {
    					    	 //TODO set level
    					    	 popupWindow.dismiss();
    					    	 popupWindow = null;
    					     }
    					});
    		            
    		            Button level3btn = (Button)popupView.findViewById(R.id.level3);
    		            level3btn.setOnClickListener(new Button.OnClickListener(){
    					     @Override
    					     public void onClick(View v) {
    					    	 //TODO set level
    						     popupWindow.dismiss();
    						     popupWindow = null;
    					     }
    					});
    		            Display display = getWindowManager().getDefaultDisplay();
    		            @SuppressWarnings("deprecation")
    					int ScreenWidth = (display.getWidth()*4)/5; 
    		            @SuppressWarnings("deprecation")
    					int ScreenHeight = display.getHeight(); 
    		    	    popupWindow.setHeight(ScreenHeight);
    		    	    popupWindow.setWidth(ScreenWidth);
    		    	    popupWindow.showAtLocation(selectLevelPopup, Gravity.LEFT, 0, 0);
    	        	}
    	        });
    	        //removing for hacking purposes
//	    	    LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);  
//	    	    popupView = layoutInflater.inflate(R.layout.upgrades_menu_popup, null);
//	    	    popupWindow = new PopupWindow(popupView, LayoutParams.WRAP_CONTENT,  LayoutParams.WRAP_CONTENT);
    	        Display display = getWindowManager().getDefaultDisplay();
    	        
	            Button rangePlusbtn = (Button) findViewById(R.id.rangePlus);
	            rangePlusbtn.setOnClickListener(new Button.OnClickListener(){
				     @Override
				     public void onClick(View v) {
				    	 //TODO: change the text in the upgrades menu
				    	 rangeTextView = (TextView) findViewById(R.id.rangeText);
				    	 rangeCounter++;
				    	 rangeTextView.setText(Integer.toString(rangeCounter));
				    	 
				     }
				});

	            
	            Button rangeMinusbtn = (Button) findViewById(R.id.rangeMinus);
	            rangeMinusbtn.setOnClickListener(new Button.OnClickListener(){
				     @Override
				     public void onClick(View v) {
				    	 //TODO: change the text in the upgrades menu
				    	 rangeTextView = (TextView) findViewById(R.id.rangeText);
				    	 rangeCounter--;
				    	 rangeTextView.setText(Integer.toString(rangeCounter));
				    	 
				     }
				});
	            Button meleePlusbtn = (Button) findViewById(R.id.meleePlus);
	            meleePlusbtn.setOnClickListener(new Button.OnClickListener(){
				     @Override
				     public void onClick(View v) {
				    	 //TODO: change the text in the upgrades menu
				    	 meleeTextView = (TextView) findViewById(R.id.meleeText);
				    	 meleeCounter++;
				    	 meleeTextView.setText(Integer.toString(meleeCounter));
				    	 
				     }
				});

	            
	            Button meleeMinusbtn = (Button) findViewById(R.id.meleeMinus);
	            meleeMinusbtn.setOnClickListener(new Button.OnClickListener(){
				     @Override
				     public void onClick(View v) {
				    	 //TODO: change the text in the upgrades menu
				    	 meleeTextView = (TextView) findViewById(R.id.meleeText);
				    	 meleeCounter--;
				    	 meleeTextView.setText(Integer.toString(meleeCounter));
				    	 
				     }
				});
//	            Display display = getWindowManager().getDefaultDisplay();
//	            int ScreenWidth = (display.getWidth()*4)/5; 
//	            int ScreenHeight = display.getHeight(); 
//	    	    popupWindow.setHeight(ScreenHeight);
//	    	    popupWindow.setWidth(ScreenWidth);
//	    	    popupWindow.showAtLocation(selectLevelPopup, Gravity.LEFT, 0, 0);
        	}
        });
        
        final Button StatisticsPopup = (Button)findViewById(R.id.button4);
        StatisticsPopup.setOnClickListener(new Button.OnClickListener(){
        	@Override
    	    public void onClick(View arg0) {
    	    	if(popupWindow != null) {
    	    		popupWindow.dismiss();
    	    		popupWindow = null;
    	    	}
	    	    LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);  
	    	    popupView = layoutInflater.inflate(R.layout.statistics_popup, null);
	    	    popupWindow = new PopupWindow(popupView, LayoutParams.WRAP_CONTENT,  LayoutParams.WRAP_CONTENT);
	            Display display = getWindowManager().getDefaultDisplay();
	            int ScreenWidth = (display.getWidth()*4)/5; 
	            int ScreenHeight = display.getHeight(); 
	    	    popupWindow.setHeight(ScreenHeight);
	    	    popupWindow.setWidth(ScreenWidth);
	    	    popupWindow.showAtLocation(selectLevelPopup, Gravity.LEFT, 0, 0);
    	    }
        });
        
        final Button TutorialPopup = (Button)findViewById(R.id.button5);
        TutorialPopup.setOnClickListener(new Button.OnClickListener(){
        	@Override
    	    public void onClick(View arg0) {
    	    	if(popupWindow != null) {
    	    		popupWindow.dismiss();
    	    		popupWindow = null;
    	    	}
	    	    LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);  
	    	    popupView = layoutInflater.inflate(R.layout.tutorial_popup, null);
	    	    popupWindow = new PopupWindow(popupView, LayoutParams.WRAP_CONTENT,  LayoutParams.WRAP_CONTENT);
	            Display display = getWindowManager().getDefaultDisplay();
	            int ScreenWidth = (display.getWidth()*4)/5; 
	            int ScreenHeight = display.getHeight(); 
	    	    popupWindow.setHeight(ScreenHeight);
	    	    popupWindow.setWidth(ScreenWidth);
	    	    popupWindow.showAtLocation(selectLevelPopup, Gravity.LEFT, 0, 0);
    	    }
        });
        
    }

	@Override
	protected void onDestroy() {
		Log.d(TAG, "Destroying...");
		super.onDestroy();
	}

	@Override
	protected void onStop() {
		Log.d(TAG, "Stopping...");
		super.onStop();
	}
    
    
}