package com.my.spring.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.my.spring.dao.CategoryDAO;
import com.my.spring.exception.CategoryException;
import com.my.spring.pojo.Category;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class CategoryValidator implements Validator {

	@Autowired
	@Qualifier("categoryDao")
	CategoryDAO categoryDAO;
	
        @Override
	public boolean supports(Class aClass) {
		return Category.class.equals(aClass);
	}
        
        
        private static final   
	 String STRING_PATTERN = "[a-zA-Z]+";  
        

        @Override
	public void validate(Object obj, Errors errors) {
		Category newCategory = (Category) obj;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "error.invalid.category", "Category Required");
		
		if (errors.hasErrors()) {
            return;//Skip the rest of the validation rules
        }
        
	
		try {
			Category c = categoryDAO.get(newCategory.getTitle());
			if(c !=null)
				errors.rejectValue("title", "error.invalid.category", "Category already Exists");
			
		} catch (CategoryException e) {
			System.err.println("Exception in Category Validator");
		}
                
                
                if (!(newCategory.getTitle() != null && newCategory.getTitle().isEmpty())) {  
                    Pattern  pattern = Pattern.compile(STRING_PATTERN);  
			   Matcher matcher = pattern.matcher(newCategory.getTitle());  
			   if (!matcher.matches()) {  
			    errors.rejectValue("title", "title.containNonChar",  
			      "Enter a valid Category name");  
			   }  
			  }  
		
		
		
	
	}
}
