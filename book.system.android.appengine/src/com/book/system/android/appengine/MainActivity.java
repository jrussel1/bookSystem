package com.book.system.android.appengine;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
<<<<<<< HEAD
		Intent intent = new Intent(this, BookListActivity.class);
//		Intent intent = new Intent(this, GooglePlusLoginActivity.class);
=======
//		Intent intent = new Intent(this, GooglePlusLoginActivity.class);
		Intent intent = new Intent(this, BookListActivity.class);
>>>>>>> d072249043d4d395e428f160d8fede801a6cc717
		startActivity(intent);
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}

}