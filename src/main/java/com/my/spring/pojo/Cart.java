package com.my.spring.pojo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "cart_table")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)

public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	@Column(name = "cartID", unique = true, nullable = false)
	private long id;

	@OneToOne
	private User user;

	@ManyToMany
	@JoinTable(
		name = "cart_product_table",
		joinColumns = {
			@JoinColumn(name = "cartID", nullable = false, updatable = false, referencedColumnName = "cartID")
		},
		inverseJoinColumns = {
			@JoinColumn(name = "productID", referencedColumnName = "productID")
		}
	)
	private Set<Product> products = new HashSet<Product> ();

	@Column(name = "title")
	private String title;

	@Column(name = "filename")
	private String filename;

	@Column(name = "totalprice")
	private Float totalprice;

	@Column(name = "quantity")
	private int quantity;

	public Cart() {

	}

	public Cart(String title, User user, String filename, Float totalprice, int quantity, Float finalPrice) {
		this.title = title;
		this.filename = filename;
		this.totalprice = totalprice;
		this.user = user;
		this.quantity = quantity;
		//this.finalPrice = finalPrice;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Float getTotalprice() {
		return totalprice;
	}

	public void setTotalprice(Float totalprice) {
		this.totalprice = totalprice;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}