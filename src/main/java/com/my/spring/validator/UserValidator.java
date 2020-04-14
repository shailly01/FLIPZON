package com.my.spring.validator;

import com.my.spring.dao.UserDAO;
import com.my.spring.exception.UserException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.my.spring.pojo.User;
import com.my.spring.pojo.Email;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class UserValidator implements Validator {
    
    
    @Autowired
	@Qualifier("userDao")
	 UserDAO userDAO;
    

    @Override
	public boolean supports(Class aClass) {
		return aClass.equals(User.class);
	}
	 private static final 
	 String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"  
			   + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";    
	 String STRING_PATTERN = "[a-zA-Z]+";  
         
         String pattern1 = "^[a-zA-Z0-9]{6,10}$";
  		 
    @Override
	public void validate(Object obj, Errors errors) {
	 ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "error.invalid.user", "First Name Required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "error.invalid.user", "Last Name Required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "error.invalid.user", "User Name Required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "error.invalid.password", "Password Required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email.emailAddress", "error.invalid.email.emailAddress","Email Required");
       
        User newuser = (User) obj;

                 if (errors.hasErrors()) {
            return;//Skip the rest of the validation rules
                }
        
	
		try {
			User u= userDAO.getU(newuser.getUsername());
			if(u !=null)
				errors.rejectValue("username", "error.invalid.user", "Username Already Exists");
			
		} catch (UserException e) {
			System.err.println("Exception in User Validator");
		} 
                                
                try {
		 boolean e= userDAO.checkEmail(newuser.getEmail().getEmailAddress());
			if(!e)
				errors.rejectValue("email.emailAddress", "error.invalid.email.emailAddress", "Email Address Already Exists");
			
		} catch (UserException e) {
			System.err.println("Exception in User Validator");
		}
                
                 if (!(newuser.getPassword() != null && newuser.getPassword().isEmpty())) {  
                    System.out.println("password is "+ newuser.getPassword());
			 Pattern pattern2 = Pattern.compile(pattern1);  
			 Matcher  mat = pattern2.matcher(newuser.getPassword());
			   if (!mat.matches()) {  
			    errors.rejectValue("password", "password.containNonChar",  
			      "Enter a valid password of strings");  
			   }  
			  }
        
        
        
                if (!(newuser.getFirstName() != null && newuser.getFirstName().isEmpty())) {  
                    
			 Pattern  pattern = Pattern.compile(STRING_PATTERN);  
			   Matcher matcher = pattern.matcher(newuser.getFirstName());  
			   if (!matcher.matches()) {  
			    errors.rejectValue("firstName", "firstName.containNonChar",  
			      "Enter a valid first name");  
			   }  
			  }  
		if (!(newuser.getLastName() != null && newuser.getLastName().isEmpty())) {  
			 Pattern   pattern = Pattern.compile(STRING_PATTERN);  
			 Matcher  matcher = pattern.matcher(newuser.getLastName());  
			   if (!matcher.matches()) {  
			    errors.rejectValue("lastName", "lastName.containNonChar",  
			      "Enter a valid last name");  
			   }  
			  } 
		
		if (!(newuser.getUsername() != null && newuser.getUsername().isEmpty())) {  
			 Pattern   pattern = Pattern.compile(STRING_PATTERN);  
			 Matcher  matcher = pattern.matcher(newuser.getUsername());  
			   if (!matcher.matches()) {  
			    errors.rejectValue("username", "username.containNonChar",  
			      "Enter a valid username");  
			   }  
			  }
                
             if("NONE".equals(newuser.getUsertype())){
            errors.rejectValue("usertype", "error.invalid.usertype","User type is required");
                }
       
                
	}
}
