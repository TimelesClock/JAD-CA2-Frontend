package servlets.customer;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Card;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.PaymentSourceCollection;
import com.stripe.model.StripeObject;
import com.stripe.model.Token;

import models.BookDAO;
import models.Cart;
import models.CartDAO;
import models.Order;
import models.OrderDAO;
import models.OrderItem;
import models.User;
import models.UserDAO;
import stripe.StripeConnection;

/**
 * Servlet implementation class CustomerCheckout
 */
@WebServlet("/customer/cart/stripe")
public class CustomerStripe extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CustomerStripe() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("You've reached Stripe!");
//		String cardNumber = (String) request.getAttribute("cardNumber");
//		String expMonth = (String) request.getAttribute("expMonth");
//		String expYear = (String) request.getAttribute("expYear");
//		String cvv = (String) request.getAttribute("cvv");
		String cardNumber = request.getParameter("cardNumber");
		String expMonth = request.getParameter("expMonth");
		String expYear = request.getParameter("expYear");
		String cvv = request.getParameter("cvv");
		System.out.println(cardNumber);
		System.out.println(expMonth);
		System.out.println(expYear);
		System.out.println(cvv);
		HttpSession session = request.getSession(false);
		int userid = -1;
		try {
			userid = Integer.parseInt((String) session.getAttribute("userid"));
		} catch (NumberFormatException e) {
        	e.printStackTrace();
            System.out.println("error");
            request.setAttribute("err", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }
		
		ArrayList<Cart> cartItems = new ArrayList<Cart>();
		int maxQuantity = -1;
		User user = new User();
		double subtotal = -1;
		int total = -1;
		ArrayList<OrderItem> orderItems = new ArrayList<>();
		int rowsAffected = -1;
		try {			
			CartDAO cartDb = new CartDAO();
        	cartItems = cartDb.getCartItems(userid);
        	
        	if (cartItems.size() == 0) {
    			request.setAttribute("err", "Cart is empty.");
    			response.sendRedirect(request.getContextPath() + "/customer/cart?p=myCart");
    			return;
    		} else {
				BookDAO bookDb = new BookDAO();
    			for (Cart item : cartItems) {
    				maxQuantity = bookDb.getQuantityOfBook(item.getBookId());
    				if (item.getQuantity() > maxQuantity) {
    					response.sendRedirect(request.getContextPath() + "/customer/cart?p=myCart&err=" + item.getTitle() + "Does%20Not%20Have%20Enough%20Stock");
    	    			return;
    				}
    			}
    		}
        	
        	UserDAO userDb = new UserDAO();
			user = userDb.getUserById(userid);
			
			if (user.getAddressId() == null) {
				response.sendRedirect(request.getContextPath() + "/customer/address");
				return;
			}
			
			subtotal = cartDb.getSubtotal(userid);
            
            if (subtotal == -1) {
    			request.setAttribute("err", "Subtotal failed to be retrieved.");
    		} else {
    			total = (int) (1.08 * 100 * subtotal);
    		}
			
            String formattedTotal = Integer.toString(total);
            System.out.println("formattedTotal: " + formattedTotal);
			Stripe.apiKey = StripeConnection.getStripeApiKey();
			
			Customer customer = new Customer();
			String customerId = user.getStripeCustomerId();
			
			if (user.getStripeCustomerId() == null) {
				HashMap<String, Object> customerParam = new HashMap<String, Object>();
				customerParam.put("userid", user.getUserId());
				customerParam.put("email", user.getEmail());
				
				Customer newCustomer = Customer.create(customerParam);
				customerId = newCustomer.getId();
				
				customer = Customer.retrieve(customerId);
			} else {
				customer = Customer.retrieve(user.getStripeCustomerId());
			}
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			System.out.println(gson.toJson(customer));
			
			PaymentSourceCollection allCardDetails = customer.getSources();
			
			HashMap<String, Object> cardParam = new HashMap<String, Object>();
			cardParam.put("cardNumber", cardNumber);
			cardParam.put("expMonth", expMonth);
			cardParam.put("expYear", expYear);
			cardParam.put("cvv", cvv);
			
			HashMap<String, Object> tokenParam = new HashMap<String, Object>();
			tokenParam.put("card", cardParam);
			
			Token token = Token.create(cardParam);
			String cardId = "";
			Boolean cardNotExist = true;
			for (int i = 0; i < allCardDetails.getData().size(); i++) {
				String cardDetails = ((StripeObject) allCardDetails.getData().get(i)).toJson();
				Card card = gson.fromJson(cardDetails, Card.class);
				if (card.getFingerprint().equals(token.getCard().getFingerprint())) {
					cardNotExist = false;
					cardId = allCardDetails.getData().get(i).getId();
				}
			}
			if (cardNotExist) {
				HashMap<String, Object> source = new HashMap<String, Object>();
				source.put("source", token.getId());
				
				cardId = customer.getSources().create(source).getId();
				System.out.println("Card created");
			} else {
				System.out.println("Card already exists");
			}
			
			HashMap<String, Object> chargeParam = new HashMap<String, Object>();
			chargeParam.put("amount", total);
			chargeParam.put("currency", "usd");
			chargeParam.put("customerId", customerId);
			chargeParam.put("source", cardId);
			
			Charge charge = Charge.create(chargeParam);
			System.out.println(gson.toJson(charge));
			
			for (Cart item : cartItems) {
				OrderItem orderItem = new OrderItem();
				orderItem.setBookId(item.getBookId());
				orderItem.setQuantity(item.getQuantity());
				orderItems.add(orderItem);
			}
			
			Order order = new Order();
			order.setOrderItems(orderItems);
			
			Date currentDate = new Date(System.currentTimeMillis());
			order.setOrderDate(currentDate);
			
			order.setSubtotal(subtotal);
			
			OrderDAO orderDb = new OrderDAO();
			rowsAffected = orderDb.insertOrder(order);
			
			rowsAffected = cartDb.deleteAllFromCart(userid);
			if (rowsAffected != cartItems.size()) {
				response.sendRedirect(request.getContextPath() + "/customer/cart?p=myCart&err=Something%20Went%20Wrong");
				return;
			} else {
				response.sendRedirect(request.getContextPath() + "/customer/cart?p=myCart&success=Checked%20Out%20Successfully");
				return;
			}
		} catch (StripeException e) {
			e.printStackTrace();
			request.setAttribute("err", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("err", e.getMessage());
		}
	}

}
