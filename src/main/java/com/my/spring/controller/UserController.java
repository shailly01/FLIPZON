package com.my.spring.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.my.spring.dao.UserDAO;
import com.my.spring.exception.UserException;
import com.my.spring.pojo.User;
import com.my.spring.validator.UserValidator;

@Controller
@RequestMapping("/user/*")
public class UserController {

	
	@Autowired
	@Qualifier("userDao")
	UserDAO userDao;

	@Autowired
	@Qualifier("userValidator")
	UserValidator validator;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(validator);
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	protected ModelAndView registerUser(HttpServletRequest request,  @ModelAttribute("user") User user, BindingResult result) throws Exception {
		System.out.print("registerUser/n/n/n");

		ModelAndView mav = new ModelAndView("register-user");
		Map<String,String> usertype = new LinkedHashMap<String,String>();
		usertype.put("Buyer", "Buyer");
		usertype.put("Seller", "Seller");
		
		mav.addObject("usertype", usertype);
               mav.addObject("user", new User());
               return mav;
	
	}
	
	@RequestMapping(value = "/user/register", method = RequestMethod.POST)
	protected ModelAndView registerNewUser(HttpServletRequest request,  @ModelAttribute("user") User user, BindingResult result) throws Exception {

		validator.validate(user, result);

		if (result.hasErrors()) {
			ModelAndView mav = new ModelAndView("register-user");
			Map<String,String> usertype = new LinkedHashMap<String,String>();
			usertype.put("Buyer", "Buyer");
			usertype.put("Seller", "Seller");
			
			mav.addObject("usertype", usertype);
	        mav.addObject("user", user);
	        return mav;
			
		}

		try {

			System.out.print("registerNewUser/n/n");

			User u = userDao.register(user);
			
			request.getSession().setAttribute("user", u);
			Email email= new SimpleEmail();
	           email.setHostName("smtp.googlemail.com");
	           email.setSmtpPort(465);
	           email.setAuthentication("shaillyj6@gmail.com", "Abc123$$");
	           email.setSSLOnConnect(true);
	           email.setFrom(user.getEmail().getEmailAddress());
	           email.setSubject("Sign Up Successful");
	           email.setMsg("Welcome to the Flipzon Store \n\n Your account has been successfully created.");
	           email.addTo(user.getEmail().getEmailAddress());
	           email.send();
			
			return new ModelAndView("account-success", "user", u);

		} catch (UserException e) {
			System.out.println("Exception: " + e.getMessage());
			return new ModelAndView("error", "errorMessage", "error while login");
		}
	}

		protected Map referenceData(HttpServletRequest request) throws Exception {

		Map referenceData = new HashMap();

		Map<String,String> usertype = new LinkedHashMap<String,String>();
		usertype.put("Buyer", "Buyer");
		usertype.put("Seller", "Seller");
		referenceData.put("usertype", usertype);
		
		return referenceData;
	}
}
