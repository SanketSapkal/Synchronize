package tableOps;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import adminOps.Response;
import pojos.UsersModel;
import connect.Connect;

public class Users extends Connect {
	
	private String email, fullName, mobile, x, y, dp, check, operation, token;
	private String id, code, message;
	private UsersModel um;
	private Response res = new Response ();
	
	public Response selectOp(JSONObject obj, UsersModel ubm) {
		try {
			operation = obj.getString("operation");
			operation = operation.toLowerCase();
			um = ubm;
			
			switch(operation) {
			
			case "loginandsignup" :
				System.out.println("loginandsignup op is selected..");
				LoginAndSignUp();
				break;
				
			case "updatedp" :
				System.out.println("UpdateDP op is selected..");
				UpdateDP();
				break;
				
			case "updatemobile" :
				System.out.println("UpdateMobile op is selected..");
				UpdateMobile();
				break;
				
			case "updatelocation" :
				System.out.println("UpdateLocation op is selected..");
				UpdateLocation();
				break;
				
			default:
				res.setData("0", "INVALID_OPERATION", "Error in JSON Data Construction");
				break;
			}
			
		} catch (JSONException e) {
			System.out.println("JSON Exception!");
			res.setData("0", "JSON_EXCEPTION", "JSON Data cannot be unparsed..");
			e.printStackTrace();
		}
	
		return res;
	}
	
	private void LoginAndSignUp () {
		email = um.getEmail();
		fullName = um.getFullName();
		check = null;
		System.out.println("Inside Login and Sign up method...");
		
		String sql = "SELECT * FROM users WHERE user_email = ?";
		
		getConnection();
		
		try {
			System.out.println("Creating statement.....");
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, email);
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				check = rs.getString("user_email");
			}
			
			if (check != null) {
				System.out.println("Entry Found in Database");
				res.setData("0", "LOGIN_S", "Login successfull");
			}
			
			else {
				sql = "INSERT INTO users (user_email, user_name, user_mobile, user_dp, x, y) values (?,?,null,null,null,null)";
				PreparedStatement stmt2 = connection.prepareStatement(sql);
				
				System.out.println("Statement created. Executing Query....");
				stmt2.setString(1, email);
				stmt2.setString(2,fullName);
				
				stmt2.executeUpdate();
				System.out.println("User entry added to table");
				res.setData("0", "SIGNUP_S", "SignUp successfull");
			}
			
		} catch (SQLException e) {
			res.setData("0","SQL_EXCEPTION","Couldn't create statement (SQL Exception)");
			e.printStackTrace();
		}
	}
	
	private void UpdateDP () {
		email = um.getEmail();
		dp = um.getDP();
		System.out.println("Inside updateDP");
		
		String sql = "UPDATE users SET user_dp = ? WHERE user_email = ? ";
		getConnection ();
		
		System.out.println("Creating Statement");
		
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, dp);
			stmt.setString(2, email);
			
			stmt.executeUpdate();
			res.setData("0","UPDATE_DP_S", "DP updated successfully");
		} catch (SQLException e) {
			res.setData("0","SQL_EXCEPTION","Couldn't create statement (SQL Exception)");
			e.printStackTrace();
		}
	}
	
	private void UpdateLocation () {
		email = um.getEmail();
		x = um.getX();
		y = um.getY();
		
		System.out.println("Inside update location");
		String sql = "UPDATE users SET x = ?, y=? WHERE user_email=?";
		getConnection();
		
		System.out.println("Creating Statement");
		
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, x);
			stmt.setString(2, y);
			
			stmt.executeUpdate();
			res.setData("0","UPDATE_LOCATION_S", "location updated successfully");
		} catch (SQLException e) {
			res.setData("0","SQL_EXCEPTION","Couldn't create statement (SQL Exception)");
			e.printStackTrace();
		}
	}
	
	private void UpdateMobile () {
		email = um.getEmail();
		mobile = um.getMobile();
		
		System.out.println("Inside update location");
		String sql = "UPDATE users SET user_mobile = ? WHERE user_email=?";
		getConnection();
		
		System.out.println("Creating Statement");
		
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, mobile);
			
			stmt.executeUpdate();
			res.setData("0","UPDATE_MOBILE_S", "Mobile updated successfully");
		} catch (SQLException e) {
			res.setData("0","SQL_EXCEPTION","Couldn't create statement (SQL Exception)");
			e.printStackTrace();
		}
	}
}
