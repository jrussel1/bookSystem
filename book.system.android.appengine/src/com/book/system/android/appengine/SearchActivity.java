package com.book.system.android.appengine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import com.appspot.mac_books.bookSystem.model.BookForSale;
import com.appspot.mac_books.bookSystem.model.SaleShelf;

import android.app.Fragment;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SearchActivity extends ListActivity {
	private String currentUserEmail=null;
	private HashMap<String, ArrayList<BookForSale>> saleshelf = null;
	protected TextView mQuery;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		
		Intent intent = getIntent();
		String query = intent.getStringExtra("query").toLowerCase();
		
		currentUserEmail = BookData.getInstance().getCurrentUserEmail();
		saleshelf = BookData.getInstance().getBookData();
		
		HashMap<String, ArrayList<BookForSale>> foundBooks = new HashMap<String, ArrayList<BookForSale>>();
		ArrayList<BookForSale> foundBookList = new ArrayList<BookForSale>();
		
		for (Entry<String, ArrayList<BookForSale>> entry: saleshelf.entrySet()){
			if (entry.getValue().get(0).getBook().getIsbn().toLowerCase().indexOf(query)>-1 ||
					entry.getValue().get(0).getBook().getTitle().toLowerCase().indexOf(query)>-1 ||
					entry.getValue().get(0).getBook().getAuthor().toLowerCase().indexOf(query)>-1) {
				foundBooks.put(entry.getKey(), entry.getValue());
				foundBookList.add(entry.getValue().get(0));
			}
			
		}

		
		BookAdapter adapter = new BookAdapter(this, foundBookList);
		// Attach the adapter to a ListView
		ListView list = getListView();
		ListView listView = (ListView) list;
		listView.setAdapter(adapter);
		setListAdapter(adapter);

		
		
		
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}
	
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		Object o =  this.getListAdapter().getItem(position);
		BookForSale bookObject = (BookForSale) o;
		String a = bookObject.getBook().getTitle();


		Intent intent = new Intent(SearchActivity.this, BookForSaleListActivity.class);

		String isbn = bookObject.getBook().getIsbn();
		
		String bookAuthor = bookObject.getBook().getAuthor();
		String bookTitle = bookObject.getBook().getTitle();
		intent.putExtra("isbn", isbn);
		intent.putExtra("bookAuthor", bookAuthor);
		intent.putExtra("bookTitle", bookTitle);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
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
		}else if (id == R.id.home) {
			Intent intent = new Intent(SearchActivity.this,BookListActivity.class);
			startActivity(intent);
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
			View rootView = inflater.inflate(R.layout.fragment_search,
					container, false);
			return rootView;
		}
	}

}
