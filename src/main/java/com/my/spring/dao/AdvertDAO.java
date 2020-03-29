package com.my.spring.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.my.spring.exception.AdvertException;
import com.my.spring.pojo.Advert;
import com.my.spring.pojo.User;
import com.my.spring.pojo.Category;
import java.util.Map;
import javax.servlet.DispatcherType;
import javax.servlet.http.HttpSession;
import org.hibernate.Transaction;

public class AdvertDAO extends DAO {

    public Advert create(Advert advert)
            throws AdvertException {
        try {
            begin();            
            getSession().save(advert);     
            commit();
            return advert;
        } catch (HibernateException e) {
            rollback();
            //throw new AdException("Could not create advert", e);
            throw new AdvertException("Exception while creating advert: " + e.getMessage());
        }
    }

//    public void delete(Advert advert)
//            throws AdvertException {
//        try {
//            begin();
//            getSession().delete(advert);
//            commit();
//        } catch (HibernateException e) {
//            rollback();
//            throw new AdvertException("Could not delete advert", e);
//        }
//    }
    
   public Advert deleteAdvert(long id) throws AdvertException{
      
       try{
           
               begin();    
            Query q = getSession().createQuery("from Advert a where a.id= :id");
            q.setLong("id", id);
            Advert a = (Advert) q.uniqueResult();
            getSession().delete(a);
           commit();  
           return a;
        }
        catch (HibernateException e) {
           rollback();
           throw new AdvertException("Could not delete advert", e);
        }     
    
   }
   
      public Advert updateProduct(long Id, Map<String,String> product) throws AdvertException{
          try{
       begin();
       Advert advert = (Advert) getSession().load(Advert.class, Id);
       advert.setTitle(product.get("title"));
       advert.setMessage(product.get("description"));
       advert.setPrice(Float.parseFloat((product.get("price"))));
       getSession().update(advert);
       commit();
       return advert;
          }
          catch (HibernateException e) {
           rollback();
           throw new AdvertException("Could not update product", e);
        }         
   }
    
 
    public List<Advert> list(long id) throws AdvertException{
    	
    	try {            
            begin();
             Query q = getSession().createQuery("from Advert a where a.user.personID = :id");
            q.setLong("id",id);
            List<Advert> adverts = q.list();
            commit();
            return adverts;
        } catch (HibernateException e) {
            rollback();
           throw new AdvertException("Could not exists product", e);
        }
    }
    
    
//        public List<Advert> list() throws AdvertException{
//    	
//    	try {            
//            begin();
//             Query q = getSession().createQuery("from Advert");
//            List<Advert> adverts = q.list();
//            commit();
//            return adverts;
//        } catch (HibernateException e) {
//            rollback();
//           throw new AdvertException("Could not exists product", e);
//        }
//    }
    
    
    
}