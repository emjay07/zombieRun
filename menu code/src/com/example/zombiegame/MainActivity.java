package com.example.zombiegame;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.PopupWindow;
import android.view.Display;
import android.view.MenuItem;
//import android.view.WindowManager.LayoutParams;
import android.widget.FrameLayout;

public class MainActivity extends Activity {
	
	View popupView = null;
	PopupWindow popupWindow = null;
	
	@Override
	   public void onBackPressed() {
	      Log.d("CDA", "onBackPressed Called");
	      if(popupWindow == null) {
	    	  finish();
	      } else {
	    	  popupWindow.dismiss();
	      }
	   }
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); //hide title bar
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        final Button playButton = (Button) findViewById(R.id.button1);
        playButton.setOnClickListener(new Button.OnClickListener() {
        	public void onClick(View arg0) {
        		//TODO: start new activity
        	}
        });
        
        //creating the popup screen when select level is hit
        final Button selectLevelPopup = (Button)findViewById(R.id.button2);
        selectLevelPopup.setOnClickListener(new Button.OnClickListener(){
        	//@Override
    	    public void onClick(View arg0) {
	    	    LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);  
	    	    popupView = layoutInflater.inflate(R.layout.select_level_popup, null);
	    	    popupWindow = new PopupWindow(popupView, LayoutParams.WRAP_CONTENT,  LayoutParams.WRAP_CONTENT);
	    	    
	    	    
	    	    //TODO: figure out a better way to set all the on click functions for the 
	    	    //		buttons in this menu
	            Button level1btn = (Button)popupView.findViewById(R.id.level1);
	            level1btn.setOnClickListener(new Button.OnClickListener(){
				     //@Override
				     public void onClick(View v) {
				    	 //TODO set level
				      popupWindow.dismiss();
				      popupWindow = null;
				     }
				});
	            Button level2btn = (Button)popupView.findViewById(R.id.level2);
	            level2btn.setOnClickListener(new Button.OnClickListener(){
				     //@Override
				     public void onClick(View v) {
				    	 //TODO set level
				      popupWindow.dismiss();
				      popupWindow = null;
				     }
				});
	            
	            Button level3btn = (Button)popupView.findViewById(R.id.level3);
	            level2btn.setOnClickListener(new Button.OnClickListener(){
				     //@Override
				     public void onClick(View v) {
				    	 //TODO set level
				      popupWindow.dismiss();
				      popupWindow = null;
				     }
				});
	    	    popupWindow.setHeight(1000);
	    	    popupWindow.setWidth(1000);
	    	    popupWindow.showAtLocation(selectLevelPopup, Gravity.LEFT, 0, 0);
        	}
        });
        
        //creating the upgrade menu
        final Button upgradeMenuPopup = (Button)findViewById(R.id.button3);
        upgradeMenuPopup.setOnClickListener(new Button.OnClickListener(){
        	//@Override
    	    public void onClick(View arg0) {
	    	    LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);  
	    	    popupView = layoutInflater.inflate(R.layout.upgrades_menu_popup, null);
	    	    popupWindow = new PopupWindow(popupView, LayoutParams.WRAP_CONTENT,  LayoutParams.WRAP_CONTENT);
	    	    
	    	    
	             Button rangePlusbtn = (Button)popupView.findViewById(R.id.rangePlus);
	             rangePlusbtn.setOnClickListener(new Button.OnClickListener(){
				     //@Override
				     public void onClick(View v) {
				    	 //TODO: change the text in the upgrades menu
				     }
				});
	            Button rangeMinusbtn = (Button)popupView.findViewById(R.id.rangeMinus);
	            rangeMinusbtn.setOnClickListener(new Button.OnClickListener(){
				     //@Override
				     public void onClick(View v) {
				    	 //TODO: change the text in the upgrades menu
				     }
				});
	    	    popupWindow.setHeight(1000);
	    	    popupWindow.setWidth(1000);
	    	    popupWindow.showAtLocation(selectLevelPopup, Gravity.LEFT, 100, 100);
        	}
        });
        
        //place holder
        final Button StatisticsPopup = (Button)findViewById(R.id.button4);
        StatisticsPopup.setOnClickListener(new Button.OnClickListener(){
        	//@Override
    	    public void onClick(View arg0) {
	    	    LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);  
	    	    popupView = layoutInflater.inflate(R.layout.default_view, null);
	    	    popupWindow = new PopupWindow(popupView, LayoutParams.WRAP_CONTENT,  LayoutParams.WRAP_CONTENT);
	    	    
    	    }
        });
        
        //place holder
        final Button TutorialPopup = (Button)findViewById(R.id.button5);
        TutorialPopup.setOnClickListener(new Button.OnClickListener(){
        	//@Override
    	    public void onClick(View arg0) {
	    	    LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);  
	    	    popupView = layoutInflater.inflate(R.layout.default_view, null);
	    	    popupWindow = new PopupWindow(popupView, LayoutParams.WRAP_CONTENT,  LayoutParams.WRAP_CONTENT);
	    	    
    	    }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
