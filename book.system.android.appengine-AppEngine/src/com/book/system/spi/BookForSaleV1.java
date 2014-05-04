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
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Named;

import com.book.system.model.Book;
import com.book.system.model.BookForSale;
import com.book.system.model.SaleShelf;
import com.book.system.model.Seller;
import com.google.api.server.spi.SystemService;
import com.google.api.server.spi.SystemServiceServlet;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.AuthLevel;
import com.google.appengine.api.backends.BackendService;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.users.User;

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
	private static Connection conn = null;
    private static final Logger log = Logger.getLogger(SystemServiceServlet.class.getName());

	@ApiMethod(name = "bookforsale.listAll", authLevel=AuthLevel.OPTIONAL_CONTINUE)
	public SaleShelf listAll(User user) throws OAuthRequestException, IOException {
		SaleShelf booksForSale = new SaleShelf();
		try{
			if(conn==null){
				log.setLevel(Level.WARNING);
				log.warning("Creating connection...");
				conn = DBConnection.createConnection();
			}else{
				log.setLevel(Level.WARNING);
				boolean valid = conn.isValid(10);
				log.warning("isValid():"+String.valueOf(valid));
			}
			PreparedStatement stmt = null;
			PreparedStatement getPersonStmt = null;
			PreparedStatement getBookStmt = null;
			ResultSet resultSet = null;
			try {
				log.setLevel(Level.WARNING);
				log.warning(conn.getMetaData().getURL());
				
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
					book = getBookByISBNWithoutClosing(ISBN);

					Long personId = resultSet.getLong("Person_ID");
					seller = getSellerByIDWithoutClosing(personId);
					Double price = resultSet.getDouble("Price");
					bfs = new BookForSale(book,seller,price);
					booksForSale.addToShelf(bfs);
					book = null;
					seller = null;
					bfs = null;
				}

			} finally {
				try{
					if(stmt!=null)
						stmt.close();
					if(getPersonStmt!=null)
						getPersonStmt.close();
					if(getBookStmt!=null)
						getBookStmt.close();
					if(resultSet!=null)
						resultSet.close();	
				}catch (SQLException e) {
					e.printStackTrace();
					return null;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return booksForSale;
	}
	@ApiMethod(name = "bookforsale.list", authLevel=AuthLevel.OPTIONAL_CONTINUE)
	public SaleShelf list(User user) throws OAuthRequestException, IOException {
		SaleShelf booksForSale = new SaleShelf();
		try{
			if(conn==null){
				log.setLevel(Level.WARNING);
				log.warning("Creating connection...");
				conn = DBConnection.createConnection();
			}else{
				log.setLevel(Level.WARNING);
				boolean valid = conn.isValid(10);
				log.warning("isValid():"+String.valueOf(valid));
				if(!valid){
					conn = DBConnection.createConnection();
				}
			}
			PreparedStatement stmt = null;
			PreparedStatement getPersonStmt = null;
			PreparedStatement getBookStmt = null;
			ResultSet resultSet = null;
			try {
				log.setLevel(Level.WARNING);
				log.warning(conn.getMetaData().getURL());
				
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
					book = getBookByISBNWithoutClosing(ISBN);

					Long personId = resultSet.getLong("Person_ID");
					seller = getSellerByIDWithoutClosing(personId);
					Double price = resultSet.getDouble("Price");
					bfs = new BookForSale(book,seller,price);
					
					booksForSale.addToList(bfs);
					book = null;
					seller = null;
					bfs = null;
				}

			} finally {
				try{
					if(stmt!=null)
						stmt.close();
					if(getPersonStmt!=null)
						getPersonStmt.close();
					if(getBookStmt!=null)
						getBookStmt.close();
					if(resultSet!=null)
						resultSet.close();	
				}catch (SQLException e) {
					e.printStackTrace();
					return null;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
//		log.setLevel(Level.WARNING);
//		log.warning(booksForSale.getList().toString());
		
		return booksForSale;
	}
	@ApiMethod(name = "bookforsale.getBookByISBN", authLevel=AuthLevel.OPTIONAL_CONTINUE)
	public Book getBookByISBN(@Named("ISBN") String ISBN){

		ResultSet resultSetBook = null;
		PreparedStatement getBookStmt = null;
		Book book = null;
		try{
			if(conn==null){
				log.setLevel(Level.WARNING);
				log.warning("Creating connection...");
				conn = DBConnection.createConnection();
			}else{
				log.setLevel(Level.WARNING);
				boolean valid = conn.isValid(10);
				log.warning("isValid():"+String.valueOf(valid));
			}
			String getBook = "SELECT * FROM Book WHERE ISBN=?";
			getBookStmt = conn.prepareStatement(getBook);
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
			if(getBookStmt!=null)
				try {
					getBookStmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}

		return book;
	}
	private Book getBookByISBNWithoutClosing(String ISBN){

		ResultSet resultSetBook = null;
		PreparedStatement getBookStmt = null;
		Book book = null;
		try{
			String getBook = "SELECT * FROM Book WHERE ISBN=?";
			getBookStmt = conn.prepareStatement(getBook);
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
			if(getBookStmt!=null)
				try {
					getBookStmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}

		return book;
	}
	@ApiMethod(name = "bookforsale.getSellerByID", authLevel=AuthLevel.OPTIONAL_CONTINUE)
	public Seller getSellerByID(@Named("personId") Long personId){
		ResultSet resultSetPerson = null;
		Seller seller = null;
		PreparedStatement getPersonStmt = null;
		try{
			if(conn==null){
				log.setLevel(Level.WARNING);
				log.warning("Creating connection...");
				conn = DBConnection.createConnection();
			}else{
				log.setLevel(Level.WARNING);
				boolean valid = conn.isValid(10);
				log.warning("isValid():"+String.valueOf(valid));
			}
			String getPerson = "SELECT * FROM Person WHERE Person_ID=?";
			getPersonStmt = conn.prepareStatement(getPerson);
			getPersonStmt.setLong(1, personId);
			resultSetPerson = getPersonStmt.executeQuery();
			resultSetPerson.next();

			String firstName = resultSetPerson.getString("First_Name");
			String lastName = resultSetPerson.getString("Last_Name");
			String email = resultSetPerson.getString("Email");

			seller = new Seller( personId.intValue(), email, firstName, lastName);
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
			if(getPersonStmt!=null)
				try {
					getPersonStmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return seller;
	}
	private Seller getSellerByIDWithoutClosing(Long personId){
		ResultSet resultSetPerson = null;
		Seller seller = null;
		PreparedStatement getPersonStmt = null;
		try{
		
			String getPerson = "SELECT * FROM Person WHERE Person_ID=?";
			getPersonStmt = conn.prepareStatement(getPerson);
			getPersonStmt.setLong(1, personId);
			resultSetPerson = getPersonStmt.executeQuery();
			resultSetPerson.next();

			String firstName = resultSetPerson.getString("First_Name");
			String lastName = resultSetPerson.getString("Last_Name");
			String email = resultSetPerson.getString("Email");

			seller = new Seller( personId.intValue(), email, firstName, lastName);
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
			if(getPersonStmt!=null)
				try {
					getPersonStmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return seller;
	}
	//TODO: Combine the middle portions of the next two methods
	@ApiMethod(name = "bookforsale.getAllBooksBySeller", authLevel=AuthLevel.OPTIONAL_CONTINUE)
	public List<Book> getAllBooksBySeller(@Named("personId") Long personId){
		String getBooksQry ="SELECT Book.* "
				+ "FROM `book-system`.Book_For_Sale JOIN `book-system`.Book "
				+ "WHERE Book_For_Sale.ISBN = Book.ISBN AND Book_For_Sale.Person_ID=?";
		PreparedStatement stmt = null;
		ResultSet resultSetBooks = null;
		Book book = null;
		List<Book> bookList = new ArrayList<Book>();
		try{
			if(conn==null){
				log.setLevel(Level.WARNING);
				log.warning("Creating connection...");
				conn = DBConnection.createConnection();
			}else{
				log.setLevel(Level.WARNING);
				boolean valid = conn.isValid(10);
				log.warning("isValid():"+String.valueOf(valid));
			}
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
	@ApiMethod(name = "bookforsale.getAllSellersOfBook", authLevel=AuthLevel.OPTIONAL_CONTINUE)
	public List<Seller> getAllSellersOfBook(@Named("ISBN") String ISBN){
		String getBooksQry ="SELECT Person.* "
				+ "FROM `book-system`.Book_For_Sale JOIN `book-system`.Person "
				+ "WHERE Book_For_Sale.Person_ID = Person.Person_ID AND Book_For_Sale.ISBN=?";
		PreparedStatement stmt = null;
		ResultSet resultSetPerson = null;
		Seller seller = null;
		List<Seller> sellerList = new ArrayList<Seller>();
		try{
			if(conn==null){
				log.setLevel(Level.WARNING);
				log.warning("Creating connection...");
				conn = DBConnection.createConnection();
			}else{
				log.setLevel(Level.WARNING);
				boolean valid = conn.isValid(10);
				log.warning("isValid():"+String.valueOf(valid));
			}
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
	@ApiMethod(name = "seller.insert", httpMethod = "post", authLevel=AuthLevel.OPTIONAL_CONTINUE)
	public Seller insertSeller(@Named("email") String email, @Named("first_name") String first_name, @Named("last_name") String last_name) {
		Seller response = new Seller(email);
		if(!first_name.isEmpty()){
			response.setfFirstName(first_name);
		}else {
			first_name="Unknown";
		}
		if(!last_name.isEmpty()){
			response.setfLastName(last_name);
		}else {
			last_name="Unknown";
		}
		
		String insertPersonQry ="INSERT IGNORE INTO `book-system`.`Person` (`Email`, `First_Name`, `Last_Name`) VALUES (?,?,?)";
		PreparedStatement stmt = null;
		ResultSet resultSetPerson = null;
		try{
			if(conn==null){
				log.setLevel(Level.WARNING);
				log.warning("Creating connection...");
				conn = DBConnection.createConnection();
			}else{
				log.setLevel(Level.WARNING);
				boolean valid = conn.isValid(10);
				log.warning("isValid():"+String.valueOf(valid));
			}
			stmt = conn.prepareStatement(insertPersonQry,PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setString(1, email);
			stmt.setString(2, first_name);
			stmt.setString(3, last_name);
			stmt.executeUpdate(); 
			resultSetPerson = stmt.getGeneratedKeys();
			long person_id = -1L;
			if (resultSetPerson != null && resultSetPerson.next()) {
				person_id = resultSetPerson.getLong(1);
				response.setId(person_id);

				String insertSellerQry ="INSERT IGNORE INTO `book-system`.`Seller` (`Person_ID`) VALUES (?)";
				PreparedStatement stmtSeller = null;
				try{
					stmtSeller = conn.prepareStatement(insertSellerQry);
					stmtSeller.setLong(1, person_id);
					stmtSeller.executeUpdate(); 
				}catch(SQLException e){
					e.printStackTrace();
					return null;
				}finally{
					try{
						if(stmtSeller!=null)
							stmtSeller.close();
					}catch (SQLException e) {
						e.printStackTrace();
					}
				}
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

		return response;
	}
	private Seller insertSellerWithoutClosing(String email, String first_name, String last_name) {
		Seller response = new Seller(email);
		if(!first_name.isEmpty()){
			response.setfFirstName(first_name);
		}else {
			first_name="Unknown";
		}
		if(!last_name.isEmpty()){
			response.setfLastName(last_name);
		}else {
			last_name="Unknown";
		}
		
		String insertPersonQry ="INSERT IGNORE INTO `book-system`.`Person` (`Email`, `First_Name`, `Last_Name`) VALUES (?,?,?)";
		PreparedStatement stmt = null;
		ResultSet resultSetPerson = null;
		try{
			
			stmt = conn.prepareStatement(insertPersonQry,PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setString(1, email);
			stmt.setString(2, first_name);
			stmt.setString(3, last_name);
			stmt.executeUpdate(); 
			resultSetPerson = stmt.getGeneratedKeys();
			long person_id = -1L;
			if (resultSetPerson != null && resultSetPerson.next()) {
				person_id = resultSetPerson.getLong(1);
				response.setId(person_id);

				String insertSellerQry ="INSERT IGNORE INTO `book-system`.`Seller` (`Person_ID`) VALUES (?)";
				PreparedStatement stmtSeller = null;
				try{
					stmtSeller = conn.prepareStatement(insertSellerQry);
					stmtSeller.setLong(1, person_id);
					stmtSeller.executeUpdate(); 
				}catch(SQLException e){
					e.printStackTrace();
					return null;
				}finally{
					try{
						if(stmtSeller!=null)
							stmtSeller.close();
					}catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}

		}
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		finally{
			try {
				if(stmt!=null)
					stmt.close();
				if(resultSetPerson!=null)
					resultSetPerson.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return response;
	}
	@ApiMethod(name = "book.insert", httpMethod = "post", authLevel=AuthLevel.OPTIONAL_CONTINUE)
	public Book insertBook(@Named("isbn") String isbn, @Named("title") String title, @Named("author") String author) {

		Book response = new Book(isbn);
		if(!title.isEmpty()){
			response.setTitle(title);
		}else {
			title="Unknown";
		}
		if(!author.isEmpty()){
			response.setAuthor(author);
		}else {
			author="Unknown";
		}
		
		String insertBookQry ="INSERT IGNORE INTO `book-system`.`Book` (`ISBN`, `Title`, `Author`) VALUES (?,?,?)";
		PreparedStatement stmt = null;
		try{
			if(conn==null){
				log.setLevel(Level.WARNING);
				log.warning("Creating connection...");
				conn = DBConnection.createConnection();
			}else{
				log.setLevel(Level.WARNING);
				boolean valid = conn.isValid(10);
				log.warning("isValid():"+String.valueOf(valid));
			}
			stmt = conn.prepareStatement(insertBookQry);
			stmt.setString(1, isbn);
			stmt.setString(2, title);
			stmt.setString(3, author);
			stmt.executeUpdate(); //return int of rows affected, but with the insert ignore, there won't be an error if it exists already
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
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return response;
	}
	private Book insertBookWithoutClosing(String isbn, String title, String author) {

		Book response = new Book(isbn);
		if(!title.isEmpty()){
			response.setTitle(title);
		}else {
			title="Unknown";
		}
		if(!author.isEmpty()){
			response.setAuthor(author);
		}else {
			author="Unknown";
		}
		
		String insertBookQry ="INSERT IGNORE INTO `book-system`.`Book` (`ISBN`, `Title`, `Author`) VALUES (?,?,?)";
		PreparedStatement stmt = null;
		try{
			stmt = conn.prepareStatement(insertBookQry);
			stmt.setString(1, isbn);
			stmt.setString(2, title);
			stmt.setString(3, author);
			stmt.executeUpdate(); //return int of rows affected, but with the insert ignore, there won't be an error if it exists already
		}
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		finally{
			try {
				if(stmt!=null)
					stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return response;
	}
	@ApiMethod(name = "bookforsale.getSellerByEmail", path="seller/email", authLevel=AuthLevel.OPTIONAL_CONTINUE)
	public Seller getSellerByEmail(@Named("email") String email){
		ResultSet resultSetPerson = null;
		Seller seller = null;
		PreparedStatement getPersonStmt = null;
		try{
			if(conn==null){
				log.setLevel(Level.WARNING);
				log.warning("Creating connection...");
				conn = DBConnection.createConnection();
			}else{
				log.setLevel(Level.WARNING);
				boolean valid = conn.isValid(10);
				log.warning("isValid():"+String.valueOf(valid));
			}
			String getPerson = "SELECT * FROM Person WHERE Email=?";
			getPersonStmt = conn.prepareStatement(getPerson);
			getPersonStmt.setString(1, email);
			resultSetPerson = getPersonStmt.executeQuery();
			resultSetPerson.next();

			String firstName = resultSetPerson.getString("First_Name");
			String lastName = resultSetPerson.getString("Last_Name");
			Long personId = resultSetPerson.getLong("Person_ID");

			seller = new Seller( personId.intValue(), email, firstName, lastName);
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
			if(getPersonStmt!=null)
				try {
					getPersonStmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return seller;
	}
	private Seller getSellerByEmailWithoutClosing(String email){
		ResultSet resultSetPerson = null;
		Seller seller = null;
		PreparedStatement getPersonStmt = null;
		try{
			String getPerson = "SELECT * FROM Person WHERE Email=?";
			getPersonStmt = conn.prepareStatement(getPerson);
			getPersonStmt.setString(1, email);
			resultSetPerson = getPersonStmt.executeQuery();
			resultSetPerson.next();

			String firstName = resultSetPerson.getString("First_Name");
			String lastName = resultSetPerson.getString("Last_Name");
			Long personId = resultSetPerson.getLong("Person_ID");

			seller = new Seller( personId.intValue(), email, firstName, lastName);
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
			if(getPersonStmt!=null)
				try {
					getPersonStmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return seller;
	}
	@ApiMethod(name = "bookforsale.insert", httpMethod = "post", authLevel=AuthLevel.OPTIONAL_CONTINUE)
	public BookForSale insertBookForSale(@Named("isbn") String isbn, @Named("title") String title, @Named("author") String author,
			@Named("email") String email, @Named("first_name") String first_name, @Named("last_name") String last_name, @Named("Price") Double price) {
		Book book = null;
		Seller seller = null;
		String insertBFSQry ="INSERT INTO `book-system`.`Book_For_Sale` (`ISBN`, `Person_ID`, `Price`) "
				+ "VALUES (?, ?, ?);";
		PreparedStatement stmt = null;
		try{
			if(conn==null){
				log.setLevel(Level.WARNING);
				log.warning("Creating connection...");
				conn = DBConnection.createConnection();
			}else{
				log.setLevel(Level.WARNING);
				boolean valid = conn.isValid(10);
				log.warning("isValid():"+String.valueOf(valid));
			}
			
			seller = getSellerByEmailWithoutClosing(email);
			if(seller==null){
				seller = insertSellerWithoutClosing(email,first_name,last_name);
			}
			book = getBookByISBNWithoutClosing(isbn);
			if(book==null){
				book = insertBookWithoutClosing(isbn,title,author);
			}
			
			stmt = conn.prepareStatement(insertBFSQry);
			stmt.setString(1, book.getISBN());
			stmt.setLong(2, seller.getId());
			stmt.setDouble(3, price);
			stmt.executeUpdate(); //return int of rows affected, but with the insert ignore, there won't be an error if it exists already

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
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return new BookForSale(book,seller,price);
	}
	@ApiMethod(name = "bookforsale.delete", authLevel=AuthLevel.OPTIONAL_CONTINUE)
	public void deleteBookForSale(@Named("email") String email, @Named("isbn") String isbn){
		Seller seller = null;
		PreparedStatement deleteBFSStmt = null;
		
		try{
			if(conn==null){
				log.setLevel(Level.WARNING);
				log.warning("Creating connection...");
				conn = DBConnection.createConnection();
			}else{
				log.setLevel(Level.WARNING);
				boolean valid = conn.isValid(10);
				log.warning("isValid():"+String.valueOf(valid));
			}
			String deleteBFS = "DELETE FROM `book-system`.`Book_For_Sale` WHERE ISBN=? AND Person_ID=?";
			
			seller = getSellerByEmailWithoutClosing(email);
			
			deleteBFSStmt = conn.prepareStatement(deleteBFS);
			deleteBFSStmt.setString(1, isbn);
			deleteBFSStmt.setLong(2, seller.getId());
			
			deleteBFSStmt.executeUpdate();
			
		}catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(deleteBFSStmt!=null)
				try {
					deleteBFSStmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}
}
