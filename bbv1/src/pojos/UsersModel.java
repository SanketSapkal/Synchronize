package pojos;

import org.json.JSONException;
import org.json.JSONObject;

public class UsersModel {
	private String email, fullName, mobile, x, y, dp;
	private JSONObject obj;
	
	public void getData (JSONObject ob) {
		obj = ob;
		extractData();
	}
	
	private void extractData () {
		try {
			email = obj.getString("email");
			fullName = obj.getString("fullName");
			mobile = obj.getString("mobile");
			dp = obj.getString("dp");
			x = obj.getString("x");
			y = obj.getString("y");
		} catch (JSONException e) {
			System.out.println("Couldn't parse json");
			e.printStackTrace();
		}
	}
	
	public String getEmail () {
		return this.email;
	}
	
	public String getFullName () {
		return this.fullName;
	}
	
	public String getMobile () {
		return this.mobile;
	}
	
	public String getX () {
		return this.x;
	}
	
	public String getY () {
		return this.y;
	}
	
	public String getDP () {
		return this.dp;
	}
}
