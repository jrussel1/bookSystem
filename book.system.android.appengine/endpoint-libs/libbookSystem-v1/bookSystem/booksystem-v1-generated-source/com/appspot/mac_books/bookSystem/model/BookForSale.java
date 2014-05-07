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
 * on 2014-05-07 at 21:28:46 UTC 
 * Modify at your own risk.
 */

package com.appspot.mac_books.bookSystem.model;

/**
 * Model definition for BookForSale.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the bookSystem. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class BookForSale extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private Book book;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Double price;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private Seller seller;

  /**
   * @return value or {@code null} for none
   */
  public Book getBook() {
    return book;
  }

  /**
   * @param book book or {@code null} for none
   */
  public BookForSale setBook(Book book) {
    this.book = book;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Double getPrice() {
    return price;
  }

  /**
   * @param price price or {@code null} for none
   */
  public BookForSale setPrice(java.lang.Double price) {
    this.price = price;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public Seller getSeller() {
    return seller;
  }

  /**
   * @param seller seller or {@code null} for none
   */
  public BookForSale setSeller(Seller seller) {
    this.seller = seller;
    return this;
  }

  @Override
  public BookForSale set(String fieldName, Object value) {
    return (BookForSale) super.set(fieldName, value);
  }

  @Override
  public BookForSale clone() {
    return (BookForSale) super.clone();
  }

}
