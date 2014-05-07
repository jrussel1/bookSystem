package com.book.system.android.appengine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.appspot.mac_books.bookSystem.BookSystem;
import com.appspot.mac_books.bookSystem.model.BookForSale;
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
//TODO: Add call to get books of seller
				} else {
					Log.e("getting Seller error", "No seller returned by API");
				}
			}
		};

		getSeller.execute();
	}
	
	public void unauthenticatedGetSellerListofBooks(){
		AsyncTask<String, Void, List<BookForSale>> getSellersBooks =
				new AsyncTask<String, Void, List<BookForSale>> () {
			@Override
			protected List<BookForSale> doInBackground(String... strings) {
				// Retrieve service handle.
				try {
					BookSystem.Bookforsale.GetAllBooksForSaleBySeller getBooks = service.bookforsale().getAllBooksForSaleBySeller(currentUserEmail);
					List<BookForSale> sellersbooks = getBooks.execute();
					return sellersbooks;
				} catch (IOException e) {
					Log.e("BookSystem call", "Exception during API call", e);
				}
				return null;
			}

			@Override
			protected void onPostExecute(Seller seller) {
				if (seller!=null) {
					Log.d("Seller Received", seller.toString());
//TODO: Add call to get books of seller
				} else {
					Log.e("getting Seller error", "No seller returned by API");
				}
			}
		};

		getSeller.execute();
	}
	
	private void setAdapter() {
		ArrayList<BookForSale> aList = new ArrayList<BookForSale>();
		for(Entry<String, ArrayList<BookForSale>> entry:saleshelf.entrySet()){
			aList.add(entry.getValue().get(0));
		}
		Log.i(LOG_TAG,aList.toString());
		Log.i(LOG_TAG,String.valueOf(aList.size()));

		BookAdapter adapter = new BookAdapter(MyProfileActivity.this, aList);
		// Attach the adapter to a ListView
		ListView list = (ListView)findViewById(R.id.mybooklist);
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
		
		addButton = (Button) findViewById(R.id.addButton);
		addButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MyProfileActivity.this, AddBookActivity.class);
				startActivity(intent);
			}
		});
		

//		if (savedInstanceState == null) {
//			getFragmentManager().beginTransaction()
//					.add(R.id.container, new PlaceholderFragment()).commit();
//		}
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

}
