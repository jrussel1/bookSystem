package com.book.system.android.appengine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

public class BookListActivity extends ListActivity {

	protected Map<String,BookForSale> bookList = new HashMap<String,BookForSale>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);

		//add stuff to the bookList 
		BookForSale a = new BookForSale("Harry Poter","12345","author A","X",30);
		BookForSale a1 = new BookForSale("The Great Gatsby","1123","author B","The Great Gatsby, seller: Junyi Wang",20);

		bookList.put(a.getSaleId(), a);
		bookList.put(a1.getSaleId(),a1);


		ArrayList<BookForSale> bookNames = new ArrayList<BookForSale>();

		for (Map.Entry<String, BookForSale> entry : bookList.entrySet()) {
		    String key = entry.getKey();
		    BookForSale value = entry.getValue();
		    bookNames.add(value);

		}
		Log.i("fail2","fail2");
		Log.i(bookNames.get(1).getTitle(),"fail" + bookNames.get(1).getTitle());




		BookAdapter adapter = new BookAdapter(this, bookNames);
		// Attach the adapter to a ListView
		ListView list = getListView();
		ListView listView = (ListView) list;
		listView.setAdapter(adapter);
		setListAdapter(adapter);



	}


	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Log.i("alex","alex");
		Object o =  this.getListAdapter().getItem(position);
		BookForSale bookObject = (BookForSale) o;
		String a = bookObject.getTitle();
		Log.i("alex","alex"+a);


		Intent intent = new Intent(BookListActivity.this, BookDetailActivity.class);
//		intent.putExtra("key", book);

		String isbn = bookObject.getISBN();
		int price1 = (bookObject.getPrice());
		String price = Integer.toString(price1);
		String bookName = bookObject.getTitle();

		intent.putExtra("ISBNkey", isbn);
		intent.putExtra("priceKey", price);
		intent.putExtra("nameKey", bookName);

		startActivity(intent);

	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_list, container,
					false);
			return rootView;
		}
	}

}
