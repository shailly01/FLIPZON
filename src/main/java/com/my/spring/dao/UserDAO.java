package com.my.spring.dao;

import static com.my.spring.dao.DAO.getSession;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import com.my.spring.exception.UserException;
import com.my.spring.pojo.Email;
import com.my.spring.pojo.User;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

public class UserDAO extends DAO {

	public UserDAO() {}

	public User get(String username, String password) throws UserException {
		try {
			begin();
			Query q = getSession().createQuery("from User where username = :username and password = :password");
                         q.setCacheable(true);
                        q.setString("username", username);
			q.setString("password", password);
			User user = (User) q.uniqueResult();
			commit();
			return user;
		} catch (HibernateException e) {
			rollback();
			throw new UserException("Could not get user " + username, e);
		}
	}

	public User get(int userId) throws UserException {
		try {
			begin();
			Query q = getSession().createQuery("from User where personID= :personID");
                       q.setCacheable(true);
			q.setInteger("personID", userId);
			User user = (User) q.uniqueResult();
			commit();
			return user;
		} catch (HibernateException e) {
			rollback();
			throw new UserException("Could not get user " + userId, e);
		}
	}

	public User getU(String username) throws UserException {
		try {
			begin();
			System.out.println("inside userDAO");
			Query q = getSession().createQuery("from User u where u.username= :username");
                        q.setCacheable(true);
			q.setString("username", username);
			User u = (User) q.uniqueResult();
			commit();
			return u;
		} catch (HibernateException e) {
			rollback();
			throw new UserException("Could not obtain the username " + username + " " + e.getMessage());
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
			if (u.getUsertype().equalsIgnoreCase("Buyer")) {
				user.setActive("true");
			} else {
				user.setActive("false");
			}
			getSession().save(user);
			commit();
			return user;

		} catch (HibernateException e) {
			rollback();
			throw new UserException("Exception while creating user: " + e.getMessage());
		}
	}

	public void delete(User user) throws UserException {
		try {
			begin();
			getSession().delete(user);
			commit();
		} catch (HibernateException e) {
			rollback();
			throw new UserException("Could not delete user " + user.getUsername(), e);
		}
	}

	public Boolean checkEmail(String emailAddress) throws UserException {
		try {
			begin();
			System.out.println("inside userDAO1111111111");
			Query q = getSession().createQuery("from  Email e where e.emailAddress= :emailAddress");
                        q.setCacheable(true);
			q.setString("emailAddress", emailAddress);
			Email e = (Email) q.uniqueResult();
			commit();
			if (e == null) {
				return Boolean.TRUE;
			}
			return Boolean.FALSE;
		} catch (HibernateException e) {
			rollback();
			throw new UserException("Could not obtain the emailAddress " + emailAddress + " " + e.getMessage());
		}
	}

}