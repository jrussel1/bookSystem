package com.book.system.android.appengine;

import com.appspot.mac_books.bookSystem.model.SaleShelf;

public class BookData {
	private SaleShelf data;
	private static final BookData holder = new BookData();

	public SaleShelf getData() {
		return data;
	}
	
	public void setData(SaleShelf data) { 
		this.data = data;
	}

	public static BookData getInstance() {
		return holder;
	}

}