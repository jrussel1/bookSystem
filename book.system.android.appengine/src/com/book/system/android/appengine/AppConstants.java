package com.book.system.android.appengine;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;

import com.appspot.mac_books.bookSystem.BookSystem;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;

import javax.annotation.Nullable;

/**
 * Application constants and simple utilities.
 */
public class AppConstants {
	/**
	 * Your WEB CLIENT ID from the API Access screen of the Developer Console for your project. This
	 * is NOT the Android client id from that screen.
	 *
	 * @see <a href="https://developers.google.com/console">https://developers.google.com/console</a>
	 */
	public static final String WEB_CLIENT_ID = "615375088497-cecfir0egouhcem4qc36u20ioegi2eps.apps.googleusercontent.com";

	/**
	 * The audience is defined by the web client id, not the Android client id.
	 */
	public static final String AUDIENCE = "server:client_id:" + WEB_CLIENT_ID;

	/**
	 * Class instance of the JSON factory.
	 */
	public static final JsonFactory GSON_FACTORY = new GsonFactory();

	/**
	 * Class instance of the HTTP transport.
	 */
	public static final HttpTransport HTTP_TRANSPORT = AndroidHttp.newCompatibleTransport();

	/**
	 * Count Google accounts on the device.
	 */
	public static int countGoogleAccounts(Context context) {
		AccountManager am = AccountManager.get(context);
		Account[] accounts = am.getAccountsByType(GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE);
		if (accounts == null || accounts.length < 1) {
			return 0;
		} else {
			return accounts.length;
		}
	}

	/**
	 * Retrieve a BookSystem api service handle to access the API.
	 */
	public static BookSystem getApiServiceHandle(@Nullable GoogleAccountCredential credential) {
		BookSystem.Builder bookSystem = null;
		// Use a builder to help formulate the API request.
		if(credential==null){
			bookSystem = new BookSystem.Builder(
					AppConstants.HTTP_TRANSPORT, AppConstants.GSON_FACTORY, null);
		}else{
			bookSystem = new BookSystem.Builder(
					AppConstants.HTTP_TRANSPORT,AppConstants.GSON_FACTORY, credential);
		}

		if (CloudEndpointUtils.LOCAL_ANDROID_RUN) {
			bookSystem.setRootUrl(CloudEndpointUtils.LOCAL_APP_ENGINE_SERVER_URL_FOR_ANDROID
					+ "/_ah/api/");
		}
		bookSystem.setApplicationName("Mac Books");
		return bookSystem.build();
	}

	/**
	 * Check that Google Play services APK is installed and up to date.
	 */
	public static boolean checkGooglePlayServicesAvailable(Activity activity) {
		final int connectionStatusCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity);
		if (GooglePlayServicesUtil.isUserRecoverableError(connectionStatusCode)) {
			showGooglePlayServicesAvailabilityErrorDialog(activity, connectionStatusCode);
			return false;
		}
		return true;
	}

	/**
	 * Called if the device does not have Google Play Services installed.
	 */
	public static void showGooglePlayServicesAvailabilityErrorDialog(final Activity activity,
			final int connectionStatusCode) {
		final int REQUEST_GOOGLE_PLAY_SERVICES = 0;
		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Dialog dialog = GooglePlayServicesUtil.getErrorDialog(
						connectionStatusCode, activity, REQUEST_GOOGLE_PLAY_SERVICES);
				dialog.show();
			}
		});
	}
}