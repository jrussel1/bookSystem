package com.book.system.android.appengine;

import java.io.IOException;

import com.appspot.mac_books.bookSystem.BookSystem;
import com.appspot.mac_books.bookSystem.model.SaleShelf;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

public class TestDataActivity extends Activity {
	private String mEmailAccount;
	private static final String LOG_TAG = "TestDataActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_data);

		mEmailAccount = getIntent().getExtras().getString("mEmailAccount");
		Log.e(LOG_TAG, mEmailAccount);
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
			.add(R.id.container, new PlaceholderFragment()).commit();
		}
		getAuthenticatedSaleShelf(null);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test_data, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_test_data,
					container, false);
			return rootView;
		}
	}
	public void getAuthenticatedSaleShelf(View unused) {

		AsyncTask<Void, Void, SaleShelf> getAuthedSaleShelfAndDisplay =
				new AsyncTask<Void, Void, SaleShelf> () {
			@Override
			protected SaleShelf doInBackground(Void... unused) {


				if (!AppConstants.checkGooglePlayServicesAvailable(TestDataActivity.this)) {
					return null;
				}
				GoogleAccountCredential credential = null;
				try {
					// If the application has the appropriate access then a token will be retrieved, otherwise
					// an error will be thrown.
					credential= GoogleAccountCredential.usingAudience(
							TestDataActivity.this, AppConstants.AUDIENCE);
					credential.setSelectedAccountName(mEmailAccount);

//					String accessToken = credential.getToken();

					String accessToken = GoogleAuthUtil.getToken(TestDataActivity.this, mEmailAccount, "https://www.googleapis.com/auth/userinfo.email");
					Log.i(LOG_TAG, accessToken);

				} catch (GoogleAuthException unrecoverableException) {
					Log.e(LOG_TAG, "Exception checking OAuth2 authentication.", unrecoverableException);
				} catch (IOException ioException) {
					Log.e(LOG_TAG, "Exception checking OAuth2 authentication.", ioException);
				}

				// Retrieve service handle using credential since this is an authenticated call.
				BookSystem serviceHandle = AppConstants.getApiServiceHandle(credential);

				try {
					BookSystem.Bookforsale.List listCommand = serviceHandle.bookforsale().list();
					SaleShelf shelf = listCommand.execute();  
					return shelf;
				} catch (IOException e) {
					Log.e("TestDataActivity", "Exception during API call", e);
				}
				return null;
			}

			@Override
			protected void onPostExecute(SaleShelf shelf) {
				if (shelf!=null) {
					Log.e("TestDataActivity", "GOT SOME!");
					//		             displaySaleShelf(shelf);
				} else {
					Log.e("TestDataActivity", "No shelves were returned by the API.");
				}
			}
		};

		getAuthedSaleShelfAndDisplay.execute((Void)null);
	}
}
