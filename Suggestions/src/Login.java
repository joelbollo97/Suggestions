import java.util.Scanner;
import java.sql.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class Login {
	Scanner in = new Scanner(System.in);
	DatabaseManager db = new DatabaseManager();
	
	
	public Admin LoginOrRegisterAdmin(ArrayList<Admin> array) throws SQLException {
		db.connect();
		Connection conn = db.getConnection();
		System.out.println("-----CHOOSE SIGN IN METHOD-----");
		System.out.println("[1] Login");
		System.out.println("[2] Register");
		Admin current = new Admin();
		
		String sign = in.nextLine();
		String name;
		String password;
		String sql = new String();
		
		
		switch(sign) {
		case "1":
			boolean flag = false;
			while(flag == false) {
				System.out.print("Username: ");
				name = in.nextLine();
				sql = "select count(*) as total from admin where admin_name = '"+name+"'";
				Statement stat = conn.createStatement();
				
				ResultSet rs = stat.executeQuery(sql);
				int total = 0;
				
				while(rs.next()) {
					total = rs.getInt("total");
				}
				
				if(total==1) {
					System.out.print("Password: ");
					password = in.nextLine();
					sql = "select password from admin where admin_name = '"+name+"'";
					rs = stat.executeQuery(sql);
					String pass = new String(); ;
					while(rs.next()) {
						pass = rs.getString("password");
						System.out.println(pass);
					}
					
					while(!pass.equals(password)) {
						System.out.println("WRONG_PASSWORD! Re-enter password: ");
						password = in.nextLine();
					}
					
					Admin admin = new Admin();
					admin.setUsername(name);
					admin.setPassword(pass);
					current = admin;
					flag = true;
				}
				
				else System.out.println("NO_ADMIN_FOUND");
			}
			
			
			break;
			
			/*System.out.print("Username: ");
			boolean found = false;
			name = in.nextLine();
			for(int i=0; i<array.size(); i++) {
				if(!name.equals(array.get(i).getUsername())){
						found = false;
					}
				else {
					found = true;
					System.out.print("Password: ");
					password = in.nextLine();
					while(!array.get(i).getPassword().equals(password)) {
						System.out.println("WRONG_PASSWORD! Re-enter password:");
						password = in.nextLine();
					}
					current = array.get(i);
					break;
				}
			}
			
			if(found==false) {
				System.out.println("USERNAME_NOT_FOUND!");
			}
			
			break;*/
			
		case "2":
			Admin admin = new Admin();
			boolean notfound = false;
			while(notfound==false) {
				System.out.print("Username: ");
				name = in.nextLine();
				sql = "select count(*) as total1 from admin where admin_name = '"+name+"'";
				Statement stat1 = conn.createStatement();
				ResultSet rs1 = stat1.executeQuery(sql);
				int total1 = 0;
				while(rs1.next()) {
					total1 = rs1.getInt("total1");
				}
				
				if(total1>0) {
					System.out.println("USERNAME_EXISTS! Re-enter username: ");
				}
				else {
					notfound = true;
					System.out.print("Password: ");
					password = in.nextLine();
					admin.setUsername(name);
					admin.setPassword(password);
					current = admin;
					sql = "insert into admin " + " (admin_name, password)"
		                    + " values ('"+name+"', '"+password+"')";
					array.add(admin);
					db.newStatement(sql);
					System.out.println(admin.getUsername()+ " added!");
				}
			}
			break;
			
			/*Admin user = new Admin();
			boolean exists = false;
			System.out.print("Username: ");
			name = in.nextLine();
			for(int i=0; i<array.size(); i++) {
				if(name.equals(array.get(i).getUsername()))	{
					System.out.println("USERNAME_EXISTS! Re-enter username: ");
					name = in.nextLine();
					i=-1;
				}
			}
			
			if(!exists) {
				System.out.print("Password: ");
				password = in.nextLine();
				user.setUsername(name);
				user.setPassword(password);
				array.add(user);
				System.out.println(user.getUsername()+ " added!");
				current = user;
			}
			else	{
				System.out.println("LOGIN_FAILED");
				break;
			}
			
			break;*/
		}
		return current;
	}
	
	
	public User loginOrRegisterUser(ArrayList<User> array) throws SQLException {
		db.connect();
		Connection conn = db.getConnection();
		System.out.println("-----CHOOSE SIGN IN METHOD-----");
		System.out.println("[1] Login");
		System.out.println("[2] Register");

		User current = new User();
		
		Scanner in = new Scanner(System.in);
		String sign = in.nextLine();
		String name;
		String password;
		String sql = new String();
		
		switch(sign) {
		case "1":
			boolean flag = false;
			while(flag == false) {
				System.out.print("Username: ");
				name = in.nextLine();
				sql = "select count(*) as total from user where user_name = '"+name+"'";
				Statement stat = conn.createStatement();
				
				ResultSet rs = stat.executeQuery(sql);
				int total = 0;
				
				while(rs.next()) {
					total = rs.getInt("total");
				}
				
				if(total==1) {
					System.out.print("Password: ");
					password = in.nextLine();
					sql = "select password from user where user_name = '"+name+"'";
					rs = stat.executeQuery(sql);
					String pass = new String();
					while(rs.next()) {
						pass = rs.getString("password");
					}
					
					while(!pass.equals(password)) {
						System.out.println("WRONG_PASSWORD! Re-enter password: ");
						password = in.nextLine();
					}
					User user = new User();
					user.setUsername(name);
					user.setPassword(pass);
					current = user;
					flag = true;
				}
				
				else System.out.println("NO_USER_FOUND");
			}
			
			
			break;
			/*for(int i=0; i<array.size(); i++) {
				if(!name.equals(array.get(i).getUsername())){
						found = false;
					}
				else {
					found = true;
					System.out.print("Password: ");
					password = in.nextLine();
					while(!array.get(i).getPassword().equals(password)) {
						System.out.println("WRONG_PASSWORD! Re-enter password: ");
						password = in.nextLine();
					}
					current = array.get(i);
					break;
				}
			}
			
			if(found==false) {
				System.out.println("USERNAME_NOT_FOUND!");
			}*/
			
			
			
			
		case "2":
			User user = new User();
			boolean notfound = false;
			while(notfound==false) {
				System.out.print("Username: ");
				name = in.nextLine();
				sql = "select count(*) as total1 from user where user_name = '"+name+"'";
				Statement stat1 = conn.createStatement();
				
				ResultSet rs1 = stat1.executeQuery(sql);
				int total1 = 0;
				while(rs1.next()) {
					total1 = rs1.getInt("total1");
				}
				
				if(total1>0) {
					System.out.println("USERNAME_EXISTS! Re-enter username: ");
					name = in.nextLine();
				}
				else {
					notfound = true;
					System.out.print("Password: ");
					password = in.nextLine();
					user.setUsername(name);
					user.setPassword(password);
					current = user;
					array.add(user);
					sql = "insert into user " + " (user_name, password)"
		                    + " values ('"+name+"', '"+password+"')";
					db.newStatement(sql);
					System.out.println(user.getUsername()+ " added!");
				}
			}
			break;
			/*boolean exists = false;
			for(int i=0; i<array.size(); i++) {
				if(name.equals(array.get(i).getUsername()))	{
					System.out.println("USERNAME_EXISTS! Re-enter username: ");
					name = in.nextLine();
					i=-1;
				}
			}
			
			if(exists == false) {
				System.out.print("Password: ");
				password = in.nextLine();
				user.setUsername(name);
				user.setPassword(password);
				array.add(user);
				System.out.println(user.getUsername()+ " added!");
				current = user;
			}
			else	{
				System.out.println("LOGIN_FAILED");
				break;
			}*/
		}
		return current;
	}
	
	public void closeScanner() {
		in.close();
	}
}
