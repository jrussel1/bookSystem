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
 * on 2014-05-01 at 21:50:48 UTC 
 * Modify at your own risk.
 */

package com.appspot.mac_books.bookSystem.model;

/**
 * Model definition for Book.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the bookSystem. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class Book extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String author;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String isbn;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String title;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getAuthor() {
    return author;
  }

  /**
   * @param author author or {@code null} for none
   */
  public Book setAuthor(java.lang.String author) {
    this.author = author;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getIsbn() {
    return isbn;
  }

  /**
   * @param isbn isbn or {@code null} for none
   */
  public Book setIsbn(java.lang.String isbn) {
    this.isbn = isbn;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getTitle() {
    return title;
  }

  /**
   * @param title title or {@code null} for none
   */
  public Book setTitle(java.lang.String title) {
    this.title = title;
    return this;
  }

  @Override
  public Book set(String fieldName, Object value) {
    return (Book) super.set(fieldName, value);
  }

  @Override
  public Book clone() {
    return (Book) super.clone();
  }

}
