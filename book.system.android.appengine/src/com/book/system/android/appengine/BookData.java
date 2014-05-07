package com.book.system.android.appengine;

import java.util.ArrayList;
import java.util.HashMap;

import com.appspot.mac_books.bookSystem.model.BookForSale;
import com.appspot.mac_books.bookSystem.model.SaleShelf;

public class BookData {
	private HashMap<String, ArrayList<BookForSale>> bookData;
	private ArrayList<BookForSale> userBookData;
	private static final BookData holder = new BookData();

	public HashMap<String, ArrayList<BookForSale>> getBookData() {
		return bookData;
	}
	
	public void setBookData(HashMap<String, ArrayList<BookForSale>> data) { 
		this.bookData = data;
	}
	public ArrayList<BookForSale> getUserBookData() {
		return userBookData;
	}
	
	public void setData(ArrayList<BookForSale> data) { 
		this.userBookData = data;
	}
	public static BookData getInstance() {
		return holder;
	}

}