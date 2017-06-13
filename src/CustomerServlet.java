

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


@WebServlet("/CustomerServlet")
public class CustomerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public CustomerServlet() {
        super();
       
    }
    Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;
	PreparedStatement pstmt;
    String fName ;
    String lName;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String nextURL = "/output.jsp";
		fName = request.getParameter("firstName");
		lName = request.getParameter("lastName");
		Customer cust = search(fName, lName);
	
		request.setAttribute("customer",cust);
		
		getServletContext().getRequestDispatcher(nextURL).forward(request,response);
	}

	
	public Customer search(String fName, String lName){
		
		
		Customer customer = new Customer();
		
		String search = "Select * from customers inner join Company c on customers.companyId=c.companyId "
				+ " and customers.FirstName = '" + fName + "' and customers.LastName = '" + lName + "'";
				
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/Customers?"
					+ "user=root&password=password");
			stmt = con.createStatement();
			rs = stmt.executeQuery(search);

		while(rs.next()){
			
				customer.setCustomerId(rs.getInt("customerId"));
				customer.setTitle(rs.getString("Title"));      
				customer.setFirstName(rs.getString("FirstName")); 
				customer.setLastName(rs.getString("LastName"));
				customer.setStreetAddress(rs.getString("StreetAddress"));
				customer.setCity(rs.getString("City"));
				customer.setState(rs.getString("state")); customer.setZipcode(rs.getString("ZipCode")); 
				customer.setEmailAddress(rs.getString("EmailAddress"));
				customer.setPosition(rs.getString("Position") );
				customer.setCompany(rs.getString("Company"));

				System.out.println();
		}
		}catch (SQLException e) {
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return customer;
	}
	
	
}
