package com.book.system.android.appengine;

import java.io.IOException;

import com.appspot.mac_books.bookSystem.BookSystem;
import com.appspot.mac_books.bookSystem.BookSystem.Bookforsale;
import com.appspot.mac_books.bookSystem.model.BookForSale;
import com.appspot.mac_books.bookSystem.model.SaleShelf;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.appspot.mac_books.bookSystem.model.Book;

public class AddBookActivity extends Activity {

	protected EditText mISBN;
	protected EditText mBookTitle;
	protected EditText mPrice;
	protected Button mAddButton;
	protected EditText mAuthor;
	private BookSystem service = null;
	private String currentUserEmail = null;
	private String currentUserFirstName = null;
	private String currentUserLastName = null;

	public void unauthenticatedAddBookForSaleTask(){
		AsyncTask<String, Void, BookForSale> addBookForSale =
				new AsyncTask<String, Void, BookForSale> () {
			@Override
			protected BookForSale doInBackground(String... strings) {
				// Retrieve service handle.
				String ISBN = mISBN.getText().toString();
				String bookTitle = mBookTitle.getText().toString();
				String author = mAuthor.getText().toString();
				String firstName = null;
				String lastName = null;
				if(currentUserFirstName==null){
					firstName = "Unknown";
				}else{
					firstName = currentUserFirstName;
				}
				if(currentUserLastName==null){
					lastName = "Unknown";
				}else{
					lastName = currentUserLastName;
				}
				
				Double Price = Double.valueOf(mPrice.getText().toString());
				try {
					BookSystem.Bookforsale.Insert insertBookCommand = service.bookforsale().insert(ISBN,bookTitle,author,currentUserEmail,firstName, lastName,Price);
					BookForSale bookforsale = insertBookCommand.execute();
					return bookforsale;
				} catch (IOException e) {
					Log.e("BookSystem call", "Exception during API call", e);
				}
				return null;
			}

			@Override
			protected void onPostExecute(BookForSale bookforsale) {
				if (bookforsale!=null) {
					try {
						Log.d("BookForSale Insert", bookforsale.toPrettyString());
						Intent intent = new Intent(AddBookActivity.this,MyProfileActivity.class);
						startActivity(intent);
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					Log.e("BookForSale Insert Error", "No shelf were returned by the API.");
				}
			}
		};

		addBookForSale.execute();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_book);
		
		service = AppConstants.getApiServiceHandle(null);
		
		Intent intent = getIntent();
		currentUserEmail = intent.getStringExtra("CURRENT_USER_EMAIL");
		currentUserFirstName = intent.getStringExtra("first_name");
		currentUserLastName = intent.getStringExtra("last_name");
		
		
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
		mAuthor = (EditText)findViewById(R.id.EditText_Author);
		mAuthor.setTypeface(tf2);
		mAddButton = (Button)findViewById(R.id.button_AddBook);
		mAddButton.setTypeface(tf2);
		
		TextView t = (TextView)findViewById(R.id.ISBN_static1);
		t.setTypeface(tf2);
		TextView t1 = (TextView)findViewById(R.id.BookTitle_static);
		t1.setTypeface(tf2);
		TextView t2 = (TextView)findViewById(R.id.price_static);
		t2.setTypeface(tf2);
		TextView t3 = (TextView)findViewById(R.id.Author_static);
		t3.setTypeface(tf2);
		
		

		


		

		mAddButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String ISBN = mISBN.getText().toString();
				String bookTitle = mBookTitle.getText().toString();
				String price = mPrice.getText().toString();
				String author = mAuthor.getText().toString();
				
				ISBN = ISBN.trim();
				bookTitle = bookTitle.trim();
				price = price.trim();
				author = author.trim();
				
				
				AlertDialog.Builder builder = new AlertDialog.Builder(AddBookActivity.this);
				builder.setMessage("ISBN:  " + ISBN + "   BookTitle:" + bookTitle + "    Price:" + price + "     Author:" + author);
				builder.setTitle("test");
				builder.setPositiveButton(android.R.string.ok, null);
				
				AlertDialog dialog = builder.create();
				dialog.show();
				
				unauthenticatedAddBookForSaleTask();
				
				
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
