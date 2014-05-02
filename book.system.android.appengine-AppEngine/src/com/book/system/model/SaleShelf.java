package com.book.system.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SaleShelf {

	private HashMap<String,List<BookForSale>> isbnToList = null;
	private HashMap<Long,List<BookForSale>> sellerIdToList = null;
	private HashMap<String,HashMap<Long,BookForSale>> isbnToMap = null;
	private HashMap<Long,HashMap<String,BookForSale>> sellerIdToMap = null;
	private ArrayList<BookForSale> list = new ArrayList<BookForSale>();
	
	public SaleShelf(){
		this.isbnToList = new HashMap<String,List<BookForSale>>();
		this.sellerIdToList = new HashMap<Long,List<BookForSale>>();
		this.isbnToMap = new HashMap<String,HashMap<Long,BookForSale>>();
		this.sellerIdToMap = new HashMap<Long,HashMap<String,BookForSale>>();
	}

	public void addToShelf(BookForSale bfs){
		Book book = bfs.getBook();
		Seller seller = bfs.getSeller();
		String isbn = book.getISBN();
		Long id = seller.getId();
		addToISBNList(isbn,bfs);
		addToIDList(id,bfs);
		addToISBNMap(isbn,id,bfs);
		addToIDMap(isbn,id,bfs);
	}
	
	public void addToISBNList(String isbn, BookForSale bfs){
		List<BookForSale> temp;
		if(isbnToList.containsKey(isbn)){
			isbnToList.get(isbn).add(bfs);
		}else{
			temp = new ArrayList<BookForSale>();
			temp.add(bfs);
			isbnToList.put(isbn, temp);
			temp = null;
		}
	}
	public void addToList(BookForSale bfs){
		this.list.add(bfs);
	}
	public void addToIDList(Long id, BookForSale bfs){
		List<BookForSale> temp;
		if(sellerIdToList.containsKey(id)){
			sellerIdToList.get(id).add(bfs);
		}else{
			temp = new ArrayList<BookForSale>();
			temp.add(bfs);
			sellerIdToList.put(id, temp);
			temp = null;
		}
	}
	
	public void addToISBNMap(String isbn, Long id, BookForSale bfs){
		HashMap<Long,BookForSale> temp;
		if(isbnToMap.containsKey(isbn)){
			isbnToMap.get(isbn).put(id, bfs);
		}else{
			temp = new HashMap<Long,BookForSale>();
			temp.put(id,bfs);
			isbnToMap.put(isbn, temp);
			temp = null;
		}
	}
	
	public void addToIDMap(String isbn, Long id, BookForSale bfs){
		HashMap<String,BookForSale> temp;
		if(sellerIdToMap.containsKey(id)){
			sellerIdToMap.get(id).put(isbn, bfs);
		}else{
			temp = new HashMap<String,BookForSale>();
			temp.put(isbn,bfs);
			sellerIdToMap.put(id, temp);
			temp = null;
		}
	}

	/**
	 * @return the isbnToList
	 */
	public HashMap<String, List<BookForSale>> getIsbnToList() {
		return isbnToList;
	}

	/**
	 * @return the sellerIdToList
	 */
	public HashMap<Long, List<BookForSale>> getSellerIdToList() {
		return sellerIdToList;
	}

	/**
	 * @return the isbnToMap
	 */
	public HashMap<String, HashMap<Long, BookForSale>> getIsbnToMap() {
		return isbnToMap;
	}

	/**
	 * @return the sellerIdToMap
	 */
	public HashMap<Long, HashMap<String, BookForSale>> getSellerIdToMap() {
		return sellerIdToMap;
	}

	public ArrayList<BookForSale> getList() {
		return list;
	}

	public void setList(ArrayList<BookForSale> list) {
		this.list = list;
	}
}
