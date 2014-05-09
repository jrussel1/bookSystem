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
import android.widget.Toast;

public class BookDetailActivity extends Activity {
	private String currentUserEmail = null;
	private String bookISBN = null;
	private String bookTitle = null;
	private String bookPrice = null;
	private String bookAuthor = null;
	private String firstName = null;
	private String lastName = null;
	private String sellerEmail = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_book_detail);
		
		Typeface tf = Typeface.createFromAsset(getAssets(),
		        "fonts/Roboto-Thin.ttf");
		Typeface tf2 = Typeface.createFromAsset(getAssets(),
		        "fonts/Roboto-Light.ttf");
		

		Intent intent = getIntent();
		bookISBN = intent.getStringExtra("isbn");
		bookTitle = intent.getStringExtra("bookTitle");
		bookPrice = intent.getStringExtra("price");
		bookAuthor = intent.getStringExtra("bookAuthor");
		firstName = intent.getStringExtra("sellerFirstName");
		lastName = intent.getStringExtra("sellerLastName");
		sellerEmail = intent.getStringExtra("sellerEmail");
		
		currentUserEmail = intent.getStringExtra("CURRENT_USER_EMAIL");

		TextView t = (TextView)findViewById(R.id.bookTitleTag);
		t.setText(bookTitle);
		t.setTypeface(tf);

		TextView t1 = (TextView)findViewById(R.id.isbn);
		t1.setText(bookISBN);
		t1.setTypeface(tf);

		TextView t2 = (TextView)findViewById(R.id.price);
		t2.setText(bookPrice);
		t2.setTypeface(tf);
		
		TextView t4 = (TextView)findViewById(R.id.bookTitle_Static);
		t4.setTypeface(tf2);
		
		TextView t5 = (TextView)findViewById(R.id.ISBN_static);
		t5.setTypeface(tf2);
		
		TextView t6 = (TextView)findViewById(R.id.price_static);
		t6.setTypeface(tf2);
		
		TextView t7 = (TextView)findViewById(R.id.purchaseButton);
		t7.setTypeface(tf2);
		
		TextView t8 = (TextView)findViewById(R.id.seller_detail);
		t8.setText(firstName + "" + lastName);
		t8.setTypeface(tf);
		
		TextView t9 = (TextView)findViewById(R.id.seller_detail_static);
		t9.setTypeface(tf2);
		
//		TextView t10 = (TextView)findViewById(R.id.bookInfo);
//		t10.setTypeface(tf2);
//		
//		TextView t11 = (TextView)findViewById(R.id.sellerInfo);
//		t11.setTypeface(tf2);
		
		t7.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Send to method all information necessary for email
				sendEmail();

			}
		});

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

	//Adapter from http://stackoverflow.com/a/2197841
	private void sendEmail(){
		Intent i = new Intent(Intent.ACTION_SEND);
		String emailBody = "Hi!"
							+ "\n\nI saw you posted the following book on Mac Books and would like buy it!"
							+ "\n\nBook Title: "+bookTitle
							+"\nBook Author: "+bookAuthor
							+"\nBook ISBN: "+bookISBN
							+"\nPrice: "+bookPrice
							+"\n\nPlease let me know if it is still available."
							+ "\n\nThanks!";
		i.setType("message/rfc822");
		i.putExtra(Intent.EXTRA_EMAIL  , new String[]{sellerEmail});
		i.putExtra(Intent.EXTRA_SUBJECT, "Mac Books: I want to buy "+bookTitle+"!");
		i.putExtra(Intent.EXTRA_TEXT   , emailBody);
		try {
		    startActivity(Intent.createChooser(i, "Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
		    Toast.makeText(BookDetailActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
		}
	}
}
