package com.my.spring.dao;

import static com.my.spring.dao.DAO.getSession;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import com.my.spring.exception.UserException;
import com.my.spring.pojo.Email;
import com.my.spring.pojo.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

public class UserDAO extends DAO {
@Autowired PersonDao personDao;

	public UserDAO() {
	}

        public User register(User u)
			throws UserException {
		try {
//			begin();
			System.out.println("inside DAO");

			Email email = new Email(u.getEmail().getEmailAddress());
                        User user = new User(u.getFirstName(),u.getLastName(),u.getUsername(), u.getPassword(), u.getUsertype());
			//User user = new User(u.getUsername(), u.getPassword(), u.getUsertype());

			//user.setFirstName(u.getFirstName());
			//user.setLastName(u.getLastName());
                        user.setEmail(email);
			email.setUser(user);
                        
                        Session session  =getSession();
                        Transaction tr = session.beginTransaction();
			getSession().save(user);
                        System.out.println("personName is " + u.getFirstName());
                        System.out.println("personid  is " + u.getPersonID());
                        System.out.println("EmailId is " + u.getEmail().getId());
			tr.commit();
			return user;
                        
		} catch (HibernateException e) {
			rollback();
			throw new UserException("Exception while creating user: " + e.getMessage());
		}
	}

}