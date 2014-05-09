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
 * on 2014-05-09 at 04:11:51 UTC 
 * Modify at your own risk.
 */

package com.appspot.mac_books.bookSystem.model;

/**
 * Model definition for IntegerResponse.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the bookSystem. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class IntegerResponse extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer numRows;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getNumRows() {
    return numRows;
  }

  /**
   * @param numRows numRows or {@code null} for none
   */
  public IntegerResponse setNumRows(java.lang.Integer numRows) {
    this.numRows = numRows;
    return this;
  }

  @Override
  public IntegerResponse set(String fieldName, Object value) {
    return (IntegerResponse) super.set(fieldName, value);
  }

  @Override
  public IntegerResponse clone() {
    return (IntegerResponse) super.clone();
  }

}
