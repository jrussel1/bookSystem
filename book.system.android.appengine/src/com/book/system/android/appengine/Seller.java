package com.book.system.android.appengine;

public class Seller {

	private Long id;

	private String email;

	private String firstName;

	private String lastName;

	public Seller(String email) {
		this.email=email;
	}
	public Seller(int personId, String email, String firstName, String lastName) {
		this.id=(long) personId;
		this.email=email;
		this.firstName=firstName;
		this.lastName=lastName;
	}
	/**
	 * @return the ID
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param ID the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setfFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setfLastName(String lastName) {
		this.lastName = lastName;
	}

	

}
