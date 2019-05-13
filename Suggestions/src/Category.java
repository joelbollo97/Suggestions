import java.util.ArrayList;
import java.sql.*;

public class Category {
	private int id;
	private String name;
	private ArrayList<Subcategory> subcategories = new ArrayList<Subcategory>();
	private DatabaseManager db = new DatabaseManager();
	
	public Category() {
		name = "";
		id = 0;
	}
	public Category(String cat,int nr) {
		name = cat;
		id = nr;
	}
	
	public void setId(int nr) {
		id = nr;
	}
	public int getId(int cat) throws SQLException {
		db.connect();
		int nr=0;
		Connection conn = db.getConnection();
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery("select category_id from category where category_name = '"+cat+"'");
		while(rs.next()) {
			nr = rs.getInt("category_id");
		}
		return nr;
	}
	
	public void setName(String cat) {
		name = cat;
	}
	public String getName() {
		return name;
	}
	
	public void setSubcategories(Subcategory sub) {
		subcategories.add(sub);
	}
	public ArrayList<Subcategory> getSubcategories(){
		return subcategories;
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
		System.out.println(rows3);
	}
	
	public void printSubcategories(int nr) throws SQLException {
		db.connect();
		Connection conn = db.getConnection();
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery("select * from subcategories where category_id ="+nr);
		while(rs.next()) {
			System.out.println(rs.getInt("subcategory_id")+ " "+rs.getString("subcategory_name"));
		}
	}
	
	public int searchSubcategories(int nr) throws SQLException {
		int selected_id = 0;
		String selected_name = new String();
		Subcategory sub = new Subcategory();
		db.connect();
		Connection conn = db.getConnection();
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery("select * from subcategories s inner join categories c "
				+ "on s.category_id = c.category_id where subcategory_id = "+nr);
		while(rs.next()) {
			selected_id = rs.getInt("subcategory_id");
			selected_name = rs.getString("subcategory_name");
		}
		
		if(selected_id == nr) {
			sub.setName(selected_name);
		}
		 
		return selected_id;
	}
}
