package com.book.system.android.appengine;

import java.io.IOException;

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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;

import com.appspot.mac_books.bookSystem.BookSystem;
import com.appspot.mac_books.bookSystem.model.SaleShelf;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.plus.Plus;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

public class GooglePlusLoginActivity extends Activity implements ConnectionCallbacks, OnConnectionFailedListener, OnClickListener {
	/* Request code used to invoke sign in user interactions. */
	private static final int RC_SIGN_IN = 0;

	/* Client used to interact with Google APIs. */
	private GoogleApiClient mGoogleApiClient;

	/* A flag indicating that a PendingIntent is in progress and prevents
	 * us from starting further intents.
	 */
	private boolean mIntentInProgress;

	/* Track whether the sign-in button has been clicked so that we know to resolve
	 * all issues preventing sign-in without waiting.
	 */
	private boolean mSignInClicked;

	/* Store the connection result from onConnectionFailed callbacks so that we can
	 * resolve them when the user clicks sign-in.
	 */
	private ConnectionResult mConnectionResult;

	private static final String LOG_TAG = "GooglePlusLoginActivity";

	private String mEmailAccount;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_google_plus_login);
		findViewById(R.id.sign_in_button).setOnClickListener(this);

		mGoogleApiClient = new GoogleApiClient.Builder(this)
        .addConnectionCallbacks(this)
        .addOnConnectionFailedListener(this)
        .addApi(Plus.API, null)
        .addScope(Plus.SCOPE_PLUS_LOGIN)
        .build();

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
			.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	public void onConnectionSuspended(int cause) {
		mGoogleApiClient.connect();
	}

	protected void onStart() {
		super.onStart();
		mGoogleApiClient.connect();
	}

	protected void onStop() {
		super.onStop();
		if (mGoogleApiClient.isConnected()) {
			mGoogleApiClient.disconnect();
		}
	}

	protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
		if (requestCode == RC_SIGN_IN) {
			if (responseCode != RESULT_OK) {
				mSignInClicked = false;
			}

			mIntentInProgress = false;

			if (!mGoogleApiClient.isConnecting()) {
				mGoogleApiClient.connect();
			}
		}
	}
	@Override
	public void onConnected(Bundle connectionHint) {
		mSignInClicked = false;
		Toast.makeText(this, "User is connected!", Toast.LENGTH_LONG).show();
//		Intent intent = new Intent(GooglePlusLoginActivity.this, TestDataActivity.class);
//		intent.putExtra("mEmailAccount", Plus.AccountApi.getAccountName(mGoogleApiClient));
		
//		startActivity(intent);
		mEmailAccount=Plus.AccountApi.getAccountName(mGoogleApiClient);
		getAuthenticatedSaleShelf(null);
	}

	/* A helper method to resolve the current ConnectionResult error. */
	private void resolveSignInErrors() {
		if (mConnectionResult.hasResolution()) {
			try {
				mIntentInProgress = true;
				mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);

			} catch (SendIntentException e) {
				// The intent was canceled before it was sent.  Return to the default
				// state and attempt to connect to get an updated ConnectionResult.
				mIntentInProgress = false;
				mGoogleApiClient.connect();
			}
		}
	}

	public void onConnectionFailed(ConnectionResult result) {
		if (!mIntentInProgress) {
			// Store the ConnectionResult so that we can use it later when the user clicks
			// 'sign-in'.
			mConnectionResult = result;

			if (mSignInClicked) {
				// The user has already clicked 'sign-in' so we attempt to resolve all
				// errors until the user is signed in, or they cancel.
				resolveSignInErrors();
			}
		}
	}

	@Override
	public void onClick(View view) {
		if (view.getId() == R.id.sign_in_button
				&& !mGoogleApiClient.isConnecting()) {
			mSignInClicked = true;
			resolveSignInErrors();
		}
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
	public void getAuthenticatedSaleShelf(View unused) {

		AsyncTask<Void, Void, SaleShelf> getAuthedSaleShelfAndDisplay =
				new AsyncTask<Void, Void, SaleShelf> () {
			@Override
			protected SaleShelf doInBackground(Void... unused) {


				if (!AppConstants.checkGooglePlayServicesAvailable(GooglePlusLoginActivity.this)) {
					return null;
				}
				GoogleAccountCredential credential = null;
				try {
					// If the application has the appropriate access then a token will be retrieved, otherwise
					// an error will be thrown.
					credential= GoogleAccountCredential.usingAudience(
							GooglePlusLoginActivity.this, AppConstants.AUDIENCE);
					credential.setSelectedAccountName(mEmailAccount);

					String accessToken = credential.getToken();

//					String accessToken = GoogleAuthUtil.getToken(GooglePlusLoginActivity.this, mEmailAccount, "https://www.googleapis.com/auth/userinfo.email");
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
					Log.e(LOG_TAG, "Exception during API call", e);
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
