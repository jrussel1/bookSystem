package com.book.system.android.appengine;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.os.Build;

public class EditingActivity extends Activity {
	private final String LOG_TAG = "EditingActivity";
	private String currentUserEmail = null;
	private String currentUserFirstName = null;
	private String currentUserLastName = null;
	
	private String bookISBN = null;
	private String bookTitle = null;
	private String bookPrice = null;
	private String bookAuthor = null;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editing);

		Typeface tf = Typeface.createFromAsset(getAssets(),
		        "fonts/Roboto-Thin.ttf");
		Typeface tf2 = Typeface.createFromAsset(getAssets(),
		        "fonts/Roboto-Light.ttf");
		

		Intent intent = getIntent();
		bookISBN = intent.getStringExtra("isbn");
		bookTitle = intent.getStringExtra("bookTitle");
		bookPrice = intent.getStringExtra("price");
		bookAuthor = intent.getStringExtra("bookAuthor");

		currentUserFirstName = intent.getStringExtra("first_name");
		currentUserLastName = intent.getStringExtra("last_name");
		currentUserEmail = intent.getStringExtra("CURRENT_USER_EMAIL");
		
		Log.d(LOG_TAG,intent.getExtras().toString());
	

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.editing, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_editing,
					container, false);
			return rootView;
		}
	}

}
