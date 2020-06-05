package handler;

import org.json.JSONException;
import org.json.JSONObject;

import tableOps.Location;

public class Handler {
	
	private String table;
	private Response res = new Response();
	
	public Response getInfo (JSONObject obj) {
		
		try {
			table = obj.getString("table");
			selectTable(obj);
			
		} catch (JSONException e) {
			res.setData("0", "JSON_EXCEPTION", "JSON Exception occured....");
			e.printStackTrace();
		}
		
		return res;
	}
	
	private void selectTable (JSONObject obj) {
		table = table.toLowerCase();
		
		switch (table) {
		
		case "location" :
			Location location = new Location();
			res = location.selectOp(obj);
			break;
			
		default:
			res.setData("0", "INVALID_TABLE", "Invalid table name");
			break;
		}
	}

}
