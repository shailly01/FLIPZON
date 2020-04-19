package com.my.spring.dao;

import static com.my.spring.dao.DAO.getSession;
import com.my.spring.exception.ProductException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.my.spring.exception.CartException;
import com.my.spring.exception.CategoryException;
import com.my.spring.pojo.Product;
import com.my.spring.pojo.Cart;
import com.my.spring.pojo.Category;
import com.my.spring.pojo.User;
import java.util.Iterator;
import java.util.Map;

public class CartDAO extends DAO {

	public CartDAO() {

	}

	public void delete(Cart cart)
	throws CartException {
		try {
			begin();
			getSession().delete(cart);
			commit();
		} catch (HibernateException e) {
			rollback();
			throw new CartException("Could not delete cart", e);
		}
	}

	public Cart deleteCart(long id) throws CartException {

		try {

			begin();
			Query q = getSession().createQuery("from Cart c where c.id= :id");
                        q.setCacheable(true);
			q.setLong("id", id);
			Cart c = (Cart) q.uniqueResult();
			getSession().delete(c);
			commit();
			return c;
		} catch (HibernateException e) {
			rollback();
			throw new CartException("Could not delete Cart", e);
		}

	}

	public Cart insert(long id, User u, int quant) throws CartException {

		try {
			begin();
			String hql = "select a.title,a.filename,a.price from Product a where a.id= :id";
			System.out.println("dvdvfd  " + id);
			Query q = getSession().createQuery(hql);
			q.setLong("id", id);
			//Product a = (Product)q.uniqueResult();
			List<Product> a = q.getResultList();
			Iterator it = a.iterator();
			Cart cart = new Cart();
			while (it.hasNext()) {
				Object[] b = (Object[]) it.next();
				cart.setUser(u);
				cart.setQuantity(quant);
				// cart.setFinalPrice(quant*(Float.parseFloat(b[2].toString())));
				cart.setFilename(b[1].toString());
				cart.setTitle(b[0].toString());
				cart.setTotalprice(Float.parseFloat(b[2].toString()));
			}
			getSession().save(cart);
			commit();
			return cart;
		} catch (HibernateException e) {
			rollback();
			throw new CartException("Could not save the cart", e);
		}
	}

	public void update(Cart cart) throws CategoryException {
		try {
			begin();
			getSession().update(cart);
			commit();
		} catch (HibernateException e) {
			rollback();
			throw new CategoryException("Could not save the cart", e);
		}
	}

	public User update(User user) throws CategoryException {
		try {
			begin();
			getSession().update(user);
			commit();
		} catch (HibernateException e) {
			rollback();
			throw new CategoryException("Could not save the user", e);
		}
		return user;
	}

	public Cart updateQuantity(long Id, Map<String, String> quantity) throws CartException {
		try {
			begin();
			Cart cart = (Cart) getSession().load(Cart.class, Id);
			cart.setQuantity(Integer.parseInt(quantity.get("quantity")));
			getSession().update(cart);
			commit();
			return cart;
		} catch (HibernateException e) {
			rollback();
			throw new CartException("Could not update product", e);
		}
	}

	public List<Cart> list(long id) throws CartException {

		try {
			begin();
			Query q = getSession().createQuery("from Cart c where c.user.personID = :id");
                        q.setCacheable(true);
			q.setLong("id", id);
			List<Cart> carts = q.list();
			commit();
			return carts;
		} catch (HibernateException e) {
			rollback();
			throw new CartException("Could not exists product", e);
		}
	}

	public Cart updateCart(Cart cart) throws CategoryException {
		try {
			begin();
			getSession().update(cart);
			commit();
			return cart;
		} catch (HibernateException e) {
			rollback();
			throw new CategoryException("Could not save the cart", e);
		}
	}

}