/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://code.google.com/p/google-apis-client-generator/
 * (build: 2014-04-15 19:10:39 UTC)
 * on 2014-04-30 at 03:48:07 UTC 
 * Modify at your own risk.
 */

package com.appspot.mac_books.bookSystem;

/**
 * Service definition for BookSystem (v1).
 *
 * <p>
 * This is an API
 * </p>
 *
 * <p>
 * For more information about this service, see the
 * <a href="" target="_blank">API Documentation</a>
 * </p>
 *
 * <p>
 * This service uses {@link BookSystemRequestInitializer} to initialize global parameters via its
 * {@link Builder}.
 * </p>
 *
 * @since 1.3
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public class BookSystem extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient {

  // Note: Leave this static initializer at the top of the file.
  static {
    com.google.api.client.util.Preconditions.checkState(
        com.google.api.client.googleapis.GoogleUtils.MAJOR_VERSION == 1 &&
        com.google.api.client.googleapis.GoogleUtils.MINOR_VERSION >= 15,
        "You are currently running with version %s of google-api-client. " +
        "You need at least version 1.15 of google-api-client to run version " +
        "1.16.0-rc of the bookSystem library.", com.google.api.client.googleapis.GoogleUtils.VERSION);
  }

  /**
   * The default encoded root URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_ROOT_URL = "https://mac-books.appspot.com/_ah/api/";

  /**
   * The default encoded service path of the service. This is determined when the library is
   * generated and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_SERVICE_PATH = "bookSystem/v1/";

  /**
   * The default encoded base URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   */
  public static final String DEFAULT_BASE_URL = DEFAULT_ROOT_URL + DEFAULT_SERVICE_PATH;

  /**
   * Constructor.
   *
   * <p>
   * Use {@link Builder} if you need to specify any of the optional parameters.
   * </p>
   *
   * @param transport HTTP transport, which should normally be:
   *        <ul>
   *        <li>Google App Engine:
   *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
   *        <li>Android: {@code newCompatibleTransport} from
   *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
   *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
   *        </li>
   *        </ul>
   * @param jsonFactory JSON factory, which may be:
   *        <ul>
   *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
   *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
   *        <li>Android Honeycomb or higher:
   *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
   *        </ul>
   * @param httpRequestInitializer HTTP request initializer or {@code null} for none
   * @since 1.7
   */
  public BookSystem(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
      com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
    this(new Builder(transport, jsonFactory, httpRequestInitializer));
  }

  /**
   * @param builder builder
   */
  BookSystem(Builder builder) {
    super(builder);
  }

  @Override
  protected void initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest<?> httpClientRequest) throws java.io.IOException {
    super.initialize(httpClientRequest);
  }

  /**
   * An accessor for creating requests from the Book collection.
   *
   * <p>The typical use is:</p>
   * <pre>
   *   {@code BookSystem bookSystem = new BookSystem(...);}
   *   {@code BookSystem.Book.List request = bookSystem.book().list(parameters ...)}
   * </pre>
   *
   * @return the resource collection
   */
  public Book book() {
    return new Book();
  }

  /**
   * The "book" collection of methods.
   */
  public class Book {

    /**
     * Create a request for the method "book.insert".
     *
     * This request holds the parameters needed by the the bookSystem server.  After setting any
     * optional parameters, call the {@link Insert#execute()} method to invoke the remote operation.
     *
     * @param isbn
     * @param title
     * @param author
     * @return the request
     */
    public Insert insert(java.lang.String isbn, java.lang.String title, java.lang.String author) throws java.io.IOException {
      Insert result = new Insert(isbn, title, author);
      initialize(result);
      return result;
    }

    public class Insert extends BookSystemRequest<com.appspot.mac_books.bookSystem.model.Book> {

      private static final String REST_PATH = "book/{isbn}/{title}/{author}";

      /**
       * Create a request for the method "book.insert".
       *
       * This request holds the parameters needed by the the bookSystem server.  After setting any
       * optional parameters, call the {@link Insert#execute()} method to invoke the remote operation.
       * <p> {@link
       * Insert#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)} must
       * be called to initialize this instance immediately after invoking the constructor. </p>
       *
       * @param isbn
       * @param title
       * @param author
       * @since 1.13
       */
      protected Insert(java.lang.String isbn, java.lang.String title, java.lang.String author) {
        super(BookSystem.this, "POST", REST_PATH, null, com.appspot.mac_books.bookSystem.model.Book.class);
        this.isbn = com.google.api.client.util.Preconditions.checkNotNull(isbn, "Required parameter isbn must be specified.");
        this.title = com.google.api.client.util.Preconditions.checkNotNull(title, "Required parameter title must be specified.");
        this.author = com.google.api.client.util.Preconditions.checkNotNull(author, "Required parameter author must be specified.");
      }

      @Override
      public Insert setAlt(java.lang.String alt) {
        return (Insert) super.setAlt(alt);
      }

      @Override
      public Insert setFields(java.lang.String fields) {
        return (Insert) super.setFields(fields);
      }

      @Override
      public Insert setKey(java.lang.String key) {
        return (Insert) super.setKey(key);
      }

      @Override
      public Insert setOauthToken(java.lang.String oauthToken) {
        return (Insert) super.setOauthToken(oauthToken);
      }

      @Override
      public Insert setPrettyPrint(java.lang.Boolean prettyPrint) {
        return (Insert) super.setPrettyPrint(prettyPrint);
      }

      @Override
      public Insert setQuotaUser(java.lang.String quotaUser) {
        return (Insert) super.setQuotaUser(quotaUser);
      }

      @Override
      public Insert setUserIp(java.lang.String userIp) {
        return (Insert) super.setUserIp(userIp);
      }

      @com.google.api.client.util.Key
      private java.lang.String isbn;

      /**

       */
      public java.lang.String getIsbn() {
        return isbn;
      }

      public Insert setIsbn(java.lang.String isbn) {
        this.isbn = isbn;
        return this;
      }

      @com.google.api.client.util.Key
      private java.lang.String title;

      /**

       */
      public java.lang.String getTitle() {
        return title;
      }

      public Insert setTitle(java.lang.String title) {
        this.title = title;
        return this;
      }

      @com.google.api.client.util.Key
      private java.lang.String author;

      /**

       */
      public java.lang.String getAuthor() {
        return author;
      }

      public Insert setAuthor(java.lang.String author) {
        this.author = author;
        return this;
      }

      @Override
      public Insert set(String parameterName, Object value) {
        return (Insert) super.set(parameterName, value);
      }
    }

  }

  /**
   * An accessor for creating requests from the Bookforsale collection.
   *
   * <p>The typical use is:</p>
   * <pre>
   *   {@code BookSystem bookSystem = new BookSystem(...);}
   *   {@code BookSystem.Bookforsale.List request = bookSystem.bookforsale().list(parameters ...)}
   * </pre>
   *
   * @return the resource collection
   */
  public Bookforsale bookforsale() {
    return new Bookforsale();
  }

  /**
   * The "bookforsale" collection of methods.
   */
  public class Bookforsale {

    /**
     * Create a request for the method "bookforsale.getAllBooksBySeller".
     *
     * This request holds the parameters needed by the the bookSystem server.  After setting any
     * optional parameters, call the {@link GetAllBooksBySeller#execute()} method to invoke the remote
     * operation.
     *
     * @param personId
     * @return the request
     */
    public GetAllBooksBySeller getAllBooksBySeller(java.lang.Long personId) throws java.io.IOException {
      GetAllBooksBySeller result = new GetAllBooksBySeller(personId);
      initialize(result);
      return result;
    }

    public class GetAllBooksBySeller extends BookSystemRequest<com.appspot.mac_books.bookSystem.model.BookCollection> {

      private static final String REST_PATH = "bookcollection/{personId}";

      /**
       * Create a request for the method "bookforsale.getAllBooksBySeller".
       *
       * This request holds the parameters needed by the the bookSystem server.  After setting any
       * optional parameters, call the {@link GetAllBooksBySeller#execute()} method to invoke the remote
       * operation. <p> {@link GetAllBooksBySeller#initialize(com.google.api.client.googleapis.services.
       * AbstractGoogleClientRequest)} must be called to initialize this instance immediately after
       * invoking the constructor. </p>
       *
       * @param personId
       * @since 1.13
       */
      protected GetAllBooksBySeller(java.lang.Long personId) {
        super(BookSystem.this, "GET", REST_PATH, null, com.appspot.mac_books.bookSystem.model.BookCollection.class);
        this.personId = com.google.api.client.util.Preconditions.checkNotNull(personId, "Required parameter personId must be specified.");
      }

      @Override
      public com.google.api.client.http.HttpResponse executeUsingHead() throws java.io.IOException {
        return super.executeUsingHead();
      }

      @Override
      public com.google.api.client.http.HttpRequest buildHttpRequestUsingHead() throws java.io.IOException {
        return super.buildHttpRequestUsingHead();
      }

      @Override
      public GetAllBooksBySeller setAlt(java.lang.String alt) {
        return (GetAllBooksBySeller) super.setAlt(alt);
      }

      @Override
      public GetAllBooksBySeller setFields(java.lang.String fields) {
        return (GetAllBooksBySeller) super.setFields(fields);
      }

      @Override
      public GetAllBooksBySeller setKey(java.lang.String key) {
        return (GetAllBooksBySeller) super.setKey(key);
      }

      @Override
      public GetAllBooksBySeller setOauthToken(java.lang.String oauthToken) {
        return (GetAllBooksBySeller) super.setOauthToken(oauthToken);
      }

      @Override
      public GetAllBooksBySeller setPrettyPrint(java.lang.Boolean prettyPrint) {
        return (GetAllBooksBySeller) super.setPrettyPrint(prettyPrint);
      }

      @Override
      public GetAllBooksBySeller setQuotaUser(java.lang.String quotaUser) {
        return (GetAllBooksBySeller) super.setQuotaUser(quotaUser);
      }

      @Override
      public GetAllBooksBySeller setUserIp(java.lang.String userIp) {
        return (GetAllBooksBySeller) super.setUserIp(userIp);
      }

      @com.google.api.client.util.Key
      private java.lang.Long personId;

      /**

       */
      public java.lang.Long getPersonId() {
        return personId;
      }

      public GetAllBooksBySeller setPersonId(java.lang.Long personId) {
        this.personId = personId;
        return this;
      }

      @Override
      public GetAllBooksBySeller set(String parameterName, Object value) {
        return (GetAllBooksBySeller) super.set(parameterName, value);
      }
    }
    /**
     * Create a request for the method "bookforsale.getAllSellersOfBook".
     *
     * This request holds the parameters needed by the the bookSystem server.  After setting any
     * optional parameters, call the {@link GetAllSellersOfBook#execute()} method to invoke the remote
     * operation.
     *
     * @param iSBN
     * @return the request
     */
    public GetAllSellersOfBook getAllSellersOfBook(java.lang.String iSBN) throws java.io.IOException {
      GetAllSellersOfBook result = new GetAllSellersOfBook(iSBN);
      initialize(result);
      return result;
    }

    public class GetAllSellersOfBook extends BookSystemRequest<com.appspot.mac_books.bookSystem.model.SellerCollection> {

      private static final String REST_PATH = "sellercollection/{ISBN}";

      /**
       * Create a request for the method "bookforsale.getAllSellersOfBook".
       *
       * This request holds the parameters needed by the the bookSystem server.  After setting any
       * optional parameters, call the {@link GetAllSellersOfBook#execute()} method to invoke the remote
       * operation. <p> {@link GetAllSellersOfBook#initialize(com.google.api.client.googleapis.services.
       * AbstractGoogleClientRequest)} must be called to initialize this instance immediately after
       * invoking the constructor. </p>
       *
       * @param iSBN
       * @since 1.13
       */
      protected GetAllSellersOfBook(java.lang.String iSBN) {
        super(BookSystem.this, "GET", REST_PATH, null, com.appspot.mac_books.bookSystem.model.SellerCollection.class);
        this.iSBN = com.google.api.client.util.Preconditions.checkNotNull(iSBN, "Required parameter iSBN must be specified.");
      }

      @Override
      public com.google.api.client.http.HttpResponse executeUsingHead() throws java.io.IOException {
        return super.executeUsingHead();
      }

      @Override
      public com.google.api.client.http.HttpRequest buildHttpRequestUsingHead() throws java.io.IOException {
        return super.buildHttpRequestUsingHead();
      }

      @Override
      public GetAllSellersOfBook setAlt(java.lang.String alt) {
        return (GetAllSellersOfBook) super.setAlt(alt);
      }

      @Override
      public GetAllSellersOfBook setFields(java.lang.String fields) {
        return (GetAllSellersOfBook) super.setFields(fields);
      }

      @Override
      public GetAllSellersOfBook setKey(java.lang.String key) {
        return (GetAllSellersOfBook) super.setKey(key);
      }

      @Override
      public GetAllSellersOfBook setOauthToken(java.lang.String oauthToken) {
        return (GetAllSellersOfBook) super.setOauthToken(oauthToken);
      }

      @Override
      public GetAllSellersOfBook setPrettyPrint(java.lang.Boolean prettyPrint) {
        return (GetAllSellersOfBook) super.setPrettyPrint(prettyPrint);
      }

      @Override
      public GetAllSellersOfBook setQuotaUser(java.lang.String quotaUser) {
        return (GetAllSellersOfBook) super.setQuotaUser(quotaUser);
      }

      @Override
      public GetAllSellersOfBook setUserIp(java.lang.String userIp) {
        return (GetAllSellersOfBook) super.setUserIp(userIp);
      }

      @com.google.api.client.util.Key("ISBN")
      private java.lang.String iSBN;

      /**

       */
      public java.lang.String getISBN() {
        return iSBN;
      }

      public GetAllSellersOfBook setISBN(java.lang.String iSBN) {
        this.iSBN = iSBN;
        return this;
      }

      @Override
      public GetAllSellersOfBook set(String parameterName, Object value) {
        return (GetAllSellersOfBook) super.set(parameterName, value);
      }
    }
    /**
     * Create a request for the method "bookforsale.getBookByISBN".
     *
     * This request holds the parameters needed by the the bookSystem server.  After setting any
     * optional parameters, call the {@link GetBookByISBN#execute()} method to invoke the remote
     * operation.
     *
     * @param iSBN
     * @return the request
     */
    public GetBookByISBN getBookByISBN(java.lang.String iSBN) throws java.io.IOException {
      GetBookByISBN result = new GetBookByISBN(iSBN);
      initialize(result);
      return result;
    }

    public class GetBookByISBN extends BookSystemRequest<com.appspot.mac_books.bookSystem.model.Book> {

      private static final String REST_PATH = "book/{ISBN}";

      /**
       * Create a request for the method "bookforsale.getBookByISBN".
       *
       * This request holds the parameters needed by the the bookSystem server.  After setting any
       * optional parameters, call the {@link GetBookByISBN#execute()} method to invoke the remote
       * operation. <p> {@link GetBookByISBN#initialize(com.google.api.client.googleapis.services.Abstra
       * ctGoogleClientRequest)} must be called to initialize this instance immediately after invoking
       * the constructor. </p>
       *
       * @param iSBN
       * @since 1.13
       */
      protected GetBookByISBN(java.lang.String iSBN) {
        super(BookSystem.this, "GET", REST_PATH, null, com.appspot.mac_books.bookSystem.model.Book.class);
        this.iSBN = com.google.api.client.util.Preconditions.checkNotNull(iSBN, "Required parameter iSBN must be specified.");
      }

      @Override
      public com.google.api.client.http.HttpResponse executeUsingHead() throws java.io.IOException {
        return super.executeUsingHead();
      }

      @Override
      public com.google.api.client.http.HttpRequest buildHttpRequestUsingHead() throws java.io.IOException {
        return super.buildHttpRequestUsingHead();
      }

      @Override
      public GetBookByISBN setAlt(java.lang.String alt) {
        return (GetBookByISBN) super.setAlt(alt);
      }

      @Override
      public GetBookByISBN setFields(java.lang.String fields) {
        return (GetBookByISBN) super.setFields(fields);
      }

      @Override
      public GetBookByISBN setKey(java.lang.String key) {
        return (GetBookByISBN) super.setKey(key);
      }

      @Override
      public GetBookByISBN setOauthToken(java.lang.String oauthToken) {
        return (GetBookByISBN) super.setOauthToken(oauthToken);
      }

      @Override
      public GetBookByISBN setPrettyPrint(java.lang.Boolean prettyPrint) {
        return (GetBookByISBN) super.setPrettyPrint(prettyPrint);
      }

      @Override
      public GetBookByISBN setQuotaUser(java.lang.String quotaUser) {
        return (GetBookByISBN) super.setQuotaUser(quotaUser);
      }

      @Override
      public GetBookByISBN setUserIp(java.lang.String userIp) {
        return (GetBookByISBN) super.setUserIp(userIp);
      }

      @com.google.api.client.util.Key("ISBN")
      private java.lang.String iSBN;

      /**

       */
      public java.lang.String getISBN() {
        return iSBN;
      }

      public GetBookByISBN setISBN(java.lang.String iSBN) {
        this.iSBN = iSBN;
        return this;
      }

      @Override
      public GetBookByISBN set(String parameterName, Object value) {
        return (GetBookByISBN) super.set(parameterName, value);
      }
    }
    /**
     * Create a request for the method "bookforsale.getSellerByID".
     *
     * This request holds the parameters needed by the the bookSystem server.  After setting any
     * optional parameters, call the {@link GetSellerByID#execute()} method to invoke the remote
     * operation.
     *
     * @param personId
     * @return the request
     */
    public GetSellerByID getSellerByID(java.lang.Long personId) throws java.io.IOException {
      GetSellerByID result = new GetSellerByID(personId);
      initialize(result);
      return result;
    }

    public class GetSellerByID extends BookSystemRequest<com.appspot.mac_books.bookSystem.model.Seller> {

      private static final String REST_PATH = "seller/{personId}";

      /**
       * Create a request for the method "bookforsale.getSellerByID".
       *
       * This request holds the parameters needed by the the bookSystem server.  After setting any
       * optional parameters, call the {@link GetSellerByID#execute()} method to invoke the remote
       * operation. <p> {@link GetSellerByID#initialize(com.google.api.client.googleapis.services.Abstra
       * ctGoogleClientRequest)} must be called to initialize this instance immediately after invoking
       * the constructor. </p>
       *
       * @param personId
       * @since 1.13
       */
      protected GetSellerByID(java.lang.Long personId) {
        super(BookSystem.this, "GET", REST_PATH, null, com.appspot.mac_books.bookSystem.model.Seller.class);
        this.personId = com.google.api.client.util.Preconditions.checkNotNull(personId, "Required parameter personId must be specified.");
      }

      @Override
      public com.google.api.client.http.HttpResponse executeUsingHead() throws java.io.IOException {
        return super.executeUsingHead();
      }

      @Override
      public com.google.api.client.http.HttpRequest buildHttpRequestUsingHead() throws java.io.IOException {
        return super.buildHttpRequestUsingHead();
      }

      @Override
      public GetSellerByID setAlt(java.lang.String alt) {
        return (GetSellerByID) super.setAlt(alt);
      }

      @Override
      public GetSellerByID setFields(java.lang.String fields) {
        return (GetSellerByID) super.setFields(fields);
      }

      @Override
      public GetSellerByID setKey(java.lang.String key) {
        return (GetSellerByID) super.setKey(key);
      }

      @Override
      public GetSellerByID setOauthToken(java.lang.String oauthToken) {
        return (GetSellerByID) super.setOauthToken(oauthToken);
      }

      @Override
      public GetSellerByID setPrettyPrint(java.lang.Boolean prettyPrint) {
        return (GetSellerByID) super.setPrettyPrint(prettyPrint);
      }

      @Override
      public GetSellerByID setQuotaUser(java.lang.String quotaUser) {
        return (GetSellerByID) super.setQuotaUser(quotaUser);
      }

      @Override
      public GetSellerByID setUserIp(java.lang.String userIp) {
        return (GetSellerByID) super.setUserIp(userIp);
      }

      @com.google.api.client.util.Key
      private java.lang.Long personId;

      /**

       */
      public java.lang.Long getPersonId() {
        return personId;
      }

      public GetSellerByID setPersonId(java.lang.Long personId) {
        this.personId = personId;
        return this;
      }

      @Override
      public GetSellerByID set(String parameterName, Object value) {
        return (GetSellerByID) super.set(parameterName, value);
      }
    }
    /**
     * Create a request for the method "bookforsale.list".
     *
     * This request holds the parameters needed by the the bookSystem server.  After setting any
     * optional parameters, call the {@link List#execute()} method to invoke the remote operation.
     *
     * @return the request
     */
    public List list() throws java.io.IOException {
      List result = new List();
      initialize(result);
      return result;
    }

    public class List extends BookSystemRequest<com.appspot.mac_books.bookSystem.model.SaleShelf> {

      private static final String REST_PATH = "list";

      /**
       * Create a request for the method "bookforsale.list".
       *
       * This request holds the parameters needed by the the bookSystem server.  After setting any
       * optional parameters, call the {@link List#execute()} method to invoke the remote operation. <p>
       * {@link List#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
       * must be called to initialize this instance immediately after invoking the constructor. </p>
       *
       * @since 1.13
       */
      protected List() {
        super(BookSystem.this, "GET", REST_PATH, null, com.appspot.mac_books.bookSystem.model.SaleShelf.class);
      }

      @Override
      public com.google.api.client.http.HttpResponse executeUsingHead() throws java.io.IOException {
        return super.executeUsingHead();
      }

      @Override
      public com.google.api.client.http.HttpRequest buildHttpRequestUsingHead() throws java.io.IOException {
        return super.buildHttpRequestUsingHead();
      }

      @Override
      public List setAlt(java.lang.String alt) {
        return (List) super.setAlt(alt);
      }

      @Override
      public List setFields(java.lang.String fields) {
        return (List) super.setFields(fields);
      }

      @Override
      public List setKey(java.lang.String key) {
        return (List) super.setKey(key);
      }

      @Override
      public List setOauthToken(java.lang.String oauthToken) {
        return (List) super.setOauthToken(oauthToken);
      }

      @Override
      public List setPrettyPrint(java.lang.Boolean prettyPrint) {
        return (List) super.setPrettyPrint(prettyPrint);
      }

      @Override
      public List setQuotaUser(java.lang.String quotaUser) {
        return (List) super.setQuotaUser(quotaUser);
      }

      @Override
      public List setUserIp(java.lang.String userIp) {
        return (List) super.setUserIp(userIp);
      }

      @Override
      public List set(String parameterName, Object value) {
        return (List) super.set(parameterName, value);
      }
    }

  }

  /**
   * An accessor for creating requests from the Seller collection.
   *
   * <p>The typical use is:</p>
   * <pre>
   *   {@code BookSystem bookSystem = new BookSystem(...);}
   *   {@code BookSystem.Seller.List request = bookSystem.seller().list(parameters ...)}
   * </pre>
   *
   * @return the resource collection
   */
  public Seller seller() {
    return new Seller();
  }

  /**
   * The "seller" collection of methods.
   */
  public class Seller {

    /**
     * Create a request for the method "seller.insert".
     *
     * This request holds the parameters needed by the the bookSystem server.  After setting any
     * optional parameters, call the {@link Insert#execute()} method to invoke the remote operation.
     *
     * @param email
     * @param firstName
     * @param lastName
     * @return the request
     */
    public Insert insert(java.lang.String email, java.lang.String firstName, java.lang.String lastName) throws java.io.IOException {
      Insert result = new Insert(email, firstName, lastName);
      initialize(result);
      return result;
    }

    public class Insert extends BookSystemRequest<com.appspot.mac_books.bookSystem.model.Seller> {

      private static final String REST_PATH = "seller/{email}/{first_name}/{last_name}";

      /**
       * Create a request for the method "seller.insert".
       *
       * This request holds the parameters needed by the the bookSystem server.  After setting any
       * optional parameters, call the {@link Insert#execute()} method to invoke the remote operation.
       * <p> {@link
       * Insert#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)} must
       * be called to initialize this instance immediately after invoking the constructor. </p>
       *
       * @param email
       * @param firstName
       * @param lastName
       * @since 1.13
       */
      protected Insert(java.lang.String email, java.lang.String firstName, java.lang.String lastName) {
        super(BookSystem.this, "POST", REST_PATH, null, com.appspot.mac_books.bookSystem.model.Seller.class);
        this.email = com.google.api.client.util.Preconditions.checkNotNull(email, "Required parameter email must be specified.");
        this.firstName = com.google.api.client.util.Preconditions.checkNotNull(firstName, "Required parameter firstName must be specified.");
        this.lastName = com.google.api.client.util.Preconditions.checkNotNull(lastName, "Required parameter lastName must be specified.");
      }

      @Override
      public Insert setAlt(java.lang.String alt) {
        return (Insert) super.setAlt(alt);
      }

      @Override
      public Insert setFields(java.lang.String fields) {
        return (Insert) super.setFields(fields);
      }

      @Override
      public Insert setKey(java.lang.String key) {
        return (Insert) super.setKey(key);
      }

      @Override
      public Insert setOauthToken(java.lang.String oauthToken) {
        return (Insert) super.setOauthToken(oauthToken);
      }

      @Override
      public Insert setPrettyPrint(java.lang.Boolean prettyPrint) {
        return (Insert) super.setPrettyPrint(prettyPrint);
      }

      @Override
      public Insert setQuotaUser(java.lang.String quotaUser) {
        return (Insert) super.setQuotaUser(quotaUser);
      }

      @Override
      public Insert setUserIp(java.lang.String userIp) {
        return (Insert) super.setUserIp(userIp);
      }

      @com.google.api.client.util.Key
      private java.lang.String email;

      /**

       */
      public java.lang.String getEmail() {
        return email;
      }

      public Insert setEmail(java.lang.String email) {
        this.email = email;
        return this;
      }

      @com.google.api.client.util.Key("first_name")
      private java.lang.String firstName;

      /**

       */
      public java.lang.String getFirstName() {
        return firstName;
      }

      public Insert setFirstName(java.lang.String firstName) {
        this.firstName = firstName;
        return this;
      }

      @com.google.api.client.util.Key("last_name")
      private java.lang.String lastName;

      /**

       */
      public java.lang.String getLastName() {
        return lastName;
      }

      public Insert setLastName(java.lang.String lastName) {
        this.lastName = lastName;
        return this;
      }

      @Override
      public Insert set(String parameterName, Object value) {
        return (Insert) super.set(parameterName, value);
      }
    }

  }

  /**
   * Builder for {@link BookSystem}.
   *
   * <p>
   * Implementation is not thread-safe.
   * </p>
   *
   * @since 1.3.0
   */
  public static final class Builder extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient.Builder {

    /**
     * Returns an instance of a new builder.
     *
     * @param transport HTTP transport, which should normally be:
     *        <ul>
     *        <li>Google App Engine:
     *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
     *        <li>Android: {@code newCompatibleTransport} from
     *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
     *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
     *        </li>
     *        </ul>
     * @param jsonFactory JSON factory, which may be:
     *        <ul>
     *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
     *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
     *        <li>Android Honeycomb or higher:
     *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
     *        </ul>
     * @param httpRequestInitializer HTTP request initializer or {@code null} for none
     * @since 1.7
     */
    public Builder(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
        com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      super(
          transport,
          jsonFactory,
          DEFAULT_ROOT_URL,
          DEFAULT_SERVICE_PATH,
          httpRequestInitializer,
          false);
    }

    /** Builds a new instance of {@link BookSystem}. */
    @Override
    public BookSystem build() {
      return new BookSystem(this);
    }

    @Override
    public Builder setRootUrl(String rootUrl) {
      return (Builder) super.setRootUrl(rootUrl);
    }

    @Override
    public Builder setServicePath(String servicePath) {
      return (Builder) super.setServicePath(servicePath);
    }

    @Override
    public Builder setHttpRequestInitializer(com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      return (Builder) super.setHttpRequestInitializer(httpRequestInitializer);
    }

    @Override
    public Builder setApplicationName(String applicationName) {
      return (Builder) super.setApplicationName(applicationName);
    }

    @Override
    public Builder setSuppressPatternChecks(boolean suppressPatternChecks) {
      return (Builder) super.setSuppressPatternChecks(suppressPatternChecks);
    }

    @Override
    public Builder setSuppressRequiredParameterChecks(boolean suppressRequiredParameterChecks) {
      return (Builder) super.setSuppressRequiredParameterChecks(suppressRequiredParameterChecks);
    }

    @Override
    public Builder setSuppressAllChecks(boolean suppressAllChecks) {
      return (Builder) super.setSuppressAllChecks(suppressAllChecks);
    }

    /**
     * Set the {@link BookSystemRequestInitializer}.
     *
     * @since 1.12
     */
    public Builder setBookSystemRequestInitializer(
        BookSystemRequestInitializer booksystemRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(booksystemRequestInitializer);
    }

    @Override
    public Builder setGoogleClientRequestInitializer(
        com.google.api.client.googleapis.services.GoogleClientRequestInitializer googleClientRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
    }
  }
}
