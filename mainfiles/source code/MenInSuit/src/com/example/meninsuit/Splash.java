package com.example.meninsuit;

//import android.R;
//import com.Broken_Heart_Wallpaper.R;

//import android.R;
//import disney_land_wallpaper.R;

//import com.disney_land_wallpaper.R;

//import android.R;
//import android.R;
//import android.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;

public class Splash extends Activity {
protected int _splashTime = 2000; 
	
	private Thread splashTread;

	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        requestWindowFeature(Window.FEATURE_NO_TITLE);
	        setContentView(R.layout.slash_screen);	

	        final Splash sPlashScreen = this; 
		    
		    // thread for displaying the SplashScreen
		    splashTread = new Thread() {
		        @Override
		        public void run() {
		            try {	            	
		            	synchronized(this){
		            		wait(_splashTime);
		            	}
		            	
		            } catch(InterruptedException e) {} 
		            finally {
		                finish();
		                
		                Intent i = new Intent();
		                i.setClass(Splash.this, MainActivity.class);
		        		startActivity(i);
		                
		               try {
						sleep(_splashTime);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		            }
		        }
		    };
		    
		    splashTread.start();
		}
		@Override
		public boolean onTouchEvent(MotionEvent event) {
		    if (event.getAction() == MotionEvent.ACTION_DOWN) {
		    	synchronized(splashTread){
		    		splashTread.notifyAll();
		    	}
		    }
		    return true;
		}
	    }
	 