package adminOps;

import org.json.JSONException;
import org.json.JSONObject;

import pojos.UsersModel;
import tableOps.Users;


public class AdminOpsHandler {
	
	private String table, operation;
	private JSONObject jsonobj1, jsonobj2;
	
	private Response res = new Response ();
	
	public Response getInfo (String Table, JSONObject obj) {
		table = Table;
		
		try {
			jsonobj2 = obj;
			jsonobj1 = obj.getJSONObject("row");
			operation = obj.getString("operation");
			System.out.println(operation);
			
			selectTable();
			
		} catch (JSONException e) {
			System.out.println("Couldnt parse operation from json");
			e.printStackTrace();
		}
		return res;
	}
	
	private void selectTable () {
		table = table.toLowerCase();
		System.out.println("Inside switch case.");
		switch(table){
		
		case "users" :
			Users user = new Users();
			UsersModel um = new UsersModel();
			
			System.out.println("Users table is selected..");
			um.getData(jsonobj1);
			res = user.selectOp(jsonobj2,um);
			break;
			
		default:
			System.out.println("Table not present..");
			res.setData("999", "0", "Invalid table name!!!");
			break;
		}
	}
	
}
