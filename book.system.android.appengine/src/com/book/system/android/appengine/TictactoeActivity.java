package com.book.system.android.appengine;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.appspot.mac_books.bookSystem.BookSystem;
import com.appspot.mac_books.bookSystem.model.SaleShelf;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.AccountPicker;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.repackaged.com.google.common.base.Strings;


/**
 * Main activity for the application, it handles the game UI and auth and spawns
 * tasks to Endpoints.
 */
public class TictactoeActivity extends Activity {
	private static final String LOG_TAG = "TictactoeActivity";
	String accountName;
    private AuthorizationCheckTask mAuthTask;
    private String mEmailAccount = "";

	/**
	 * Credentials object that maintains tokens to send to the backend.
	 */
	GoogleAccountCredential credential;

	boolean signedIn = false;

	/**
	 * Service object that manages requests to the backend.
	 */
	BookSystem service;
	public void onClickSignIn(View view) {
		TextView emailAddressTV = (TextView) view.getRootView().findViewById(R.id.email_address_tv);
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
				emailAddressTV.setText(accounts[0].name);
				accountName = accounts[0].name;
				  	      performAuthCheck(accounts[0].name);
			}
		} else {
			// More than one Google Account is present, a chooser is necessary.

			// Reset selected account.
			emailAddressTV.setText("");

			// Invoke an {@code Intent} to allow the user to select a Google account.
			Intent accountSelector = AccountPicker.newChooseAccountIntent(null, null,
					new String[]{GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE}, false,
					"Select the account to access Google Compute Engine API.", null, null, null);
			startActivityForResult(accountSelector,
					2222);
		}


		if (credential.getSelectedAccountName() != null) {
			onSignIn();
		}
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		credential = GoogleAccountCredential.usingAudience(this,
				AppConstants.AUDIENCE);

		BookSystem.Builder builder = new BookSystem.Builder(
				AndroidHttp.newCompatibleTransport(), new GsonFactory(),
				credential);
		service = builder.build();

		if (credential.getSelectedAccountName() != null) {
			onSignIn();
		}
	}
	public void performAuthCheck(String emailAccount) {
	    // Cancel previously running tasks.
	    if (mAuthTask != null) {
	      try {
	        mAuthTask.cancel(true);
	      } catch (Exception e) {
	        e.printStackTrace();
	      }
	      return;
	    }
	 // Start task to check authorization.
	    mAuthTask = new AuthorizationCheckTask();
	    mAuthTask.execute(emailAccount);
	    
	  }

	  /**
	   * Verifies OAuth2 token access for the application and Google account combination with
	   * the {@code AccountManager} and the Play Services installed application. If the appropriate
	   * OAuth2 access hasn't been granted (to this application) then the task may fire an
	   * {@code Intent} to request that the user approve such access. If the appropriate access does
	   * exist then the button that will let the user proceed to the next activity is enabled.
	   */
	  class AuthorizationCheckTask extends AsyncTask<String, Integer, Boolean> {
	    @Override
	    protected Boolean doInBackground(String... emailAccounts) {

	      if (!AppConstants.checkGooglePlayServicesAvailable(TictactoeActivity.this)) {
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

	      

	      try {
	        // If the application has the appropriate access then a token will be retrieved, otherwise
	        // an error will be thrown.
	        GoogleAccountCredential credential = GoogleAccountCredential.usingAudience(
	        		TictactoeActivity.this, AppConstants.AUDIENCE);
	        credential.setSelectedAccountName(emailAccount);

	        String accessToken = credential.getToken();

	        

	        // Success.
	        return true;
	      } catch (GoogleAuthException unrecoverableException) {
//	        Log.e(LOG_TAG, "Exception checking OAuth2 authentication.", unrecoverableException);
	        publishProgress(R.string.toast_exception_checking_authorization);
	        // Failure.
	        return false;
	      } catch (IOException ioException) {
//	        Log.e(LOG_TAG, "Exception checking OAuth2 authentication.", ioException);
	        publishProgress(R.string.toast_exception_checking_authorization);
	        // Failure or cancel request.
	        return false;
	      }
	    }

	  @Override
	  protected void onProgressUpdate(Integer... stringIds) {
	    // Toast only the most recent.
	    Integer stringId = stringIds[0];
	    Toast.makeText(TictactoeActivity.this, stringId, Toast.LENGTH_SHORT).show();
	  }

	  @Override
	  protected void onPreExecute() {
	    mAuthTask = this;
	  }

	  @Override
	  protected void onPostExecute(Boolean success) {
	    TextView emailAddressTV = (TextView) TictactoeActivity.this.findViewById(R.id.email_address_tv);
	    if (success) {
	      // Authorization check successful, set internal variable.
	      mEmailAccount = emailAddressTV.getText().toString();
	      onClickGetAuthenticatedSaleShelf(emailAddressTV);
	    } else {
	      // Authorization check unsuccessful, reset TextView to empty.
	      emailAddressTV.setText("");
	    }
	    mAuthTask = null;
	  }

	  @Override
	  protected void onCancelled() {
	    mAuthTask = null;
	  }
	}
	private void onSignIn() {
		this.signedIn = true;
		setAccountLabel(this.accountName);
		setSignInEnablement(false);

		try {
			setTextDump(service.bookforsale().list().toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();

	}

	private void setAccountLabel(String label) {
		TextView userLabel = (TextView) findViewById(R.id.email_address_tv);
		userLabel.setText(label);
	}
	private void setTextDump(String text) {
		TextView textDump = (TextView) findViewById(R.id.textDump);
		textDump.setText(text);
	}
	private void setSignInEnablement(boolean state) {
		Button button = (Button) findViewById(R.id.sign_in_button);
		if (state) {
			button.setText("Sign In");
		} else {
			button.setText("Sign Out");
		}
	}
	@Override
	protected void onResume() {
		super.onResume();
		AppConstants.checkGooglePlayServicesAvailable(TictactoeActivity.this);
	}
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      super.onActivityResult(requestCode, resultCode, data);

      if (requestCode == 2222 && resultCode == RESULT_OK) {
        // This path indicates the account selection activity resulted in the user selecting a
        // Google account and clicking OK.

        // Set the selected account.
        String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
        TextView emailAccountTextView = (TextView)this.findViewById(R.id.email_address_tv);
        emailAccountTextView.setText(accountName);

        // Fire off the authorization check for this account and OAuth2 scopes.
        performAuthCheck(accountName);
      }
    }
	public void onClickGetAuthenticatedSaleShelf(View unused) {
		   if (!isSignedIn()) {
		     Toast.makeText(this, "You must sign in for this action.", Toast.LENGTH_LONG).show();
		     return;
		   }

		   AsyncTask<Void, Void, SaleShelf> getAuthedSaleShelfAndDisplay =
		       new AsyncTask<Void, Void, SaleShelf> () {
		         @Override
		         protected SaleShelf doInBackground(Void... unused) {
		           if (!isSignedIn()) {
		             return null;
		           };

		           if (!AppConstants.checkGooglePlayServicesAvailable(TictactoeActivity.this)) {
		             return null;
		           }

		           // Create a Google credential since this is an authenticated request to the API.
		           GoogleAccountCredential credential = GoogleAccountCredential.usingAudience(
		        		   TictactoeActivity.this, AppConstants.AUDIENCE);
		           credential.setSelectedAccountName(mEmailAccount);

		           // Retrieve service handle using credential since this is an authenticated call.
		           BookSystem serviceHandle = AppConstants.getApiServiceHandle(credential);

		           try {
		             BookSystem.Bookforsale.List listCommand = serviceHandle.bookforsale().list();
		             SaleShelf shelf = listCommand.execute(); //TODO: Fix this 
		             return shelf;
		           } catch (IOException e) {
		             Log.e(LOG_TAG, "Exception during API call", e);
		           }
		           return null;
		         }

		         @Override
		         protected void onPostExecute(SaleShelf shelf) {
		           if (shelf!=null) {
		             displaySaleShelf(shelf);
		           } else {
		             Log.e(LOG_TAG, "No greetings were returned by the API.");
		           }
		         }
		       };

		       getAuthedSaleShelfAndDisplay.execute((Void)null);
		 }
	private void displaySaleShelf(SaleShelf shelf) {
	    String msg;
	    if (shelf==null) {
	      msg = "Shelf was not present";
	      Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
	    } else {
	      try {
			setTextDump(shelf.getIsbnToList().toPrettyString());
		} catch (IOException e) {
			msg = "Shelf error...";
		      Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
		}
	    }
	  }
		 private boolean isSignedIn() {
		   if (!Strings.isNullOrEmpty(mEmailAccount)) {
		     return true;
		   } else {
		     return false;
		   }
		 }
}