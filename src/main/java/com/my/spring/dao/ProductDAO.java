package com.my.spring.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.my.spring.exception.ProductException;
import com.my.spring.pojo.Product;
import com.my.spring.pojo.User;
import com.my.spring.pojo.Category;
import java.util.Map;
import javax.servlet.DispatcherType;
import javax.servlet.http.HttpSession;
import org.hibernate.Transaction;

public class ProductDAO extends DAO {

	public Product create(Product product)
	throws ProductException {
		try {
			begin();
			getSession().save(product);
			commit();
			return product;
		} catch (HibernateException e) {
			rollback();
			throw new ProductException("Exception while creating product: " + e.getMessage());
		}
	}

	public Product deleteProduct(long id) throws ProductException {

		try {

			begin();
			Query q = getSession().createQuery("from Product a where a.id= :id");
                        q.setCacheable(true);
			q.setLong("id", id);
			Product a = (Product) q.uniqueResult();
			getSession().delete(a);
			commit();
			return a;
		} catch (HibernateException e) {
			rollback();
			throw new ProductException("Could not delete product", e);
		}

	}

	public Product updateProduct(long Id, Map<String, String> product) throws ProductException {
		try {
			begin();
			Product p = (Product) getSession().load(Product.class, Id);
			p.setTitle(product.get("title"));
			p.setMessage(product.get("description"));
			p.setPrice(Float.parseFloat((product.get("price"))));
			getSession().update(p);
			commit();
			return p;
		} catch (HibernateException e) {
			rollback();
			throw new ProductException("Could not update product", e);
		}
	}

	public List<Product> list(long id) throws ProductException {

		try {
			begin();
			Query q = getSession().createQuery("from Product a where a.user.personID = :id");
                        q.setCacheable(true);
			q.setLong("id", id);
			List<Product> products = q.list();
			commit();
			return products;
		} catch (HibernateException e) {
			rollback();
			throw new ProductException("Could not exists product", e);
		}
	}

	public List<Product> list() throws ProductException {

		try {
			begin();
			Query q = getSession().createQuery("from Product");
                        q.setCacheable(true);
			List<Product> products = q.list();
			commit();
			return products;
		} catch (HibernateException e) {
			rollback();
			throw new ProductException("Could not exists product", e);
		}
	}

}