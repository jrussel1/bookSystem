package com.book.system.android.appengine;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BookDetailActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_book_detail);
		
		Typeface tf = Typeface.createFromAsset(getAssets(),
		        "fonts/Roboto-Light.ttf");
		Typeface tf2 = Typeface.createFromAsset(getAssets(),
		        "fonts/Roboto-Regular.ttf");
		

		Intent intent = getIntent();
		String value = intent.getStringExtra("ISBNkey");
		String bookName = intent.getStringExtra("nameKey");
		String price = intent.getStringExtra("priceKey");

		TextView t = (TextView)findViewById(R.id.bookTitleTag);
		t.setText(bookName);
		t.setTypeface(tf);

		TextView t1 = (TextView)findViewById(R.id.isbn);
		t1.setText(value);
		t1.setTypeface(tf);

		TextView t2 = (TextView)findViewById(R.id.price);
		t2.setText(price);
		t2.setTypeface(tf);
		
		TextView t4 = (TextView)findViewById(R.id.bookTitle_Static);
		t4.setTypeface(tf2);
		
		TextView t5 = (TextView)findViewById(R.id.ISBN_static);
		t5.setTypeface(tf2);
		
		TextView t6 = (TextView)findViewById(R.id.price_static);
		t6.setTypeface(tf2);
		
		TextView t7 = (TextView)findViewById(R.id.purchaseButton);
		t7.setTypeface(tf2);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.book_detail, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_book_detail,
					container, false);
			return rootView;
		}
	}

}
