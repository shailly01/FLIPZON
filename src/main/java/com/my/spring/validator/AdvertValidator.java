package com.my.spring.validator;

import com.my.spring.dao.AdvertDAO;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.my.spring.pojo.Advert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.multipart.MultipartFile;

@Component
public class AdvertValidator implements Validator {

    
    
                @Autowired
		@Qualifier("advertDao")
		AdvertDAO advertDao;
                
	public boolean supports(Class aClass) {
		return aClass.equals(Advert.class);
	}

	private Pattern pattern;  
	 private Matcher matcher;  
	
	 private static final String PRICE_PATTERN = "[0-9]+";    
	private static final String STRING_PATTERN = "[a-zA-Z]+";
         
         
        private static final String IMAGE_PATTERN = "([^\\s]+(\\.(?i)(jpg|png|gif|bmp))$)";
         
	
	public void validate(Object obj, Errors errors) {
		Advert newAdvert = (Advert) obj;

		// ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title",
		// "error.invalid.category", "Category Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "error.invalid.title", "Title Required");
		 if (!(newAdvert.getTitle() != null && newAdvert.getTitle().isEmpty())) {  
			   pattern = Pattern.compile(STRING_PATTERN);  
			   matcher = pattern.matcher(newAdvert.getTitle());  
			   if (!matcher.matches()) {  
			    errors.rejectValue("title", "title.containNonChar",  
			      "Enter a valid title");  
			   }  
			  }
		 
		 ValidationUtils.rejectIfEmptyOrWhitespace(errors, "filename", "error.invalid.filename", "Filename Required");
		 if (!(newAdvert.getFilename() != null && newAdvert.getFilename().isEmpty())) {  
			   pattern = Pattern.compile(STRING_PATTERN);  
			   matcher = pattern.matcher(newAdvert.getFilename());  
			   if (!matcher.matches()) {  
			    errors.rejectValue("filename", "filename.containNonChar",  
			      "Enter a valid filename");  
			   }  
			  }
		 
		 ValidationUtils.rejectIfEmptyOrWhitespace(errors, "message", "error.invalid.message", "Product Description Required");
		 if (!(newAdvert.getMessage() != null && newAdvert.getMessage().isEmpty())) {  
			   pattern = Pattern.compile(STRING_PATTERN);  
			   matcher = pattern.matcher(newAdvert.getMessage());  
			   if (!matcher.matches()) {  
			    errors.rejectValue("message", "message.containNonChar",  
			      "Enter a valid product description");  
			   }  
			  }
                 
                 
                ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price", "error.invalid.price", "Price Required");
		 if (!(newAdvert.getPrice() != null && newAdvert.getPrice()!=0)) {  
			   pattern = Pattern.compile(PRICE_PATTERN);  
			   matcher = pattern.matcher(String.valueOf(newAdvert.getPrice()));  
			   if (!matcher.matches()) {  
			    errors.rejectValue("price", "price.containNonChar",  
			      "Enter a valid price Number");  
			   }  
			  }

                Pattern pattern = Pattern.compile(IMAGE_PATTERN);
		Matcher matcher;
		MultipartFile productPhoto = newAdvert.getProductPicture();
		if(productPhoto!=null){
			System.out.println("Veryfying product Picture");
			matcher = pattern.matcher(productPhoto.getOriginalFilename());

			if (0 == productPhoto.getSize()) {
				errors.rejectValue("productPicture", "Test", "File is empty");
			}
                        else if (!matcher.matches()) {
				errors.rejectValue("productPicture", "Test", "Invalid Image Format");
			}

                        else if (5000000 < productPhoto.getSize()) {
				errors.rejectValue("productPicture", "Test", "File size is over 5mb !");
			}
		}
		else{
			errors.rejectValue("productPicture", "error.invalid.user", "File is empty");
		}
                 
                 
                 
	}
}
