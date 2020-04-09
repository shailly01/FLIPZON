package com.my.spring.validator;

import com.my.spring.dao.UserDAO;
import com.my.spring.exception.UserException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.my.spring.pojo.User;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class UserValidator implements Validator {
    
    
    @Autowired
	@Qualifier("userDao")
	UserDAO userDao;

	public boolean supports(Class aClass) {
		return aClass.equals(User.class);
	}

	 private Pattern pattern;  
	 private Matcher matcher;  
	
	 private static final 
	 String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"  
			   + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";    
	 String STRING_PATTERN = "[a-zA-Z]+";  
	
			 
			 
	public void validate(Object obj, Errors errors) {
		User user = (User) obj;
                
                
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "error.invalid.user", "First Name Required");
		 if (!(user.getFirstName() != null && user.getFirstName().isEmpty())) {  
			   pattern = Pattern.compile(STRING_PATTERN);  
			   matcher = pattern.matcher(user.getFirstName());  
			   if (!matcher.matches()) {  
			    errors.rejectValue("firstName", "firstName.containNonChar",  
			      "Enter a valid first name");  
			   }  
			  }  
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "error.invalid.user", "Last Name Required");
		if (!(user.getLastName() != null && user.getLastName().isEmpty())) {  
			   pattern = Pattern.compile(STRING_PATTERN);  
			   matcher = pattern.matcher(user.getLastName());  
			   if (!matcher.matches()) {  
			    errors.rejectValue("lastName", "lastName.containNonChar",  
			      "Enter a valid last name");  
			   }  
			  } 
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "error.invalid.user", "User Name Required");
		if (!(user.getUsername() != null && user.getUsername().isEmpty())) {  
//                    User someUser=userDao.getUserByUsername(user.getUsername());
//                if(someUser!=null){
//       
//			errors.rejectValue("username", "error.invalid.user", "Username Already Exists");
//	
//                }
			   pattern = Pattern.compile(STRING_PATTERN);  
			   matcher = pattern.matcher(user.getUsername());  
			   if (!matcher.matches()) {  
			    errors.rejectValue("username", "username.containNonChar",  
			      "Enter a valid username");  
			   }  
			  }
                
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "error.invalid.password", "Password Required");
		if (!(user.getPassword() != null && user.getPassword().isEmpty())) {  
			   pattern = Pattern.compile(STRING_PATTERN);  
			   matcher = pattern.matcher(user.getPassword());  
			   if (!matcher.matches()) {  
			    errors.rejectValue("password", "password.containNonChar",  
			      "Enter a valid password of strings");  
			   }  
			  }
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email.emailAddress", "error.invalid.email.emailAddress",
				"Email Required");
		 
		if("NONE".equals(user.getUsertype())){
			errors.rejectValue("usertype", "error.invalid.usertype","User type is required");
		}
		// check if user exists
	
                		
		
                
                
                
	}
}
