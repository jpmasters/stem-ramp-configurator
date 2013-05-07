package com.siemens.stem;

import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.app.Activity;
import android.content.Intent;

/**
 * Implements the splash activity.
 * @author Jon Masters
 *
 */
public class SplashActivity extends Activity {

	/*
	 * The time the splash screen stays active in ms
	 */
	static private int SPLASH_TIME = 3000; 
	
	/**
	 * Called when the activity is first created.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// put the splash screen into full screen.
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
	        
		setContentView(R.layout.activity_splash);
	}

	/**
	 * Called once the activity is displayed
	 */
	protected void onResume() {
		super.onResume();
		
		// create a new runnable to handle an event to move to
		// the main activity.
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                //Finish the splash activity so it can't be returned to.
                SplashActivity.this.finish();
                
                // Create an Intent that will start the main activity.
                Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                SplashActivity.this.startActivity(mainIntent);
            }
        }, SPLASH_TIME);
	}
}
