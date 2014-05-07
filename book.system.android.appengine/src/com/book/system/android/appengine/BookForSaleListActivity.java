package com.book.system.android.appengine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.appspot.mac_books.bookSystem.BookSystem;
import com.appspot.mac_books.bookSystem.model.BookForSale;
import com.appspot.mac_books.bookSystem.model.SaleShelf;

import android.app.Fragment;
import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class BookForSaleListActivity extends ListActivity {
	private String currentUserEmail = null;
	private String currentIsbn = null;
	private String currentBookTitle = null;
	private String currentBookAuthor =null;
	HashMap<String,ArrayList<BookForSale>> saleshelf = null;
	
	
	private void setAdapter() {
		
		BookAdapter adapter = new BookAdapter(BookForSaleListActivity.this, saleshelf.get(currentIsbn));
		// Attach the adapter to a ListView
		ListView list = (ListView)findViewById(android.R.id.list);
		list.setAdapter(adapter);
		setListAdapter(adapter);

		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_book_for_sale_list);
		
		Intent intent = getIntent();
		currentUserEmail = intent.getStringExtra("CURRENT_USER_EMAIL");
		currentIsbn = intent.getStringExtra("isbn");
		currentBookTitle = intent.getStringExtra("bookTitle");
		currentBookAuthor = intent.getStringExtra("bookAuthor");
		
		saleshelf=BookData.getInstance().getData();
		
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		setAdapter();
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.book_for_sale_list, menu);
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
			View rootView = inflater.inflate(
					R.layout.fragment_book_for_sale_list, container, false);
			return rootView;
		}
	}

}
