package com.book.system.spi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.api.server.spi.SystemServiceServlet;
import com.google.appengine.api.utils.SystemProperty;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

public class DBConnection {
//    private static final Logger log = Logger.getLogger(SystemServiceServlet.class.getName());
	public static DataSource getDataSource(){
		PoolProperties p = new PoolProperties();
		try {
			if (SystemProperty.environment.value() ==
					SystemProperty.Environment.Value.Production) {
				//				// Load the class that provides the new "jdbc:google:mysql://" prefix.
				p.setDriverClassName("com.mysql.jdbc.GoogleDriver");
				p.setUrl("jdbc:google:mysql://mac-books:books/book-system");
			} else {
				// Local MySQL instance to use during development.
				p.setDriverClassName("com.mysql.jdbc.Driver");
				p.setUrl("jdbc:mysql://173.194.81.34:3306/book-system"); //This might need password...
		        p.setPassword("root");

			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
        p.setUsername("root");
        p.setJmxEnabled(true);
        p.setTestWhileIdle(false);
        p.setTestOnBorrow(true);
        p.setValidationQuery("SELECT 1");
        p.setTestOnReturn(false);
        p.setValidationInterval(30000);
        p.setTimeBetweenEvictionRunsMillis(30000);
        p.setMaxActive(100);
        p.setInitialSize(10);
        p.setMaxWait(10000);
        p.setRemoveAbandonedTimeout(60);
        p.setMinEvictableIdleTimeMillis(30000);
        p.setMinIdle(10);
        p.setLogAbandoned(true);
        p.setRemoveAbandoned(true);
        p.setJdbcInterceptors(
          "org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;"+
          "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");
        DataSource datasource = new DataSource();
        datasource.setPoolProperties(p);
        
        return datasource;
	}

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
