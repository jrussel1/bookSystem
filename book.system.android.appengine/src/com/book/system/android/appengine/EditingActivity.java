package com.book.system.android.appengine;

import java.io.IOException;

import com.appspot.mac_books.bookSystem.BookSystem;
import com.appspot.mac_books.bookSystem.model.IntegerResponse;
import com.appspot.mac_books.bookSystem.model.Seller;

import android.app.Activity;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class EditingActivity extends Activity {
	private final String LOG_TAG = "EditingActivity";
	private String currentUserEmail = null;
	private String currentUserFirstName = null;
	private String currentUserLastName = null;
	
	private BookSystem service = null;
	
	private String bookISBN = null;
	private String bookTitle = null;
	private String bookPrice = null;
	private String bookAuthor = null;
	private RelativeLayout bookDetails = null;
	private TextView bookInfoTitle = null;
	private AlertDialog.Builder alertBuilder = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editing);

		Typeface tf = Typeface.createFromAsset(getAssets(),
				"fonts/Roboto-Thin.ttf");
		Typeface tf2 = Typeface.createFromAsset(getAssets(),
				"fonts/Roboto-Light.ttf");
		alertBuilder = new AlertDialog.Builder(EditingActivity.this);
		service = AppConstants.getApiServiceHandle(null);
		Intent intent = getIntent();
		bookISBN = intent.getStringExtra("isbn");
		bookTitle = intent.getStringExtra("bookTitle");
		bookPrice = intent.getStringExtra("price");
		bookAuthor = intent.getStringExtra("bookAuthor");

		currentUserFirstName = intent.getStringExtra("first_name");
		currentUserLastName = intent.getStringExtra("last_name");
		currentUserEmail = intent.getStringExtra("CURRENT_USER_EMAIL");

		TextView bookTitleView = (TextView) findViewById(R.id.book_title_detail);
		bookTitleView.setText(bookTitle);
		TextView bookAuthorView = (TextView) findViewById(R.id.book_author_detail);
		bookAuthorView.setText(bookAuthor);
		TextView bookIsbnView = (TextView) findViewById(R.id.book_isbn_detail);
		bookIsbnView.setText(bookISBN);
		EditText priceView = (EditText) findViewById(R.id.book_price_edit);
		priceView.setText(bookPrice, TextView.BufferType.EDITABLE);
		Button editButton = (Button) findViewById(R.id.button_edit_price);
		editButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				findViewById(R.id.book_price_edit).setEnabled(true);
				findViewById(R.id.button_submit_edit).setVisibility(View.VISIBLE);
				v.setVisibility(View.GONE);
			}
		});
		Button submitButton = (Button) findViewById(R.id.button_submit_edit);
		submitButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText priceView = (EditText) findViewById(R.id.book_price_edit);
				bookPrice = priceView.getText().toString();
				priceView.setEnabled(false);
				findViewById(R.id.button_edit_price).setVisibility(View.VISIBLE);
				v.setVisibility(View.GONE);
				
				unauthenticatedUpdateBookForSaleTask();
			}
		});
		Button deleteButton = (Button) findViewById(R.id.delete_book_listing);
		deleteButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				alertBuilder.setMessage("Are you sure you want to delete this post?");
				alertBuilder.setTitle("Delete?");
				alertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int which) {            	
						unauthenticatedDeleteBookForSaleTask();
						Toast.makeText(getApplicationContext(), "Deleting book listing", Toast.LENGTH_SHORT).show();
					}
				});
				alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// User pressed Cancel button. Write Logic Here
						Toast.makeText(getApplicationContext(), "Delete cancelled", Toast.LENGTH_SHORT).show();
						dialog.cancel();
					}
				});
				AlertDialog dialog = alertBuilder.create();
				dialog.show();
				
			}
		});
		
//		Log.d(LOG_TAG,intent.getExtras().toString());
		
		
//		bookInfoTitle = (TextView) findViewById(R.id.book_info_title);
//		Drawable[] d = bookInfoTitle.getCompoundDrawables();
//		d[0].setBounds(0, 0, (int)(d[0].getIntrinsicWidth()*0.5), 
//                (int)(d[0].getIntrinsicHeight()*0.5));
//		ScaleDrawable scaler = new ScaleDrawable(d[0],0, 0.5f, 0.5f);
//		bookInfoTitle.setCompoundDrawables(scaler.getDrawable(), null, null, null);
//		bookDetails = (RelativeLayout) findViewById(R.id.book_details_collapse);
		// defaulting to hidden
//		bookDetails.setVisibility(View.GONE);
	}
//	public void toggle_contents(View v){
//		if(v.getId()==bookInfoTitle.getId()){
//			if(bookDetails.isShown()){
//				Fx.slide_up(this, bookDetails);
//				bookDetails.setVisibility(View.GONE);
//				bookInfoTitle.setActivated(false);
//			}
//			else{
//				bookDetails.setVisibility(View.VISIBLE);
//				Fx.slide_down(this, bookDetails);
//				bookInfoTitle.setActivated(true);
//			}
//		}
//	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.editing, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_editing,
					container, false);
			return rootView;
		}
	}
	public void unauthenticatedDeleteBookForSaleTask(){
		AsyncTask<String, Void, Void> deleteBookForSale =
				new AsyncTask<String, Void, Void> () {
			@Override
			protected Void doInBackground(String... strings) {
				// Retrieve service handle.
				try {
					BookSystem.Bookforsale.Delete deleteCommand = service.bookforsale().delete(currentUserEmail,bookISBN);
					deleteCommand.execute();

				} catch (IOException e) {
					Log.e("BookSystem call", "Exception during API call", e);
				}
				return null;
			}

			@Override
			protected void onPostExecute(Void unused) {
				//Do Something
				Log.d(LOG_TAG, "Deleted (but not exactly verified)");
				
				BookData.getInstance().removeBookForSale(currentUserEmail,bookISBN);
				
				Intent intent = new Intent(EditingActivity.this,MyProfileActivity.class);	
				intent.putExtra("CURRENT_USER_EMAIL", currentUserEmail);
				intent.putExtra("last_name", currentUserLastName);
				intent.putExtra("first_name", currentUserFirstName);
				startActivity(intent);
				
			}
		};

		deleteBookForSale.execute();
	}
	public void unauthenticatedUpdateBookForSaleTask(){
		AsyncTask<String, Void, IntegerResponse> updateBookForSale =
				new AsyncTask<String, Void, IntegerResponse> () {
			@Override
			protected IntegerResponse doInBackground(String... strings) {
				IntegerResponse intR=null;
				
				try {
					BookSystem.Bookforsale.UpdatePrice updateCommand = service.bookforsale().updatePrice(currentUserEmail,bookISBN,Double.valueOf(bookPrice));
					intR=updateCommand.execute();

				} catch (IOException e) {
					Log.e("BookSystem call", "Exception during API call", e);
				}
				return intR;
			}

			@Override
			protected void onPostExecute(IntegerResponse rowsAffected) {
				if (rowsAffected!=null) {
					try {
						Log.d("BookForSale Update", rowsAffected.toPrettyString());
						BookData.getInstance().updateBookForSale(currentUserEmail,bookISBN,Double.valueOf(bookPrice));
						
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					Log.e("BookForSale Update Error", "No books for sale were affected by the API.");
				}
				
			}
		};

		updateBookForSale.execute();
	}
}
