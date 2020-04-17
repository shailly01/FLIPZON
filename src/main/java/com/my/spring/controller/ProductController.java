package com.my.spring.controller;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.my.spring.dao.ProductDAO;
import com.my.spring.dao.CartDAO;
import com.my.spring.dao.CategoryDAO;
import com.my.spring.dao.UserDAO;
import com.my.spring.exception.ProductException;
import com.my.spring.pojo.Product;
import com.my.spring.pojo.Cart;
import com.my.spring.pojo.Category;
import com.my.spring.pojo.User;
import com.my.spring.validator.ProductValidator;
import java.util.HashMap;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

@Controller
//@RequestMapping("/product/*")
public class ProductController {

	@Autowired
	@Qualifier("productDao")
	ProductDAO productDao;

	@Autowired
	@Qualifier("categoryDao")
	CategoryDAO categoryDao;

	@Autowired
	@Qualifier("cartDao")
	CartDAO cartDao;

	@Autowired
	@Qualifier("userDao")
	UserDAO userDao;

	@Autowired
	@Qualifier("productValidator")
	ProductValidator validator;

	@Autowired
	ServletContext servletContext;

	@RequestMapping(value = "/product/add", method = RequestMethod.GET)
	public ModelAndView initializeForm(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.addObject("categories", categoryDao.list());
		mv.addObject("product", new Product());
		mv.setViewName("product-form");
		return mv;
	}

	@RequestMapping(value = "/product/add", method = RequestMethod.POST)
	public ModelAndView addCategory(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("product") Product product, @RequestParam("productPicture") MultipartFile productPicture, BindingResult result) throws Exception {

		if ("true" == request.getAttribute("unsafe_check")) {
			ModelAndView mvc = null;
			mvc = new ModelAndView("securityerror");
			return mvc;
		}

		System.out.println("Sending to Validator");
		product.setProductPicture(productPicture);

		validator.validate(product, result);
		if (result.hasErrors()) {
			ModelAndView mv = new ModelAndView();
			mv.addObject("categories", categoryDao.list());
			mv.addObject("product", product);
			mv.setViewName("product-form");
			return mv;
		}

		try {
			System.out.println("Done from validation");
			User u = userDao.get(product.getPostedBy());
			product.setUser(u);
			product = productDao.create(product);

			for (Category c: product.getCategories()) {
				c = categoryDao.get(c.getTitle());
				c.getProducts().add(product);
				categoryDao.update(c); //to maintain many to many relationship

			}

			File file;
			String path = null;

			String photolink = null;
			System.out.println("Path: " + path);
			if (product.getProductPicture() != null) {
				System.out.println("Profile Picture is not empty");

				path = "/Users/Shailly/Documents/FlipZon_FinalProject/src/main/webapp/resources/images/";
				String fileNamewithExt = System.currentTimeMillis() + product.getProductPicture().getOriginalFilename();
				file = new File(path + fileNamewithExt);
				String context = servletContext.getContextPath();
				product.getProductPicture().transferTo(file);
				//user.getProfilePicture().transferTo(file);
				photolink = "/resources/images/" + fileNamewithExt;

				System.out.println("Link to photo: " + photolink);

				product.setFilename(photolink);
				System.out.print("registerNewUser");
				Product a = productDao.create(product);

			} else {
				System.out.println("Failed to create directory!");
			}

			return new ModelAndView("product-success", "product", product);

		} catch (ProductException e) {
			System.out.println(e.getMessage());
			return new ModelAndView("error", "errorMessage", "error while login");
		}

	}

	@RequestMapping(value = "/product/list", method = RequestMethod.GET)
	public ModelAndView addCategory(HttpServletRequest request) throws Exception {

		ModelAndView mav = new ModelAndView("product-list");
		List<Product> products = productDao.list();
		Map<Integer, Integer> quantity = new LinkedHashMap<Integer, Integer> ();
		quantity.put(1, 1);
		quantity.put(2, 2);
		quantity.put(3, 3);
		quantity.put(4, 4);
		quantity.put(5, 5);
		quantity.put(6, 6);
		quantity.put(7, 7);
		quantity.put(8, 8);
		quantity.put(9, 9);
		quantity.put(10, 10);

		mav.addObject("quantity", quantity);
		mav.addObject("products", products);
		mav.addObject("cart", new Cart());
		return mav;

	}

	@RequestMapping(value = "/product/sellerlist", method = RequestMethod.GET)
	public ModelAndView displayProduct(HttpServletRequest request) throws Exception {

		try {
			HttpSession session = (HttpSession) request.getSession();
			User u = (User) session.getAttribute("user");
			long id = u.getPersonID();
			List<Product> products = productDao.list(id);
			return new ModelAndView("seller-product-list", "products", products);

		} catch (ProductException e) {
			System.out.println(e.getMessage());
			return new ModelAndView("error", "errorMessage", "error while login");
		}

	}

	@RequestMapping(value = "/product/remove/*", method = RequestMethod.GET)
	public ModelAndView removeProduct(HttpServletRequest request) throws Exception {

		String url = request.getRequestURI();
		long id = 0;
		id = Integer.parseInt((url.split("/")[4]).split("\\.")[0]);
		try {
			Product ad = productDao.deleteProduct(id);
			HttpSession session = (HttpSession) request.getSession();
			User u = (User) session.getAttribute("user");
			long ident = u.getPersonID();
			List<Product> products = productDao.list(ident);

			return new ModelAndView("seller-product-list", "products", products);
		} catch (ProductException e) {
			System.out.println(e.getMessage());
			return new ModelAndView("error", "errorMessage", "error while login");
		}
	}

	@RequestMapping(value = "/product/edit", method = RequestMethod.GET)
	public ModelAndView displayProductList(HttpServletRequest request) throws Exception {

		try {
			HttpSession session = (HttpSession) request.getSession();
			User u = (User) session.getAttribute("user");
			long id = u.getPersonID();
			List<Product> products = productDao.list(id);
			return new ModelAndView("edit-product-list", "products", products);

		} catch (ProductException e) {
			System.out.println(e.getMessage());
			return new ModelAndView("error", "errorMessage", "error while login");
		}
	}

	@RequestMapping(value = "/product/update/*", method = RequestMethod.POST)
	public ModelAndView editProduct(HttpServletRequest request) throws Exception {

		String url = request.getRequestURI();
		long id = 0;
		id = Integer.parseInt((url.split("/")[4]).split("\\.")[0]);
		try {

			Map<String, String> product = new HashMap<>();
			product.put("title", request.getParameter("title"));
			product.put("description", request.getParameter("description"));
			product.put("price", request.getParameter("price"));
			Product ad = productDao.updateProduct(id, product);
			HttpSession session = (HttpSession) request.getSession();
			User u = (User) session.getAttribute("user");
			long ident = u.getPersonID();
			List<Product> products = productDao.list(ident);

			return new ModelAndView("seller-product-list", "products", products);

			// return new ModelAndView("updateSucess");
		} catch (ProductException e) {
			System.out.println(e.getMessage());
			return new ModelAndView("error", "errorMessage", "error while login");
		}
	}

}