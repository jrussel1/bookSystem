package com.book.system.android.appengine;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddBookActivity extends Activity {
//test Alex
	protected EditText mISBN;
	protected EditText mBookTitle;
	protected EditText mPrice;
	protected Button mAddButton;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_book);
		
		Typeface tf = Typeface.createFromAsset(getAssets(),
		        "fonts/Roboto-Thin.ttf");
		Typeface tf2 = Typeface.createFromAsset(getAssets(),
		        "fonts/Roboto-Light.ttf");
		
		setTitle("Sell Book");
		
		mISBN = (EditText)findViewById(R.id.EditText_ISBN);
		mISBN.setTypeface(tf2);
		mBookTitle = (EditText)findViewById(R.id.EditText_BookTitle);
		mBookTitle.setTypeface(tf2);
		mPrice = (EditText)findViewById(R.id.EditText_Price);
		mPrice.setTypeface(tf2);
		mAddButton = (Button)findViewById(R.id.button_AddBook);
		mAddButton.setTypeface(tf2);
		
		TextView t = (TextView)findViewById(R.id.SearchTest);
		t.setTypeface(tf2);
		TextView t1 = (TextView)findViewById(R.id.TextEdit_BookTitle);
		t1.setTypeface(tf2);
		TextView t2 = (TextView)findViewById(R.id.price_static);
		t2.setTypeface(tf2);
		
		

		


		

		mAddButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String ISBN = mISBN.getText().toString();
				String bookTitle = mBookTitle.getText().toString();
				String price = mPrice.getText().toString();
				
				ISBN = ISBN.trim();
				bookTitle = bookTitle.trim();
				price = price.trim();
				
				
				AlertDialog.Builder builder = new AlertDialog.Builder(AddBookActivity.this);
				builder.setMessage("ISBN:  " + ISBN + "   BookTitle:" + bookTitle + "    Price:" + price);
				builder.setTitle("test");
				builder.setPositiveButton(android.R.string.ok, null);
				
				AlertDialog dialog = builder.create();
				dialog.show();
				
			}
		});

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_book, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_add_book,
					container, false);
			return rootView;
		}
	}

}
