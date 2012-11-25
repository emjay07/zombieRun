package com.example.zombiegame;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.ImageView;

public class GameActivity extends Activity implements OnGestureListener{

	
    private GestureDetector gestureDetector;
    
    
    @SuppressWarnings("deprecation")
	@Override
    public void onCreate(Bundle savedInstanceState) {
    	requestWindowFeature(Window.FEATURE_NO_TITLE); //hide title bar
    	super.onCreate(savedInstanceState);

        setContentView(R.layout.default_view);
        gestureDetector = new GestureDetector(this);
    }
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }


	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}


	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		if(e1.getRawY() < e2.getRawY()) {
			((ImageView) findViewById(R.id.test_image)).setImageResource(R.drawable.appa2);
		}
		if(e1.getRawY() > e2.getRawY()) {
			((ImageView) findViewById(R.id.test_image)).setImageResource(R.drawable.ic_launcher);
		}
		if(e1.getRawX() < e2.getRawX()) {
			((ImageView) findViewById(R.id.test_image)).setImageResource(R.drawable.right_arrow);
		}
		if(e1.getRawX() > e2.getRawX()) {
			((ImageView) findViewById(R.id.test_image)).setImageResource(R.drawable.left_arrow);
		}
		return false;
	}

	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}


	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}


	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}


	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent me) {
		return gestureDetector.onTouchEvent(me);
	}
}
