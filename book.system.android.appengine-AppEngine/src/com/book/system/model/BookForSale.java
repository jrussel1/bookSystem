/* Copyright 2013 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.book.system.model;


public class BookForSale {

	private Book book;
	private Seller seller;

	public BookForSale(Book book, Seller seller) {
		this.setBook(book);
		this.setSeller(seller);
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