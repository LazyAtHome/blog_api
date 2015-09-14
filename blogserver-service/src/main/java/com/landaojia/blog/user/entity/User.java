package com.landaojia.blog.user.entity;

import com.landaojia.blog.common.dao.BaseEntity;

/**
 * 
 * 
 * Auto Generated by <code>CodeGenerator</code>
 */
public class User extends BaseEntity {
	private static final long serialVersionUID = 1L;

	private String username;//

	private String email;//

	private String cryptedPass;//

	private String role;//

	/**
	 * return 
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * return 
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * return 
	 */
	public String getCryptedPass() {
		return cryptedPass;
	}

	/**
	 * @param cryptedPass
	 *            
	 */
	public void setCryptedPass(String cryptedPass) {
		this.cryptedPass = cryptedPass;
	}

	/**
	 * return 
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param role
	 *            
	 */
	public void setRole(String role) {
		this.role = role;
	}
}
