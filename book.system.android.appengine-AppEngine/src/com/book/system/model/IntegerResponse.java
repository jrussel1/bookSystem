package com.book.system.model;

public class IntegerResponse {
	Integer numRows=0;

	public Integer getNumRows() {
		return numRows;
	}

	public void setNumRows(Integer numRows) {
		this.numRows = numRows;
	}
	public String toString(){
		return "The number of rows affected is: "+numRows;
	}
}