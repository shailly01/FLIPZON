package com.my.spring.dao;

import static com.my.spring.dao.DAO.getSession;
import com.my.spring.exception.AdvertException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.my.spring.exception.CartException;
import com.my.spring.exception.CategoryException;
import com.my.spring.pojo.Advert;
import com.my.spring.pojo.Cart;
import com.my.spring.pojo.Category;
import com.my.spring.pojo.User;
import java.util.Iterator;

public class CartDAO extends DAO {
	
	public CartDAO(){
	
	}

//	public Cart insert(Cart cart) throws CartException {
//		try{
//			begin();            
//			getSession().save(cart);     
//            commit();
//            return cart;
//		} catch (HibernateException e){
//			rollback();
//            throw new CartException("Could not save the cart", e);
//		}
//	}
        
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
       
       
       
          public Cart deleteCart(long id) throws CartException{
      
       try{
           
               begin();    
            Query q = getSession().createQuery("from Cart c where c.id= :id");
            q.setLong("id", id);
            Cart c = (Cart) q.uniqueResult();
            getSession().delete(c);
            commit();  
            return c;
         }
          catch (HibernateException e) {
           rollback();
           throw new CartException("Could not delete Cart", e);
        }     
    
   }
       
public Cart insert(long id,User u,int quant) throws CartException {
                    
		try{
                    begin(); 
                        String hql = "select a.title,a.filename,a.price from Advert a where a.id= :id";
                        System.out.println("dvdvfd  "+ id);
                        Query q = getSession().createQuery(hql);
                       q.setLong("id", id);
                     //Advert a = (Advert)q.uniqueResult();
                     List<Advert>a = q.getResultList();
                     Iterator it = a.iterator();
                   Cart cart = new Cart();
                     while(it.hasNext()){
                         Object[] b = (Object[])it.next();
                       cart.setUser(u);
                       cart.setQuantity(quant);
                       cart.setFinalPrice(quant*(Float.parseFloat(b[2].toString())));
                        cart.setFilename(b[1].toString());
                        cart.setTitle(b[0].toString());
                        cart.setTotalprice(Float.parseFloat(b[2].toString()));
                     }
			getSession().save(cart);     
            commit();
            return cart;
		} catch (HibernateException e){
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
	
//	public List<Cart> list(){
//		begin();
//		Query q = getSession().createQuery("from Cart");
//		List<Cart> cart1 = q.list();
//		commit();
//		return cart1;
//	}
        
        
        
      public List<Cart> list(long id) throws CartException{
    	
    	try {            
            begin();
             Query q = getSession().createQuery("from Cart c where c.user.personID = :id");
            q.setLong("id",id);
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
