package com.book.system.android.appengine;

public class Book {
	private String title;
	private String ISBN;
	private String author;

	public Book(String title, String ISBN, String author) {
		this.ISBN=ISBN;
		this.author=author;
		this.title=title;

	}

	public void setTitle(String bookname){
		title = bookname;
	}

	public void setISBN(String isbn){
		ISBN = isbn;
	}

	public void setAuthor(String a){
		author = a;
	}

	public String getTitle(){
		return title;
	}

	public String getISBN(){
		return ISBN;
	}

	public String getAuthor() {
		return author;
	}
}
