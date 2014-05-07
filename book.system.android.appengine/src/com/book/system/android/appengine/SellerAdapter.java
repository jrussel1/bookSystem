package com.book.system.android.appengine;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.appspot.mac_books.bookSystem.model.BookForSale;

public class SellerAdapter extends ArrayAdapter<BookForSale>{
	
	Context c;

    public SellerAdapter(Context context, ArrayList<BookForSale> books) {
        super(context, R.layout.item_book, books);
        c = context;
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
//       Button btn = (Button)convertView.findViewById(R.id.button_item_seller);
       
       // Populate the data into the template view using the data object

       
       double temp = book.getPrice();
       String priceString = Double.toString(temp);
       
       seller.setText(book.getSeller().getFirstName());
       price.setText(priceString);
       // Return the completed view to render on screen
       
//       btn.setOnClickListener(new OnClickListener() {
//		
//		@Override
//		public void onClick(View v) {
//			// TODO Auto-generated method stub
//			Intent intent = new Intent(c, BookDetailActivity.class);
//			
//		}
//	});
       
       return convertView;
   }
}
