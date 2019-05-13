
public class Request {
	private String categoryName;
	private User user;
	private boolean isApproved;
	
	public Request() {
		categoryName = "";
		user = null;
	}
	public Request(String cat, User name) {
		categoryName = cat;
		user = name;
	}
	
	public void setCategoryName(String cat) {
		categoryName = cat;
	}
	public String getCategoryName() {
		return categoryName;
	}
	
	public void setUsername(User name) {
		user = name;
	}
	public User getUsername() {
		return user;
	}
	
	public void setApproved(boolean bool) {
		isApproved = bool;
	}
	
	public boolean getApproved() {
		return isApproved;
	}
}
