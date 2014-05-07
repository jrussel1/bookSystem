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

public class SellerAdapter extends ArrayAdapter<BookForSale>{

    public SellerAdapter(Context context, ArrayList<BookForSale> books) {
        super(context, R.layout.item_book, books);
     }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       // Get the data item for this position
       BookForSale book = getItem(position);    
       // Check if an existing view is being reused, otherwise inflate the view
       if (convertView == null) {
          convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_seller, null);
       }
       
       // Lookup view for data population
       TextView seller = (TextView) convertView.findViewById(R.id.Seller_item_seller);
       TextView price = (TextView) convertView.findViewById(R.id.Price_item_seller);
       // Populate the data into the template view using the data object

       
       double temp = book.getPrice();
       String priceString = Double.toString(temp);
       
       seller.setText(book.getSeller().getFirstName());
       price.setText(priceString);
       // Return the completed view to render on screen
       
       return convertView;
   }
}
