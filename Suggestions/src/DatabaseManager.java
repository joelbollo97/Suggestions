import java.sql.*;

public class DatabaseManager {

	Connection conn = null;
	Statement stat = null;
	
	public Connection connect() throws SQLException {
		
		try {
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/suggestions", 
					"root", 
					"root");
			
			stat = conn.createStatement();
			
			ResultSet res = stat.executeQuery("SELECT table_name "
					+ "FROM information_schema.tables "
					+ "WHERE table_schema ='suggestions';");
			
			/*while(res.next()) {
				System.out.println(res.getString("table_name"));
			}*/
			
		}
		
		catch(Exception exc){
			exc.printStackTrace();
		}
		
		/*finally {
			if(stat != null) {
				stat.close();
			}
			if(conn != null) {
				conn.close();
			}
			
		}*/
		return conn;
	}
	
	public Connection getConnection() {
		return conn;
	}
	
	public Statement newStatement(String query) throws SQLException {
		stat = conn.createStatement();
		//stat.executeQuery(query);
		int nr = stat.executeUpdate(query);
		System.out.println("Number of rows effected : "+ nr);
		return stat;
	}
	
}
