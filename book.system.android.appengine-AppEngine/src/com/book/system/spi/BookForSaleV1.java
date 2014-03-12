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

		String url = null;
		try {
			if (SystemProperty.environment.value() ==
					SystemProperty.Environment.Value.Production) {
				// Load the class that provides the new "jdbc:google:mysql://" prefix.
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
			PreparedStatement stmt = null;
			PreparedStatement getPersonStmt = null;
			PreparedStatement getBookStmt = null;
			ResultSet resultSet = null;
			ResultSet resultSetBook = null;
			ResultSet resultSetPerson = null;
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
					getBookStmt.setString(1, ISBN);
					resultSetBook = getBookStmt.executeQuery();
					resultSetBook.next();

					int personId = resultSet.getInt("Person_ID");
					getPersonStmt.setInt(1, personId);
					resultSetPerson = getPersonStmt.executeQuery();
					resultSetPerson.next();

					String title = resultSetBook.getString("Title");
					String author = resultSetBook.getString("Author");

					String firstName = resultSetPerson.getString("First_Name");
					String lastName = resultSetPerson.getString("Last_Name");
					String email = resultSetPerson.getString("Email");

					book = new Book(ISBN, author, title);
					seller = new Seller(personId, email, firstName, lastName);
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
				if(resultSetBook!=null)
					resultSetBook.close();
				if(resultSetPerson!=null)
					resultSetPerson.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//for(Entry<Long, List<BookForSale>> b : booksForSale.getSellerIdToList().entrySet()){
		//		System.out.println(b);
		//}

		return booksForSale;
	}

}
