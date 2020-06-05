package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import adminOps.AdminOpsHandler;
import adminOps.Response;

/**
 * Servlet implementation class LoginAndSignUpAPI
 */
@WebServlet("/LoginAndSignUpAPI")
public class LoginAndSignUpAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AdminOpsHandler aoh = new AdminOpsHandler();
	private Response res = new Response();
	JSONObject json = new JSONObject ();
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		
		System.out.println("Inside POST Method");
		
		PrintWriter out = response.getWriter();
		
		//String Builder method
		StringBuilder jb = new StringBuilder ();
		String line = null;
		
		BufferedReader reader = request.getReader();
		while((line = reader.readLine()) != null){
			jb.append(line);
		}
		
		String str = jb.toString();
		//End of string buider method
		//String str = request.getParameter("req");
		System.out.println(str);
		
		try {
			JSONObject obj = new JSONObject();
			JSONObject row = new JSONObject(str);
			row.put("mobile", "0");
			row.put("dp", "0");
			row.put("x", "0");
			row.put("y", "0");
			
			obj.put("table", "users");
			obj.put("operation", "loginandsignup");
			obj.put("row", row);
			
			res = aoh.getInfo("users",obj);
			
			json.put("code", res.getCode());
			json.put("id", res.getID());
			json.put("message", res.getMessage());
			out.print(json);
			
		} catch (JSONException e) {
			e.printStackTrace();
			try {
				json.put("code", "JSON_EXCEPTION");
				json.put("id", "0");
				json.put("message", "JSON Exception Occured..");
				out.print(json);
			} catch (JSONException e1) {
				out.print(json);
				e1.printStackTrace();
			}
		}
	}

}
