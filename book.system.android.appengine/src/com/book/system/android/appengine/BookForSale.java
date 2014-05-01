package com.book.system.android.appengine;

import com.book.system.android.appengine.Book;
import com.book.system.android.appengine.Seller;

public class BookForSale {

	private Book book;
	private Seller seller;
	private Double price;

	public BookForSale(Book book, Seller seller, Double price) {
		this.setBook(book);
		this.setSeller(seller);
		this.setPrice(price);
	}

	/**
	 * @return the seller
	 */
	public Seller getSeller() {
		return seller;
	}
	/**
	 * @param seller the seller to set
	 */
	public void setSeller(Seller seller) {
		this.seller = seller;
	}

	/**
	 * @return the book
	 */
	public Book getBook() {
		return book;
	}

	/**
	 * @param book the book to set
	 */
	public void setBook(Book book) {
		this.book = book;
	}
	
	public void setPrice(Double price){
		this.price = price;
	}
	
	public Double getPrice(){
		return price;
	}
	public String toString(){
		StringBuilder s = new StringBuilder();
		s.append("Book:\n"
				+ "\tISBN:\t"+book.getISBN()
				+ "\n\tTitle:\t"+book.getTitle()
				+ "\n\tAuthor:\t"+book.getAuthor());
		s.append("\nSeller:\n"
				+ "\tID:\t"+seller.getId()
				+ "\n\tEmail:\t"+seller.getEmail()
				+ "\n\tFirst name:\t"+seller.getFirstName()
				+ "\n\tLast name:\t"+seller.getLastName());
		return s.toString();
	}
}