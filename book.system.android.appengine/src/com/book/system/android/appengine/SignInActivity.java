package com.book.system.android.appengine;

import java.io.IOException;

import com.appspot.mac_books.bookSystem.BookSystem;
import com.appspot.mac_books.bookSystem.model.SaleShelf;
import com.book.system.android.appengine.GooglePlusLoginActivity.PlaceholderFragment;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.plus.Account;
import com.google.android.gms.plus.Plus;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class SignInActivity extends Activity {


	private String mEmailAccount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mEmailAccount = getIntent().getExtras().getString("mEmailAccount");

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
			.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		getAuthenticatedSaleShelf(null);
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


	public void getAuthenticatedSaleShelf(View unused) {

		AsyncTask<Void, Void, SaleShelf> getAuthedSaleShelfAndDisplay =
				new AsyncTask<Void, Void, SaleShelf> () {
			@Override
			protected SaleShelf doInBackground(Void... unused) {


				if (!AppConstants.checkGooglePlayServicesAvailable(SignInActivity.this)) {
					return null;
				}

				// Create a Google credential since this is an authenticated request to the API.
				GoogleAccountCredential credential = GoogleAccountCredential.usingAudience(
						SignInActivity.this, AppConstants.AUDIENCE);
				credential.setSelectedAccountName(mEmailAccount);

				// Retrieve service handle using credential since this is an authenticated call.
				BookSystem serviceHandle = AppConstants.getApiServiceHandle(credential);

				try {
					BookSystem.Bookforsale.List listCommand = serviceHandle.bookforsale().list();
					SaleShelf shelf = listCommand.execute();  
					return shelf;
				} catch (IOException e) {
					Log.e("SignInActivity", "Exception during API call", e);
				}
				return null;
			}

			@Override
			protected void onPostExecute(SaleShelf shelf) {
				if (shelf!=null) {
					Log.e("SignInActivity", "GOT SOME!");
					//		             displaySaleShelf(shelf);
				} else {
					Log.e("SignInActivity", "No shelves were returned by the API.");
				}
			}
		};

		getAuthedSaleShelfAndDisplay.execute((Void)null);
	}


}
