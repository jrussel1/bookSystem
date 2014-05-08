package com.book.system.android.appengine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.appspot.mac_books.bookSystem.BookSystem;
import com.appspot.mac_books.bookSystem.model.BookForSale;
import com.appspot.mac_books.bookSystem.model.BookForSaleCollection;
import com.appspot.mac_books.bookSystem.model.Seller;
import com.appspot.mac_books.bookSystem.model.Book;
import com.appspot.mac_books.bookSystem.model.BookForSale;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Paint;
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
import android.widget.ListView;
import android.widget.TextView;
import android.os.Build;

public class MyProfileActivity extends ListActivity {
	protected Button addButton;
	private final String LOG_TAG = "MyProfileActivity";
	private HashMap<String, ArrayList<BookForSale>> saleshelf = null;
	private BookSystem service = null;
	private String currentUserEmail = null;
	private String currentUserFirstName = null;
	private String currentUserLastName = null;
	private Seller userAsSeller = null;
	private ArrayList<BookForSale> usersBooks = null;
	
	public void unauthenticatedGetSellerTask(){
		AsyncTask<String, Void, Seller> getSeller =
				new AsyncTask<String, Void, Seller> () {
			@Override
			protected Seller doInBackground(String... strings) {
				// Retrieve service handle.
				try {
					BookSystem.Bookforsale.GetSellerByEmail getSellerCommand = service.bookforsale().getSellerByEmail(currentUserEmail);
					Seller seller = getSellerCommand.execute();
					return seller;
				} catch (IOException e) {
					Log.e("BookSystem call", "Exception during API call", e);
				}
				return null;
			}

			@Override
			protected void onPostExecute(Seller seller) {
				if (seller!=null) {
					Log.d("Seller Received", seller.toString());
					userAsSeller=seller;
					unauthenticatedGetSellerListofBooks();
				} else {
					Log.e("getting Seller error", "No seller returned by API");
				}
			}
		};

		getSeller.execute();
	}
	
	public void unauthenticatedGetSellerListofBooks(){
		AsyncTask<String, Void, BookForSaleCollection> getSeller =
				new AsyncTask<String, Void, BookForSaleCollection> () {
			@Override
			protected BookForSaleCollection doInBackground(String... strings) {
				// Retrieve service handle.
				try {
					BookSystem.Bookforsale.GetAllBooksForSaleBySeller getBooksForSaleCommand = service.bookforsale().getAllBooksForSaleBySeller(userAsSeller.getId());
					BookForSaleCollection books = getBooksForSaleCommand.execute();
					return books;

				} catch (IOException e) {
					Log.e("BookSystem call", "Exception during API call", e);
				}
				return null;
			}
			@Override
			protected void onPostExecute(BookForSaleCollection books) {
				if (books!=null) {
					Log.d("GetAllBooks", books.toString());
					usersBooks= new ArrayList<BookForSale>(books.getItems());
					BookData.getInstance().setUserBookData(usersBooks);
					setAdapter();
				} else {
					Log.e("GetAllBooks Error", "No books for sale returned by API");
				}
			}
		};

		getSeller.execute();
	}
	
	private void setAdapter() {
		BookAdapter adapter = new BookAdapter(MyProfileActivity.this, usersBooks);
		// Attach the adapter to a ListView
		ListView list = (ListView)findViewById(android.R.id.list);
		list.setAdapter(adapter);
		setListAdapter(adapter);
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_profile);
		setTitle("My Profile");
		
		service = AppConstants.getApiServiceHandle(null);
		Intent intent = getIntent();
		currentUserEmail = intent.getStringExtra("CURRENT_USER_EMAIL");
		currentUserFirstName = intent.getStringExtra("first_name");
		currentUserLastName = intent.getStringExtra("last_name");

		
		TextView myinfo = (TextView) findViewById(R.id.myinfo);
		myinfo.setPaintFlags(myinfo.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
		
		TextView mybooks = (TextView) findViewById(R.id.mybooks);
		mybooks.setPaintFlags(myinfo.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
		


 		 TextView textview1=(TextView) findViewById(R.id.username);

		 textview1.setText(currentUserFirstName+currentUserLastName);

		 TextView textview2=(TextView) findViewById(R.id.email);

		 textview2.setText(currentUserEmail);

	
		addButton = (Button) findViewById(R.id.addButton);
		addButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MyProfileActivity.this, AddBookActivity.class);
				startActivity(intent);
			}
		});
		

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		usersBooks=BookData.getInstance().getUserBookData();
		if(usersBooks==null){
			unauthenticatedGetSellerTask();
		}else{
			setAdapter();
		}
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_profile, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_my_profile,
					container, false);
			return rootView;
		}
	}
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Object o =  this.getListAdapter().getItem(position);
		BookForSale bookObject = (BookForSale) o;

		Intent intent = new Intent(MyProfileActivity.this, EditingActivity.class);

		String isbn = bookObject.getBook().getIsbn();
		String bookAuthor = bookObject.getBook().getAuthor();
		String bookTitle = bookObject.getBook().getTitle();
		
		intent.putExtra("isbn", isbn);
		intent.putExtra("bookAuthor", bookAuthor);
		intent.putExtra("bookTitle", bookTitle);
		intent.putExtra("CURRENT_USER_EMAIL", currentUserEmail);
		intent.putExtra("first_name", currentUserFirstName);
		intent.putExtra("last_name", currentUserLastName);
		
		startActivity(intent);

	}
}
