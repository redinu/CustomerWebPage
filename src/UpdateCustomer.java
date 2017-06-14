

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/UpdateCustomer")
public class UpdateCustomer extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
    public UpdateCustomer() {
        super();
        
    }
    Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;
	PreparedStatement pstmt;
    String fName ;
    String lName;
    String city;
    String state;
    String streetAddress;
    String zipCode;

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String nextURL = "/updated.jsp";
		fName = request.getParameter("firstName");
		city = request.getParameter("city");
		state = request.getParameter("state");
		lName = request.getParameter("lastName");
		streetAddress= request.getParameter("streetAddress");
		zipCode= request.getParameter("zipCode");
		
		Customer cust = update(fName, lName);
		HttpSession session = request.getSession();
		session.setAttribute("customer",cust);
		
		getServletContext().getRequestDispatcher(nextURL).forward(request,response);
	}
	
	public Customer update(String fName,String lName){
		
		String search = "Update customers set city = ? , state = ?, streetAddress=?, ZipCode = ?  where customers.FirstName = '" + fName + "' and customers.LastName = '" + lName + "'";
		Customer customer = new Customer();
		try{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/Customers?"
					+ "user=root&password=password");
			pstmt = con.prepareStatement(search);
			pstmt.setString(1, city);
			pstmt.setString(2, state);
			pstmt.setString(3, streetAddress);
			pstmt.setString(4, zipCode);
			pstmt.executeUpdate();
			
			customer.setFirstName(fName); 
			customer.setLastName(lName);
			customer.setStreetAddress(streetAddress);
			customer.setCity(city);
			customer.setState(state); 
			customer.setZipcode(zipCode); 
		
		}catch (SQLException e) {
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		
		return customer;
		
	}
}
