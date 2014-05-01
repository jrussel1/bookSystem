package com.book.system.android.appengine;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class BookAdapter extends ArrayAdapter<BookForSale>{

    public BookAdapter(Context context, ArrayList<BookForSale> books) {
        super(context, R.layout.item_book, books);
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
       TextView seller = (TextView) convertView.findViewById(R.id.tvHome);
       TextView price = (TextView) convertView.findViewById(R.id.tvPrice);
       // Populate the data into the template view using the data object

       
       bookTitle.setText(book.getBook().getTitle());
       seller.setText(book.getSeller().getFirstName());
       double temp = book.getPrice();
       String priceString = Double.toString(temp);
       price.setText(priceString);
       // Return the completed view to render on screen
       
       return convertView;
   }
}
