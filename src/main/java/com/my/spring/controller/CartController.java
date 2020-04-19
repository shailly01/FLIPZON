package com.my.spring.controller;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.my.spring.dao.ProductDAO;
import com.my.spring.dao.CategoryDAO;
import com.my.spring.dao.DAO;
import com.my.spring.dao.UserDAO;
import com.my.spring.dao.CartDAO;
import com.my.spring.exception.ProductException;
import com.my.spring.exception.CartException;
import com.my.spring.pojo.Product;
import com.my.spring.pojo.Cart;
import com.my.spring.pojo.User;
import java.util.HashMap;
import java.util.Map;

@Controller
//@RequestMapping("/cart/*")
public class CartController extends DAO {

	@Autowired
	@Qualifier("productDao")
	ProductDAO productDao;

	@Autowired
	@Qualifier("userDao")
	UserDAO userDao;

	@Autowired
	@Qualifier("cartDao")
	CartDAO cartDao;

	@Autowired
	ServletContext servletContext;

	@RequestMapping(value = "/cart/insert/*", method = RequestMethod.POST)
	public ModelAndView addCart(@ModelAttribute("cart") Cart cart, BindingResult result, HttpServletRequest request) throws Exception {
		String url = request.getRequestURI();
		long id = 0;
		id = Integer.parseInt((url.split("/")[4]).split("\\.")[0]);

		HttpSession session = (HttpSession) request.getSession();
		User u = (User) session.getAttribute("user");
		int quant = cart.getQuantity();
		cart = cartDao.insert(id, u, quant);
		return new ModelAndView("cart-success", "cart", cart);
	}

	@RequestMapping(value = "/cart/customerlist", method = RequestMethod.GET)
	public ModelAndView displayProduct(HttpServletRequest request) throws Exception {

		try {
			HttpSession session = (HttpSession) request.getSession();
			User u = (User) session.getAttribute("user");
			long id = u.getPersonID();
			List<Cart> carts = cartDao.list(id);
			int len = carts.size();
			return new ModelAndView("user-cart", "carts", carts);

		} catch (CartException e) {
			System.out.println(e.getMessage());
			return new ModelAndView("error", "errorMessage", "error while login");
		}
	}

	@RequestMapping(value = "/cart/remove/*", method = RequestMethod.GET)
	public ModelAndView removeProduct(HttpServletRequest request) throws Exception {

		String url = request.getRequestURI();
		long id = 0;
		id = Integer.parseInt((url.split("/")[4]).split("\\.")[0]);
		try {
			Cart cart = cartDao.deleteCart(id);
			HttpSession session = (HttpSession) request.getSession();
			User u = (User) session.getAttribute("user");
			long ident = u.getPersonID();
			List<Cart> carts = cartDao.list(ident);

			return new ModelAndView("user-cart", "carts", carts);
		} catch (CartException e) {
			System.out.println(e.getMessage());
			return new ModelAndView("error", "errorMessage", "error while login");
		}
	}

	@RequestMapping(value = "/cart/edit", method = RequestMethod.GET)
	public ModelAndView displayQuantity(HttpServletRequest request) throws Exception {

		try {
			HttpSession session = (HttpSession) request.getSession();
			User u = (User) session.getAttribute("user");
			long id = u.getPersonID();
			List<Cart> carts = cartDao.list(id);
			int len = carts.size();
			return new ModelAndView("edit-user-cart", "carts", carts);

		} catch (CartException e) {
			System.out.println(e.getMessage());
			return new ModelAndView("error", "errorMessage", "error while login");
		}
	}

	@RequestMapping(value = "/cart/update/*", method = RequestMethod.POST)
	public ModelAndView editquantity(HttpServletRequest request) throws Exception {

		String url = request.getRequestURI();
		long id = 0;
		id = Integer.parseInt((url.split("/")[4]).split("\\.")[0]);

		try {
                    int q = Integer.parseInt(request.getParameter("quantity"));
                    if(q<0 || q==0){
                       HttpSession session = (HttpSession) request.getSession();
			User u = (User) session.getAttribute("user");
			id = u.getPersonID();
			List<Cart> carts = cartDao.list(id);
			int len = carts.size();
			return new ModelAndView("edit-user-cart", "carts", carts); 
                    }
                    else{
			Map<String, String> quantity = new HashMap<>();
			quantity.put("quantity", request.getParameter("quantity"));
			System.out.println("Hash Map is  " + quantity);
			Cart cart = cartDao.updateQuantity(id, quantity);
			HttpSession session = (HttpSession) request.getSession();
			User u = (User) session.getAttribute("user");
			long ident = u.getPersonID();
			List<Cart> carts = cartDao.list(ident);
			return new ModelAndView("user-cart", "carts", carts);
                    }

		} catch (CartException e) {
			System.out.println(e.getMessage());
			return new ModelAndView("error", "errorMessage", "error while login");
		}
	}

}