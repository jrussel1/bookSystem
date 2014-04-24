package com.book.system.android.appengine;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

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
		
		setTitle("Sell Book");
		
		mISBN = (EditText)findViewById(R.id.EditText_ISBN);
		mBookTitle = (EditText)findViewById(R.id.EditText_BookTitle);
		mPrice = (EditText)findViewById(R.id.EditText_Price);
		mAddButton = (Button)findViewById(R.id.button_AddBook);

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
