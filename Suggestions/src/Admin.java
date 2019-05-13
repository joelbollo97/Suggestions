import java.util.ArrayList;
import java.util.Scanner;
import java.sql.*;


public class Admin extends User{
	private boolean admin = true;
	private ArrayList<Category> categories = new ArrayList<Category>();
	private DatabaseManager db = new DatabaseManager();

	public boolean isAdmin() {
		return admin;
	}
	
	
	public void addContent(Category cat, Subcategory sub, Content con) {
		for(int i=0; i<cat.getSubcategories().size(); i++) {
			if(sub.equals(cat.getSubcategories().get(i))){
				sub.setContents(con);
				System.out.println("Content "+con.getName()+" added to "+sub.getName());
			}
		}
	}
	
	public String handleRequest() throws SQLException {
		Scanner input = new Scanner(System.in);
		String s = new String();
		db.connect();
		Connection conn = db.getConnection();
		Statement stat = conn.createStatement();
		String request = "select * from requests";
		ResultSet rs = stat.executeQuery(request);
		while(rs.next()) {
			String name = rs.getString("request_name");
			System.out.println("Do you want to add "+name+" to categories Y/N?");
			if(input.nextLine().equals("y")) {
				String sql = "insert into categories " + " (category_name)"
	                    + " values ('"+(name)+"')";
				db.newStatement(sql);
				String update = "update requests set is_approved ="+true;
				db.newStatement(update);
				s = "Request to add "+name+" was approved";
			}
			else {
				String update = "update requests set is_approved ="+false;
				db.newStatement(update);
				s = "Request to add "+name+" was not approved";
			}
			
		}
		
		/*System.out.println("Do you want to add "+req.getCategoryName()+" to categories Y/N?");
		
		if(input.nextLine().equals("y")) {
			Category cat = new Category();
			cat.setName(req.getCategoryName());
			setCategories(cat);
			s = "The request to add "+req.getCategoryName()+" has been approved";
			System.out.println("The request to add "+req.getCategoryName()+" has been approved");
			req.setApproved(true);

		}
		else if(input.nextLine().equals("n")) {
			s = "The request to add "+req.getCategoryName()+" has been disapproved";
			System.out.println("The request to add "+req.getCategoryName()+" has been disapproved");
			req.setApproved(false);
		}
		else {
			System.out.println("You have sent no requests");
		}*/
		
		
		return s;
		
	}
	
	public void removeCategories(int nr) throws SQLException {
			db.connect();
			Connection conn = db.getConnection();
			Statement stat = conn.createStatement();
			int subnr = 0;
			String select = "select s.subcategory_id from subcategories s inner join categories c on "
					+ "s.category_id = c.category_id where s.category_id="+nr;
			ResultSet rs = stat.executeQuery(select);
			while(rs.next()) {
				subnr = rs.getInt("s.subcategory_id");
				System.out.println(subnr);
			}
			
			String rs1 = "delete from categories where category_id="+nr;
			int rows1 = stat.executeUpdate(rs1);
			
			String rs2 = "delete s.* from subcategories s inner join categories c "
					+ "on s.category_id = c.category_id where s.category_id="+nr;
			int rows2 = stat.executeUpdate(rs2);
			
			String rs3 = "delete from content where subcategory_id = "+subnr;
			int rows3 = stat.executeUpdate(rs3);
			
			System.out.println(rows2);
			System.out.println(rows3);
			System.out.println(rows1);
			
	}
	
	public void removeSubcategories(int nr) throws SQLException {
		db.connect();
		Connection conn = db.getConnection();
		Statement stat = conn.createStatement();
		String rs = "delete from subcategories where subcategory_id="+nr;
		int rows = stat.executeUpdate(rs);
		String rs3 = "delete from content where subcategory_id = "+nr;
		int rows3 = stat.executeUpdate(rs3);
		System.out.println(rows);
	}
	
	public void printCategories() throws SQLException {
		db.connect();
		Connection conn = db.getConnection();
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery("select * from categories");
		System.out.println("+ Categories \r\n");
		while(rs.next()) {
			System.out.println(rs.getInt("category_id")+ "."+rs.getString("category_name"));
		}
	
	}

	public void printSubCategories() throws SQLException{
		db.connect();
		Connection conn = db.getConnection();
		Statement stat = conn.createStatement();
		ResultSet rs2 = stat.executeQuery("select * from subcategories");
		System.out.println("- Subcategories \r\n");
		while(rs2.next()) {
			System.out.println(rs2.getInt("subcategory_id")+ "."+rs2.getString("subcategory_name"));
		}
	}
	
	
	public Category searchCategories(int nr) throws SQLException {
		int selected_id = 0;
		String selected_name = new String();
		Category cat = new Category();
		db.connect();
		Connection conn = db.getConnection();
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery("select * from categories where category_id = "+nr);
		while(rs.next()) {
			selected_id = rs.getInt("category_id");
			selected_name = rs.getString("category_name");
		}
		
		if(selected_id == nr) {
			cat.setName(selected_name);
		}
		 
		return cat;
	}
	
	
}
