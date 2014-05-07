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
 * Model definition for SaleShelf.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the bookSystem. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class SaleShelf extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private JsonMap isbnToList;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private JsonMap isbnToMap;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<BookForSale> list;

  static {
    // hack to force ProGuard to consider BookForSale used, since otherwise it would be stripped out
    // see http://code.google.com/p/google-api-java-client/issues/detail?id=528
    com.google.api.client.util.Data.nullOf(BookForSale.class);
  }

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private JsonMap sellerIdToList;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private JsonMap sellerIdToMap;

  /**
   * @return value or {@code null} for none
   */
  public JsonMap getIsbnToList() {
    return isbnToList;
  }

  /**
   * @param isbnToList isbnToList or {@code null} for none
   */
  public SaleShelf setIsbnToList(JsonMap isbnToList) {
    this.isbnToList = isbnToList;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public JsonMap getIsbnToMap() {
    return isbnToMap;
  }

  /**
   * @param isbnToMap isbnToMap or {@code null} for none
   */
  public SaleShelf setIsbnToMap(JsonMap isbnToMap) {
    this.isbnToMap = isbnToMap;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<BookForSale> getList() {
    return list;
  }

  /**
   * @param list list or {@code null} for none
   */
  public SaleShelf setList(java.util.List<BookForSale> list) {
    this.list = list;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public JsonMap getSellerIdToList() {
    return sellerIdToList;
  }

  /**
   * @param sellerIdToList sellerIdToList or {@code null} for none
   */
  public SaleShelf setSellerIdToList(JsonMap sellerIdToList) {
    this.sellerIdToList = sellerIdToList;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public JsonMap getSellerIdToMap() {
    return sellerIdToMap;
  }

  /**
   * @param sellerIdToMap sellerIdToMap or {@code null} for none
   */
  public SaleShelf setSellerIdToMap(JsonMap sellerIdToMap) {
    this.sellerIdToMap = sellerIdToMap;
    return this;
  }

  @Override
  public SaleShelf set(String fieldName, Object value) {
    return (SaleShelf) super.set(fieldName, value);
  }

  @Override
  public SaleShelf clone() {
    return (SaleShelf) super.clone();
  }

}
