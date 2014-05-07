package com.book.system.android.appengine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import com.appspot.mac_books.bookSystem.model.BookForSale;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.os.Build;

public class MyProfileActivity extends ListActivity {
	protected Button addButton;
	private final String LOG_TAG = "MyProfileActivity";
	private HashMap<String, ArrayList<BookForSale>> saleshelf = null;
	
	private void setAdapter() {
		ArrayList<BookForSale> aList = new ArrayList<BookForSale>();
		for(Entry<String, ArrayList<BookForSale>> entry:saleshelf.entrySet()){
			aList.add(entry.getValue().get(0));
		}
		Log.i(LOG_TAG,aList.toString());
		Log.i(LOG_TAG,String.valueOf(aList.size()));

		BookAdapter adapter = new BookAdapter(MyProfileActivity.this, aList);
		// Attach the adapter to a ListView
		ListView list = (ListView)findViewById(R.id.mybooklist);
		list.setAdapter(adapter);
		setListAdapter(adapter);
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_profile);
		setTitle("My Profile");
		
		TextView myinfo = (TextView) findViewById(R.id.myinfo);
		myinfo.setPaintFlags(myinfo.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
		
		TextView mybooks = (TextView) findViewById(R.id.mybooks);
		mybooks.setPaintFlags(myinfo.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
		
		addButton = (Button) findViewById(R.id.addButton);
		addButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MyProfileActivity.this, AddBookActivity.class);
				startActivity(intent);
			}
		});
		

//		if (savedInstanceState == null) {
//			getFragmentManager().beginTransaction()
//					.add(R.id.container, new PlaceholderFragment()).commit();
//		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_profile, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_my_profile,
					container, false);
			return rootView;
		}
	}

}
