package com.book.system.android.appengine;

import java.util.ArrayList;
import java.util.HashMap;

import com.appspot.mac_books.bookSystem.model.BookForSale;
import com.appspot.mac_books.bookSystem.model.SaleShelf;

public class BookData {
	private HashMap<String, ArrayList<BookForSale>> data;
	private static final BookData holder = new BookData();

	public HashMap<String, ArrayList<BookForSale>> getData() {
		return data;
	}
	
	public void setData(HashMap<String, ArrayList<BookForSale>> data) { 
		this.data = data;
	}

	public static BookData getInstance() {
		return holder;
	}

}