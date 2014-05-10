package com.book.system.android.appengine;

import java.util.ArrayList;
import java.util.List;

import com.appspot.mac_books.bookSystem.model.BookForSale;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class BookAdapter extends ArrayAdapter<BookForSale>{
	Context actContext = null;
    public BookAdapter(Context context, ArrayList<BookForSale> books) {
        super(context, R.layout.item_book, books);
        actContext=context;
     }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       // Get the data item for this position
       BookForSale book = getItem(position);    
       // Check if an existing view is being reused, otherwise inflate the view
       if (convertView == null) {
          convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_book, null);
       }
       
       // Lookup view for data population
       TextView bookTitle = (TextView) convertView.findViewById(R.id.tvName);
       TextView author = (TextView) convertView.findViewById(R.id.tvHome);
       TextView isbn = (TextView) convertView.findViewById(R.id.isbn);
       // Populate the data into the template view using the data object
       
       Typeface tf = Typeface.createFromAsset(actContext.getAssets(),
		        "fonts/Roboto-Thin.ttf");
		Typeface tf2 = Typeface.createFromAsset(actContext.getAssets(),
		        "fonts/Roboto-Light.ttf");
       bookTitle.setTypeface(tf);
       author.setTypeface(tf);
       isbn.setTypeface(tf);
       
       bookTitle.setText(book.getBook().getTitle());
       author.setText(book.getBook().getAuthor());
       isbn.setText("ISBN: "+book.getBook().getIsbn());
       
       // Return the completed view to render on screen
       
       return convertView;
   }
}
