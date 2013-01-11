package com.graupnerservo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
	protected int _splashTime = 5000;
	private Thread splashTread;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        final MainActivity sPlashScreen = this;

		// thread for displaying the SplashScreen
		splashTread = new Thread() {
			@Override
			public void run() {
				try {
					synchronized (this) {
						wait(_splashTime);
					}

				} catch (InterruptedException e) {
				} finally {
					Intent i = new Intent();
					i.setClass(sPlashScreen, Start.class);
					startActivity(i);
					finish();
				}
			}
		};

		splashTread.start();
    }
	// Function that will handle the touch
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			synchronized (splashTread) {
				splashTread.notifyAll();
			}
		}
		return true;
	}

	/**
	 * @see android.app.Activity#onStart()
	 */
	@Override
	protected void onStart() {
		super.onStart();
	}
}