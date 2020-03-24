package com.my.spring.dao;

//import static com.my.spring.dao.DAO.getSession;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import com.my.spring.exception.UserException;
import com.my.spring.pojo.Email;
//import com.my.spring.pojo.Email;
import com.my.spring.pojo.User;
import com.my.spring.pojo.Person;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

public class UserDAO extends DAO {
    
    
    
    
    
    	public User get(String username, String password) throws UserException {
		try {
			begin();
			Query q = getSession().createQuery("from User where username = :username and password = :password");
			q.setString("username", username);
			q.setString("password", password);
			User user = (User) q.uniqueResult();
			//System.out.println(user.getUsertype());
			commit();
			return user;
		} catch (HibernateException e) {
			rollback();
			throw new UserException("Could not get user " + username, e);
		}
	}
    
    
   public User register(User u)
			throws UserException {
		try {
			begin();
			System.out.println("inside DAO");

                        Email email = new Email(u.getEmail().getEmailAddress());
			User user = new User(u.getUsername(), u.getPassword(), u.getUsertype());

			user.setFirstName(u.getFirstName());
			user.setLastName(u.getLastName());
			user.setEmail(email);
			email.setUser(user);
			getSession().save(user);
			commit();
			return user;

		} catch (HibernateException e) {
			rollback();
			throw new UserException("Exception while creating user: " + e.getMessage());
		}
	}

}