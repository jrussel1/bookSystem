package com.book.system.android.appengine;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONException;
import org.json.JSONObject;




import android.app.ActionBar;
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
import android.widget.Toast;

import com.appspot.mac_books.bookSystem.BookSystem;
import com.appspot.mac_books.bookSystem.model.Book;
import com.appspot.mac_books.bookSystem.model.BookForSale;
import com.appspot.mac_books.bookSystem.model.JsonMap;
import com.appspot.mac_books.bookSystem.model.SaleShelf;
import com.appspot.mac_books.bookSystem.model.Seller;
import com.google.api.client.json.GenericJson;
import com.google.gson.Gson;

public class BookListActivity extends ListActivity {

	protected TextView mAddBookTextView;
	protected TextView mMyProfileButton;
	protected SearchView mSearchView;
	protected List<BookForSale> tempList;
	private BookSystem service = null;
	private HashMap<String, ArrayList<BookForSale>> saleshelf = null;
	private String currentUserEmail = null;
	private final String LOG_TAG = "BookListActivity";
	String currentUserFirstName = null;
	String currentUserLastName = null;
	ArrayList<String> isbnList = null;
	public void unauthenticatedSaleShelfTask(){
		AsyncTask<Integer, Void, GenericJson> getShelf =
				new AsyncTask<Integer, Void, GenericJson> () {
			@Override
			protected GenericJson doInBackground(Integer... integers) {
				// Retrieve service handle.

				try {
					BookSystem.Bookforsale.ListBooksAndSellers getListCommand = service.bookforsale().listBooksAndSellers();
					AbstractMap<String, Object> shelf = getListCommand.execute();
					return (GenericJson) shelf;
				} catch (IOException e) {
					Log.e("BookSystem call", "Exception during API call", e);
				}
				return null;
			}

			@Override
			protected void onPostExecute(GenericJson shelf) {
				if (shelf!=null) {
					saleshelf=new HashMap<String, ArrayList<BookForSale>>();
					Log.d("SaleShelf", shelf.toString());
					isbnList = new ArrayList<String>(shelf.keySet());
					JSONObject jObject = new JSONObject(shelf);
					Iterator iter = jObject.keys();
					String key=null;
					ArrayList<BookForSale> bfs = null;
					JSONObject cur = null;
					JSONObject curBook = null;
					JSONObject curSeller = null;
					while(iter.hasNext()){
						key=(String) iter.next();
						bfs = new ArrayList<BookForSale>();
						try {
							for(int i = 0; i < jObject.getJSONArray(key).length(); i++){
								cur=jObject.getJSONArray(key).getJSONObject(i);
								curBook=cur.getJSONObject("book");
								curSeller=cur.getJSONObject("seller");
								bfs.add(
										new BookForSale()
										.setBook(
												new Book()
												.setIsbn(curBook.getString("isbn"))
												.setAuthor(curBook.getString("author"))
												.setTitle(curBook.getString("title"))
												)
												.setSeller(
														new Seller()
														.setId(curSeller.getLong("id"))
														.setEmail(curSeller.getString("email"))
														.setFirstName(curSeller.getString("firstName"))
														.setLastName(curSeller.getString("lastName"))
														)
														.setPrice(
																cur.getDouble("price")
																)
										);
								//										Log.d(LOG_TAG,jObject.getJSONArray(key).get(i).toString());
								
								saleshelf.put(key, bfs);
								bfs=null;
							}


							
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						catch (NullPointerException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					BookData.getInstance().setData(saleshelf);
					setAdapter();

				} else {
					Log.e("SaleShelf error", "No shelf were returned by the API.");
				}
			}
		};

		getShelf.execute();
	}


	private void setAdapter() {
		ArrayList<BookForSale> aList = new ArrayList<BookForSale>();
		for(Entry<String, ArrayList<BookForSale>> entry:saleshelf.entrySet()){
			aList.add(entry.getValue().get(0));
		}
		Log.i(LOG_TAG,aList.toString());
		Log.i(LOG_TAG,String.valueOf(aList.size()));

		BookAdapter adapter = new BookAdapter(BookListActivity.this, aList);
		// Attach the adapter to a ListView
		ListView list = (ListView)findViewById(android.R.id.list);
		list.setAdapter(adapter);
		setListAdapter(adapter);

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		setTitle("");

		Intent intent = getIntent();
		currentUserEmail = intent.getStringExtra("CURRENT_USER_EMAIL");
		currentUserFirstName = intent.getStringExtra("first_name");
		currentUserLastName = intent.getStringExtra("last_name");

		mAddBookTextView = (TextView) findViewById(R.id.sellBookButton);
		mAddBookTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(BookListActivity.this, AddBookActivity.class);
				intent.putExtra("CURRENT_USER_EMAIL", currentUserEmail);
				intent.putExtra("last_name", currentUserLastName);
				intent.putExtra("first_name", currentUserFirstName);
				startActivity(intent);
			}
		});

		mMyProfileButton = (TextView) findViewById(R.id.myProfileButton);
		mMyProfileButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(BookListActivity.this, MyProfileActivity.class);
				intent.putExtra("CURRENT_USER_EMAIL", currentUserEmail);
				intent.putExtra("last_name", currentUserLastName);
				intent.putExtra("first_name", currentUserFirstName);
				startActivity(intent);

			}
		});

		mSearchView = (SearchView) findViewById(R.id.search_view);
		mSearchView.setQueryHint("Search by ISBN, Title, or Author");
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
		saleshelf=BookData.getInstance().getData();
		if(saleshelf==null){
			unauthenticatedSaleShelfTask();
		}else{
			setAdapter();
		}
	}


	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Object o =  this.getListAdapter().getItem(position);
		BookForSale bookObject = (BookForSale) o;
		String a = bookObject.getBook().getTitle();


		Intent intent = new Intent(BookListActivity.this, BookForSaleListActivity.class);
		//		intent.putExtra("key", book);

		String isbn = bookObject.getBook().getIsbn();
		
		String bookAuthor = bookObject.getBook().getAuthor();
		String bookTitle = bookObject.getBook().getTitle();
		intent.putExtra("isbn", isbn);
		intent.putExtra("bookAuthor", bookAuthor);
		intent.putExtra("bookTitle", bookTitle);
		intent.putExtra("CURRENT_USER_EMAIL", currentUserEmail);
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
