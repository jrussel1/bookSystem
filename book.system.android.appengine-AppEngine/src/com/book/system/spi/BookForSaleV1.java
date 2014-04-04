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

package com.book.system.spi;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import com.book.system.model.Book;
import com.book.system.model.BookForSale;
import com.book.system.model.SaleShelf;
import com.book.system.model.Seller;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.AuthLevel;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.users.User;
import com.google.appengine.api.utils.SystemProperty;

/**
 * Defines v1 of a BookForSale resource as part of the bookSystem API, which provides
 * clients the ability to insert and list books for sale.
 */
@Api(
		name = "bookSystem",
		version = "v1",
		scopes = {Ids.EMAIL_SCOPE},
		clientIds = {Ids.WEB_CLIENT_ID, Ids.ANDROID_CLIENT_ID, Ids.API_EXPLORER_CLIENT_ID},
		audiences = {Ids.ANDROID_AUDIENCE}
		)
public class BookForSaleV1 {

	private static final String DEFAULT_LIMIT = "10";

	@ApiMethod(name = "bookforsale.list", authLevel=AuthLevel.OPTIONAL)
	public SaleShelf list(User user) throws OAuthRequestException,
	IOException {
		SaleShelf booksForSale = new SaleShelf();
		try{
			Connection conn = createConnection();
			PreparedStatement stmt = null;
			PreparedStatement getPersonStmt = null;
			PreparedStatement getBookStmt = null;
			ResultSet resultSet = null;
			try {
				String statement = "SELECT * FROM Book_For_Sale";
				stmt = conn.prepareStatement(statement);

				String getPerson = "SELECT * FROM Person WHERE Person_ID=?";
				getPersonStmt = conn.prepareStatement(getPerson);
				String getBook = "SELECT * FROM Book WHERE ISBN=?";
				getBookStmt = conn.prepareStatement(getBook);

				Book book = null;
				Seller seller = null;
				BookForSale bfs = null;

				resultSet = stmt.executeQuery();

				while (resultSet.next()) {
					String ISBN = resultSet.getString("ISBN");
					book = getBookByISBN(getBookStmt,ISBN);

					int personId = resultSet.getInt("Person_ID");
					seller = getSellerByID(getPersonStmt,personId);

					bfs = new BookForSale(book,seller);
					booksForSale.addToShelf(bfs);
					book = null;
					seller = null;
					bfs = null;
				}

			} finally {
				if(conn!=null)
					conn.close();
				if(stmt!=null)
					stmt.close();
				if(getPersonStmt!=null)
					getPersonStmt.close();
				if(getBookStmt!=null)
					getBookStmt.close();
				if(resultSet!=null)
					resultSet.close();	
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return booksForSale;
	}

	private Connection createConnection(){
		String url = null;
		try {
			if (SystemProperty.environment.value() ==
					SystemProperty.Environment.Value.Production) {
				//				// Load the class that provides the new "jdbc:google:mysql://" prefix.
				Class.forName("com.mysql.jdbc.GoogleDriver");
				url = "jdbc:google:mysql://mac-books:books/book-system?user=root&password=karma4YOU";
			} else {
				// Local MySQL instance to use during development.
				Class.forName("com.mysql.jdbc.Driver");
				url = "jdbc:mysql://173.194.81.34:3306/book-system?user=root&password=karma4YOU";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		try {
			Connection conn = DriverManager.getConnection(url);
			return conn;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@ApiMethod(name = "bookforsale.getBookByISBN", authLevel=AuthLevel.OPTIONAL)
	public Book getBookByISBN(PreparedStatement getBookStmt,String ISBN){
		ResultSet resultSetBook = null;
		Book book = null;
		try{
			getBookStmt.setString(1, ISBN);
			resultSetBook = getBookStmt.executeQuery();
			resultSetBook.next();
			book = new Book(ISBN, resultSetBook.getString("Author"), resultSetBook.getString("Title"));
		}
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(resultSetBook!=null)
				try {
					resultSetBook.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}

		return book;
	}

	@ApiMethod(name = "bookforsale.getSellerByID", authLevel=AuthLevel.OPTIONAL)
	public Seller getSellerByID(PreparedStatement getPersonStmt, int personId){
		ResultSet resultSetPerson = null;
		Seller seller = null;
		try{
			getPersonStmt.setInt(1, personId);
			resultSetPerson = getPersonStmt.executeQuery();
			resultSetPerson.next();

			String firstName = resultSetPerson.getString("First_Name");
			String lastName = resultSetPerson.getString("Last_Name");
			String email = resultSetPerson.getString("Email");

			seller = new Seller(personId, email, firstName, lastName);
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}finally{
			if(resultSetPerson!=null)
				try {
					resultSetPerson.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return seller;
	}
	//TODO: Combine the middle portions of the next two methods
	@ApiMethod(name = "bookforsale.getAllBooksBySeller", authLevel=AuthLevel.OPTIONAL)
	public List<Book> getAllBooksBySeller(int personId){
		Connection conn = createConnection();
		String getBooksQry ="SELECT Book.* "
				+ "FROM `book-system`.Book_For_Sale JOIN `book-system`.Book "
				+ "WHERE Book_For_Sale.ISBN = Book.ISBN AND Book_For_Sale.Person_ID=?";
		PreparedStatement stmt = null;
		ResultSet resultSetBooks = null;
		Book book = null;
		List<Book> bookList = new ArrayList<Book>();
		try{
			stmt = conn.prepareStatement(getBooksQry);
			stmt.setLong(1, personId);
			resultSetBooks = stmt.executeQuery();
			while(resultSetBooks.next()){
				book = new Book(resultSetBooks.getString("ISBN"), resultSetBooks.getString("Author"), resultSetBooks.getString("Title"));
				bookList.add(book);
				book=null;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		finally{
			try {
				if(conn!=null)
					conn.close();
				if(stmt!=null)
					stmt.close();
				if(resultSetBooks!=null)
					resultSetBooks.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return bookList;
	}
	@ApiMethod(name = "bookforsale.getAllSellersOfBook", authLevel=AuthLevel.OPTIONAL)
	public List<Seller> getAllSellersOfBook(String ISBN){
		Connection conn = createConnection();
		String getBooksQry ="SELECT Person.* "
				+ "FROM `book-system`.Book_For_Sale JOIN `book-system`.Person "
				+ "WHERE Book_For_Sale.Person_ID = Person.Person_ID AND Book_For_Sale.ISBN=?";
		PreparedStatement stmt = null;
		ResultSet resultSetPerson = null;
		Seller seller = null;
		List<Seller> sellerList = new ArrayList<Seller>();
		try{
			stmt = conn.prepareStatement(getBooksQry);
			stmt.setString(1, ISBN);
			resultSetPerson = stmt.executeQuery();
			while(resultSetPerson.next()){
				seller = new Seller(
						resultSetPerson.getInt("Person_ID"), 
						resultSetPerson.getString("Email"), 
						resultSetPerson.getString("First_Name"),
						resultSetPerson.getString("Last_Name")
						);
				sellerList.add(seller);
				seller=null;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		finally{
			try {
				if(conn!=null)
					conn.close();
				if(stmt!=null)
					stmt.close();
				if(resultSetPerson!=null)
					resultSetPerson.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return sellerList;
	}
}
