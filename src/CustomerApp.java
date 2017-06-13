
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.PreparedStatement;

public class CustomerApp {
		
	static Connection con = null;
	static Statement stmt = null;
	static ResultSet rs = null;
	static PreparedStatement pstmt;
	static String fName;
	static String lName;
	boolean update = false;

	public static void main(String[] args) {

		Scanner scn = new Scanner(System.in);


		System.out.println("Please enter the first name of the person you want to search");
		fName= scn.nextLine();
		System.out.println("Please enter the last name of the person you want to search");
		lName= scn.nextLine();
		String result = find();
		System.out.println(result);
	
		try{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/customers?"
					+ "user=root&password=password");
			stmt = con.createStatement();
			rs = stmt.executeQuery(result);

			while(rs.next()){

				System.out.println(rs.getString("Title") + " " + rs.getString("FirstName") + " " + rs.getString("LastName"));
				System.out.println(rs.getString("StreetAddress"));
				System.out.println(rs.getString("City") + ", " + rs.getString("state") + " " + rs.getString("ZipCode"));
				System.out.println(rs.getString("EmailAddress"));
				System.out.println(rs.getString("Position") + " at " + rs.getString("Company"));

				System.out.println();
			}


		}catch (SQLException e) {
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("Press (1) to search for another customer or press (2) to Edit the customer's address.");
		int input = scn.nextInt();
		
		if(input == 2){
			System.out.println("what do you want to update? customer's Street address, City, State or ZIP code");
			scn.nextLine();
			String field = scn.nextLine();

			if(field.equalsIgnoreCase("city")) {
				try {
					updateCity(scn);
				} catch (SQLException e) {
					e.printStackTrace();
				}

			} 
			else if(field.equalsIgnoreCase("State")){
				try {
					updateState(scn);
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
			}
			else if(field.equalsIgnoreCase("Street")){
				try {
					updateStreet(scn);
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
			}
			else if(field.equalsIgnoreCase("ZIP")) {
				try {
					updateZip(scn);
				} catch (SQLException e) {
					
					e.printStackTrace();
				}

			}
		}


	}


	public static String find(){

		String search = "Select * from customers inner join Company c on customers.companyId=c.companyId "
				+ " and customers.firstName = '" + fName + "' and customers.lastName = '" + lName + "'"
				+ " inner join Position p on customers.positionId = p.positionId "
				+ " inner join State s on customers.stateId = s.stateId "
				+ " inner join City on customers.cityId = City.cityId;";

		return search;

	}
	

	public static void updateCity(Scanner s) throws SQLException{
		int cityId = 0;
		System.out.println("please enter the customer's full name that you want to make change");
		String fullName = s.nextLine();
		System.out.println("enter the new city ");
		String newCity = s.nextLine();
		ResultSet rs =null;
		rs = stmt.executeQuery("Select customers.cityID from customers where customers.fullName = '" + fullName + "';");
		if(rs.next()){
		cityId = rs.getInt("cityID");
		}
		String updateCity = "update City set city = ? where cityID = ?;";
		pstmt = con.prepareStatement(updateCity);
		pstmt.setString(1, newCity);
		pstmt.setInt(2, cityId);
		pstmt.executeUpdate();

	}

	public static void updateState(Scanner s) throws SQLException{

		int stateId = 0;
		System.out.println("please enter the customer's full name that you want to make change");
		String fullName = s.nextLine();
		System.out.println("enter the new state");
		String newState = s.nextLine();
		ResultSet rs = stmt.executeQuery("Select stateID from State where state = '" + newState + "';");
		if(rs.next()){
		stateId = rs.getInt("StateID");
		System.out.println(stateId);
		
		}
		String updateState = "update customers set stateId = ? where customers.fullName = ? ;";
		pstmt = con.prepareStatement(updateState);
		pstmt.setInt(1, stateId);
		pstmt.setString(2,fullName);
		pstmt.executeUpdate();
		
		System.out.println("Successfully updated.");

	}
	public static void updateStreet(Scanner s) throws SQLException{
		
		System.out.println("please enter the customer's full name that you want to make change");
		String fullName = s.nextLine();
		System.out.println("enter the new street");
		String newSt = s.nextLine();
		String updateStreet = "Update customers set StreetAddress = ? where customers.fullName =  ? ;";
		pstmt = con.prepareStatement(updateStreet);
		pstmt.setString(1, newSt);
		pstmt.setString(2, fullName);
		pstmt.executeUpdate();
		
		System.out.println("Successfully updated.");
		
	}
		
	public static void updateZip(Scanner s) throws SQLException{

		System.out.println("please enter the customer's full name that you want to make change");
		String fullName = s.nextLine();
		System.out.println("enter the new zip code");
		String newZip = s.nextLine();
		String updateStreet = "Update customers set ZipCode = ? where customers.fullName =  ? ;";
		pstmt = con.prepareStatement(updateStreet);
		pstmt.setString(1, newZip);
		pstmt.setString(2, fullName);
		pstmt.executeUpdate();
		
		System.out.println("Successfully updated.");
		

	}

}




