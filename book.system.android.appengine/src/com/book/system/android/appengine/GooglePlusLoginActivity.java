package com.book.system.android.appengine;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

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
import org.json.JSONStringer;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.accounts.Account;
import android.accounts.AccountManager;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.AccountPicker;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.util.Strings;
import com.appspot.mac_books.bookSystem.BookSystem;
import com.appspot.mac_books.bookSystem.BookSystem.Bookforsale.List;
import com.appspot.mac_books.bookSystem.model.SaleShelf;


public class GooglePlusLoginActivity extends Activity implements OnClickListener {

	private static final int ACTIVITY_RESULT_FROM_ACCOUNT_SELECTION = 2222;

	public static final int REQUEST_AUTHORIZATION = 991;

	private final String LOG_TAG = "GooglePlusLoginActivity";
	String authToken = null;
	private AuthorizationCheckTask mAuthTask;
	private String mEmailAccount = "";
	private BookSystem service = null;
	private String attemptEmail = "";
	private Account mAccount = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("Mac Books - Sign In");
		setContentView(R.layout.activity_google_plus_login);
		findViewById(R.id.sign_in_button).setOnClickListener(this);
		
		Typeface thin = Typeface.createFromAsset(getAssets(),
		        "fonts/Roboto-Thin.ttf");
		Typeface light = Typeface.createFromAsset(getAssets(),
		        "fonts/Roboto-Light.ttf");
		Typeface black = Typeface.createFromAsset(getAssets(),
		        "fonts/Roboto-Black.ttf");
		Typeface blackItalic = Typeface.createFromAsset(getAssets(),
		        "fonts/Roboto-BlackItalic.ttf");
		Typeface boldItalic = Typeface.createFromAsset(getAssets(),
		        "fonts/Roboto-BoldItalic.ttf");
		Typeface thinItalic = Typeface.createFromAsset(getAssets(),
		        "fonts/Roboto-ThinItalic.ttf");
		Typeface bold = Typeface.createFromAsset(getAssets(),
		        "fonts/Roboto-Bold.ttf");
		Typeface condensed = Typeface.createFromAsset(getAssets(),
		        "fonts/RobotoCondensed-Bold.ttf");
		
		TextView t1 = (TextView)findViewById(R.id.textView1);
		t1.setTypeface(thin);
		
		TextView t2 = (TextView)findViewById(R.id.textView2);
		t2.setTypeface(condensed);
		
		TextView t3 = (TextView)findViewById(R.id.textView3);
		t3.setTypeface(thinItalic);
		
		TextView t4 = (TextView)findViewById(R.id.textView4);
		t4.setTypeface(black);
		
		TextView t5 = (TextView)findViewById(R.id.textView5);
		t5.setTypeface(blackItalic);
		
		TextView t6 = (TextView)findViewById(R.id.textView6);
		t6.setTypeface(light);
		



		service = AppConstants.getApiServiceHandle(null);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
			.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}


	@Override
	public void onClick(View view) {
		// Check to see how many Google accounts are registered with the device.
		int googleAccounts = AppConstants.countGoogleAccounts(this);
		if (googleAccounts == 0) {
			// No accounts registered, nothing to do.
			Toast.makeText(this, R.string.toast_no_google_accounts_registered,
					Toast.LENGTH_LONG).show();
		} else if (googleAccounts == 1) {
			// If only one account then select it.
			Toast.makeText(this, R.string.toast_only_one_google_account_registered,
					Toast.LENGTH_LONG).show();
			AccountManager am = AccountManager.get(this);
			Account[] accounts = am.getAccountsByType(GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE);
			if (accounts != null && accounts.length > 0) {
				// Select account and perform authorization check.
				//		      emailAddressTV.setText(accounts[0].name);
				mEmailAccount = accounts[0].name;
				mAccount = accounts[0];
				performAuthCheck(accounts[0].name);
			}
		} else {
			// More than one Google Account is present, a chooser is necessary.

			// Reset selected account.
			//		    emailAddressTV.setText("");

			// Invoke an {@code Intent} to allow the user to select a Google account.
			Intent accountSelector = AccountPicker.newChooseAccountIntent(null, null,
					new String[]{GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE}, false,
					"Select an account to access the BookSystem API.", null, null, null);
			startActivityForResult(accountSelector,
					ACTIVITY_RESULT_FROM_ACCOUNT_SELECTION);
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

	/**
	 * An unauthenticated call to the app engine server.
	 */
	public void unauthenticatedSaleShelfTask(){
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
						e.printStackTrace();
					}
				} else {
					Log.e("SaleShelf error", "No shelf were returned by the API.");
				}
			}
		};

		getShelf.execute();
	}

	public void performAuthCheck(String emailAccount) {
		// Cancel previously running tasks.
		if (mAuthTask != null) {
			try {
				mAuthTask.cancel(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		attemptEmail=emailAccount;
		new AuthorizationCheckTask().execute(emailAccount);
	}

	class AuthorizationCheckTask extends AsyncTask<String, Integer, Boolean> {
		@Override
		protected Boolean doInBackground(String... emailAccounts) {
			Log.i(LOG_TAG, "Background task started.");

			if (!AppConstants.checkGooglePlayServicesAvailable(GooglePlusLoginActivity.this)) {
				return false;
			}
			String emailAccount = emailAccounts[0];
			// Ensure only one task is running at a time.
			mAuthTask = this;

			// Ensure an email was selected.
			if (Strings.isNullOrEmpty(emailAccount)) {
				publishProgress(R.string.toast_no_google_account_selected);
				// Failure.
				return false;
			}

			Log.d(LOG_TAG, "Attempting to get AuthToken for account: " + emailAccount);
			
			try {
				// If the application has the appropriate access then a token will be retrieved, otherwise
				// an error will be thrown.
//				GoogleAccountCredential credential = GoogleAccountCredential.usingAudience(
//						GooglePlusLoginActivity.this, AppConstants.AUDIENCE);
//				Log.d(LOG_TAG,credential.getScope());
//				credential.setSelectedAccountName(emailAccount);
//				//
//				String accessToken = credential.getToken();
				authToken = GoogleAuthUtil.getToken(GooglePlusLoginActivity.this, emailAccount, "oauth2:https://www.googleapis.com/auth/plus.me");
				
				Log.d(LOG_TAG, "AuthToken retrieved");

				// Success.
				return true;
			} catch (UserRecoverableAuthException userAuthEx) {
				// Start the user recoverable action using the intent returned by
				// getIntent()
				startActivityForResult(
						userAuthEx.getIntent(),
						REQUEST_AUTHORIZATION);
				return false;
			}catch (GoogleAuthException unrecoverableException) {
				Log.e(LOG_TAG, "Exception checking OAuth2 authentication.", unrecoverableException);
				publishProgress(R.string.toast_exception_checking_authorization);
				// Failure.
				try{
					authToken = GoogleAuthUtil.getToken(GooglePlusLoginActivity.this, emailAccount, "oauth2:https://www.googleapis.com/auth/userinfo.email");
					Log.d(LOG_TAG, "AuthToken retrieved");
					return true;
				}catch (GoogleAuthException unrecoverableException2) {
					Log.e(LOG_TAG, "Exception checking OAuth2 authentication.", unrecoverableException2);
					publishProgress(R.string.toast_exception_checking_authorization);
					// Failure.
					return false;
				} catch (IOException e) {
					Log.e(LOG_TAG, "Exception checking OAuth2 authentication.", e);
					publishProgress(R.string.toast_exception_checking_authorization);
					// Failure or cancel request.
					return false;
				}
			} catch (IOException ioException) {
				Log.e(LOG_TAG, "Exception checking OAuth2 authentication.", ioException);
				publishProgress(R.string.toast_exception_checking_authorization);
				// Failure or cancel request.
				try{
					authToken = GoogleAuthUtil.getToken(GooglePlusLoginActivity.this, emailAccount, "oauth2:https://www.googleapis.com/auth/userinfo.email");
					Log.d(LOG_TAG, "AuthToken retrieved");
					return true;
				}catch (GoogleAuthException unrecoverableException2) {
					Log.e(LOG_TAG, "Exception checking OAuth2 authentication.", unrecoverableException2);
					publishProgress(R.string.toast_exception_checking_authorization);
					// Failure.
					return false;
				} catch (IOException e) {
					Log.e(LOG_TAG, "Exception checking OAuth2 authentication.", ioException);
					publishProgress(R.string.toast_exception_checking_authorization);
					// Failure or cancel request.
					return false;
				}
			}
		}

		@Override
		protected void onProgressUpdate(Integer... stringIds) {
			// Toast only the most recent.
			Integer stringId = stringIds[0];
			Toast.makeText(GooglePlusLoginActivity.this, stringId, Toast.LENGTH_SHORT).show();
		}

		@Override
		protected void onPreExecute() {
			mAuthTask = this;
		}

		@Override
		protected void onPostExecute(Boolean success) {
			mAuthTask = null;
			if(success){
				Log.d(LOG_TAG, "Successful auth of email");
				mEmailAccount = attemptEmail;
//				unauthenticatedSaleShelfTask();
				Log.d(LOG_TAG, authToken);
				new RequestTask().execute("https://www.googleapis.com/plus/v1/people/me?access_token="+authToken);
				
			}else{
				Log.e(LOG_TAG, "Failure to authenticate email");
			}

		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (requestCode == ACTIVITY_RESULT_FROM_ACCOUNT_SELECTION && resultCode == RESULT_OK) {
			// This path indicates the account selection activity resulted in the user selecting a
			// Google account and clicking OK.
			AccountManager am = AccountManager.get(this);
			Account[] accounts = am.getAccountsByType(GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE);
			
			// Set the selected account.
			String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
			
			for(int i=0;i<accounts.length;i++){
				if(accounts[i].name.equals(accountName)){
					mAccount=accounts[i];
				}
			}
			

			// Fire off the authorization check for this account and OAuth2 scopes.
			performAuthCheck(accountName);
		}else if (requestCode == REQUEST_AUTHORIZATION && resultCode == RESULT_OK) {
			// This path indicates the account selection activity resulted in the user needing
			// to authorize the application

			// Set the selected account.
			String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);

			// Fire off the authorization check for this account and OAuth2 scopes.
			performAuthCheck(accountName);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mAuthTask!=null) {
			mAuthTask.cancel(true);
			mAuthTask = null;
		}
	}

	public void onClickGetAuthenticatedSaleShelf(View unused) {
		if (!isSignedIn()) {
			Toast.makeText(this, "You must sign in for this action.", Toast.LENGTH_LONG).show();
			return;
		}

		AsyncTask<Void, Void, SaleShelf> getAuthedSaleShelf =
				new AsyncTask<Void, Void, SaleShelf> () {
			@Override
			protected SaleShelf doInBackground(Void... unused) {
				if (!isSignedIn()) {
					return null;
				};

				if (!AppConstants.checkGooglePlayServicesAvailable(GooglePlusLoginActivity.this)) {
					return null;
				}

				// Create a Google credential since this is an authenticated request to the API.
				GoogleAccountCredential credential = GoogleAccountCredential.usingAudience(
						GooglePlusLoginActivity.this, AppConstants.AUDIENCE);
				Log.d(LOG_TAG,credential.getScope());
				credential.setSelectedAccountName(mEmailAccount);

				// Retrieve service handle using credential since this is an authenticated call.
				BookSystem apiServiceHandle = AppConstants.getApiServiceHandle(credential);

				try {
					List getAuthedListCommand = apiServiceHandle.bookforsale().list();
					SaleShelf shelf = getAuthedListCommand.execute();
					return shelf;
				}  catch (IOException e) {
					Log.e(LOG_TAG, "Exception during API call", e);
				}
				return null;
			}

			@Override
			protected void onPostExecute(SaleShelf shelf) {
				if (shelf!=null) {
					try {
						Log.d("SaleShelf", shelf.toPrettyString());
//						Log.d(LOG_TAG,AccountManager.KEY_USERDATA);
						
						
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					Log.e(LOG_TAG, "No shelf was returned by the API.");
				}
			}
		};

		getAuthedSaleShelf.execute((Void)null);
	}

	private boolean isSignedIn() {
		if (!Strings.isNullOrEmpty(mEmailAccount)) {
			return true;
		} else {
			return false;
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
//	                throw new IOException(statusLine.getReasonPhrase());
	            }
	        } catch (ClientProtocolException e) {
	            //TODO Handle problems..
	        	e.printStackTrace();
	        } catch (IOException e) {
	            //TODO Handle problems..
	        	e.printStackTrace();
	        	Log.e(LOG_TAG,e.getLocalizedMessage());
	        }

//	        Log.d(LOG_TAG,responseString);

	        return responseString;
	    }

	    @Override
	    protected void onPostExecute(String result) {
	        super.onPostExecute(result);
//	        Log.d(LOG_TAG,result);
	        
	        Intent intent = new Intent(GooglePlusLoginActivity.this,BookListActivity.class);
			intent.putExtra("CURRENT_USER_EMAIL", mEmailAccount);
			
	        if (result != null) {
	        	
	        	try {
					JSONObject jObject = new JSONObject(result);
					
					
					if(jObject.has("name")){
						intent.putExtra("last_name", jObject.getJSONObject("name").getString("familyName"));
						intent.putExtra("first_name", jObject.getJSONObject("name").getString("givenName"));
					}else{
						Log.e(LOG_TAG,"No name");
					}
					startActivity(intent);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	
	        } else {
	        	startActivity(intent);
	        }
	        
	        
	        
	    }
	}
}
