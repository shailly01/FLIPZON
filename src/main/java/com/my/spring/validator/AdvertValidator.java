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
                   String title =  stripXSS1(newAdvert.getTitle());
                     newAdvert.setTitle(title);   
            }    
          
                 
                 ValidationUtils.rejectIfEmptyOrWhitespace(errors, "filename", "error.invalid.filename", "Filename Required");
		 if (!(newAdvert.getFilename() != null && newAdvert.getFilename().isEmpty())) {  
                    String title =  stripXSS1(newAdvert.getFilename());
                     newAdvert.setFilename(title);     
			  }
		 
		 ValidationUtils.rejectIfEmptyOrWhitespace(errors, "message", "error.invalid.message", "Product Description Required");
		 if (!(newAdvert.getMessage() != null && newAdvert.getMessage().isEmpty())) {  
			String title =  stripXSS1(newAdvert.getMessage());
                            newAdvert.setMessage(title);     
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
        
        private String stripXSS1(String value) {
        if (value != null) {
            // NOTE: It's highly recommended to use the ESAPI library and uncomment the following line to
            // avoid encoded attacks.
            // value = ESAPI.encoder().canonicalize(value);
 
            // Avoid null characters
            value = value.replaceAll("", "");
 
            // Avoid anything between script tags
            Pattern scriptPattern = Pattern.compile("insert", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");

            scriptPattern = Pattern.compile("delete", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");

            scriptPattern = Pattern.compile("update", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");

            scriptPattern = Pattern.compile("drop", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll(""); 
            
            scriptPattern = Pattern.compile("--", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");            
            
            scriptPattern = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");
 
            // Avoid anything in a src='...' type of expression
            scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");
 
            scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");
 
            // Remove any lonesome </script> tag
            scriptPattern = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");
 
            // Remove any lonesome <script ...> tag
            scriptPattern = Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");
 
            // Avoid eval(...) expressions
            scriptPattern = Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");
 
            // Avoid expression(...) expressions
            scriptPattern = Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");
 
            // Avoid javascript:... expressions
            scriptPattern = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");
 
            // Avoid vbscript:... expressions
            scriptPattern = Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");
 
            // Avoid onload= expressions
            scriptPattern = Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");
        }
        return value;
    }
        
}
