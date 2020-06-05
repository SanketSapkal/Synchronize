package tableOps;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.json.JSONException;
import org.json.JSONObject;

import handler.Response;
import connect.Connect;

public class Location extends Connect{
	private String user, latitude, longitude, operation, timestamp;
	private Response res = new Response();
	private JSONObject row = new JSONObject();
	
	public Response selectOp(JSONObject obj) {
		try {
			operation = obj.getString("operation");
			row = obj.getJSONObject("row");
			operation = operation.toLowerCase();
			switch(operation) {
			case "send":
				sendLocation();
				break;
				
			case "get":
				getLocation();
				break;
				
			default :
				res.setData("0", "INVALID_OPERATION", "Invalid Operation Selected");
				break;
				
			}
		} catch (JSONException e) {
			res.setData("0", "JSON_EXCEPTION", "JSON exception occured");
			e.printStackTrace();
		}
		
		return res;
	}
	
	private void sendLocation() {
		try {
			user = row.getString("user");
			latitude = row.getString("latitude");
			longitude = row.getString("longitude");
			
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			timestamp = sdf.format(cal.getTime());
			System.out.println(timestamp);
			
			String sql = "insert into location (user, latitude, longitude, timestamp) values (?,?,?,?)";
			getConnection();
			
			try {
				PreparedStatement stmt = connection.prepareStatement(sql);
				
				stmt.setString(1, user);
				stmt.setString(2, latitude);
				stmt.setString(3, longitude);
				stmt.setString(4, timestamp);
				
				stmt.executeUpdate();
				System.out.println("Location added");
				res.setData(user, "SEND_S","Location sent to server successfully!!");
				
			} catch (SQLException e) {
				System.out.println("Couldn't create statement...");
				res.setData("0","SQL_EXCEPTION","Couldn't create statement (SQL Exception)");
				e.printStackTrace();
			}
			
			
		} catch (JSONException e) {
			res.setData("0", "JSON_EXCEPTION", "JSON exception occured");
			e.printStackTrace();
		}
	}
	
	private void getLocation () {
		String check = null,message = null,code=null,id=null;
		
		try {
			user = row.getString("user");
			
			String sql = "SELECT * FROM location WHERE user = ? ORDER BY timestamp DESC LIMIT 1";
			getConnection();
			try {
				PreparedStatement stmt = connection.prepareStatement(sql);
				
				stmt.setString(1, user);
				
				ResultSet rs = stmt.executeQuery();
				
				while(rs.next()) {
					JSONObject json = new JSONObject();
					json.put("user", rs.getString("user"));
					json.put("latitude", rs.getString("latitude"));
					json.put("longitude", rs.getString("longitude"));
					json.put("timestamp", rs.getString("timestamp"));
					message = json.toString();
					
					check = rs.getString("user");
					id = check;
				}
				
				if(check != null) {
					code = "GET_S";
					System.out.println("Getting location!!!");
				}
				
				else {
					message = "USER NOT FOUND!!!";
					code = "USER_NOT_FOUND..";
					System.out.println("User not found!!!");
				}
				
				res.setData(id, code, message);
			} catch (SQLException e) {
				res.setData("0", "SQL_EXCEPTION", "SQL Exception occured");
				e.printStackTrace();
			}
		} catch (JSONException e) {
			res.setData("0", "JSON_EXCEPTION", "JSON exception occured");
			e.printStackTrace();
		}
	}
}
