package com.my.spring.pojo;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Cacheable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "user_table")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@PrimaryKeyJoinColumn(name = "personID")
public class User extends Person {

	@Column(name = "username", unique = true)
	private String username;

	@Column(name = "password")
	private String password;

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private Email email;

	@Column(name = "usertype")
	private String usertype;

	@Column(name = "isactive")
	private String active;

	public User(String username, String password, String usertype) {
		this.username = username;
		this.password = password;
		this.usertype = usertype;
	}

	public User() {

	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Email getEmail() {
		return email;
	}

	public void setEmail(Email email) {
		this.email = email;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

}