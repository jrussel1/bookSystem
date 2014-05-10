package com.book.system.android.appengine;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.appspot.mac_books.bookSystem.BookSystem;
import com.appspot.mac_books.bookSystem.BookSystem.Bookforsale;
import com.appspot.mac_books.bookSystem.model.BookForSale;
import com.appspot.mac_books.bookSystem.model.SaleShelf;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.appspot.mac_books.bookSystem.model.Book;
import com.book.system.android.appengine.GooglePlusLoginActivity.RequestTask;

public class AddBookActivity extends Activity {

	private final String LOG_TAG = "AddBookActivity";
	protected EditText mISBN;
	protected EditText mBookTitle;
	protected EditText mPrice;
	protected Button mAddButton;
	protected EditText mAuthor;
	private BookSystem service = null;
	private String currentUserEmail = null;
	private String currentUserFirstName = null;
	private String currentUserLastName = null;
	private AlertDialog.Builder alertBuilder = null;
	private JSONArray returnedItems = null;
	private int currentItem=0;
	private String selectedBookTitle = null;
	private String selectedAuthor = null;
	private String selectedISBN = null;
	private final int PAGE_SIZE = 5;
	private int totalItems = -1;
	private int startIndex = 0;
	private int totalItemsReturned = -1;

	public void unauthenticatedAddBookForSaleTask(){
		AsyncTask<String, Void, BookForSale> addBookForSale =
				new AsyncTask<String, Void, BookForSale> () {
			@Override
			protected BookForSale doInBackground(String... strings) {
				// Retrieve service handle.
				//				String ISBN = mISBN.getText().toString();
				//				String bookTitle = mBookTitle.getText().toString();
				//				String author = mAuthor.getText().toString();
				String price = mPrice.getText().toString();
				//				ISBN = ISBN.trim();
				//				bookTitle = bookTitle.trim();
				price = price.trim();
				//				author = author.trim();
				String firstName = null;
				String lastName = null;

				Double priceNum = Double.valueOf(price);

				if(currentUserFirstName==null || currentUserFirstName.length()<1){
					firstName = "Unknown";
				}else{
					firstName = currentUserFirstName;
				}
				if(currentUserLastName==null || currentUserFirstName.length()<1){
					lastName = "Unknown";
				}else{
					lastName = currentUserLastName;
				}

				try {
					BookSystem.Bookforsale.Insert insertBookCommand = service.bookforsale().insert(selectedISBN,selectedBookTitle,selectedAuthor,currentUserEmail,firstName, lastName,priceNum);
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
						BookData.getInstance().addNewBookForSale(bookforsale);
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
		

		currentUserEmail = BookData.getInstance().getCurrentUserEmail();
		currentUserFirstName = BookData.getInstance().getCurrentUserFirstName();
		currentUserLastName = BookData.getInstance().getCurrentUserLastName();


		Typeface tf = Typeface.createFromAsset(getAssets(),
				"fonts/Roboto-Thin.ttf");
		Typeface tf2 = Typeface.createFromAsset(getAssets(),
				"fonts/Roboto-Light.ttf");

		setTitle("Mac Books - Sell Book");

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
			private void hideSoftKeyboard(Activity activity) {

		        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
		        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
		    }
			@Override
			public void onClick(View v) {
				hideSoftKeyboard(AddBookActivity.this);
				
				startIndex=0;

				boolean validated = true;
				String ISBN = mISBN.getText().toString();
				String bookTitle = mBookTitle.getText().toString();
				String price = mPrice.getText().toString();
				String author = mAuthor.getText().toString();
				
				ISBN = ISBN.trim();
				bookTitle = bookTitle.trim();
				price = price.trim();
				author = author.trim();

				//				if(ISBN.length()==0){
				//					mISBN.setError( "ISBN is required!" );
				//					validated = false;
				//				}
				//				if(bookTitle.length()==0){
				//					mBookTitle.setError( "The book's title is required!" );
				//					validated = false;
				//				}
				if(price.length()<1 || (price.length()>0 && Double.valueOf(price)<1.0)){
					mPrice.setError( "The price is required and it must be greater than $1!" );
					validated = false;
				}
				//				if(author.length()==0){
				//					mAuthor.setError( "The book's author is required!" );
				//					validated = false;
				//				}
				//				
				if(ISBN.length()==0 && author.length()==0 && bookTitle.length()==0){
					mISBN.setError( "At least one search field is required!" );
					mAuthor.setError( "At least one search field is required!" );
					mBookTitle.setError( "At least one search field is required!" );
					validated = false;
				}

				Log.d(LOG_TAG, "Form is validated: "+String.valueOf(validated));
				if(validated){
					try{
						String qry = "https://www.googleapis.com/books/v1/volumes?q=";
						if(ISBN.length()>0)
							qry+="isbn:"+URLEncoder.encode(ISBN, "UTF-8");
						if(author.length()>0)
							qry+="+inauthor:"+URLEncoder.encode(author, "UTF-8");
						if(bookTitle.length()>0)
							qry+="+intitle:"+URLEncoder.encode(bookTitle, "UTF-8");
						Log.d(LOG_TAG,qry);
						new RequestTask().execute(qry);
						//Test isbn 030795479X
						//					new RequestTask().execute("https://www.googleapis.com/books/v1/volumes?q=isbn:"+ISBN+"&key="+AppConstants.SIMPLE_ACCESS_API_KEY);
						//					new RequestTask().execute("http://isbndb.com/api/v2/json/"+AppConstants.ISBN_DB_KEY+"/book/"+ISBN);
						//					unauthenticatedAddBookForSaleTask();
					}catch(UnsupportedEncodingException e){
						e.printStackTrace();
					}
				}else{
					alertBuilder = new AlertDialog.Builder(AddBookActivity.this);
					alertBuilder.setMessage("Please fix errors first!");
					alertBuilder.setTitle("Book cannot be submitted!!!");
					alertBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int which) {            	
							alertBuilder=null;
						}
					});
					AlertDialog dialog = alertBuilder.create();
					dialog.show();
				}



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
		}else if (id == R.id.home) {
			Intent intent = new Intent(AddBookActivity.this,BookListActivity.class);
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
			View rootView = inflater.inflate(R.layout.fragment_add_book,
					container, false);
			return rootView;
		}
		
	}
	class RequestTask extends AsyncTask<String, String, String>{

		@Override
		protected String doInBackground(String... uri) {
			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse response;
			String responseString = null;

			try {
				response = httpclient.execute(new HttpGet(uri[0]));
				StatusLine statusLine = response.getStatusLine();
				if(statusLine.getStatusCode() == HttpStatus.SC_OK){
					ByteArrayOutputStream out = new ByteArrayOutputStream();
					response.getEntity().writeTo(out);
					out.close();
					responseString = out.toString();
				} else{
					//Closes the connection.
					response.getEntity().getContent().close();
					throw new IOException(statusLine.getReasonPhrase());
				}
			} catch (ClientProtocolException e) {
				//TODO Handle problems..
				e.printStackTrace();
			} catch (IOException e) {
				//TODO Handle problems..
				e.printStackTrace();
				Log.e(LOG_TAG,e.getLocalizedMessage());
			}
			Log.d(LOG_TAG,responseString);
			return responseString;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			alertBuilder = new AlertDialog.Builder(AddBookActivity.this);
			try {
				JSONObject jObject = new JSONObject(result);
				Log.d(LOG_TAG,jObject.toString());
				totalItems = jObject.getInt("totalItems");
				if(totalItems==0){
					Toast.makeText(getApplicationContext(), "No results!!", Toast.LENGTH_SHORT).show();
				}
				else if(totalItems>0){
					returnedItems = jObject.getJSONArray("items");
					totalItemsReturned=returnedItems.length();
					String subtitle = null;
					//JSONArray isbns = jObject.getJSONArray("industryIdentifiers");
					JSONObject volume = returnedItems.getJSONObject(currentItem).getJSONObject("volumeInfo");
					selectedBookTitle= volume.getString("title");
					if(volume.has("subtitle"))
						subtitle = volume.getString("subtitle");
					if(subtitle!=null&&subtitle.length()>0)
						selectedBookTitle+=": "+subtitle;
					selectedAuthor = volume.getJSONArray("authors").getString(0);
					String alertMessage = "Title: "+selectedBookTitle+"\nAuthor: "+selectedAuthor;
					
					JSONArray isbns = null;
					if(returnedItems.getJSONObject(0).getJSONObject("volumeInfo").has("industryIdentifiers"))
						isbns=returnedItems.getJSONObject(0).getJSONObject("volumeInfo").getJSONArray("industryIdentifiers");
					for(int i = 0; i<isbns.length();i++){
						if(isbns.getJSONObject(i).getString("type").equals("ISBN_13"))
							selectedISBN =isbns.getJSONObject(i).getString("identifier");
					}
					alertBuilder.setTitle("Is this your book???");
					if(totalItems==1){
						alertBuilder.setMessage(alertMessage);
						alertBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int which) {            	
								unauthenticatedAddBookForSaleTask();
								Toast.makeText(getApplicationContext(), "User accepted book as correct", Toast.LENGTH_SHORT).show();
							}
						});
						alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								// User pressed Cancel button. Write Logic Here
								Toast.makeText(getApplicationContext(), "User cancelled book lookup", Toast.LENGTH_SHORT).show();
								dialog.cancel();
							}
						});
						AlertDialog dialog = alertBuilder.create();
						dialog.show();
					}else{
						makePagingDialog();
					}

				}


			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		}
		private void makePagingDialog(){
			int num = 0;
			int limit=0;
			if(totalItemsReturned>PAGE_SIZE)
				num=PAGE_SIZE;
			else
				num=totalItemsReturned;
			
			if(startIndex+num>totalItemsReturned)
				limit=totalItemsReturned;
			else
				limit=startIndex+num;

			CharSequence[] items = new CharSequence[num];
			for(int i = startIndex; i<limit;i++){
				items[i-startIndex]=getMessage(i);
			}
			alertBuilder.setItems(items, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int item) {
					try{
						JSONArray isbns = null;
						if(returnedItems.getJSONObject(item).getJSONObject("volumeInfo").has("industryIdentifiers"))
							isbns=returnedItems.getJSONObject(item).getJSONObject("volumeInfo").getJSONArray("industryIdentifiers");
						for(int i = 0; i<isbns.length();i++){
							if(isbns.getJSONObject(i).getString("type").equals("ISBN_13"))
								selectedISBN =isbns.getJSONObject(i).getString("identifier");
						}
						selectedBookTitle = returnedItems.getJSONObject(item).getJSONObject("volumeInfo").getString("title");
						selectedAuthor = returnedItems.getJSONObject(item).getJSONObject("volumeInfo").getJSONArray("authors").getString(0);
						unauthenticatedAddBookForSaleTask();
					}catch(JSONException e) {
						e.printStackTrace();
					}
				}
			});
			//NOTE: Google apparently returns 10 results by default, paging more than 10 results will require making another call to the api
			if(startIndex+PAGE_SIZE<totalItemsReturned){
				alertBuilder.setNegativeButton("Show next "+PAGE_SIZE+" results", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
						startIndex+=PAGE_SIZE;
						alertBuilder = new AlertDialog.Builder(AddBookActivity.this);
						alertBuilder.setTitle("Is this your book???");
						makePagingDialog();
					}
				});
			}
			alertBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {

					Toast.makeText(getApplicationContext(), "User cancelled book lookup", Toast.LENGTH_SHORT).show();
					dialog.cancel();
				}
			});
			AlertDialog dialog = alertBuilder.create();
			dialog.show();
		}
		private String getMessage(int i){
			try{
				if(returnedItems.getJSONObject(i)!=null){
					JSONObject volume = null;
					if(returnedItems.getJSONObject(i).has("volumeInfo"))
						volume=returnedItems.getJSONObject(i).getJSONObject("volumeInfo");
					String title = null;
					if(volume.has("title"))
						title=volume.getString("title");
					String subtitle = null;
					if(volume.has("subtitle"))
						subtitle = volume.getString("subtitle");
					if(subtitle!=null&&subtitle.length()>0)
						title+=": "+subtitle;
					String author = null;
					if(volume.has("authors"))
						author=volume.getJSONArray("authors").getString(0);
					String alertMessage = "Title: "+title+"\nAuthor: "+author;
					return alertMessage;
				}else{
					return "";
				}

			}catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
	}
}
