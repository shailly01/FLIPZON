/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.my.spring.controller;

import com.my.spring.dao.AdminDAO;
import com.my.spring.dao.ProductDAO;
import com.my.spring.dao.UserDAO;
import com.my.spring.exception.ProductException;
import com.my.spring.exception.UserException;
import com.my.spring.pojo.Product;
import com.my.spring.pojo.User;
import com.my.spring.pojo.Admin;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Shailly
 */
@Controller
public class AdminController {

	@Autowired
	@Qualifier("productDao")
	ProductDAO productDao;

	@Autowired
	@Qualifier("userDao")
	UserDAO userDao;

	@Autowired
	@Qualifier("adminDao")
	AdminDAO adminDao;

	@Autowired
	ServletContext servletContext;

	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	protected String goToUserHome(HttpServletRequest request) throws Exception {
		return "admin-home";
	}

	@RequestMapping(value = "/admin/userlist", method = RequestMethod.GET)
	public ModelAndView displayActiveUser(HttpServletRequest request) throws Exception {

		try {
			List<User> users = adminDao.list();
			return new ModelAndView("user-list", "users", users);

		} catch (UserException e) {
			System.out.println(e.getMessage());
			return new ModelAndView("error", "errorMessage", "error while login");
		}

	}

	@RequestMapping(value = "/admin/inactivelist", method = RequestMethod.GET)
	public ModelAndView displayInactiveUser(HttpServletRequest request) throws Exception {

		try {
			List<User> users = adminDao.inactivelist();
			return new ModelAndView("inactive-seller-list", "users", users);

		} catch (UserException e) {
			System.out.println(e.getMessage());
			return new ModelAndView("error", "errorMessage", "error while login");
		}

	}

	@RequestMapping(value = "/admin/remove/*", method = RequestMethod.GET)
	public ModelAndView removeUser(HttpServletRequest request) throws Exception {

		String url = request.getRequestURI();
		long id = 0;
		id = Integer.parseInt((url.split("/")[4]).split("\\.")[0]);
		try {
			User u = adminDao.deleteUser(id);
			List<User> users = adminDao.list();
			return new ModelAndView("user-list", "users", users);
		} catch (UserException e) {
			System.out.println(e.getMessage());
			return new ModelAndView("error", "errorMessage", "error while login");
		}
	}

	@RequestMapping(value = "/admin/approve/*", method = RequestMethod.POST)
	public ModelAndView approveSeller(HttpServletRequest request) throws Exception {

		String url = request.getRequestURI();
		long id = 0;
		id = Integer.parseInt((url.split("/")[4]).split("\\.")[0]);
		try {

			Map<String, String> status = new HashMap<>();
			status.put("active", "true");

			User u = adminDao.updateUserA(id, status);
			Email email = new SimpleEmail();
			email.setHostName("smtp.googlemail.com");
			email.setSmtpPort(465);
			email.setAuthentication("shaillyj6@gmail.com", "Abc123$$");
			email.setSSLOnConnect(true);
			email.setFrom(u.getEmail().getEmailAddress());
			email.setSubject("Sign Up Successful");
			email.setMsg("Your account has been Activate Successfully.");
			email.addTo(u.getEmail().getEmailAddress());
			email.send();
			List<User> users = adminDao.inactivelist();
			return new ModelAndView("inactive-seller-list", "users", users);
		} catch (UserException e) {
			System.out.println(e.getMessage());
			return new ModelAndView("error", "errorMessage", "error while login");
		}
	}

	@RequestMapping(value = "/admin/reject/*", method = RequestMethod.GET)
	public ModelAndView rejectSeller(HttpServletRequest request) throws Exception {

		String url = request.getRequestURI();
		long id = 0;
		id = Integer.parseInt((url.split("/")[4]).split("\\.")[0]);
		try {

			Map<String, String> status = new HashMap<>();
			status.put("active", "Reject");
			User u = adminDao.updateUserR(id, status);
			List<User> users = adminDao.rejectlist();
			return new ModelAndView("reject-seller", "users", users);
		} catch (UserException e) {
			System.out.println(e.getMessage());
			return new ModelAndView("error", "errorMessage", "error while login");
		}
	}

	@RequestMapping(value = "/admin/rejectlist", method = RequestMethod.GET)
	public ModelAndView rejectSellerlist(HttpServletRequest request) throws Exception {
		try {
			List<User> users = adminDao.rejectlist();
			return new ModelAndView("reject-seller", "users", users);
		} catch (UserException e) {
			System.out.println(e.getMessage());
			return new ModelAndView("error", "errorMessage", "error while login");
		}
	}

	@RequestMapping(value = "/admin/productlist", method = RequestMethod.GET)
	public ModelAndView showProduct(HttpServletRequest request) throws Exception {

		ModelAndView mav = new ModelAndView("product-list-admin");
		List<Product> products = productDao.list();
		mav.addObject("products", products);
		return mav;
	}

}