import java.sql.*;
import java.util.ArrayList;


public class User {
	private String username;
	private String password;
	private DatabaseManager db = new DatabaseManager();
	
	
	public User() {
		username = "";
		password = "";
	}
	public User(String name, String pass) {
		username = name;
		password = pass;
	}
	
	public int getUserId(String user) throws SQLException {
		db.connect();
		int nr=0;
		Connection conn = db.getConnection();
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery("select user_id from user where user_name = '"+user+"'");
		while(rs.next()) {
			nr = rs.getInt("user_id");
		}
		return nr;
	}
	
	public void setUsername(String name) {
		username = name;
	}
	public String getUsername() {
		return username;
	}
	
	public void setPassword(String pass) {
		password = pass;
	}
	public String getPassword() {
		return password;
	}
	
	public void setFavoriteCategories(int user_id, int nr) throws SQLException {
		db.connect();
		Connection conn = db.getConnection();
		Statement stat = conn.createStatement();
		String sql = "insert into favorite_categories " + " (user_id, category_id)"
                + " values ("+user_id+", "+nr+")";
		int rows = stat.executeUpdate(sql);
		System.out.println(rows);
	}
	
	public void setFavoriteSubcategories(int user_id, int nr) throws SQLException {
		db.connect();
		Connection conn = db.getConnection();
		Statement stat = conn.createStatement();
		String sql = "insert into favorite_subcategories " + " (user_id, subcategory_id)"
                + " values ("+user_id+", "+nr+")";
		int rows = stat.executeUpdate(sql);
		System.out.println(rows);
	}

	public void setLikedContent(int nr) throws SQLException {
		db.connect();
		int like = 0;
		Connection conn = db.getConnection();
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery("select * from content where content_id="+nr);
		while(rs.next()) {
			System.out.println("You liked: "+rs.getInt("content_id")+"."+rs.getString("content_name"));
			like = rs.getInt("likes");
			like++;
			PreparedStatement prep = conn.prepareStatement("update content set likes = ? where content_id = ?");
			prep.setInt(1, like);
		    prep.setInt(2, nr);
			
		}
	}
	


	public void printFavoriteCategories(int user_id) throws SQLException {
		db.connect();
		Connection conn = db.getConnection();
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery("select c.category_id, c.category_name from categories c inner join favorite_categories f"
				+ " on c.category_id = f.category_id where f.user_id = "+user_id);
		while(rs.next()) {
			System.out.println(rs.getInt("category_id")+ " "+rs.getString("category_name"));
		}
	}
	
	public int searchFavoriteCategories(int user_id ,int nr) throws SQLException {
		int selected_id = 0;
		String selected_name = new String();
		Category cat = new Category();
		db.connect();
		Connection conn = db.getConnection();
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery("select c.category_id, c.category_name from categories c inner join favorite_categories f on c.category_id = f.category_id where f.category_id = "+nr+" and f.user_id= "+user_id);
		while(rs.next()) {
			selected_id = rs.getInt("category_id");
			selected_name = rs.getString("category_name");
		}
		
		cat.setName(selected_name);
		 
		return selected_id;
		
		/*int position = 0;
		for(int i=0; i<favorite_categories.size(); i++) {
			if(nr==favorite_categories.get(i).getId())
				position = i;
		}
		return favorite_categories.get(position);*/
	}
	
	public void printFavoriteSubcategories(int user_id) throws SQLException {
		db.connect();
		Connection conn = db.getConnection();
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery("select subcategory_name from subcategories s"
				+ " inner join favorite_subcategories f "
				+ "on s.subcategory_id = f.subcategory_id where f.user_id = "+user_id);
		while(rs.next()) {
			System.out.println(rs.getString("subcategory_name"));
		}
	}
	
	public void removeFavoriteCategories(int nr) throws SQLException {
		db.connect();
		Connection conn = db.getConnection();
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery("delete from favorite_categories where id="+nr);
		while(rs.next()) {
			System.out.println("Deleted!");
		}
	}
	
	public void removeFavoriteSubcategories(int nr) throws SQLException {
		db.connect();
		Connection conn = db.getConnection();
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery("delete from favorite_subcategories where id="+nr);
		while(rs.next()) {
			System.out.println("Deleted!");
		}
	}
	
	public void printAllContent() throws SQLException {
		db.connect();
		Connection conn = db.getConnection();
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery("select * from content order by likes desc");
		System.out.println("Content \r\n");
		while(rs.next()) {
			System.out.println(rs.getInt("content_id")+" "+rs.getString("content_name"));
		}
	}
	
	public int searchContent(int nr) throws SQLException {
		db.connect();
		int selected = 0;
		Connection conn = db.getConnection();
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery("select * from content where content_id="+nr);
		while(rs.next()) {
			selected = rs.getInt("content_id");
		}
		return selected;
	}
	
	
}
