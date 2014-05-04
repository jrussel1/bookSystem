package com.book.system.android.appengine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.appspot.mac_books.bookSystem.model.BookForSale;

import android.R.menu;
import android.app.Fragment;
import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.appspot.mac_books.bookSystem.BookSystem;
import com.appspot.mac_books.bookSystem.model.BookForSale;
import com.appspot.mac_books.bookSystem.model.SaleShelf;

public class BookListActivity extends ListActivity {

	protected TextView mAddBookTextView;
	protected TextView mMyProfileButton;
	protected SearchView mSearchView;
	private BookSystem service = null;
	private SaleShelf saleshelf = null;
	

	public void unauthenticatedSaleShelfTask(){
		AsyncTask<Integer, Void, SaleShelf> getShelf =
				new AsyncTask<Integer, Void, SaleShelf> () {
			@Override
			protected SaleShelf doInBackground(Integer... integers) {
				// Retrieve service handle.

				try {
					BookSystem.Bookforsale.List getListCommand = service.bookforsale().list();
					SaleShelf shelf = getListCommand.execute();
					return shelf;
				} catch (IOException e) {
					Log.e("BookSystem call", "Exception during API call", e);
				}
				return null;
			}

			@Override
			protected void onPostExecute(SaleShelf shelf) {
				if (shelf!=null) {
					try {
						Log.d("SaleShelf", shelf.toPrettyString());
						saleshelf = shelf;
						setAdapter();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					Log.e("SaleShelf error", "No shelf were returned by the API.");
				}
			}
		};

		getShelf.execute();
	}
	
	
	private void setAdapter() {
		ArrayList<BookForSale> aList = new ArrayList<BookForSale>(saleshelf.getList());
		BookAdapter adapter = new BookAdapter(BookListActivity.this, aList);
		// Attach the adapter to a ListView
		ListView list = (ListView)findViewById(android.R.id.list);
		Log.d("TESTINGSBNG", String.valueOf(list.getCount()));
//		ListView listView = (ListView) list;
		list.setAdapter(adapter);
		setListAdapter(adapter);
		
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		setTitle("");
		
		mAddBookTextView = (TextView) findViewById(R.id.sellBookButton);
		mAddBookTextView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(BookListActivity.this, AddBookActivity.class);
				startActivity(intent);

			}
		});
		mMyProfileButton = (TextView) findViewById(R.id.myProfileButton);
		mMyProfileButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(BookListActivity.this, MyProfileActivity.class);
				startActivity(intent);
				
			}
		});
		mSearchView = (SearchView) findViewById(R.id.search_view);
		mSearchView.setQueryHint("Search by ISBN");
		String a = mSearchView.getQuery().toString();
		SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
			
			@Override
			public boolean onQueryTextSubmit(String query) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(BookListActivity.this, SearchActivity.class);
				intent.putExtra("query", query.toString());
				startActivity(intent);
				return false;
			}
			
			@Override
			public boolean onQueryTextChange(String newText) {
				// TODO Auto-generated method stub
				return false;
			}
		};	
		mSearchView.setOnQueryTextListener(queryTextListener);
		
		service = AppConstants.getApiServiceHandle(null);
		unauthenticatedSaleShelfTask();
	}


	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Object o =  this.getListAdapter().getItem(position);
		BookForSale bookObject = (BookForSale) o;
		String a = bookObject.getBook().getTitle();


		Intent intent = new Intent(BookListActivity.this, BookDetailActivity.class);
//		intent.putExtra("key", book);

		String isbn = bookObject.getBook().getIsbn();
		Double price1 = (bookObject.getPrice());
		String price = Double.toString(price1);
		String bookName = bookObject.getBook().getTitle();

		intent.putExtra("ISBNkey", isbn);
		intent.putExtra("priceKey", price);
		intent.putExtra("nameKey", bookName);

		startActivity(intent);

	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater mif = getMenuInflater();
		mif.inflate(R.menu.main_activity_action, menu);
		return super.onCreateOptionsMenu(menu);
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
			View rootView = inflater.inflate(R.layout.fragment_list, container,
					false);
			return rootView;
		}
	}

}
