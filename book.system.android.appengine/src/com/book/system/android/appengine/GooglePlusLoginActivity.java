package com.book.system.android.appengine;

import java.io.IOException;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.appspot.mac_books.bookSystem.BookSystem;
import com.appspot.mac_books.bookSystem.model.SaleShelf;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;

public class GooglePlusLoginActivity extends Activity implements OnClickListener {


	private BookSystem service = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_google_plus_login);
		findViewById(R.id.sign_in_button).setOnClickListener(this);

		
		service = AppConstants.getApiServiceHandle(null);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
			.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	
	@Override
	public void onClick(View view) {
//		if (view.getId() == R.id.sign_in_button
//				&& !mGoogleApiClient.isConnecting()) {
//			mSignInClicked = true;
//			resolveSignInErrors();
//		}
		
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
								Intent intent = new Intent(GooglePlusLoginActivity.this,BookListActivity.class);
								startActivity(intent);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
	                    } else {
	                        Log.e("SaleShelf error", "No shelf were returned by the API.");
	                    }
	                }
	            };

	            getShelf.execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.google_plus_login, menu);
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
					R.layout.fragment_google_plus_login, container, false);
			return rootView;
		}
	}

	

}
