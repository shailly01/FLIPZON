package com.my.spring.controller;

import com.my.spring.dao.AdminDAO;
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
import com.my.spring.pojo.Admin;
import com.my.spring.validator.UserValidator;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/user/*")
public class UserController {

	
	@Autowired
	@Qualifier("userDao")
	UserDAO userDao;
        
        
        @Autowired
	@Qualifier("adminDao")
	AdminDAO adminDao;

	@Autowired
	@Qualifier("userValidator")
	UserValidator validator;
        
        @Autowired
	ServletContext servletContext;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(validator);
	}
 
        @RequestMapping(value = "/", method = RequestMethod.GET)
	protected String goToUserHome(HttpServletRequest request) throws Exception {
		return "user-home";
	}
        
        @RequestMapping(value = "/user/buyer", method = RequestMethod.GET)
	protected String goToBuyerHome(HttpServletRequest request) throws Exception {
		return "buyer-home";
	}
	
	
	@RequestMapping(value = "/user/login", method = RequestMethod.POST)
	protected String loginUser(HttpServletRequest request) throws Exception {

		HttpSession session = (HttpSession) request.getSession();
		
		try {

			System.out.print("loginUser");

                       User u = userDao.get(request.getParameter("username"), request.getParameter("password"));
                       Admin a = adminDao.get(request.getParameter("username"), request.getParameter("password"));
			
			if((u == null) && (a == null)){
				System.out.println("UserName/Password does not exist");
				session.setAttribute("errorMessage", "UserName/Password does not exist");
				return "error";
			}
                        
                         else if((a!=null) && (a.getUsertype().equals("admin"))){
				session.setAttribute("admin", "a");
				return "admin-home";
			}
			
			else if((u!=null) && u.getUsertype().equals("Buyer")&&(u.getActive().equalsIgnoreCase("true"))){
				session.setAttribute("user", u);
				return "buyer-home";
			}
			
//			else if(!(u.getUsertype().equals("Buyer"))&&!(u.getUsertype().equals("Seller")) && !(a.getUsertype().equals("admin")) ){
//				session.setAttribute("errorMessage", "UserName/Password/admin does not exist");
//				return "error";
//			}
//                        
                        else if(((u!=null)&&u.getUsertype().equals("Seller") &&(u.getActive().equalsIgnoreCase("false")))
                                ||((u!=null)&&u.getUsertype().equals("Seller") &&(u.getActive().equalsIgnoreCase("Reject")))){
				session.setAttribute("errorMessage", "Account is not activate");
				return "error";
			}
                        
                       
			else{
				session.setAttribute("user", u);
				return "user-home";
			}
			
			//return "user-home";

		} catch (UserException e) {
			System.out.println("Exception: " + e.getMessage());
			session.setAttribute("errorMessage", "error while login");
			return "error";
		}

	}
	
	@RequestMapping(value = "/user/register", method = RequestMethod.GET)
	protected ModelAndView registerUser() throws Exception {
		System.out.print("registerUser");

		ModelAndView mav = new ModelAndView("register-user");
		Map<String,String> usertype = new LinkedHashMap<String,String>();
		usertype.put("Buyer", "Buyer");
		usertype.put("Seller", "Seller");
		
		mav.addObject("usertype", usertype);
        mav.addObject("user", new User());
        return mav;
		//return new ModelAndView("", "user", new User());

	}
	
	@RequestMapping(value = "/user/register", method = RequestMethod.POST)
	protected ModelAndView registerNewUser(HttpServletRequest request, @ModelAttribute("user") User user, BindingResult result, HttpServletResponse response) throws Exception {

		validator.validate(user, result);

		if (result.hasErrors()) {
			ModelAndView mav = new ModelAndView("register-user");
			Map<String,String> usertype = new LinkedHashMap<String,String>();
			usertype.put("Buyer", "Buyer");
			usertype.put("Seller", "Seller");
			
			mav.addObject("usertype", usertype);
	        mav.addObject("user", user);
	        return mav;
			//return new ModelAndView("register-user", "user", user);
		}

		try {

                System.out.print("registerNewUser");
               //  User someUser=userDao.getUserByUsername(user.getUsername());
                 //boolean em = userDao.checkIfEmailAlreadyExists(user.getEmail().getEmailAddress());
                //if((someUser==null) && em){
                   // if(someUser==null){
                    User u = userDao.register(user);
                   request.getSession().setAttribute("user", u);
                   if(u.getActive().equalsIgnoreCase("true")){
                   Email email= new SimpleEmail();
	           email.setHostName("smtp.googlemail.com");
	           email.setSmtpPort(465);
	           email.setAuthentication("shaillyj6@gmail.com", "Abc123$$");
	           email.setSSLOnConnect(true);
	           email.setFrom(user.getEmail().getEmailAddress());
	           email.setSubject("Sign Up Successful");
	           email.setMsg("Welcome to the flipzon Store\n\n Your account has been successfully created.");
	           email.addTo(user.getEmail().getEmailAddress());
	           email.send();
			
			return new ModelAndView("account-success", "user", u);
                   }
                    return new ModelAndView("seller_inactive_account", "user", u);
                  
		//}
//                else{
//                return new ModelAndView("error", "errorMessage", "Username/Email Address Already Exists"); //javascript alert
//                }
		   
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
