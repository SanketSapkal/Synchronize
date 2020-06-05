package api;

import handler.Handler;
import handler.Response;

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


@WebServlet(asyncSupported = true, urlPatterns = { "/GetLocation" })
public class GetLocation extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Handler handler = new Handler();
	private Response res = new Response();
	private JSONObject json = new JSONObject();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		
		System.out.println("Inside get location servlet");
		
		PrintWriter out = response.getWriter();
		
		//string builder method
				StringBuilder jb = new StringBuilder();
				String line = null;
				
				BufferedReader reader = request.getReader();
				while((line = reader.readLine()) != null){
					jb.append(line);
				}
				
				String str = jb.toString();
				System.out.println(str);
		
		//String str = request.getParameter("req");
		System.out.println(str);
		
		try {
			JSONObject row = new JSONObject(str);
			JSONObject obj = new JSONObject();
			
			obj.put("table", "location");
			obj.put("operation", "get");
			obj.put("row", row);
			
		System.out.println(obj.toString());
				
			res = handler.getInfo(obj);
			
			json.put("user", res.getUser());
			json.put("code", res.getCode());
			json.put("message", res.getMessage());
			
		} catch (JSONException e) {
			System.out.println("JSON exception occured!!!!!");
			e.printStackTrace();
		}
		
		System.out.println(json.toString());
		
		out.print(json.toString());
	}

}
