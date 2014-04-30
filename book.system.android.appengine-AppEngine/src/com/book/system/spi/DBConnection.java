package com.book.system.spi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.api.server.spi.SystemServiceServlet;
import com.google.appengine.api.utils.SystemProperty;

public class DBConnection {
//    private static final Logger log = Logger.getLogger(SystemServiceServlet.class.getName());

	public static Connection createConnection(){
		String url = null;
		try {
			if (SystemProperty.environment.value() ==
					SystemProperty.Environment.Value.Production) {
				//				// Load the class that provides the new "jdbc:google:mysql://" prefix.
				Class.forName("com.mysql.jdbc.GoogleDriver");
				url = "jdbc:google:mysql://mac-books:books/book-system?user=root";
			} else {
				// Local MySQL instance to use during development.
				Class.forName("com.mysql.jdbc.Driver");
				url = "jdbc:mysql://173.194.81.34:3306/book-system?user=root"; //This might need password...
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
}
