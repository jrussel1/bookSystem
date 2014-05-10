package com.book.system.android.appengine;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;

public class MainActivity extends Activity {

    private final int SPLASH_DISPLAY_LENGTH = 3000;
	
    @Override

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

                Intent mainIntent = new Intent(MainActivity.this, GooglePlusLoginActivity.class);
                MainActivity.this.startActivity(mainIntent);
                MainActivity.this.finish();
            }
        
    }

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		MenuInflater mif = getMenuInflater();
//		mif.inflate(R.menu.main_activity_action, menu);
//		return super.onCreateOptionsMenu(menu);
//	}

