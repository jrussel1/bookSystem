package com.book.system.android.appengine;

import java.util.ArrayList;

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

public class SearchActivity extends ListActivity {

	
	protected TextView mQuery;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		
		Intent intent = getIntent();
		String query = intent.getStringExtra("query").toLowerCase();
		
		
		Book book1 = new Book("Harry Poter","A1B2C3D4","JK Rowling");
		Book book2 = new Book("The Great Gatsby","A2B3C4D5","F.Scott Fitzgerald");
		Book book3 = new Book("Carnal Curiosity","A2B3C4D5","Stuart Woods");
		Book book4 = new Book("The Fixed Triology","A2B3C4D5","Laurelin Paige");
		Book book5 = new Book("Missing You","A2B3C4D5","Harlan Coben");
		Book book6 = new Book("NYPD Red 2","A2B3C4D5","James Patterson and Marshall Karp");
		Book book7 = new Book("Got You Under My Skin","A2B3C4D5","Mary Higgins Clark");
		
		Seller seller1 = new Seller(12345,"hliu1@macalester.edu","Hongshan","Liu");
		Seller seller2 = new Seller(23456,"hxu1@macalester.edu","Hanyue","Xu");
		
		BookForSale bookForSale1 = new BookForSale(book1,seller1, 10.0);
		BookForSale bookForSale2 = new BookForSale(book2,seller2, 18.0);
		BookForSale bookForSale3 = new BookForSale(book3,seller1, 25.5);
		BookForSale bookForSale4 = new BookForSale(book4,seller2, 15.9);
		BookForSale bookForSale5 = new BookForSale(book5,seller1, 35.9);
		BookForSale bookForSale6 = new BookForSale(book6,seller2, 13.9);
		BookForSale bookForSale7 = new BookForSale(book7,seller1, 12.9);


		ArrayList<BookForSale> bookNames = new ArrayList<BookForSale>();
		ArrayList<BookForSale> bookNames2 = new ArrayList<BookForSale>();
		
		bookNames.add(bookForSale1);
		bookNames.add(bookForSale2);
		bookNames.add(bookForSale3);
		bookNames.add(bookForSale4);
		bookNames.add(bookForSale5);
		bookNames.add(bookForSale6);
		bookNames.add(bookForSale7);
		bookNames.add(bookForSale3);
		bookNames.add(bookForSale1);
		bookNames.add(bookForSale5);
		bookNames.add(bookForSale6);
		bookNames.add(bookForSale7);
		
		for (BookForSale b: bookNames){
//			if ((b.getBook().getISBN().equalsIgnoreCase(query))) {
//				bookNames2.add(b);
//			}
			if (b.getBook().getISBN().toLowerCase().indexOf(query)>-1 ||
					b.getBook().getTitle().toLowerCase().indexOf(query)>-1 ||
					b.getBook().getAuthor().toLowerCase().indexOf(query)>-1) {
				bookNames2.add(b);
			}
			
		}
		
		
		BookAdapter adapter = new BookAdapter(this, bookNames2);
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
		Log.i("alex","alex");
		Object o =  this.getListAdapter().getItem(position);
		BookForSale bookObject = (BookForSale) o;
		String a = bookObject.getBook().getTitle();
		Log.i("alex","alex"+a);


		Intent intent = new Intent(SearchActivity.this, BookDetailActivity.class);

		String isbn = bookObject.getBook().getISBN();
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
