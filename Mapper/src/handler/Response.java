package handler;

public class Response {
	private String User, Code, Message;
	
	public void setData(String user, String code, String message) {
		this.User = user;
		this.Code = code;
		this.Message = message;
	}
	
	public String getUser () {
		return this.User;
	}
	
	public String getCode () {
		return this.Code;
	}
	
	public String getMessage () {
		return this.Message;
	}
}
