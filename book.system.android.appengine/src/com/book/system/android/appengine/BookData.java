package com.book.system.android.appengine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import com.appspot.mac_books.bookSystem.model.BookForSale;
import com.appspot.mac_books.bookSystem.model.SaleShelf;

public class BookData {
	private HashMap<String, ArrayList<BookForSale>> bookData;
	private ArrayList<BookForSale> userBookData;
	private static final BookData holder = new BookData();
	private boolean userDataCollected = false;
	private String currentUserEmail = null;
	private String currentUserFirstName = null;
	private String currentUserLastName = null;

	public HashMap<String, ArrayList<BookForSale>> getBookData() {
		return bookData;
	}
	
	public void setBookData(HashMap<String, ArrayList<BookForSale>> data) { 
		this.bookData = data;
	}
	public ArrayList<BookForSale> getUserBookData() {
		return userBookData;
	}
	
	public void setUserBookData(ArrayList<BookForSale> data) { 
		this.userBookData = data;
	}
	public boolean isUserDataCollected() {
		return userDataCollected;
	}

	public void setUserDataCollected(boolean userDataCollected) {
		this.userDataCollected = userDataCollected;
	}

	public static BookData getInstance() {
		return holder;
	}
	public void addNewBookForSale(BookForSale bfs){
		if(userBookData==null){
			userBookData=new ArrayList<BookForSale>();
		}
	
		userBookData.add(bfs);
		String isbn = bfs.getBook().getIsbn();
		if(bookData.get(isbn)!=null){
			bookData.get(isbn).add(bfs);
		}else{
			bookData.put(isbn, new ArrayList<BookForSale>(Arrays.asList(bfs)));
		}
	}
	public void removeBookForSale(String email,String isbn){
		BookForSale doomedBook=null;
		for(BookForSale bfs : userBookData){
			if(bfs.getBook().getIsbn().equals(isbn))
				doomedBook=bfs;
		}
		userBookData.remove(doomedBook);
		if(bookData.get(isbn)!=null){
			for(BookForSale bfs : bookData.get(isbn)){
				if(bfs.getBook().getIsbn().equals(isbn) && bfs.getSeller().getEmail().equals(email))
					doomedBook=bfs;
			}
			bookData.get(isbn).remove(doomedBook);
		}
	}
	public void updateBookForSale(String email,String isbn,Double price){
		for(int i = 0; i<userBookData.size(); i++){
			if(userBookData.get(i).getBook().getIsbn().equals(isbn)){
				userBookData.get(i).setPrice(price);
			}
		}
		if(bookData.get(isbn)!=null){
			for(int i = 0; i<bookData.get(isbn).size(); i++){
				if(bookData.get(isbn).get(i).getBook().getIsbn().equals(isbn) 
						&& bookData.get(isbn).get(i).getSeller().getEmail().equals(email)){
					bookData.get(isbn).get(i).setPrice(price);
				}
			}
		}
	}

	public String getCurrentUserEmail() {
		return currentUserEmail;
	}

	public void setCurrentUserEmail(String currentUserEmail) {
		this.currentUserEmail = currentUserEmail;
	}

	public String getCurrentUserFirstName() {
		return currentUserFirstName;
	}

	public void setCurrentUserFirstName(String currentUserFirstName) {
		this.currentUserFirstName = currentUserFirstName;
	}

	public String getCurrentUserLastName() {
		return currentUserLastName;
	}

	public void setCurrentUserLastName(String currentUserLastName) {
		this.currentUserLastName = currentUserLastName;
	}

}